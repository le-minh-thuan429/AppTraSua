package com.example.apptrasua.CaNhan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apptrasua.R;
import com.example.apptrasua.fragment.TabViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TrangThaiDonHang extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_thai_don_hang);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.ViewPager);
        TabViewPagerAdapter orderViewPagerAdapter=new TabViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(orderViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}