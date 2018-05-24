package com.android.minlib.samplesimplewidget.vertical;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.android.minlib.samplesimplewidget.R;

public class TwoFragment extends LinearLayout {
    public TwoFragment(Context context) {
        super(context);
        inflate(context, R.layout.vertical_fragment_two,this);
    }
}
