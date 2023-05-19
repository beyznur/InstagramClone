package com.example.instagram_clone.Fragment;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram_clone.Post;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Post> postArrayList;

    public ProfileAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
