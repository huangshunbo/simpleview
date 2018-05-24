package com.android.minlib.simplewidget.filterbar;
/**
 * @author: huangshunbo
 * @Filename: IDropDownObserver
 * @Description: 观察DropDown下拉框的状态
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 16:01
 */
public interface IDropDownObserver{
    void onItemSelect(CommonFilterBar commonFilterBar,FilterBarType filterBarType,int[] position, String[] item);
    void onDissmiss(CommonFilterBar commonFilterBar,FilterBarType filterBarType);
}
