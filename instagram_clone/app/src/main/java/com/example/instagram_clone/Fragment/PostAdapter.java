package com.example.instagram_clone.Fragment;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagram_clone.Post;
import com.example.instagram_clone.R;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<Post> postList;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;


    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post currentPost = postList.get(position);

        holder.post_description.setText(currentPost.getDescription());
        holder.post_username2.setText(currentPost.getUserId()+ " ");
        holder.post_username.setText(currentPost.getUserId());
        System.out.println(currentPost.getPhotoUrl());

        // Fotoğrafı Glide kütüphanesi ile yükleme
        Glide.with(context)
                .load(currentPost.getPhotoUrl())
                .apply(new RequestOptions())
                .into(holder.post_image);

        String postId = currentPost.getPostId();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Beğeni durumunu kontrol et
        DocumentReference likesRef = FirebaseFirestore.getInstance()
                .collection("posts")
                .document(postId)
                .collection("likes")
                .document(currentUserId);

        likesRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Kullanıcı postu beğenmiş
                holder.button_like.setImageResource(R.drawable.baseline_favorite_24);
            } else {
                // Kullanıcı postu beğenmemiş
                holder.button_like.setImageResource(R.drawable.favorite);
            }
        }).addOnFailureListener(e -> {
            // Beğeni durumu sorgulanırken bir hata oluştuğunda yapılacak işlemler
        });

        // Beğen butonuna tıklama olayı
        holder.button_like.setOnClickListener(view -> {
            likesRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Kullanıcı postu beğenmiş, beğeniyi kaldır
                    likesRef.delete().addOnSuccessListener(aVoid -> {
                        holder.button_like.setImageResource(R.drawable.favorite);
                    }).addOnFailureListener(e -> {
                        // Beğeni kaldırılırken bir hata oluştuğunda yapılacak işlemler
                    });
                } else {
                    // Kullanıcı postu beğenmemiş, beğeni ekle
                    likesRef.set(new HashMap<>()).addOnSuccessListener(aVoid -> {
                        holder.button_like.setImageResource(R.drawable.baseline_favorite_24);
                    }).addOnFailureListener(e -> {
                        // Beğeni eklenirken bir hata oluştuğunda yapılacak işlemler
                    });
                }
            }).addOnFailureListener(e -> {
                // Beğeni durumu sorgulanırken bir hata oluştuğunda yapılacak işlemler
            });
        });

        // Fotoğrafa çift tıklama olayı
        holder.post_image.setOnClickListener(view -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - holder.lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // İki kez tıklanma durumu
                likesRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Kullanıcı postu beğenmiş, beğeniyi kaldır
                        likesRef.delete().addOnSuccessListener(aVoid -> {
                            holder.button_like.setImageResource(R.drawable.favorite);
                        }).addOnFailureListener(e -> {
                            // Beğeni kaldırılırken bir hata oluştuğunda yapılacak işlemler
                        });
                    } else {
                        // Kullanıcı postu beğenmemiş, beğeni ekle
                        likesRef.set(new HashMap<>()).addOnSuccessListener(aVoid -> {
                            holder.button_like.setImageResource(R.drawable.baseline_favorite_24);
                        }).addOnFailureListener(e -> {
                            // Beğeni eklenirken bir hata oluştuğunda yapılacak işlemler
                        });
                    }
                }).addOnFailureListener(e -> {
                    // Beğeni durumu sorgulanırken bir hata oluştuğunda yapılacak işlemler
                });
            }
            holder.lastClickTime = clickTime;
        });

        // Diğer olay dinleyicileri ve işlemler...
    }



    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView post_image;
         TextView post_description;
         TextView post_username;
         ImageButton button_like;
         TextView post_username2;
        long lastClickTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image=itemView.findViewById(R.id.post_image);
            post_description=itemView.findViewById(R.id.post_description);
            post_username= itemView.findViewById(R.id.post_user_name);
            post_username2=itemView.findViewById(R.id.post_username2);
            button_like=itemView.findViewById(R.id.button_like);
            lastClickTime = 0;

        }

    }
}

