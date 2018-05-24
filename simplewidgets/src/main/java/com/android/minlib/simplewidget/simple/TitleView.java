package com.android.minlib.simplewidget.simple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;

/**
 * @author: huangshunbo
 * @Filename: TitleView
 * @Description: 标题
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/15 10:15
 */
public class TitleView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private AutoWrapTextView tvTitle, tvSubTitle;
    private ImageView ivLeftIcon1, ivLeftIcon2, ivRightIcon1, ivRightIcon2;
    private TextView tvLeftTxt1, tvLeftTxt2, tvRightTxt1, tvRightTxt2;
    private LinearLayout left,right,middle;

    private Drawable leftDrawable1, leftDrawable2, rightDrawable1, rightDrawable2;
    private String leftTxt1, leftTxt2, rightTxt1, rightTxt2, titleTxt, subTitleTxt;
    private float leftMargin, rightMargin, itemMargin;
    private float itemTxtSize, titleTxtSize, subTitleTxtSize;
    private int itemTxtColor, titleTxtColor, subTitleTxtColor;

    private TitleListener mTitleListener;

    public TitleView(Context context) {
        super(context);
        init(context);
        initView();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttr(attrs);
        initView();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView);
        leftDrawable1 = ta.getDrawable(R.styleable.TitleView_left_icon_1);
        leftDrawable2 = ta.getDrawable(R.styleable.TitleView_left_icon_2);
        rightDrawable1 = ta.getDrawable(R.styleable.TitleView_right_icon_1);
        rightDrawable2 = ta.getDrawable(R.styleable.TitleView_right_icon_2);
        leftTxt1 = ta.getString(R.styleable.TitleView_left_txt_1);
        leftTxt2 = ta.getString(R.styleable.TitleView_left_txt_2);
        rightTxt1 = ta.getString(R.styleable.TitleView_right_txt_1);
        rightTxt2 = ta.getString(R.styleable.TitleView_right_txt_2);
        titleTxt = ta.getString(R.styleable.TitleView_title);
        subTitleTxt = ta.getString(R.styleable.TitleView_subtitle);
        leftMargin = ta.getDimension(R.styleable.TitleView_left_margin, -1);
        rightMargin = ta.getDimension(R.styleable.TitleView_right_margin, -1);
        itemMargin = ta.getDimension(R.styleable.TitleView_item_margin, -1);
        itemTxtSize = ta.getDimension(R.styleable.TitleView_item_txt_size, -1);
        titleTxtSize = ta.getDimension(R.styleable.TitleView_title_txt_size, -1);
        subTitleTxtSize = ta.getDimension(R.styleable.TitleView_subtitle_txt_size, -1);
        itemTxtColor = ta.getColor(R.styleable.TitleView_item_txt_color, 0);
        titleTxtColor = ta.getColor(R.styleable.TitleView_title_txt_color, 0);
        subTitleTxtColor = ta.getColor(R.styleable.TitleView_subtitle_txt_color, 0);

    }

    private void init(Context context) {
        mContext = context;
        inflate(mContext, R.layout.sample_title_view_layout, this);
        left = findViewById(R.id.title_view_left);
        right = findViewById(R.id.title_view_right);
        middle = findViewById(R.id.title_view_middle);

        tvTitle = findViewById(R.id.title_view_title);
        tvSubTitle = findViewById(R.id.title_view_subtitle);

        ivLeftIcon1 = findViewById(R.id.title_view_left_icon_1);
        ivLeftIcon2 = findViewById(R.id.title_view_left_icon_2);
        ivRightIcon1 = findViewById(R.id.title_view_right_icon_1);
        ivRightIcon2 = findViewById(R.id.title_view_right_icon_2);

        tvLeftTxt1 = findViewById(R.id.title_view_right_txt_1);
        tvLeftTxt2 = findViewById(R.id.title_view_right_txt_2);
        tvRightTxt1 = findViewById(R.id.title_view_left_txt_1);
        tvRightTxt2 = findViewById(R.id.title_view_left_txt_2);

        ivLeftIcon1.setOnClickListener(this);
        ivLeftIcon2.setOnClickListener(this);
        ivRightIcon1.setOnClickListener(this);
        ivRightIcon2.setOnClickListener(this);

        tvLeftTxt1.setOnClickListener(this);
        tvLeftTxt2.setOnClickListener(this);
        tvRightTxt1.setOnClickListener(this);
        tvRightTxt2.setOnClickListener(this);
    }

    private void initView(){
//        private int itemTxtColor, titleTxtColor, subTitleTxtColor;
        if(leftDrawable1 != null){
            ivLeftIcon1.setImageDrawable(leftDrawable1);
            ivLeftIcon1.setVisibility(VISIBLE);
        }
        if(leftDrawable2 != null){
            ivLeftIcon2.setImageDrawable(leftDrawable2);
            ivLeftIcon2.setVisibility(VISIBLE);
        }
        if(rightDrawable1 != null){
            ivRightIcon1.setImageDrawable(rightDrawable1);
            ivRightIcon1.setVisibility(VISIBLE);
        }
        if(rightDrawable2 != null){
            ivRightIcon2.setImageDrawable(rightDrawable2);
            ivRightIcon2.setVisibility(VISIBLE);
        }

        if(!TextUtils.isEmpty(leftTxt1)){
            tvLeftTxt1.setVisibility(VISIBLE);
            tvLeftTxt1.setText(leftTxt1);
        }
        if(!TextUtils.isEmpty(leftTxt2)){
            tvLeftTxt2.setVisibility(VISIBLE);
            tvLeftTxt2.setText(leftTxt2);
        }
        if(!TextUtils.isEmpty(rightTxt1)){
            tvRightTxt1.setVisibility(VISIBLE);
            tvRightTxt1.setText(rightTxt1);
        }
        if(!TextUtils.isEmpty(rightTxt2)){
            tvRightTxt2.setVisibility(VISIBLE);
            tvRightTxt2.setText(rightTxt2);
        }
        if(!TextUtils.isEmpty(titleTxt)){
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(titleTxt);
        }
        if(!TextUtils.isEmpty(subTitleTxt)){
            tvSubTitle.setVisibility(VISIBLE);
            tvSubTitle.setText(subTitleTxt);
        }

        if(leftMargin >= 0){
            left.setPadding((int) leftMargin,0,0,0);
        }
        if(rightMargin >= 0){
            right.setPadding(0,0, (int) rightMargin,0);
        }
        if(itemMargin >= 0){
            tvLeftTxt1.setPadding(0,0, (int) itemMargin,0);
            tvLeftTxt2.setPadding(0,0, (int) itemMargin,0);
            ivLeftIcon1.setPadding(0,0, (int) itemMargin,0);
            ivLeftIcon2.setPadding(0,0, (int) itemMargin,0);

            tvRightTxt1.setPadding((int) itemMargin,0,0,0);
            tvRightTxt2.setPadding((int) itemMargin,0,0,0);
            ivRightIcon1.setPadding((int) itemMargin,0,0,0);
            ivRightIcon2.setPadding((int) itemMargin,0,0,0);
        }

        if(itemTxtSize >= 0){
            tvLeftTxt1.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemTxtSize);
            tvLeftTxt2.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemTxtSize);
            tvRightTxt1.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemTxtSize);
            tvRightTxt2.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemTxtSize);
        }
        if(titleTxtSize >= 0){
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTxtSize);
        }
        if(subTitleTxtSize >= 0){
            tvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,subTitleTxtSize);
        }

        if(itemTxtColor > 0){
            tvLeftTxt1.setTextColor(itemTxtColor);
            tvLeftTxt2.setTextColor(itemTxtColor);
            tvRightTxt1.setTextColor(itemTxtColor);
            tvRightTxt2.setTextColor(itemTxtColor);
        }
        if(titleTxtColor > 0){
            tvTitle.setTextColor(titleTxtColor);
        }
        if(subTitleTxtColor > 0){
            tvSubTitle.setTextColor(subTitleTxtColor);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewTreeObserver observer = this.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                TitleView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int middleMargin = 0;
                if(left != null){
                    middleMargin = left.getMeasuredWidth();
                }
                if(right != null){
                    middleMargin = middleMargin > right.getMeasuredWidth() ? middleMargin : right.getMeasuredWidth();
                }
                if(middle != null){
                    middle.setPadding(middleMargin,0,middleMargin,0);
                }
            }
        });
    }

    public void setTitleListener(TitleListener mTitleListener) {
        this.mTitleListener = mTitleListener;
    }

    @Override
    public void onClick(View v) {
        if(mTitleListener == null){
            return;
        }
        int i = v.getId();
        if (i == R.id.title_view_left_icon_1) {
            mTitleListener.onLeftIcon1Click();
        } else if (i == R.id.title_view_left_icon_2) {
            mTitleListener.onLeftIcon2Click();
        } else if (i == R.id.title_view_right_icon_1) {
            mTitleListener.onRightIcon1Click();
        } else if (i == R.id.title_view_right_icon_2) {
            mTitleListener.onRightIcon2Click();
        }else if(i == R.id.title_view_right_txt_1){
            mTitleListener.onRightTxt1Click();
        }else if(i == R.id.title_view_right_txt_2){
            mTitleListener.onRightTxt2Click();
        }else if(i == R.id.title_view_left_txt_1){
            mTitleListener.onLeftTxt1Click();
        }else if(i == R.id.title_view_left_txt_2){
            mTitleListener.onLeftTxt2Click();
        }
    }

    public AutoWrapTextView getTvTitle() {
        return tvTitle;
    }

    public AutoWrapTextView getTvSubTitle() {
        return tvSubTitle;
    }

    public ImageView getIvLeftIcon1() {
        return ivLeftIcon1;
    }

    public ImageView getIvLeftIcon2() {
        return ivLeftIcon2;
    }

    public ImageView getIvRightIcon1() {
        return ivRightIcon1;
    }

    public ImageView getIvRightIcon2() {
        return ivRightIcon2;
    }

    public TextView getTvLeftTxt1() {
        return tvLeftTxt1;
    }

    public TextView getTvLeftTxt2() {
        return tvLeftTxt2;
    }

    public TextView getTvRightTxt1() {
        return tvRightTxt1;
    }

    public TextView getTvRightTxt2() {
        return tvRightTxt2;
    }

    public static class TitleListener {
        public void onLeftTxt1Click() {}

        public void onLeftTxt2Click() {}

        public void onRightTxt1Click() {}

        public void onRightTxt2Click() {}

        public void onLeftIcon1Click() {}

        public void onLeftIcon2Click() {}

        public void onRightIcon1Click() {}

        public void onRightIcon2Click() {}

    }


}
