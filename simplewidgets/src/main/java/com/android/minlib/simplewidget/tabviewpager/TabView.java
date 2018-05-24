package com.android.minlib.simplewidget.tabviewpager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;
import com.android.minlib.simplewidget.simple.NumberRedView;
/**
 * @author: huangshunbo
 * @Filename: TabView
 * @Description: 底部TAB
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/15 15:11
 */
public class TabView extends FrameLayout {

    ImageView ivIcon;
    TextView tvTitle;
    NumberRedView nrvCorner;
    private Context mContext;

    public TabView(Context context, String text, Drawable icon, int iconSize, int textMargin, int textSize, ColorStateList textColor) {
        super(context);
        mContext = context;
        inflate(context, R.layout.tabviewpager_tabview, this);
        ivIcon = (ImageView) findViewById(R.id.main_tab_item_img);
        tvTitle = (TextView) findViewById(R.id.main_tab_item_txt);
        nrvCorner = (NumberRedView) findViewById(R.id.dotView);
        if (!TextUtils.isEmpty(text)) tvTitle.setText(text);
        if (icon != null) ivIcon.setImageDrawable(icon);

        ViewGroup.LayoutParams vp = ivIcon.getLayoutParams();
        vp.width = iconSize;
        vp.height = iconSize;
        ivIcon.setLayoutParams(vp);

        tvTitle.setPadding(0,0,textMargin,0);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        tvTitle.setTextColor(textColor);
    }

    public void on() {
        ivIcon.setSelected(true);
        tvTitle.setSelected(true);
    }

    public void off() {
        ivIcon.setSelected(false);
        tvTitle.setSelected(false);
    }

    public void showNum(int num) {
        nrvCorner.show(num, true);
    }

    public void showIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void startAnim(Animation onAnimation) {
        this.startAnimation(onAnimation);
    }


}
