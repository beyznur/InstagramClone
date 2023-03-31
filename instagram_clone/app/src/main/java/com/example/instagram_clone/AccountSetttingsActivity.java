package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AccountSetttingsActivity extends AppCompatActivity {

    private Context mContext;
    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setttings);
        mContext=getApplicationContext();
        mViewPager=findViewById(R.id.container);
        mRelativeLayout =findViewById(R.id.relativeLayout1);
        setupSettingsList();
        setupFragments();

        ImageView backArrow= findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupFragments(){
        pagerAdapter= new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(),getString(R.string.edit_profile_fragment));
        pagerAdapter.addFragment(new SignOutFragment() , getString(R.string.sign_out_fragment));

    }

    private void setmViewPager(int fragmentNumber){
        mRelativeLayout.setVisibility(View.GONE);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);

    }

    private void setupSettingsList(){
        ListView listView= findViewById(R.id.listViewAccountSettings);

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment)); //0
        options.add(getString(R.string.sign_out_fragment)); //1

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1,options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setmViewPager(position);
            }
        });
    }
}