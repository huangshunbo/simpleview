package com.android.minlib.simplewidget.simple;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import java.text.DecimalFormat;
/**
 * @author: huangshunbo
 * @Filename: CountNumberTextView
 * @Description: 数字动画
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/16 16:04 
 */
public class CountNumberTextView extends TextView {
        //动画时长
        private int duration = 1000;
        //显示数字
        private Double mNumber;
        //显示表达式
        private DecimalFormat regex;

        //显示表示式
        //显示小数点后两位且带千分位
        public static final DecimalFormat FLOATREGEX_QUANTILE = new DecimalFormat(",##0.00");

    public CountNumberTextView(Context context) {
        super(context);
    }

    public CountNumberTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * 显示带有动画效果的数字
         * @param number
         * @param regex
         */
        public void showNumberWithAnimation(double number, final DecimalFormat regex) {
            this.mNumber = number;
            if (regex != null) {
                //默认为整数
                this.regex = FLOATREGEX_QUANTILE;
            } else {
                this.regex = regex;
            }
            //修改number属性，会调用setNumber方法
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, (float) number);
            objectAnimator.setDuration(duration);
            //加速器，从慢到快到再到慢
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    String s = regex.format(mNumber);
                    setText(s);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        /**
         * 获取当前数字
         * @return
         */
        public Double getNumber() {
            return mNumber;
        }

        /**
         * 根据正则表达式，显示对应数字样式
         * @param number
         */
        public void setNumber(float number) {
            String s = regex.format(number);
            setText(s);
        }
}
