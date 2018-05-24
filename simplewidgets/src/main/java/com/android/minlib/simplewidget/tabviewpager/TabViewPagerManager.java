package com.android.minlib.simplewidget.tabviewpager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.minlib.simplewidget.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangshunbo
 * @Filename: TabViewPagerManager
 * @Description: 底部导航 + ViewPager 管理
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/15 20:19
 */
public class TabViewPagerManager extends FrameLayout implements BottomTabLayout.BottomTabListener{


    private TabPagerAdapter tabPagerAdapter;

    private Context context;
    private ViewPager viewPager;
    private BottomTabLayout bottomTabLayout;

    private Animation onAnimation;

    private int animationRes;
    private float defaultIconSize;
    private float defaultTextMargin;
    private float defaultTextSize;
    private ColorStateList defaultTextColor;
    private Drawable defaultBackground;
    private float defaultTabHeight;

    private List<String> titles = new ArrayList<>();

    private static final int tabMinSize = dip2px(20);

    public TabViewPagerManager(@NonNull Context context) {
        this(context, null);
    }
    public TabViewPagerManager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabViewPagerManager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate(context,R.layout.tabviewpager_content,this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomTabLayout = (BottomTabLayout) findViewById(R.id.bottom_content);
        bottomTabLayout.bringToFront();
        bottomTabLayout.setBottomTabListener(this);
        initAttrs(attrs,defStyleAttr);

    }

    private void initAttrs(AttributeSet attrs,int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabViewPagerManager, defStyleAttr, R.style.tabviewpagermanager_default_attr);
        if (ta != null) {
            animationRes = ta.getResourceId(R.styleable.TabViewPagerManager_tab_animator,0);
            defaultIconSize = ta.getDimension(R.styleable.TabViewPagerManager_tab_icon_size,0);
            defaultTextMargin = ta.getDimension(R.styleable.TabViewPagerManager_tab_text_margin,0);
            defaultTextSize = ta.getDimension(R.styleable.TabViewPagerManager_tab_text_size,0);
            defaultTextColor = ta.getColorStateList(R.styleable.TabViewPagerManager_tab_text_color);
            defaultBackground = ta.getDrawable(R.styleable.TabViewPagerManager_tab_background);
            defaultTabHeight = ta.getDimension(R.styleable.TabViewPagerManager_tab_height,0);
        }

        if(defaultBackground != null){
            bottomTabLayout.setBackground(defaultBackground);
        }
        if(animationRes > 0){
            onAnimation = AnimationUtils.loadAnimation(context,animationRes);
        }
        if(defaultTabHeight > 0){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-1);
            lp.height = (int) defaultTabHeight;
            bottomTabLayout.setLayoutParams(lp);
        }
    }

    public TabViewPagerManager createTab(String text,int icon){
        return createTab(text,icon,(int)defaultIconSize,(int)defaultTextMargin,(int)defaultTextSize,defaultTextColor);
    }
    public TabViewPagerManager createTab(String text,int icon,int iconSize,int textMargin,int textSize,ColorStateList textColor){
        bottomTabLayout.create(text, ContextCompat.getDrawable(context,icon),iconSize,textMargin,textSize,textColor);
        titles.add(text);
        return this;
    }

    public void build(List<Fragment> fragmentList,FragmentManager fragmentManager){
        tabPagerAdapter = new TabPagerAdapter(fragmentManager,fragmentList,titles);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                bottomTabLayout.on(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bottomTabLayout.on(0);
        if(onAnimation != null){
            bottomTabLayout.animation(onAnimation);
        }
    }
    public void updateTabNum(int index,int num){
        bottomTabLayout.showNum(index,num);
    }

    private static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onClick(int position) {
        viewPager.setCurrentItem(position);
    }
}
