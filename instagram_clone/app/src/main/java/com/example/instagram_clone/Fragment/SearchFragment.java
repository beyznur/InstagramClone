package com.example.instagram_clone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagram_clone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        readUsers();

        userAdapter = new UserAdapter(getContext(),mUser);
        recyclerView.setAdapter(userAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void searchUsers(String s) {

        Query query = FirebaseFirestore.getInstance().collection("users")
                .whereGreaterThanOrEqualTo("UserName",s)
                .whereLessThanOrEqualTo("UserName", s+"\uf8ff");
       query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mUser.clear();
               if (task.isSuccessful()) {

                   for (QueryDocumentSnapshot document : task.getResult()) {
                       String bio = (String) document.getData().get("Bio");
                       String imageUrl = (String) document.getData().get("ImageUrl");
                       String name = (String) document.getData().get("Name");
                       String id = (String) document.getData().get("id");
                       String username = (String) document.getData().get("UserName");
                       User user =new User(id,username,name,bio,imageUrl);
                       mUser.add(user);

                   }
                   userAdapter.notifyDataSetChanged();
               }
           }
       });
    }

    private void readUsers() {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("users")
                ;
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String bio = (String) document.getData().get("Bio");
                            String imageUrl = (String) document.getData().get("ImageUrl");
                            String name = (String) document.getData().get("Name");
                            String id = (String) document.getData().get("id");
                            String username = (String) document.getData().get("UserName");

                            User user =new User(id,username,name,bio,imageUrl);
                            mUser.add(user);

                        }
                    userAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}