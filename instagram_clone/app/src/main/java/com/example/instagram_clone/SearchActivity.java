package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBottomBar=findViewById(R.id.bottomNavViewBar);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this,this,mBottomBar);
    }
}