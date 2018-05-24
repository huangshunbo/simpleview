package com.android.minlib.samplesimplewidget.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.minlib.samplesimplewidget.R;
import com.android.minlib.simplewidget.filterbar.CommonFilterBar;
import com.android.minlib.simplewidget.filterbar.DropDownMenu;
import com.android.minlib.simplewidget.filterbar.FilterBarType;
import com.android.minlib.simplewidget.filterbar.DefaultFilterTheme;
import com.android.minlib.simplewidget.filterbar.IDropDownObserver;
import com.android.minlib.simplewidget.filterbar.IFilterThemeObserver;
import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment implements IDropDownObserver,IFilterThemeObserver{
    CommonFilterBar mCommonFilterBar;
    DropDownMenu dropDownMenu;
    private int position2 = 0;
    private int position4 = 0;
    private FilterBarType two,four;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_two_refresh,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCommonFilterBar = view.findViewById(R.id.filterbar);

        mCommonFilterBar.addFilterThemeObserver(new DefaultFilterTheme());
        mCommonFilterBar.addDropDownObserver(new DefaultFilterTheme());
        mCommonFilterBar.addFilterThemeObserver(this);
        mCommonFilterBar.addDropDownObserver(this);
        mCommonFilterBar.build(
                FilterBarType.create(1,"标题1"),
                two = FilterBarType.create(2,"标题2", R.drawable.common_filter_bar_title_image_filter),
                FilterBarType.create(3,"标题3", R.drawable.common_filter_bar_title_image_up, R.drawable.common_filter_bar_title_image_down),
                four = FilterBarType.create(4,"标题4", R.drawable.common_filter_bar_title_image_filter)
        );
        mCommonFilterBar.setOrientation(LinearLayout.HORIZONTAL);

        dropDownMenu = new DropDownMenu(getContext(),mCommonFilterBar);
        dropDownMenu.setTheme(R.color.color_orange,R.mipmap.common_filter_checked);
        mCommonFilterBar.setDrowDownPopupWindow(dropDownMenu);
    }

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TitleViewHolder viewHolder) {
        commonFilterBar.dismissDrowDown();
    }

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.ArrowViewHolder viewHolder) {
        if(filterBarType == two){
            commonFilterBar.showDrowDown(new String[]{"标题1","标题2","标题3","标题4","标题5"},new int[]{position2});
        }else if(filterBarType == four){
            commonFilterBar.showDrowDown(new String[]{"标题6","标题7","标题8","标题9","标题0"},new int[]{position4,0});
        }else{
            commonFilterBar.dismissDrowDown();
        }
    }

    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TriangleViewHolder viewHolder, CommonFilterBar.TRIANGLE_TYPE type) {
        commonFilterBar.dismissDrowDown();
    }

    @Override
    public void onItemSelect(CommonFilterBar commonFilterBar,FilterBarType filterBarType,int[] position, String[] item) {
        if(filterBarType == two){
            position2 = position[0];
        }else if(filterBarType == four){
            position4 = position[0];
        }
    }

    @Override
    public void onDissmiss(CommonFilterBar commonFilterBar,FilterBarType filterBarType) {

    }

}
