package com.dailysaver.shadowhite.dailysaver.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dailysaver.shadowhite.dailysaver.fragments.addnewexpense.Fragment_Expense_Dashboard;
import com.dailysaver.shadowhite.dailysaver.fragments.addnewexpense.Fragment_Daily_Expenses;

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
                Fragment_Daily_Expenses tab1 = new Fragment_Daily_Expenses();
                return tab1;
            case 1:
                Fragment_Expense_Dashboard tab2 = new Fragment_Expense_Dashboard();
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