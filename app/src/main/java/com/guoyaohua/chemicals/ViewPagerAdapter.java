package com.guoyaohua.chemicals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John Kwok on 2016/7/14.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private MainActivity mainActivity;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


//    public ViewPagerAdapter(FragmentManager fragmentManager,
//                            ArrayList<Fragment> fragments) {
//        super(fragmentManager);
//        this.fragments = fragments;
//    }

    public ViewPagerAdapter(MainActivity mainActivity, FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int index) {
        return fragments.get(index);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
