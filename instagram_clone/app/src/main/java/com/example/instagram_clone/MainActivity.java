package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBottomBar=findViewById(R.id.bottomNavViewBar);
        Menu menu = mBottomBar.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.enableNavigation(MainActivity.this,this,mBottomBar);


    }
}