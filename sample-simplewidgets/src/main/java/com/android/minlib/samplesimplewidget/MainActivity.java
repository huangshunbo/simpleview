package com.android.minlib.samplesimplewidget;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.minlib.samplesimplewidget.tab.RefreshLayoutActivity;
import com.android.minlib.samplesimplewidget.vertical.VerticalActivity;
import com.android.minlib.simplewidget.simple.CapacityColorSizeTextView;
import com.android.minlib.simplewidget.simple.CountNumberTextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    public static Application application;

    ListView mListView;
    private static final String[] strs =
            {
                    "TitleFlodView","GuestureView","KeyboardView","SimpleView","RefreshLayout + TabViewPagerManager + FilterBar",
                    "CapacityColorSizeTextView","CountNumberTextView","VerticalPagerAdapter","TabPageIndicator"

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getApplication();
        mListView = new ListView(this);
        mListView.setAdapter(new MyAdapter());
        setContentView(mListView);

        HashMap<String,String> map = new HashMap<>();
        map.put("name","huangshunbo");
    }
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void itemClick(int position) {
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
                capacityColorSizeTextView.create("这是一段这是一段这是一段", Color.YELLOW,18)
                        .create("荡气回肠荡气回肠荡气回肠",R.color.color_orange,22)
                        .create("铿锵有力铿锵有力铿锵有力",R.color.color_gray,28)
                        .create("连绵不断连绵不断连绵不断",R.color.color_gray,22)
                        .create("de描述性文字呦呦呦呦de描述性文字呦呦呦呦de描述性文字呦呦呦呦",R.color.colorPrimary,18)
                        .build();
                mListView.addFooterView(capacityColorSizeTextView);
                break;
            case 6:
                CountNumberTextView countNumberTextView = new CountNumberTextView(this);
                countNumberTextView.setTextSize(40);
                countNumberTextView.setTextColor(Color.BLACK);
                mListView.addFooterView(countNumberTextView);
                countNumberTextView.showNumberWithAnimation(1234.56,CountNumberTextView.FLOATREGEX_QUANTILE);
                break;
            case 7:
                startActivity(new Intent(this,VerticalActivity.class));
                break;
            case 8:
                startActivity(new Intent(this,TabPageIndicatorActivity.class));
                break;
        }
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public Object getItem(int position) {
            return strs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = View.inflate(MainActivity.this,R.layout.item_main,null);
                viewHolder.textView = convertView.findViewById(R.id.item_main_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(strs[position]);
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick(position);
                }
            });
            return convertView;
        }

    }

    class ViewHolder
    {
        TextView textView;
    }

}
