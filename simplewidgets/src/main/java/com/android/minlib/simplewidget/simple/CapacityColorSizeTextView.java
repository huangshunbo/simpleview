package com.android.minlib.simplewidget.simple;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangshunbo
 * @Filename: CapacityColorSizeTextView
 * @Description: 智能颜色字体大小的TextView，支持对任意字段设置文字颜色和文字大小
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/1/12 16:33
 */
public class CapacityColorSizeTextView extends TextView {

    private List<String> txts = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private List<Integer> sizes = new ArrayList<>();

    public CapacityColorSizeTextView(Context context) {
        super(context);
    }

    public CapacityColorSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CapacityColorSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CapacityColorSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CapacityColorSizeTextView create(String txt,int textColor,int textSize){
        if(TextUtils.isEmpty(txt) || textColor ==0 || textSize == 0){
            throw new IllegalArgumentException("入参有误");
        }
        txts.add(txt);
        colors.add(textColor);
        sizes.add(textSize);
        return this;
    }

    public void build(){
        SpannableString spannableString;
        String content = "";
        for (String txt:txts) {
            content += txt;
        }
        spannableString = new SpannableString(content);
        int start = 0;
        int end = 0;
        for(int i = 0 ; i < txts.size() ; i++){
            String txt = txts.get(i);
            int textColor = colors.get(i);
            int textSize = sizes.get(i);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(textColor > 0 ? ContextCompat.getColor(getContext(),textColor) : textColor);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(textSize);
            start = end;
            end = start + txt.length();
            spannableString.setSpan(colorSpan, start , end , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan, start , end , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
    }
}
