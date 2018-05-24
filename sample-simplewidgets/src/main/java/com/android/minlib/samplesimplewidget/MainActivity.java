package com.android.minlib.samplesimplewidget;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.minlib.samplesimplewidget.tab.RefreshLayoutActivity;
import com.android.minlib.samplesimplewidget.vertical.VerticalActivity;
import com.android.minlib.simplewidget.simple.CapacityColorSizeTextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static Application application;

    ListView mListView;
    private static final String[] strs =
            {
                    "TitleFlodView","GuestureView","KeyboardView","SimpleView","RefreshLayout + TabViewPagerManager + FilterBar",
                    "CapacityColorSizeTextView","VerticalPagerAdapter",

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getApplication();
        mListView = new ListView(this);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        mListView.setOnItemClickListener(this);
        setContentView(mListView);

        HashMap<String,String> map = new HashMap<>();
        map.put("name","huangshunbo");
    }
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                startActivity(new Intent(this,FlodViewActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,GestureViewActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,KeyboardViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,SimpleViewActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, RefreshLayoutActivity.class));
                break;
            case 5:
                CapacityColorSizeTextView capacityColorSizeTextView = new CapacityColorSizeTextView(this);
                capacityColorSizeTextView.create("这是一段这是一段这是一段",R.color.color_black,18)
                        .create("荡气回肠荡气回肠荡气回肠",R.color.color_orange,22)
                        .create("铿锵有力铿锵有力铿锵有力",R.color.color_gray,28)
                        .create("连绵不断连绵不断连绵不断",R.color.color_gray,22)
                        .create("de描述性文字呦呦呦呦de描述性文字呦呦呦呦de描述性文字呦呦呦呦",R.color.colorPrimary,18)
                        .build();
                mListView.addFooterView(capacityColorSizeTextView);
                break;
            case 6:
                startActivity(new Intent(this,VerticalActivity.class));
                break;
        }
    }

}
