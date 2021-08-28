package org.techtown.catsby.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.techtown.catsby.R;

public class BowlActivity extends AppCompatActivity {
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowl);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        FragmentBowlInfo fragmentBowlInfo = new FragmentBowlInfo();
        Bundle bundle = new Bundle(1);
        bundle.putString("name", name);
        bundle.putString("name", name);
        fragmentBowlInfo.setArguments(bundle);

        //뷰페이저 세팅
        ViewPager viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new BowlViewPagerAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

        //Intent intent = getIntent();
        //String name = intent.getStringExtra("name");
        //String info = intent.getStringExtra("info");
        //System.out.println("name = " + name);

        /*
        try{

            Bundle bundle  = new Bundle();
            bundle.putString("name", name);
            FragmentBowlInfo fragmentBowlInfo = new FragmentBowlInfo();
            fragmentBowlInfo.setArguments(bundle);
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.bowl_information, fragmentBowlInfo);
            fts.addToBackStack(fragmentBowlInfo.getClass().getSimpleName());
            fts.commit();

        } catch (Exception e){

            System.out.println("e.getMessage() = " + e.getMessage());

        }*/


        //Intent bowlInfoIntent = new Intent(this, FragmentBowlInfo.class);

        //intent.putExtra("name", intent.getStringExtra("name"));
        //intent.putExtra("address", intent.getStringExtra("info"));
        //intent.putExtra("info", item.getInfo());
        //intent.putExtra("time", item.getLocalDateTime());

        //FragmentBowlInfo fragmentBowlInfo = new FragmentBowlInfo();
        //Bundle bundle = new Bundle(1);
        //bundle.putString("name", name);
        //fragmentBowlInfo.setArguments(bundle);

        //뷰페이저 세팅

        //ViewPager viewPager = findViewById(R.id.viewPager);
        //fragmentPagerAdapter = new BowlViewPagerAdapter(getSupportFragmentManager());

        //TabLayout tabLayout = findViewById(R.id.bowl_map);
        //viewPager.setAdapter(fragmentPagerAdapter);
        //tabLayout.setupWithViewPager(viewPager);
        
        //this.startActivity(bowlInfoIntent);
    //}
//}