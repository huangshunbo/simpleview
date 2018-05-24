package com.android.minlib.simplewidget.filterbar;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: huangshunbo
 * @Filename: DrowDownPopupWindow
 * @Description: 筛选下拉框父类
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 16:00
 */
public abstract class DrowDownPopupWindow extends PopupWindow {

    protected View standardView = null;
    protected List<IDropDownObserver> observers = new ArrayList<>();
    private FilterBarType filterBarType;
    private CommonFilterBar commonFilterBar;

    public DrowDownPopupWindow(Context context, View standardView) {
        super(context);
        this.standardView = standardView;
    }

    protected void setObserver(List<IDropDownObserver> observers){
        this.observers.addAll(observers);
    }
    public void addObserver(IDropDownObserver listener) {
        if(listener != null){
            observers.add(listener);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        for (IDropDownObserver listener : observers) {
            listener.onDissmiss(commonFilterBar,filterBarType);
        }
    }

    protected void sendItemCheckd(int[] positions,String[] items){
        for (IDropDownObserver listener : observers) {
            listener.onItemSelect(commonFilterBar,filterBarType,positions,items);
        }
    }

    protected void setFilterBarType(CommonFilterBar commonFilterBar,FilterBarType filterBarType) {
        this.commonFilterBar = commonFilterBar;
        this.filterBarType = filterBarType;
    }

    public abstract void showItems(String[] items, int[] checks);

    public abstract void changeVisiable();

}
