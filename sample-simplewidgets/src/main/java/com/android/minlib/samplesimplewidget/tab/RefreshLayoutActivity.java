package com.android.minlib.samplesimplewidget.tab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import com.android.minlib.samplesimplewidget.R;
import com.android.minlib.simplewidget.tabviewpager.TabViewPagerManager;
import java.util.ArrayList;
import java.util.List;
import static android.support.design.widget.TabLayout.MODE_FIXED;

public class RefreshLayoutActivity extends AppCompatActivity{

    List<Fragment> tabFragmentList = new ArrayList<>();
    TabViewPagerManager tabViewPagerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        Fragment home = getSavedInstanceFragment(savedInstanceState,OneFragment.class);
        Fragment widget = getSavedInstanceFragment(savedInstanceState,TwoFragment.class);
        Fragment me = getSavedInstanceFragment(savedInstanceState,ThreeFragment.class);
        tabFragmentList.add(home);
        tabFragmentList.add(widget);
        tabFragmentList.add(me);
        tabViewPagerManager = findViewById(R.id.activity_tagviewpager);
        tabViewPagerManager
                .createTab("ONE",R.drawable.icon_home)
                .createTab("TWO",R.drawable.icon_widget)
                .createTab("THREE",R.drawable.icon_mine)
                .build(tabFragmentList,getSupportFragmentManager());
    }

    public <T extends Fragment> T getSavedInstanceFragment(Bundle savedInstanceState,Class<T> mClass) {
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
