package com.android.minlib.simplewidget.flod;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;

/**
 * @author: huangshunbo
 * @Filename: TitleFlodView
 * @Description: 带标题的折叠控件，设置Title和折叠内容
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/4/26 16:25
 */
public class TitleFlodView extends FlodView implements FlodView.OnFlodChangeListener{

    TextView tvTitle;
    ImageView ivArrow;
    String title;
    float titleSize;
    int titleColor;
    Drawable arrowIcon;
    ObjectAnimator indicateOpenAnim,indicateCloseAnim;

    public TitleFlodView(Context context) {
        super(context);
    }
    public TitleFlodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleFlodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TitleFlodView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TitleFlodView);
        title = a.getString(R.styleable.TitleFlodView_titleTxt);
        titleSize = a.getDimensionPixelSize(R.styleable.TitleFlodView_titleSize, 15);
        titleColor = a.getColor(R.styleable.TitleFlodView_titleColor, Color.BLACK);
        arrowIcon = a.getDrawable(R.styleable.TitleFlodView_arrowIcon);


    }

    @Override
    protected void onFinishInflate() {
        initView();
        super.onFinishInflate();
    }

    protected void initView() {

        View view= View.inflate(context,R.layout.flod_title,null);
        addView(view,0);
        flodView = view;
        tvTitle = (TextView) view.findViewById(R.id.flod_title);
        ivArrow = (ImageView) view.findViewById(R.id.flod_arrow);

        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
        tvTitle.setTextColor(titleColor);
        if(arrowIcon != null){
            ivArrow.setImageDrawable(arrowIcon);
        }
        setListener(this);
        indicateOpenAnim = ObjectAnimator.ofFloat(ivArrow, "rotation", 0f, -180f);
        indicateOpenAnim.setDuration(400);
        indicateCloseAnim = ObjectAnimator.ofFloat(ivArrow, "rotation", -180f, 0f);
        indicateCloseAnim.setDuration(400);
    }

    @Override
    public void onContentVisiable(boolean isVisiable) {
        if(tvTitle == null || tvTitle == null){
            return ;
        }
        if(isVisiable){
            indicateOpenAnim.start();
        }else{
            indicateCloseAnim.start();
        }
    }
    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
    public void updateData(){
        postInvalidate();
    }
}
