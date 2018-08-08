package com.android.minlib.samplesimplewidget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.minlib.samplesimplewidget.tab.OneFragment;
import com.android.minlib.samplesimplewidget.tab.ThreeFragment;
import com.android.minlib.samplesimplewidget.tab.TwoFragment;
import com.android.minlib.simplewidget.tab.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabPageIndicatorActivity extends AppCompatActivity{

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> tabFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        Fragment home = getSavedInstanceFragment(savedInstanceState,OneFragment.class);
        Fragment widget = getSavedInstanceFragment(savedInstanceState,TwoFragment.class);
        Fragment me = getSavedInstanceFragment(savedInstanceState,ThreeFragment.class);
        tabFragmentList.add(home);
        tabFragmentList.add(widget);
        tabFragmentList.add(me);

        viewPager.setAdapter(new GoogleMusicAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "This "+position;
        }

        @Override
        public int getCount() {
            return tabFragmentList.size();
        }
    }

    public <T extends Fragment> T getSavedInstanceFragment(Bundle savedInstanceState, Class<T> mClass) {
        Fragment mFragment = null;
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, mClass.getName());
        }
        if (mFragment == null) {
            try {
                mFragment = mClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            mFragment.onAttach((Context) this);
        }
        return (T) mFragment;
    }
}
