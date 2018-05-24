package com.android.minlib.simplewidget.filterbar;


import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;

import com.android.minlib.simplewidget.R;
/**
 * @author: huangshunbo
 * @Filename: FilterBarType
 * @Description: 定义一个FitletBar 目前只包含常用的三种类型
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 16:00
 */
public class FilterBarType {

    /* 区分每个FilterBar的id，请不要设置重复值 */
    private int id;
    private String title;
    private @DrawableRes int rightRes;
    private @DrawableRes int rightUpRes;
    private @DrawableRes int rightDownRes;

    private @LayoutRes int layoutRes;
    public static final @LayoutRes int JUST_TITLE = R.layout.filterbar_view_default;
    public static final @LayoutRes int TITLE_ARROW = R.layout.filterbar_view_one_img;
    public static final @LayoutRes int TITLE_TRIANGLE = R.layout.filterbar_view_two_img;

    public static FilterBarType create(int id, String title) {
        return new FilterBarType(id, title);
    }
    public static FilterBarType create(int id, String title,@DrawableRes int rightRes) {
        return new FilterBarType(id, title,rightRes);
    }
    public static FilterBarType create(int id, String title,@DrawableRes int rightUpRes,@DrawableRes int rightDownRes) {
        return new FilterBarType(id, title,rightUpRes,rightDownRes);
    }

    public FilterBarType(int id, String title) {
        this.id = id;
        this.title = title;
        this.layoutRes = JUST_TITLE;
    }

    public FilterBarType(int id, String title, int rightRes) {
        this.id = id;
        this.title = title;
        this.rightRes = rightRes;
        this.layoutRes = TITLE_ARROW;
    }

    public FilterBarType(int id, String title, int rightUpRes, int rightDownRes) {
        this.id = id;
        this.title = title;
        this.rightUpRes = rightUpRes;
        this.rightDownRes = rightDownRes;
        this.layoutRes = TITLE_TRIANGLE;
    }

//        DEFAULT(R.layout.filterbar_view_default),
//        ARROW(R.drawable.common_filter_bar_title_image_filter, R.layout.filterbar_view_one_img),
//        TRIANGLE(R.drawable.common_filter_bar_title_image_up, R.drawable.common_filter_bar_title_image_down, R.layout.filterbar_view_two_img);


    public int getRightRes() {
        return rightRes;
    }

    public void setRightRes(int rightRes) {
        this.rightRes = rightRes;
    }

    public int getRightUpRes() {
        return rightUpRes;
    }

    public void setRightUpRes(int rightUpRes) {
        this.rightUpRes = rightUpRes;
    }

    public int getRightDownRes() {
        return rightDownRes;
    }

    public void setRightDownRes(int rightDownRes) {
        this.rightDownRes = rightDownRes;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
