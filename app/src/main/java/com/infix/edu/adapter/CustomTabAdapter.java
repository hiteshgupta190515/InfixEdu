package com.infix.edu.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.infix.edu.fragment.ApprovedFragment;
import com.infix.edu.fragment.OthersFragment;
import com.infix.edu.fragment.PendingFragment;
import com.infix.edu.fragment.PersonalFragment;
import com.infix.edu.fragment.RejectedFragment;
import com.infix.edu.fragment.TransportFragment;

public class CustomTabAdapter extends FragmentPagerAdapter {

    Context myContext;
    int totalTabs;

    public CustomTabAdapter(FragmentManager fm , Context myContext, int totalTabs) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PendingFragment homeFragment = new PendingFragment();
                return homeFragment;
            case 1:
                ApprovedFragment approvedFragment = new ApprovedFragment();
                return approvedFragment;
            case 2:
                RejectedFragment rejectedFragment = new RejectedFragment();
                return rejectedFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
