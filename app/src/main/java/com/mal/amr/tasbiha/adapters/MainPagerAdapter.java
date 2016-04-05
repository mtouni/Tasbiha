package com.mal.amr.tasbiha.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mal.amr.tasbiha.fragments.Ehsaa;
import com.mal.amr.tasbiha.fragments.Tasbih;

/**
 * Created by Amr on 4/5/2016.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    int num;

    public MainPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tasbih();
            case 1:
                return new Ehsaa();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
