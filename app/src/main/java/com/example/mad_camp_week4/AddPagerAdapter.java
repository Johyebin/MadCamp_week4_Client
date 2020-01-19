package com.example.mad_camp_week4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddPagerAdapter extends FragmentPagerAdapter {

    public AddPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentTwosome tab1 = new FragmentTwosome();
                return tab1;
            case 1:
                FragmentStarbucks tab2 = new FragmentStarbucks();
                return tab2;
            case 2:
                FragmentEtc tab3 = new FragmentEtc();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
