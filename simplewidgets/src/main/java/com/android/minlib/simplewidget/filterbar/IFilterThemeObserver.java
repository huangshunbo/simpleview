package com.android.minlib.simplewidget.filterbar;
/**
 * @author: huangshunbo
 * @Filename: IFilterThemeObserver
 * @Description: 观察所有FilterBar的点击情况
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 16:01
 */
public interface IFilterThemeObserver {
    void itemClick(CommonFilterBar commonFilterBar,
                   FilterBarType filterBarType,
                   CommonFilterBar.TitleViewHolder viewHolder);
    void itemClick(CommonFilterBar commonFilterBar,
                   FilterBarType filterBarType,
                   CommonFilterBar.ArrowViewHolder viewHolder);
    void itemClick(CommonFilterBar commonFilterBar,
                   FilterBarType filterBarType,
                   CommonFilterBar.TriangleViewHolder viewHolder,
                   CommonFilterBar.TRIANGLE_TYPE type);
}
