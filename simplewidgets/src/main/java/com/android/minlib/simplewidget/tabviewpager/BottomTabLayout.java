package com.android.minlib.simplewidget.tabviewpager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
/**
 * @author: huangshunbo
 * @Filename: BottomTabLayout
 * @Description: 底部导航
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/15 20:19
 */
class BottomTabLayout extends LinearLayout implements View.OnClickListener{

    private List<TabView> tabViews = new ArrayList<>();
    private Animation onAnimation;
    private Context mContext;
    private BottomTabListener bottomTabListener;

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(HORIZONTAL);
    }

    public void setBottomTabListener(BottomTabListener listener){
        this.bottomTabListener = listener;
    }

    public BottomTabLayout create(String text, Drawable icon, int iconSize, int textMargin, int textSize, ColorStateList textColor){
        TabView tabView = new TabView(mContext,text,icon,iconSize,textMargin,textSize,textColor);
        tabView.setTag(getChildCount());
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.weight = 1;
        tabView.setLayoutParams(lp);
        addView(tabView);
        tabView.setOnClickListener(this);
        tabViews.add(tabView);
        return this;
    }

    public List<TabView> getTabViews(){
        return tabViews;
    }

    public void on(int position) {
        for(TabView tabView : tabViews){
            tabView.off();
        }
        if(tabViews.get(position)!=null){
            tabViews.get(position).on();
        }
    }

    @Override
    public void onClick(View v) {
        if(onAnimation != null && v instanceof TabView){
            TabView tabView = (TabView) v;
            tabView.startAnim(onAnimation);
        }
        if(bottomTabListener != null){
            bottomTabListener.onClick((Integer) v.getTag());
        }
    }

    public void animation(Animation onAnimation) {
        this.onAnimation = onAnimation;
    }

    public void showNum(int index,int num){
        if(index>=0 && index<tabViews.size()){
            tabViews.get(index).showNum(num);
        }
    }

    public interface BottomTabListener
    {
        void onClick(int position);
    }
}