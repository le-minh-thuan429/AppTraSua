package com.example.apptrasua.Order;

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
                return new Tap1Fragment();
            case 1:
                return new Tap2Fragment();
            case 2:
                return new Tap3Fragment();
            default:
                return new Tap1Fragment();
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
                return  "Tab1";
            case 1:
                return  "Tab2";
            case 2:
                return "Tab3";
            default:
                return  "Tab1";
        }
    }
    

}
