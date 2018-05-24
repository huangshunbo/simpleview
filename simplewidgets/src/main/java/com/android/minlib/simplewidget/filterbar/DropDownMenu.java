package com.android.minlib.simplewidget.filterbar;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.minlib.simplewidget.R;

import java.util.ArrayList;

/**
 * @author: huangshunbo
 * @Filename: DropDownMenu
 * @Description: 筛选下拉列表
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/3/15 10:25
 */
public class DropDownMenu extends DrowDownPopupWindow{
    private Context mContext = null;
    private View mShadowFrame = null;
    private ListView mListView = null;
    private BaseAdapter mBaseAdapter = null;
    private View rootView = null;
    private String[] items;
    private int checked = 0;
    private int textcolor = R.color.color_black;
    private int rightIcon = 0;
    ArrayList<String> mData = new ArrayList<String>();


    public DropDownMenu(@NonNull Context context,View standardView) {
        super(context,standardView);
        mContext = context;
        initView();
    }

    public void setTheme(int textcolor,int rightIcon){
        if(textcolor != 0){
            this.textcolor = textcolor;
        }
        if(rightIcon != 0){
            this.rightIcon = rightIcon;
        }
    }

    private void initView() {
        rootView = View.inflate(mContext, R.layout.filterbar_view_dropdown_menu, null);
        setContentView(rootView);
        mListView = rootView.findViewById(R.id.common_lv_filter_bar_dropdown_list);
        mShadowFrame = rootView.findViewById(R.id.common_view_filter_bar_dropdown_shadow);
        mBaseAdapter = new DropDownAdapter(mContext);
        mListView.setAdapter(mBaseAdapter);
        mShadowFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setOutsideTouchable(false);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(null);
    }

    public void show() {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            standardView.getGlobalVisibleRect(visibleFrame);
            int height = standardView.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
            showAsDropDown(standardView, 0, 0);
        } else {
            showAsDropDown(standardView, 0, 0);
        }
    }

    @Override
    public void changeVisiable() {
        if(isShowing()){
            dismiss();
        }else {
            show();
        }
    }

    /**
     * <br> Description: 设置列表item及默认选中项
     * <br> Author:      huangshunbo
     * <br> Date:        2018/3/15 10:26
     */
    @Override
    public void showItems(String[] items, int[] checks) {
        this.items = items;
        mData.clear();
        for (int i = 0; i < items.length; i++) {
            mData.add(items[i]);
        }
        if (checks.length > 0 && checks[0] >= 0 && checks[0] < mData.size()) {
            this.checked = checks[0];
        }
        mBaseAdapter.notifyDataSetChanged();
    }

    class DropDownAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public DropDownAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.filterbar_list_item_dropdown, null);
                viewHolder.tvTitle = convertView.findViewById(R.id.commmon_tv_fillter_bar_dropdown_item_title);
                viewHolder.ivIcon = convertView.findViewById(R.id.commmon_tv_fillter_bar_dropdown_item_ic);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(mData.get(position));
            if(rightIcon != 0){
                viewHolder.ivIcon.setImageDrawable(ContextCompat.getDrawable(mContext,rightIcon));
            }
            if (position == checked) {
                viewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, textcolor));
                viewHolder.ivIcon.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_gray));
                viewHolder.ivIcon.setVisibility(View.INVISIBLE);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checked = position;
                    sendItemCheckd(new int[]{position},new String[]{mData.get(position)});
                    dismiss();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}
