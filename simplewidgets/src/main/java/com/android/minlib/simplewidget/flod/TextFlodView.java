package com.android.minlib.simplewidget.flod;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;

/**
 * @author: huangshunbo
 * @Filename: TextFlodView
 * @Description: 文字TextView折叠控件
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/4/26 16:26
 */
public class TextFlodView extends LinearLayout implements View.OnClickListener{

    ImageView flodView;
    TextView contentView;
    Context context;
    OnFlodChangeListener listener;
    int arrowId;
    int contentId;
    int lineNum;
    boolean isFlod = false;
    ObjectAnimator indicateOpenAnim,indicateCloseAnim;
    private void initView() {
        if(arrowId != 0){
            flodView = findViewById(arrowId);
        }
        if(contentId != 0){
            contentView = findViewById(contentId);
        }
        if(flodView == null || contentView == null){
            throw new NullPointerException("first child or second child is null");
        }
        flodView.setOnClickListener(this);
        contentView.setOnClickListener(this);
        if(listener != null){
            listener.onContentFlod(isFlod);
        }
        setBackgroundColor(Color.WHITE);

        indicateOpenAnim = ObjectAnimator.ofFloat(flodView, "rotation", 0f, -180f);
        indicateOpenAnim.setDuration(400);
        indicateCloseAnim = ObjectAnimator.ofFloat(flodView, "rotation", -180f, 0f);
        indicateCloseAnim.setDuration(400);
    }

    protected void initAttrs(AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextFlodView);
        arrowId = a.getResourceId(R.styleable.TextFlodView_arrow,0);
        contentId = a.getResourceId(R.styleable.TextFlodView_content,0);
        lineNum = a.getInteger(R.styleable.TextFlodView_flodLines,Integer.MAX_VALUE);
    }

    public void setListener(OnFlodChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public TextFlodView(Context context) {
        super(context);
        this.context = context;
    }

    public TextFlodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);

    }

    public TextFlodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextFlodView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initAttrs(attrs);

    }

    @Override
    public void onClick(View v) {
        if(v == flodView || v == contentView){
            if(!isFlod){
                isFlod = !isFlod;
                contentView.setMaxLines(lineNum);
                indicateCloseAnim.start();
                if (listener != null){
                    listener.onContentFlod(true);
                }
            }else {
                isFlod = !isFlod;
                contentView.setMaxLines(Integer.MAX_VALUE);
                indicateOpenAnim.start();
                if (listener != null){
                    listener.onContentFlod(false);
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
        void onContentFlod(boolean isFlod);
    }
}

