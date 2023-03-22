package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReelsActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reels);

        mBottomBar=findViewById(R.id.bottomNavViewBar);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(3);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.enableNavigation(ReelsActivity.this,this,mBottomBar);
    }
}