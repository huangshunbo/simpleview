package com.android.minlib.simplewidget.filterbar;

/**
 * @author: huangshunbo
 * @Filename: DefaultFilterTheme
 * @Description: FilterBar和DrowDownMenu的观察者，用于设置各种状态下FilterBar的默认样式，
 * 如需修改样式可参照该类进行修改
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 15:58
 */
public class DefaultFilterTheme implements IDropDownObserver,IFilterThemeObserver {

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TitleViewHolder viewHolder) {
        viewHolder.tvTitle.setActivated(true);
    }

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.ArrowViewHolder viewHolder) {
        viewHolder.tvTitle.setActivated(true);
        viewHolder.ivRight.setActivated(true);
    }

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TriangleViewHolder viewHolder, CommonFilterBar.TRIANGLE_TYPE type) {
        viewHolder.tvTitle.setActivated(true);
        if(type == CommonFilterBar.TRIANGLE_TYPE.NONE){
            viewHolder.ivDown.setActivated(true);
            viewHolder.ivUp.setActivated(false);
        }else if(type == CommonFilterBar.TRIANGLE_TYPE.UP){
            viewHolder.ivDown.setActivated(true);
            viewHolder.ivUp.setActivated(false);
        }else if(type == CommonFilterBar.TRIANGLE_TYPE.DOWN){
            viewHolder.ivUp.setActivated(true);
            viewHolder.ivDown.setActivated(false);
        }
    }

    @Override
    public void onItemSelect(CommonFilterBar commonFilterBar,FilterBarType filterBarType, int[] position, String[] item) {
        filterBarType.setTitle(item[0]);
    }

    @Override
    public void onDissmiss(CommonFilterBar commonFilterBar,FilterBarType filterBarType) {
        commonFilterBar.unActivate(filterBarType);
    }
}
