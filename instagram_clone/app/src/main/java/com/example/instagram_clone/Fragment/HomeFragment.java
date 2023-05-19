package com.example.instagram_clone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram_clone.Post;
import com.example.instagram_clone.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView home_recycler;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private FirebaseFirestore db;
    private CollectionReference postsRef;
    private DocumentReference followingDocRef;

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

        return view;
    }
}