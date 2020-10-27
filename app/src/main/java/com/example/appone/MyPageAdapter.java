package com.example.appone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {

    public MyPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFramgment();
        }else if(position==1){
            return new SecondFramgment();
        }else{
            return new ThirdFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
