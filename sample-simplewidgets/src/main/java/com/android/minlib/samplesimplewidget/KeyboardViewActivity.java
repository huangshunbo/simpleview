package com.android.minlib.samplesimplewidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.minlib.simplewidget.keyboard.EditView;
import com.android.minlib.simplewidget.keyboard.SKeyboardView;

public class KeyboardViewActivity extends AppCompatActivity{
    EditView mEditView;
    SKeyboardView mSKeyboardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        mEditView = findViewById(R.id.editview);
        mSKeyboardView = findViewById(R.id.skeyboardview);
        mEditView.setEditView(mSKeyboardView,true);
        mEditView.setOnKeyboardListener(new EditView.OnKeyboardListener() {
            @Override
            public void onHide(boolean isCompleted) {

            }

            @Override
            public void onShow() {

            }

            @Override
            public void onPress(int primaryCode) {

            }
        });
    }
}
