package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    ImageView profile_add,profile_line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBottomBar=findViewById(R.id.bottomNavViewBar);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this,this,mBottomBar);

        Toolbar toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        profile_add=findViewById(R.id.profile_add);
        profile_line=findViewById(R.id.profile_line);

        profile_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profil add basildi");
            }
        });

        profile_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile line basildi");
            }
        });




    }
}