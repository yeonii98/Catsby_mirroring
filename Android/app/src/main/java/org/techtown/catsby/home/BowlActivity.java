package org.techtown.catsby.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlViewPagerAdapter;

import java.util.ArrayList;

public class BowlActivity extends AppCompatActivity {
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowl);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");

        //뷰페이저 세팅
        ViewPager viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new BowlViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle(1);
        bundle.putLong("bowlId", id);
        bundle.putString("name", name);
        bundle.putString("address", address);
        fragmentPagerAdapter.getItem(0).setArguments(bundle);
        fragmentPagerAdapter.getItem(1).setArguments(bundle);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}