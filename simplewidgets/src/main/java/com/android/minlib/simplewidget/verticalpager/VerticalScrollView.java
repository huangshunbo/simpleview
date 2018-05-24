package com.android.minlib.simplewidget.verticalpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * Created by landsnail on 14-9-30.
 *
 * @author hongyun.fang
 * @email fanghongyun@gmail.com
 */
public class VerticalScrollView extends ScrollView {

    public interface OnScroll {
        public void onScrollChanged(VerticalScrollView scrollView, int x, int y, int oldx, int oldy);

        public void onScrollBottom(boolean isBottom);
    }

    private OnScroll onScroll;

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    public void setOnScroll(OnScroll onScroll) {
        this.onScroll = onScroll;
    }

    float tempx = 0;
    float tempy = 0;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempx = event.getX();
                tempy = event.getY();
                break;

            case MotionEvent.ACTION_MOVE: {
                float tempx2 = event.getX();
                float tempy2 = event.getY();
                if (Math.abs(tempx2 - tempx) > Math.abs(tempy2 - tempy)) {//横向滑动不做拦截处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if ((getChildAt(0).getMeasuredHeight() <= (getScrollY() + getHeight()))) {
                        // 滑动到底
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (getScrollY() <= 0 && (tempy - tempy2) < -1) {
                        // 滑动到顶部
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                tempx = tempx2;
                tempy = tempy2;
            }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        try {
            return super.dispatchTouchEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (onScroll != null) {
            onScroll.onScrollChanged(this, l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (scrollY != 0 && onScroll != null) {
            onScroll.onScrollBottom(clampedY);
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
