package com.ksatukeltiga.ifttw.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ksatukeltiga.ifttw.fragment.FirstFragment;
import com.ksatukeltiga.ifttw.fragment.SecondFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public TabsAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
