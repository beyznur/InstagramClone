package com.example.instagram_clone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.instagram_clone.R;
import com.example.instagram_clone.User;
import com.example.instagram_clone.UserAdapter;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> mUser;

    EditText searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBar = view.findViewById(R.id.search_bar);

        mUser= new ArrayList<>();
        userAdapter = new UserAdapter(getContext(),mUser);
        recyclerView.setAdapter(userAdapter);
        return view;
    }

    private void searchUsers(String s)
    {

    }
}