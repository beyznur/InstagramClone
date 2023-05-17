package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images");
        Uri file = Uri.fromFile(new File("path/to/image"));
        StorageReference imageRef = imagesRef.child(file.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(file);

    }
}