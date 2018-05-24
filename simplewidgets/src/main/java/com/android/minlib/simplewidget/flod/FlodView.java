package com.android.minlib.simplewidget.flod;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.minlib.simplewidget.R;
/**
 * @author: huangshunbo
 * @Filename: FlodView
 * @Description: 折叠控件，将子view分别设置为flodView和contentView，flodView为折叠动作触发控件，contentView为折叠动作目标控件
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/4/26 16:24
 */
public class FlodView extends LinearLayout implements View.OnClickListener{

    View flodView;
    View contentView;
    Context context;
    OnFlodChangeListener listener;
    int flodViewId;
    int contentViewId;
    private void initView() {
        if(flodView == null && flodViewId != 0){
            flodView = findViewById(flodViewId);
        }
        if(contentView == null && contentViewId != 0){
            contentView = findViewById(contentViewId);
        }
        if(flodView == null || contentView == null){
            throw new NullPointerException("first child or second child is null");
        }
        flodView.setOnClickListener(this);
        if(listener != null){
            listener.onContentVisiable(contentView.getVisibility()== View.VISIBLE);
        }
        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);
    }

    protected void initAttrs(AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FlodView);
        flodViewId = a.getResourceId(R.styleable.FlodView_foldView,0);
        contentViewId = a.getResourceId(R.styleable.FlodView_contentView,0);


    }

    public void setListener(OnFlodChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public FlodView(Context context) {
        super(context);
        this.context = context;
    }

    public FlodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);

    }

    public FlodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlodView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initAttrs(attrs);

    }

    @Override
    public void onClick(View v) {
        if(v == flodView){
            if(isVisible(contentView)){
                contentView.setVisibility(View.GONE);
                if (listener != null){
                    listener.onContentVisiable(false);
                }
            }else {
                contentView.setVisibility(View.VISIBLE);
                if (listener != null){
                    listener.onContentVisiable(true);
                }
            }
        }
    }


    private boolean isVisible(View view){
        if(view.getVisibility() == View.VISIBLE){
            return true;
        }
        return false;
    }
    interface OnFlodChangeListener
    {
        void onContentVisiable(boolean isVisiable);
    }
}
