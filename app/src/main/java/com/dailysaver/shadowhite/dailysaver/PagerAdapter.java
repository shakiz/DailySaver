package com.dailysaver.shadowhite.dailysaver;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    //for getting the number of tabs available
    int mNumOfTabs;

    //creating the constructor for passing the fargment and the fragment position
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Generate_Savings_Fragment tab1 = new Generate_Savings_Fragment();
                return tab1;
            case 1:
                Dashboard_fragment tab2 = new Dashboard_fragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}