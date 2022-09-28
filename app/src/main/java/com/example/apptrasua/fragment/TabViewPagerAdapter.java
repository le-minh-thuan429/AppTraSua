package com.example.apptrasua.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    FragmentTransaction transaction;
    public TabViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new XacNhanFragment();
            case 1:
                return new DaGiaoFragment();
            case 2:
                return new DaHuyFragment();
            default:
                return new XacNhanFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return  "Xác nhận";
            case 1:
                return  "Đã giao";
            case 2:
                return   "Đã hủy";
            default:
                return  "Xác nhận";
        }
    }
}
