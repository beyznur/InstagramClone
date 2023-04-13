package com.example.instagram_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.instagram_clone.Fragment.HomeFragment;
import com.example.instagram_clone.Fragment.ProfileFragment;
import com.example.instagram_clone.Fragment.ReelsFragment;
import com.example.instagram_clone.Fragment.SearchFragment;
import com.example.instagram_clone.Fragment.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomBar;
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBottomBar=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment()).commit();


        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.ic_search:
                       selectedFragment = new SearchFragment();
                        break;

                    case R.id.ic_add:
                        selectedFragment = null;
                        Intent intent=new Intent(MainActivity.this,ShareActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.ic_video:
                        selectedFragment = new ReelsFragment();
                        break;

                    case R.id.ic_circle:
                        SharedPreferences.Editor editor= getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("profileId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();
                        selectedFragment = new ProfileFragment();
                        break;

                }

                if(selectedFragment !=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                }
               return true;
            }
        });

    }

}