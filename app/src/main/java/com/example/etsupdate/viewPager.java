package com.example.etsupdate;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPager extends FragmentPagerAdapter {
    public viewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new NewBooking();
                break;
            case 1:
                fragment = new PendingActivity();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
