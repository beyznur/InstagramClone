package com.example.instagram_clone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private TextView followingCountTextView;
    private TextView followersCountTextView;
    private CollectionReference followingRef,followersRef,postsRef;
    private ArrayList<Post> postArrayList;

    private GridLayoutManager gridLayoutManager;

    private ArrayList<Post> postUserArrayList;
    private TextView postCountTextView;
    private String userID;
    private String username;
    private RecyclerView profile_reyclerview;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        followersCountTextView=view.findViewById(R.id.followers);
        followingCountTextView=view.findViewById(R.id.following);
        postCountTextView=view.findViewById(R.id.posts_number);
        profile_reyclerview=view.findViewById(R.id.profile_recycler);
        db=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        postArrayList=new ArrayList<>();
        postUserArrayList=new ArrayList<>();
        userID=  firebaseAuth.getCurrentUser().getUid();

        int spanCount = 3; // İstenilen sütun sayısı
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        profile_reyclerview.setLayoutManager(gridLayoutManager);

        int columnWidth = getResources().getDisplayMetrics().widthPixels / spanCount;
        gridLayoutManager.setSpanCount(spanCount);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // İstenilen yönde sıralama yapabilirsiniz
       // gridLayoutManager.setColumnWidth(columnWidth);

        ProfileAdapter profileAdapter =new ProfileAdapter(getContext(),postUserArrayList);
        profile_reyclerview.setAdapter(profileAdapter);
        followingNumber();
        followersNumber();
        postNumber();
        posts();

        return view;
    }

    private void followingNumber(){
        // Kullanıcının "following" sayısını almak için sorgu yapma
        followingRef = db.collection("Follow")
                .document(userID).collection("following");

        followingRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int followingCount = queryDocumentSnapshots.size();
            followingCountTextView.setText(String.valueOf(followingCount));

        });
    }

    private void followersNumber(){
        followersRef = db.collection("Follow")
                .document(userID).collection("followers");

        followersRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            int followerCount = queryDocumentSnapshots.size();
            followersCountTextView.setText(String.valueOf(followerCount));

        });
    }

    private void postNumber(){
        postsRef = db.collection("posts");
        Query query = postsRef.whereEqualTo("userId", userID);
        query.get().addOnSuccessListener(querySnapshot -> {
            int postCount = querySnapshot.size();
            postCountTextView.setText(String.valueOf(postCount));

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                String postId = document.getId();
                String photoUrl = document.getString("photoUrl");
                String description = document.getString("description");
                Timestamp timestamp = document.getTimestamp("timestamp");
                String timestampString = timestamp.toDate().toString();
                db.collection("users").document(userID).get()

                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String username = documentSnapshot.getString("UserName");
                                Post post = new Post(postId, username, photoUrl, description, timestampString);
                                postArrayList.add(post);
                            }
                        });

            }

        });
    }

    private  void posts(){

        //Kullanici adi öğrenildi.
        DocumentReference userRef = db.collection("users").document(userID);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                username = documentSnapshot.getString("UserName");

            } else {
                // Belirtilen kullanıcı ID'sine sahip bir kullanıcı bulunamadı

            }
        });

        Query query = db.collection("posts")
                .whereEqualTo("userId", userID);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (DocumentSnapshot postDocument : queryDocumentSnapshots.getDocuments()) {
                String postId = postDocument.getId();
                String photoUrl = postDocument.getString("photoUrl");
                String description = postDocument.getString("description");

                Timestamp timestamp = postDocument.getTimestamp("timestamp");
                String timestampString = timestamp.toDate().toString();

                Post post = new Post(postId,username, photoUrl, description,timestampString);
                postUserArrayList.add(post);
            }

           // postAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Hata durumunda yapılacak işlemler
        });



    }



}