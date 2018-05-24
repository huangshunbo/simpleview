package com.android.minlib.samplesimplewidget.vertical;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.android.minlib.samplesimplewidget.R;
import com.android.minlib.simplewidget.verticalpager.VerticalPagerAdapter;
import com.android.minlib.simplewidget.verticalpager.VerticalViewPager;

import javax.crypto.spec.PSource;

public class VerticalActivity extends AppCompatActivity {

    VerticalViewPager verticalViewPager;
    OneFragment oneFragment;
    TwoFragment twoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verticalViewPager = new VerticalViewPager(this);
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(R.color.color_orange)));
        verticalViewPager.setAdapter(mPagerAdapter);
        verticalViewPager.setOnPageChangeListener(mPageChangeListener);

        setContentView(verticalViewPager);
        oneFragment = new OneFragment(this);
        twoFragment = new TwoFragment(this);
    }

    VerticalPagerAdapter mPagerAdapter = new VerticalPagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(position == 0 ? oneFragment : twoFragment);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(position == 0 ? oneFragment : twoFragment);
            return position == 0 ? oneFragment : twoFragment;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

    VerticalViewPager.OnPageChangeListener mPageChangeListener = new VerticalViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                oneFragment.setMoreText("向上滑动，查看更多详情");
                verticalViewPager.setDeviation(0);
            } else {
                oneFragment.setMoreText("向下滑动，查看项目");
                verticalViewPager.setDeviation(0);
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
