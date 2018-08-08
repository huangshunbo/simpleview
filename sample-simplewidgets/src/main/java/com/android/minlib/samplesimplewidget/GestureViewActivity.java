package com.android.minlib.samplesimplewidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.minlib.simplewidget.gesturecipher.GestureView;

public class GestureViewActivity extends AppCompatActivity{

    GestureView mGestureView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mGestureView = findViewById(R.id.gestureview);
        mGestureView.setOnCompleteListener(new GestureView.OnCompleteListener() {
            @Override
            public void onComplete(int var1, String var2) {
                mGestureView.clearPassword();
            }
        });
        mGestureView.clearPassword();
    }
}
