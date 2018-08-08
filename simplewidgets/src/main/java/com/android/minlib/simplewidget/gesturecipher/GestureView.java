package com.android.minlib.simplewidget.gesturecipher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.minlib.simplewidget.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: huangshunbo
 * @Filename: GestureView
 * @Description: 手势密码
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/5/15 10:14
 */
public class GestureView extends View {

    private boolean isCache;
    private final Paint mPaint;
    private final GestureView.Point[][] mPoints;
    private final GestureView.Point mMoveingPoint;
    private float r;
    private final List<GestureView.Point> mSelectedPoints;
    private boolean checking;
    private Bitmap locus_round_original;
    private Bitmap locus_round_click;
    private int lineColor;
    private int lineWidth;
    private static final long CLEAR_TIME = 0L;
    private final Matrix mMatrix;
    private int lineAlpha;
    private final Timer timer;
    private TimerTask task;
    private boolean movingNoPoint;
    private float moveingX;
    private float moveingY;
    private GestureView.OnCompleteListener mCompleteListener;

    public GestureView(Context context) {
        this(context, (AttributeSet) null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isCache = false;
        this.mPaint = new Paint(1);
        this.mPoints = new Point[3][3];
        this.mMoveingPoint = new Point(-1, 0.0F, 0.0F);
        this.r = 0.0F;
        this.mSelectedPoints = new ArrayList(9);
        this.checking = false;
        this.mMatrix = new Matrix();
        this.lineAlpha = 50;
        this.timer = new Timer();
        this.task = null;
        if (attrs != null) {
            this.init(context, attrs);
        }

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GestureView);
        int drawablePointOriginal = typedArray.getResourceId(R.styleable.GestureView_drawablePointOriginal, R.drawable.gesture_point_original);
        int drawablePointClick = typedArray.getResourceId(R.styleable.GestureView_drawablePointClick, R.drawable.gesture_point_click);
        this.lineColor = typedArray.getColor(R.styleable.GestureView_linecolor,Color.GREEN);
        this.lineWidth = typedArray.getDimensionPixelSize(R.styleable.GestureView_lineWidth,10);
        typedArray.recycle();
        this.locus_round_original = BitmapFactory.decodeResource(this.getResources(), drawablePointOriginal);
        this.locus_round_click = BitmapFactory.decodeResource(this.getResources(), drawablePointClick);
    }

    private boolean initCache() {
        float w = (float) this.getWidth();
        float h = (float) this.getHeight();
        if (w != 0.0F && h != 0.0F) {
            float x = 0.0F;
            float y = 0.0F;
            if (w > h) {
                x = (w - h) / 8.0F * 5.0F;
                w = h;
            } else {
                y = (h - w) / 8.0F * 5.0F;
                h = w;
            }

            float canvasMinW = w > h ? h : w;
            float roundMinW = canvasMinW / 8.0F * 2.0F;
            float roundW = roundMinW / 2.0F;
            if ((float) this.locus_round_original.getWidth() > roundMinW) {
                float sf = roundMinW * 1.0F / (float) this.locus_round_original.getWidth();
                this.locus_round_original = zoom(this.locus_round_original, sf);
                this.locus_round_click = zoom(this.locus_round_click, sf);
                roundW = (float) (this.locus_round_original.getWidth() / 2);
            }

            this.mPoints[0][0] = new Point(0, x + 0.0F + roundW, y + 0.0F + roundW);
            this.mPoints[0][1] = new Point(1, x + w / 2.0F, y + 0.0F + roundW);
            this.mPoints[0][2] = new Point(2, x + w - roundW, y + 0.0F + roundW);
            this.mPoints[1][0] = new Point(3, x + 0.0F + roundW, y + h / 2.0F);
            this.mPoints[1][1] = new Point(4, x + w / 2.0F, y + h / 2.0F);
            this.mPoints[1][2] = new Point(5, x + w - roundW, y + h / 2.0F);
            this.mPoints[2][0] = new Point(6, x + 0.0F + roundW, y + h - roundW);
            this.mPoints[2][1] = new Point(7, x + w / 2.0F, y + h - roundW);
            this.mPoints[2][2] = new Point(8, x + w - roundW, y + h - roundW);
            this.r = (float) (this.locus_round_original.getHeight() / 2);
            this.isCache = true;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (!this.isCache && !this.initCache()) {
            super.onDraw(canvas);
        } else {
            Point[][] var2 = this.mPoints;
            int var3 = var2.length;
            int i;

            if (this.mSelectedPoints.size() > 0) {
                int tmpAlpha = this.mPaint.getAlpha();
                this.mPaint.setAlpha(this.lineAlpha);
                Point tp = (Point) this.mSelectedPoints.get(0);

                for (i = 1; i < this.mSelectedPoints.size(); ++i) {
                    Point p = (Point) this.mSelectedPoints.get(i);
                    this.drawLine(canvas, tp, p);
                    tp = p;
                }

                if (this.movingNoPoint) {
                    this.mMoveingPoint.update(this.moveingX, this.moveingY);
                    this.drawLine(canvas, tp, this.mMoveingPoint);
                }

                this.mPaint.setAlpha(tmpAlpha);
                this.lineAlpha = this.mPaint.getAlpha();
            }


            for (i = 0; i < var3; ++i) {
                Point[] mPoint = var2[i];
                Point[] var6 = mPoint;
                int var7 = mPoint.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    Point p = var6[var8];
                    if (p.mState == 1) {
                        int r = (this.locus_round_click.getWidth() + this.locus_round_click.getHeight())/4;
                        canvas.drawBitmap(this.locus_round_click, p.mX - r, p.mY - r, this.mPaint);
                    } else {
                        int r = (this.locus_round_original.getWidth() + this.locus_round_original.getHeight())/4;
                        canvas.drawBitmap(this.locus_round_original, p.mX - r, p.mY - r, this.mPaint);
                    }
                }
            }



        }
    }

    private void drawLine(Canvas canvas, Point a, Point b) {
        canvas.save();
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(a.mX,a.mY,b.mX,b.mY,mPaint);
        canvas.restore();
    }

    private float getDegrees(Point a, Point b) {
        float ax = a.mX;
        float ay = a.mY;
        float bx = b.mX;
        float by = b.mY;
        float degrees = 0.0F;
        if (bx == ax) {
            if (by > ay) {
                degrees = 90.0F;
            } else if (by < ay) {
                degrees = 270.0F;
            }
        } else if (by == ay) {
            if (bx > ax) {
                degrees = 0.0F;
            } else if (bx < ax) {
                degrees = 180.0F;
            }
        } else if (bx > ax) {
            if (by > ay) {
                degrees = 0.0F;
                degrees += this.switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
            } else if (by < ay) {
                degrees = 360.0F;
                degrees -= this.switchDegrees(Math.abs(by - ay), Math.abs(bx - ax));
            }
        } else if (bx < ax) {
            if (by > ay) {
                degrees = 90.0F;
                degrees += this.switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
            } else if (by < ay) {
                degrees = 270.0F;
                degrees -= this.switchDegrees(Math.abs(bx - ax), Math.abs(by - ay));
            }
        }

        return degrees;
    }

    private float switchDegrees(float y, float x) {
        return (float) pointTotoDegrees((double) y, (double) x);
    }

    private Point checkSelectPoint(float x, float y) {
        Point[][] var3 = this.mPoints;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Point[] mPoint = var3[var5];
            Point[] var7 = mPoint;
            int var8 = mPoint.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Point p = var7[var9];
                if (p != null && checkInRound(p.mX, p.mY, this.r, (float) ((int) x), (float) ((int) y))) {
                    return p;
                }
            }
        }

        return null;
    }

    private void resetView() {
        Point p;
        for (Iterator var1 = this.mSelectedPoints.iterator(); var1.hasNext(); p.mState = 0) {
            p = (Point) var1.next();
        }

        this.mSelectedPoints.clear();
    }

    private int crossPoint(Point p) {
        return this.mSelectedPoints.contains(p) ? (this.mSelectedPoints.size() > 2 && ((Point) this.mSelectedPoints.get(this.mSelectedPoints.size() - 1)).mIndex != p.mIndex ? 2 : 1) : 0;
    }

    private void addPoint(Point point) {
        this.mSelectedPoints.add(point);
    }

    private String toPointString() {
        StringBuilder builder = new StringBuilder();
        boolean flag = true;

        Point p;
        for (Iterator var3 = this.mSelectedPoints.iterator(); var3.hasNext(); builder.append(p.mIndex)) {
            p = (Point) var3.next();
            if (flag) {
                flag = false;
            } else {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.movingNoPoint = false;
        float ex = event.getX();
        float ey = event.getY();
        boolean isFinish = false;
        Point p = null;
        switch (event.getAction()) {
            case 0:
                if (this.task != null) {
                    this.task.cancel();
                    this.task = null;
                    Log.d("task", "touch cancel()");
                }

                this.resetView();
                break;
            case 1:
            case 3:
                p = this.checkSelectPoint(ex, ey);
                this.checking = false;
                isFinish = true;
                break;
            case 2:
                p = this.checkSelectPoint(ex, ey);
                if (this.checking) {
                    if (p == null) {
                        this.movingNoPoint = true;
                        this.moveingX = ex;
                        this.moveingY = ey;
                    }
                } else if (p != null) {
                    this.checking = true;
                }
        }

        if (!isFinish && this.checking && p != null) {
            int rk = this.crossPoint(p);
            if (rk == 2) {
                this.movingNoPoint = true;
                this.moveingX = ex;
                this.moveingY = ey;
            } else if (rk == 0) {
                p.mState = 1;
                this.addPoint(p);
            }
        }

        if (isFinish) {
            if (this.mSelectedPoints.size() <= 1) {
                this.resetView();
            } else if (this.mCompleteListener != null) {
                this.mCompleteListener.onComplete(this.mSelectedPoints.size(), this.toPointString());
            }
        }

        this.postInvalidate();
        return true;
    }

    private void markError(long time) {
        Point p;
        for (Iterator var3 = this.mSelectedPoints.iterator(); var3.hasNext(); p.mState = 2) {
            p = (Point) var3.next();
        }

        this.clearPassword(time);
    }

    public void clearPassword() {
        this.clearPassword(0L);
    }

    public void clearPassword(long time) {
        if (time > 1L) {
            if (this.task != null) {
                this.task.cancel();
                Log.d("task", "clearPassword cancel()");
            }

            this.lineAlpha = 130;
            this.postInvalidate();
            this.task = new TimerTask() {
                @Override
                public void run() {
                    GestureView.this.resetView();
                    GestureView.this.postInvalidate();
                    GestureView.this.task = null;
                }
            };
            Log.d("task", "clearPassword schedule(" + time + ")");
            this.timer.schedule(this.task, time);
        } else {
            this.resetView();
            this.postInvalidate();
        }

    }

    public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    private static final class Point {
        static final int STATE_NORMAL = 0;
        static final int STATE_CHECK = 1;
        static final int STATE_CHECK_ERROR = 2;
        final int mIndex;
        float mX;
        float mY;
        int mState;

        Point(int index, float x, float y) {
            this.mIndex = index;
            this.mX = x;
            this.mY = y;
        }

        void update(float x, float y) {
            this.mX = x;
            this.mY = y;
        }
    }

    public interface OnCompleteListener {
        void onComplete(int var1, String var2);
    }

    private Bitmap zoom(Bitmap bitmap, float zf) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postScale(zf, zf);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }

    private double pointTotoDegrees(double y, double x) {
        return Math.toDegrees(Math.atan2(y, x));
    }

    private boolean checkInRound(float sx, float sy, float r, float x, float y) {
        return Math.sqrt((double) ((sx - x) * (sx - x) + (sy - y) * (sy - y))) < (double) r;
    }
}
