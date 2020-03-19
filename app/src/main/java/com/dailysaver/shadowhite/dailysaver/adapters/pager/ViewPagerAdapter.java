package com.dailysaver.shadowhite.dailysaver.adapters.pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.dailysaver.shadowhite.dailysaver.fragments.ExpenseFilterableFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[];

    public ViewPagerAdapter(@NonNull FragmentManager fm, String[] tabTitles) {
        super(fm);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ExpenseFilterableFragment filterableFragment =new ExpenseFilterableFragment();
        return filterableFragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return tabTitles[0];
        else if (position == 1) return tabTitles[1];
        else return tabTitles[2];
    }
}
