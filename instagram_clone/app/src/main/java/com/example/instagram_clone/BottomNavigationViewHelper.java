package com.example.instagram_clone;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewHelper {

    public static void enableNavigation(Context context , Activity callingActivity, BottomNavigationView view ) {

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1,ActivityOptions.makeSceneTransitionAnimation(callingActivity).toBundle());
                        return true;

                    case R.id.ic_search:
                        Intent intent2 = new Intent(context, SearchActivity.class);
                        context.startActivity(intent2,ActivityOptions.makeSceneTransitionAnimation(callingActivity).toBundle());
                        return true;

                    case R.id.ic_add:
                        Intent intent3 = new Intent(context, ShareActivity.class);
                        context.startActivity(intent3,ActivityOptions.makeSceneTransitionAnimation(callingActivity).toBundle());
                        return true;

                    case R.id.ic_video:
                        Intent intent4 = new Intent(context, ReelsActivity.class);
                        context.startActivity(intent4,ActivityOptions.makeSceneTransitionAnimation(callingActivity).toBundle());
                        return true;

                    case R.id.ic_circle:
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5,ActivityOptions.makeSceneTransitionAnimation(callingActivity).toBundle());
                        return true;
                }

                return false;
            }
        });
    }

}
