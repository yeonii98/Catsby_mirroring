package org.techtown.catsby.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.techtown.catsby.home.FragmentBowlInfo;
import org.techtown.catsby.home.FragmentBowlMap;

import java.util.ArrayList;

public class BowlViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragment = new ArrayList<>();

    public BowlViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragment.add(new FragmentBowlMap());
        fragment.add(new FragmentBowlInfo());
    }

    //프래그먼트 교체를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    //상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "지도";
            case 1:
                return "밥그릇 정보";
            default:
                return null;
        }
    }
}
