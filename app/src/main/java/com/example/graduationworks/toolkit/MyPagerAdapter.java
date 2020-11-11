package com.example.graduationworks.toolkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] title={"1","2","3"};
    ArrayList<Fragment> fragments;
    public MyPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}
