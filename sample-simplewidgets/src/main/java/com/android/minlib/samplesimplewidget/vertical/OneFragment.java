package com.android.minlib.samplesimplewidget.vertical;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.minlib.samplesimplewidget.R;
import com.android.minlib.simplewidget.verticalpager.VerticalListView;

public class OneFragment extends LinearLayout {
    VerticalListView listView;
    TextView textView;
    private String[] mListStr = {"姓名","性别","年龄","居住地","邮箱"};
    public OneFragment(Context context) {
        super(context);
        inflate(context, R.layout.vertical_fragment_one,this);
        listView = findViewById(R.id.verticallistview);
        textView = findViewById(R.id.textview);
        listView.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, mListStr));
    }

    public void setMoreText(String moreText) {
        textView.setText(moreText);
    }
}
