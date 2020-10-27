package com.example.appone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ViewPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
    MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pageAdapter);
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
    tabLayout.setupWithViewPager(viewPager);
}
    public CharSequence getPageTitle(int position) {
        return "Title" + position;
    }

}
