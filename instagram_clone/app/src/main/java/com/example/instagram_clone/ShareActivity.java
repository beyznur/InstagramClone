package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShareActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        mBottomBar=findViewById(R.id.bottomNavViewBar);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);

        BottomNavigationViewHelper.enableNavigation(ShareActivity.this,this,mBottomBar);
    }
}