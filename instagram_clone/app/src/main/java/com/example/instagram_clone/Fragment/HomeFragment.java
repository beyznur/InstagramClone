package com.example.instagram_clone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagram_clone.Post;
import com.example.instagram_clone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView home_recycler;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private FirebaseFirestore db;
    private CollectionReference postsRef;
    private CollectionReference followingRef;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        home_recycler= view.findViewById(R.id.home_recycler);
        home_recycler.setHasFixedSize(true);

        home_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        home_recycler.setAdapter(postAdapter);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference followingRef = db.collection("Follow").document(userId).collection("following");

        followingRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> followingDocuments = queryDocumentSnapshots.getDocuments();

            for (DocumentSnapshot document : followingDocuments) {
                String followedUserId = document.getId();

                CollectionReference postsRef = db.collection("posts");

                postsRef.whereEqualTo("userId", followedUserId).get().addOnSuccessListener(querySnapshot -> {
                    List<DocumentSnapshot> postDocuments = querySnapshot.getDocuments();

                    for (DocumentSnapshot postDocument : postDocuments) {
                        String postId = postDocument.getId();
                        String photoUrl = postDocument.getString("photoUrl");
                        String description = postDocument.getString("description");
                        String post_user= postDocument.getString("userId");
                        Timestamp timestamp = postDocument.getTimestamp("timestamp");
                        String timestampString = timestamp.toDate().toString();

                        db.collection("users").document(post_user).get()

                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                                        String username = documentSnapshot.getString("UserName");
                                        System.out.println(username);

                                        Post post = new Post(postId, username, photoUrl, description, timestampString);
                                        postList.add(post);
                                        postAdapter.notifyDataSetChanged();
                                    }
                                });
                    }

                }).addOnFailureListener(e -> {
                    // Postları alırken bir hata oluştuğunda yapılacak işlemler
                    Toast.makeText(getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Takip edilen kullanıcıları alırken bir hata oluştu", Toast.LENGTH_SHORT).show();
            // Takip edilen kullanıcıları alırken bir hata oluştuğunda yapılacak işlemler
        });
        return view;
    }

}

