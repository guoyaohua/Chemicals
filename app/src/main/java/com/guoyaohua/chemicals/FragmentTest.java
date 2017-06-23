package com.guoyaohua.chemicals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.guoyaohua.chemicals.fragment.FeatureFragment;
import com.guoyaohua.chemicals.fragment.InformationFrgment;

import java.util.ArrayList;

/**
 * Created by John Kwok on 2016/9/8.
 */
public class FragmentTest extends AppCompatActivity {
    ViewPager pager = null;
    ArrayList<Fragment> fragments = null;
    PagerAdapter adapter = null;
    int curPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_detail_activity_layout);
        pager = (ViewPager) findViewById(R.id.viewpager_chemical_detail);
        initFragment();
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        pager.setAdapter(fragmentPagerAdapter);
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new InformationFrgment());
        fragments.add(new FeatureFragment());
    }


}
