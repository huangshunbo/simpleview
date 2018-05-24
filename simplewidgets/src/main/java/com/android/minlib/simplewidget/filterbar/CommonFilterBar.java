package com.android.minlib.simplewidget.filterbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: huangshunbo
 * @Filename: CommonFilterBar
 * @Description: 筛选控件，支持三种模式：文字、文字+右边图标、文字+右上图标+右下图标
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/1/12 16:30
 */
public class CommonFilterBar extends LinearLayout implements View.OnClickListener {

    private List<FilterBarType> filterBarTypeList = new ArrayList<>();
    private FilterBarType currentFilterBarType;
    private HashMap<FilterBarType, TitleViewHolder> titleViewCache = new HashMap<>();
    private HashMap<FilterBarType, ArrowViewHolder> arrowViewCache = new HashMap<>();
    private HashMap<FilterBarType, TriangleViewHolder> triangleViewCache = new HashMap<>();

    private int height = dp2px(40);
    /*** 筛选/下拉框监听函数 ***/
    private List<IDropDownObserver> drowDownobservers = new ArrayList<>();
    private List<IFilterThemeObserver> filterThemeObservers = new ArrayList<>();

    DrowDownPopupWindow drowDownPopupWindow;

    public CommonFilterBar(Context context) {
        super(context);
    }

    public CommonFilterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonFilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrowDownPopupWindow(DrowDownPopupWindow drowDownPopupWindow) {
        this.drowDownPopupWindow = drowDownPopupWindow;
        drowDownPopupWindow.setObserver(drowDownobservers);
    }

    public void showDrowDown(String[] items,int[] checkeds){
        if(drowDownPopupWindow != null){
            drowDownPopupWindow.setFilterBarType(this,currentFilterBarType);
            drowDownPopupWindow.showItems(items,checkeds);
            drowDownPopupWindow.changeVisiable();
        }
    }
    public void dismissDrowDown() {
        if(drowDownPopupWindow != null && drowDownPopupWindow.isShowing()){
            drowDownPopupWindow.dismiss();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, height);
    }

    public void build(int height, FilterBarType... types) {
        this.height = height;
        build(types);
    }

    public void build(FilterBarType... types) {
        filterBarTypeList.clear();
        for (int i = 0; i < types.length; i++) {
            filterBarTypeList.add(types[i]);
        }
        rebuildView();
    }

    private void rebuildView() {
        setOrientation(HORIZONTAL);
        for (FilterBarType filterBarType : filterBarTypeList) {
            View view = View.inflate(getContext(), filterBarType.getLayoutRes(), null);
            if (filterBarType.getLayoutRes() == FilterBarType.JUST_TITLE) {
                TitleViewHolder viewHolder = new TitleViewHolder();
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.common_filter_bar_title);
                viewHolder.vgContent = (ViewGroup) view.findViewById(R.id.common_filter_bar_content);
                viewHolder.vgContent.setTag(filterBarType);

                viewHolder.tvTitle.setText(filterBarType.getTitle());
                viewHolder.vgContent.setOnClickListener(this);
                titleViewCache.put(filterBarType, viewHolder);
            } else if (filterBarType.getLayoutRes() == FilterBarType.TITLE_ARROW) {
                ArrowViewHolder viewHolder = new ArrowViewHolder();
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.common_filter_bar_title);
                viewHolder.vgContent = (ViewGroup) view.findViewById(R.id.common_filter_bar_content);
                viewHolder.vgContent.setTag(filterBarType);

                viewHolder.ivRight = view.findViewById(R.id.common_filter_bat_ic);
                viewHolder.tvTitle.setText(filterBarType.getTitle());
                viewHolder.ivRight.setImageResource(filterBarType.getRightRes());
                viewHolder.vgContent.setOnClickListener(this);

                arrowViewCache.put(filterBarType, viewHolder);
            } else if (filterBarType.getLayoutRes() == FilterBarType.TITLE_TRIANGLE) {
                TriangleViewHolder viewHolder = new TriangleViewHolder();
                viewHolder.tvTitle = (TextView) view.findViewById(R.id.common_filter_bar_title);
                viewHolder.vgContent = (ViewGroup) view.findViewById(R.id.common_filter_bar_content);
                viewHolder.vgContent.setTag(filterBarType);

                viewHolder.ivUp = (ImageView) view.findViewById(R.id.common_filter_bar_up);
                viewHolder.ivDown = (ImageView) view.findViewById(R.id.common_filter_bar_down);
                viewHolder.tvTitle.setText(filterBarType.getTitle());
                viewHolder.ivUp.setImageResource(filterBarType.getRightUpRes());
                viewHolder.ivDown.setImageResource(filterBarType.getRightDownRes());
                viewHolder.vgContent.setOnClickListener(this);

                triangleViewCache.put(filterBarType, viewHolder);
            }
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -1, 1);
            lp.gravity = Gravity.CENTER;
            view.setLayoutParams(lp);
            addView(view);
        }
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        currentFilterBarType = (FilterBarType) v.getTag();
        if (currentFilterBarType.getLayoutRes() == FilterBarType.JUST_TITLE) {
            TitleViewHolder viewHolder = titleViewCache.get(currentFilterBarType);
            sendItemClick(this,currentFilterBarType,viewHolder);
        } else if (currentFilterBarType.getLayoutRes() == FilterBarType.TITLE_ARROW) {
            ArrowViewHolder viewHolder = arrowViewCache.get(currentFilterBarType);
            sendItemClick(this,currentFilterBarType,viewHolder);
        } else if (currentFilterBarType.getLayoutRes() == FilterBarType.TITLE_TRIANGLE) {
            TriangleViewHolder viewHolder = triangleViewCache.get(currentFilterBarType);
            sendItemClick(this,currentFilterBarType,viewHolder,getTriangleType(viewHolder));
        }
    }



    public class TitleViewHolder {
        ViewGroup vgContent;
        TextView tvTitle;
    }

    public class ArrowViewHolder {
        ViewGroup vgContent;
        TextView tvTitle;
        ImageView ivRight;
    }

    public class TriangleViewHolder {
        ViewGroup vgContent;
        TextView tvTitle;
        ImageView ivUp;
        ImageView ivDown;
    }

    public void refreshTitle() {
        unActivateAll();
    }

    public void activate(FilterBarType filterBarType) {
        if (filterBarType.getLayoutRes() == FilterBarType.JUST_TITLE) {
            TitleViewHolder viewHolder = titleViewCache.get(filterBarType);
            sendItemClick(this,filterBarType,viewHolder);
        } else if (filterBarType.getLayoutRes() == FilterBarType.TITLE_ARROW) {
            ArrowViewHolder viewHolder = arrowViewCache.get(filterBarType);
            sendItemClick(this,filterBarType,viewHolder);
        } else if (filterBarType.getLayoutRes() == FilterBarType.TITLE_TRIANGLE) {
            TriangleViewHolder viewHolder = triangleViewCache.get(filterBarType);
            sendItemClick(this,filterBarType,viewHolder,getTriangleType(viewHolder));
        }
    }

    private TRIANGLE_TYPE getTriangleType(TriangleViewHolder viewHolder){
        if(!viewHolder.ivUp.isActivated() && !viewHolder.ivDown.isActivated()){
            return TRIANGLE_TYPE.NONE;
        }else if(viewHolder.ivUp.isActivated() && !viewHolder.ivDown.isActivated()){
            return TRIANGLE_TYPE.UP;
        }else if(!viewHolder.ivUp.isActivated() && viewHolder.ivDown.isActivated()){
            return TRIANGLE_TYPE.DOWN;
        }
        return TRIANGLE_TYPE.NONE;
    }

    public void unActivate(FilterBarType filterBarType){
        if(filterBarTypeList.contains(filterBarType)){
            TitleViewHolder titleViewHolder = titleViewCache.get(filterBarType);
            if(titleViewHolder != null){
                titleViewHolder.tvTitle.setText(filterBarType.getTitle());
            }

            ArrowViewHolder arrowViewHolder = arrowViewCache.get(filterBarType);
            if(arrowViewHolder != null){
                arrowViewHolder.tvTitle.setText(filterBarType.getTitle());
                arrowViewHolder.ivRight.setActivated(false);
            }

            TriangleViewHolder triangleViewHolder = triangleViewCache.get(filterBarType);
            if(triangleViewHolder != null){
                triangleViewHolder.tvTitle.setText(filterBarType.getTitle());
                triangleViewHolder.ivUp.setActivated(false);
                triangleViewHolder.ivDown.setActivated(false);
            }
        }
        titleViewCache.get(filterBarType);
    }

    private void unActivateAll() {
        Iterator iter1 = titleViewCache.entrySet().iterator();
        while (iter1.hasNext()) {
            Map.Entry entry = (Map.Entry) iter1.next();
            FilterBarType filterBarType = (FilterBarType) entry.getKey();
            TitleViewHolder val = (TitleViewHolder) entry.getValue();
            val.tvTitle.setText(filterBarType.getTitle());
        }
        Iterator iter2 = arrowViewCache.entrySet().iterator();
        while (iter2.hasNext()) {
            Map.Entry entry = (Map.Entry) iter2.next();
            FilterBarType filterBarType = (FilterBarType) entry.getKey();
            ArrowViewHolder val = (ArrowViewHolder) entry.getValue();
            val.tvTitle.setText(filterBarType.getTitle());
            val.ivRight.setActivated(false);
        }
        Iterator iter3 = triangleViewCache.entrySet().iterator();
        while (iter3.hasNext()) {
            Map.Entry entry = (Map.Entry) iter3.next();
            FilterBarType filterBarType = (FilterBarType) entry.getKey();
            TriangleViewHolder val = (TriangleViewHolder) entry.getValue();
            val.tvTitle.setText(filterBarType.getTitle());
            val.ivUp.setActivated(false);
            val.ivDown.setActivated(false);
        }
    }

    public void addDropDownObserver(IDropDownObserver observer) {
        if(observer != null){
            drowDownobservers.add(observer);
        }
    }

    public void addFilterThemeObserver(IFilterThemeObserver observer){
        if(observer != null){
            filterThemeObservers.add(observer);
        }
    }

    public void sendItemClick(CommonFilterBar commonFilterBar,FilterBarType filterBarType,CommonFilterBar.TitleViewHolder viewHolder){
        for (IFilterThemeObserver observer: filterThemeObservers) {
            observer.itemClick(commonFilterBar,filterBarType,viewHolder);
        }
    }
    public void sendItemClick(CommonFilterBar commonFilterBar,FilterBarType filterBarType,CommonFilterBar.ArrowViewHolder viewHolder){
        for (IFilterThemeObserver observer: filterThemeObservers) {
            observer.itemClick(commonFilterBar,filterBarType,viewHolder);
        }
    }
    public void sendItemClick(CommonFilterBar commonFilterBar,FilterBarType filterBarType,CommonFilterBar.TriangleViewHolder viewHolder,CommonFilterBar.TRIANGLE_TYPE type){
        for (IFilterThemeObserver observer: filterThemeObservers) {
            observer.itemClick(commonFilterBar,filterBarType,viewHolder,type);
        }
    }

    public int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public enum TRIANGLE_TYPE{
        UP,DOWN,NONE
    }
}
