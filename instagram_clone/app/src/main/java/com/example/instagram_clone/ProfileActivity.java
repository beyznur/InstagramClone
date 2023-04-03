package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    ImageView profile_menu;
    ProgressBar progressBar;

    TextView editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBottomBar=findViewById(R.id.bottomNavViewBar);
        editProfile=findViewById(R.id.edit_profile);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this,this,mBottomBar);

        Toolbar toolbar=findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        progressBar=findViewById(R.id.profile_progressbar);
        progressBar.setVisibility(View.GONE);
        profile_menu=findViewById(R.id.profile_line);
        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,AccountSetttingsActivity.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });





    }


}