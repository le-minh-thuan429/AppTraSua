package com.example.apptrasua;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apptrasua.fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager=findViewById(R.id.view_pager);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.Trangchu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.giohang).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.canhan).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.Trangchu:
                        viewPager.setCurrentItem(0);
                        setContentView(R.layout.activity_main);
                        Update(0);
                        break;
                    case R.id.giohang:
                        viewPager.setCurrentItem(1);
                        setContentView(R.layout.activity_main);
                        Update(1);
                        break;
                    case R.id.canhan:
                        viewPager.setCurrentItem(2);
                        setContentView(R.layout.activity_main);
                        Update(2);
                        break;
                }
                return true;
            }
        });

    }

    public void Update(int a){
        ViewPager viewPager=findViewById(R.id.view_pager);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.Trangchu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.giohang).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.canhan).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        switch (a){
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.Trangchu:
                        viewPager.setCurrentItem(0);
                        setContentView(R.layout.activity_main);
                        Update(0);

                        break;
                    case R.id.giohang:
                        viewPager.setCurrentItem(1);
                        setContentView(R.layout.activity_main);
                        Update(1);

                        break;
                    case R.id.canhan:
                        viewPager.setCurrentItem(2);
                        setContentView(R.layout.activity_main);
                        Update(2);

                        break;
                }
                return true;
            }
        });
    }

}               