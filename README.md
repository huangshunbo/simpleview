#### Summary
---
常见控件库，包含
CommonFilterBar 筛选列表
FlodView、TextFlodView、TitleFlodView 折叠控件
GestureView 手势密码
SKeyboardView+EditView安全键盘
AutoWrapTextView解决中英文混排的TextView
AvatarImageView 圆形图片或文字控件
CapacityColorSizeText 支持不同文字大小颜色的TextView
CountNumberTexView 数字动画
LinearWrapLayout 流式布局,自动换行
TitleView 标题View
TabViewPagerManager 底部TAB导航栏
TabLayout 顶部分页条或底部TAB导航栏都可以
VerticalListView、VerticalScrollView、VerticalViewPager、VerticalWebView 类淘宝详情的滑动效果
SwipeLayout 滑动删除Item
Banner https://github.com/bingoogolapple/BGABanner-Android


#### Getting Started
---

加入依赖
```Java
allprojects {
  repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
    implementation 'com.github.huangshunbo:simpleview:lastest.release'
}
```


##### CommonFilterBar 筛选列表
这部分包含CommonFilterBar主体控件、FilterBarType每个筛选Item、DrowDownPopupWindow 下拉列表
其中FilterBarType通过以下三种方式来创建不同类型的筛选按钮
```Java
//包含一个标题TextView的筛选按钮
FilterBarType.create(int id, String title)
//包含一个标题TextView和一个右侧图标ImagView
FilterBarType.create(int id, String title,@DrawableRes int rightRes)
//包含一个标题TextView和两个右侧图标的ImageView
FilterBarType.create(int id, String title,@DrawableRes int rightUpRes,@DrawableRes int rightDownRes)
```
DrowDownPopupWindow是一个抽象类,有默认实现DropDownMenu 该弹框包含一个TextView和一个ImageView组成Item的ListView

简单使用姿势
不居中加入CommonFilterBar
```Java
<com.android.minlib.simplewidget.filterbar.CommonFilterBar
    android:id="@+id/filterbar"
    android:layout_width="match_parent"
    android:layout_height="48dp"/>
```

```Java
mCommonFilterBar = view.findViewById(R.id.filterbar);
//添加筛选按钮的事件监听,采用监听者模式,可多方监听
mCommonFilterBar.addFilterThemeObserver(new DefaultFilterTheme());
//添加下拉弹框的事件监听,采用监听者模式,可多放监听
mCommonFilterBar.addDropDownObserver(new DefaultDropDownTheme());
mCommonFilterBar.addFilterThemeObserver(this);
mCommonFilterBar.addDropDownObserver(this);
//创建四个筛选按钮,分别包含了前面所述的三种类型,同一个CommonFilterBar下id不能重复
mCommonFilterBar.build(
    one = FilterBarType.create(1,"标题1"),
    two = FilterBarType.create(2,"标题2", R.drawable.common_filter_bar_title_image_filter),
    three = FilterBarType.create(3,"标题3", R.drawable.common_filter_bar_title_image_up, R.drawable.common_filter_bar_title_image_down),
    four = FilterBarType.create(4,"标题4", R.drawable.common_filter_bar_title_image_filter)
);

//初始化DropDownMenu,这里使用默认的DropDownMenu,不同的样式可以继承DrowDownPopupWindow实现一个
dropDownMenu = new DropDownMenu(getContext(),mCommonFilterBar);
dropDownMenu.setTheme(R.color.color_orange,R.mipmap.common_filter_checked);
mCommonFilterBar.setDrowDownPopupWindow(dropDownMenu);
```
这里我们筛选按钮和下拉弹框分别添加了两个监听,一个用来控制样式及显示,一个用来控制数据
先看DefaultFilterTheme 和 DefaultDropDownTheme 两个的样式
```Java
public class DefaultFilterTheme implements IFilterThemeObserver {
    //包含一个标题TextView类型的筛选按钮被点击的样式处理,这里的处理就是设置这个TextView的Activated为true
    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TitleViewHolder viewHolder) {
        viewHolder.tvTitle.setActivated(true);
    }
    //包含一个标题TextView和一个右侧ImageView类型的筛选按钮被点击的样式处理
    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.ArrowViewHolder viewHolder) {
        viewHolder.tvTitle.setActivated(true);
        viewHolder.ivRight.setActivated(true);
    }
    //包含一个标题TextView和两个右侧ImageView类型的筛选按钮被点击的样式处理
    //type 有三种类型NONE无状态 UP向上 DOWN向下
    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TriangleViewHolder viewHolder, CommonFilterBar.TRIANGLE_TYPE type) {
        viewHolder.tvTitle.setActivated(true);
        if(type == CommonFilterBar.TRIANGLE_TYPE.NONE){
            viewHolder.ivDown.setActivated(true);
            viewHolder.ivUp.setActivated(false);
        }else if(type == CommonFilterBar.TRIANGLE_TYPE.UP){
            viewHolder.ivDown.setActivated(true);
            viewHolder.ivUp.setActivated(false);
        }else if(type == CommonFilterBar.TRIANGLE_TYPE.DOWN){
            viewHolder.ivUp.setActivated(true);
            viewHolder.ivDown.setActivated(false);
        }
    }

}
```
```Java
public class DefaultDropDownTheme implements IDropDownObserver {
    //下拉列表中被选择项目的样式处理,这里只是把筛选按钮的文字替换为被选中的文字,而其他样式与CommonFilterBar没有关联的可以在DrowDownPopupWindow的子类中处理好
    @Override
    public void onItemSelect(CommonFilterBar commonFilterBar,FilterBarType filterBarType, int[] position, String[] item) {
        filterBarType.setTitle(item[0]);
    }
    //下拉列表某Item被点击后,收起下拉列表的回调,这里会把CommonFilterBar所有筛选按钮的状态清空,也可根据需要为个别筛选按钮设置样式
    @Override
    public void onDissmiss(CommonFilterBar commonFilterBar,FilterBarType filterBarType) {
        commonFilterBar.unActivate(filterBarType);
    }
}
```
之后是数据的处理,可以发现我们后面两个监听都是this来接收的,在当前类中进行数据处理是比较方便的。
```Java
    //包含一个标题TextView类型的筛选按钮被点击的数据处理,这里会把下拉列表收起来(如果有的话),然后可以根据筛选按钮做一个数据的重新排序请求
    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TitleViewHolder viewHolder) {
        commonFilterBar.dismissDrowDown();
        // TODO reloadData
    }
    //包含一个标题TextView和一个右侧ImageView类型的筛选按钮被点击的数据处理,这里的两个按钮都需要弹出下拉列表,我们把数据传进去由默认实现DropDownMenu来处理具体显示,最后一个position2和position4传入的是弹出列表默认选中的项
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
//包含一个标题TextView和两个右侧ImageView类型的筛选按钮被点击的数据处理,这里会把下拉列表收起来(如果有的话),然后可以根据筛选按钮做一个数据的重新排序请求
    @Override
    public void itemClick(CommonFilterBar commonFilterBar, FilterBarType filterBarType, CommonFilterBar.TriangleViewHolder viewHolder, CommonFilterBar.TRIANGLE_TYPE type) {
        commonFilterBar.dismissDrowDown();
        //TODO reloadData
    }
```
下拉列表的数据处理,这里处理的主要是记录选中的项position,此外一般还有数据的重新加载
```Java
    //
    @Override
    public void onItemSelect(CommonFilterBar commonFilterBar,FilterBarType filterBarType,int[] position, String[] item) {
        if(filterBarType == two){
            position2 = position[0];
            //TODO reloadData
        }else if(filterBarType == four){
            position2 = position[4];
            //TODO reloadData
        }
    }
    @Override
    public void onDissmiss(CommonFilterBar commonFilterBar,FilterBarType filterBarType) {

    }
```


##### FlodView、TextFlodView、TitleFlodView 折叠控件
FlodView 只包含两个子View,点击第一个子View时隐藏或显示第二个子View
```Java
<com.android.minlib.simplewidget.flod.FlodView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    app:foldView="@id/title1"
    app:contentView="@id/content1"
    android:paddingLeft="50dp">
    <TextView
        android:id="@+id/title1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="标题1"
        android:textSize="18dp"/>
    <TextView
        android:id="@+id/content1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="内容1内容1内容1内容1内容1\n内容1内容1内容1"
        android:textSize="15dp"/>
</com.android.minlib.simplewidget.flod.FlodView>
```
TextFlodView 包含两个子View,一为TextView一为ImageView图标,当TextView超过一定行数可进行折叠收起与展开
```Java
<com.android.minlib.simplewidget.flod.TextFlodView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    app:content="@id/content4"
    app:arrow="@id/arrow4"
    app:flodLines="2">
    <TextView
        android:id="@+id/content4"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="111111222223333344444555556666677777888888999990000001111112222233333444445555566666777778888889999900000011111122222333334444455555666667777788888899999000000"
        />
    <ImageView
        android:id="@+id/arrow4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/flod_arrow_open"
        android:layout_gravity="center"/>
</com.android.minlib.simplewidget.flod.TextFlodView>
```
TitleFlodView 本身包含一个TextView的标题,子View只能有一个,可以是任意View
```Java
<com.android.minlib.simplewidget.flod.TitleFlodView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:titleTxt="标题3"
    app:titleColor="#eacd78"
    app:titleSize="22dp"
    app:contentView="@id/content3"
    app:arrowIcon="@drawable/flod_arrow_open"
    android:paddingLeft="15dp">
    <TextView
        android:id="@+id/content3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="内容123内容123内容123内容123内容123"/>
</com.android.minlib.simplewidget.flod.TitleFlodView>
```

##### GestureView 手势密码
```Java
<com.android.minlib.simplewidget.gesturecipher.GestureView
    android:id="@+id/gestureview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="30dp"
    app:drawablePointClick="@drawable/gesture_point_click"
    app:drawablePointOriginal="@drawable/gesture_point_original"
    app:linecolor="@color/colorPrimary"
    app:lineWidth="5dp"
/>
```
app:drawablePointClick 点击时显示的点
app:drawablePointOriginal 正常状态下的点
app:linecolor 连接线的颜色
app:lineWidth 连接线的宽度

GestureView暴露的方法
```Java
mGestureView.setOnCompleteListener(new GestureView.OnCompleteListener() {
    @Override
    public void onComplete(int var1, String var2) {
        mGestureView.clearPassword();
    }
});
```


##### SKeyboardView+EditView安全键盘
```Java
<com.android.minlib.simplewidget.keyboard.EditView
    android:id="@+id/editview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="20dp"
    android:layout_marginRight="20dp"
    android:layout_marginTop="50dp"
    android:gravity="center"
    android:hint="身份证号: "
    android:textSize="24sp"
    android:textColor="@android:color/black"
    android:textColorHint="#BABABA"/>
<com.android.minlib.simplewidget.keyboard.SKeyboardView
    android:id="@+id/skeyboardview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    android:background="#999999"
    android:focusable="true"
    android:focusableInTouchMode="true"
    android:keyBackground="@drawable/selector_keyboard_key"
    android:keyPreviewHeight="64dp"
    android:keyPreviewLayout="@layout/view_keyboard_preview"
    android:keyTextColor="@color/default_keyTextColor"
    android:keyTextSize="26dp"
    android:labelTextSize="24dp"
    android:paddingBottom="8dp"
    android:paddingTop="8dp"
    android:shadowColor="@color/default_keyShadowColor"
    android:shadowRadius="0.0"
    android:visibility="gone" />
```

```Java
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
```

##### AutoWrapTextView解决中英文混排的TextView

##### AvatarImageView 圆形图片或文字控件
这个控件是从github上拷贝过来的,进行了一些小问题的修复,必须写死设置控件的宽高值
https://github.com/Carbs0126/AvatarImageView
```Java
<com.android.minlib.simplewidget.simple.AvatarImageView
    android:id="@+id/dotView"
    android:layout_width="20dp"
    android:layout_height="20dp"
    android:layout_alignRight="@id/main_tab_item_content"
    android:layout_alignTop="@id/main_tab_item_content"
    android:layout_marginRight="-10dp"
    android:layout_marginTop="-8dp"
    app:aiv_BoarderColor="@color/color_black"
    app:aiv_BoarderWidth="2dp"
    app:aiv_CornerRadius="45dp"
    app:aiv_ShowBoarder="true"
    app:aiv_TextColor="@color/color_white"
    app:aiv_TextMaskRatio="0.6"
    app:aiv_TextSizeRatio="0.6"/>
```
app:aiv_BoarderColor 外圈的颜色
app:aiv_BoarderWidth 外圈的宽度
app:aiv_CornerRadius 边框弧度
app:aiv_ShowBoarder 是否显示外圈
app:aiv_TextColor 如果是文字,则文字颜色
app:aiv_TextMaskRatio 如果是图片,则图片大小与整个控件大小的缩放系数
app:aiv_TextSizeRatio 如果是文字,则文字大小与整个控件大小的缩放系数

AvatarImageView暴露的方法
```Java
nrvCorner.setBitmap(); //设置图片
nrvCorner.setDrawable(); //设置图片
nrvCorner.setImageDrawable(); //设置图片
nrvCorner.setImageResource(); //设置图片
nrvCorner.setTextAndColor(); //设置文字和背景色
nrvCorner.setTextColor(); //设置文字颜色
```

##### CapacityColorSizeText 支持不同文字大小颜色的TextView
```Java
CapacityColorSizeTextView capacityColorSizeTextView = new CapacityColorSizeTextView(this);
capacityColorSizeTextView.create("这是一段这是一段这是一段", Color.YELLOW,dip2px(18))
    .create("荡气回肠荡气回肠荡气回肠",R.color.color_orange,dip2px(22))
    .create("铿锵有力铿锵有力铿锵有力",R.color.color_gray,dip2px(28))
    .create("连绵不断连绵不断连绵不断",R.color.color_gray,dip2px(22))
    .create("de描述性文字呦呦呦呦de描述性文字呦呦呦呦de描述性文字呦呦呦呦",R.color.colorPrimary,dip2px(18))
    .build();
```

##### CountNumberTexView 数字动画
```Java
CountNumberTextView countNumberTextView = new CountNumberTextView(this);
countNumberTextView.setTextSize(40);
countNumberTextView.setTextColor(Color.BLACK);
countNumberTextView.showNumberWithAnimation(1234.56,CountNumberTextView.FLOATREGEX_QUANTILE);
```

##### LinearWrapLayout 流式布局,自动换行

##### TitleView 标题View
控件包含 左右各两个TextView按钮两个ImageView图标按钮,中间一个TextView主标题,一个TextView副标题,支持中英文混排
```Java
<com.android.minlib.simplewidget.simple.TitleView
        android:layout_width="match_parent"
        android:layout_height="45dp"
        app:title="这是一个Title"
        app:title_txt_color="#cead78"
        app:title_txt_size="20dp"
        app:subtitle="副标题登场"
        app:subtitle_txt_color="#999999"
        app:subtitle_txt_size="15dp"
        app:item_margin="5dp"
        app:item_txt_size="15dp"
        app:item_txt_color="#333333"
        app:left_txt_1="返回"
        app:left_txt_2="关闭"
        app:left_icon_1="@mipmap/icon_back"
        app:left_icon_2="@mipmap/icon_back"
        app:left_margin="5dp"
        app:right_txt_1="菜单菜单"
        app:right_txt_2="菜单"
        app:right_margin="15dp"
        app:right_icon_1="@drawable/titleview_icon_close"
        app:right_icon_2="@drawable/titleview_icon_close"
        />
```
暴露的方法
```Java
titleView.getIvLeftIcon1();
titleView.getIvLeftIcon2();
titleView.getTvLeftTxt1();
titleView.getTvLeftTxt2();
titleView.getIvRightIcon1();
titleView.getIvRightIcon2();
titleView.getTvRightTxt1();
titleView.getTvRightTxt2();
titleView.getTvTitle();
titleView.getTvSubTitle();
//TitleListener有每个按钮的点击回调,但是都有空实现,需要时再进行覆写
titleView.setTitleListener(new TitleView.TitleListener(){
    @Override
    public void onLeftIcon1Click() {
        super.onLeftIcon1Click();
    }
});
```

##### TabViewPagerManager 底部TAB导航栏
```Java
public class MainActivity extends AppCompatActivity{

    List<Fragment> tabFragmentList = new ArrayList<>();
    TabViewPagerManager tabViewPagerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment home = getSavedInstanceFragment(savedInstanceState,OneFragment.class);
        Fragment widget = getSavedInstanceFragment(savedInstanceState,TwoFragment.class);
        Fragment me = getSavedInstanceFragment(savedInstanceState,ThreeFragment.class);
        tabFragmentList.add(home);
        tabFragmentList.add(widget);
        tabFragmentList.add(me);
        tabViewPagerManager = findViewById(R.id.activity_tagviewpager);
        //创建三个TAB并把Fragment列表传入
        tabViewPagerManager
                .createTab("ONE",R.drawable.icon_home)
                .createTab("TWO",R.drawable.icon_widget)
                .createTab("THREE",R.drawable.icon_mine)
                .build(tabFragmentList,getSupportFragmentManager());
        //更新第1个TAB的角标
        tabViewPagerManager.updateTabNum(1,10);
    }

    public <T extends Fragment> T getSavedInstanceFragment(Bundle savedInstanceState,Class<T> mClass) {
        Fragment mFragment = null;
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, mClass.getName());
        }
        if (mFragment == null) {
            try {
                mFragment = mClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            mFragment.onAttach((Context) this);
        }
        return (T) mFragment;
    }

}
```

##### TabLayout
在原有的TabLayout基础上新增了app:tabIndicatorWidth="withIcon"参数用以修改indicator的宽度
fill 宽度充满整个TAB
withIcon 宽度与Icon相同
withTitle 宽度与Title相同

##### VerticalViewPager （附属控件 VerticalListView、VerticalScrollView、VerticalWebView） 类淘宝详情的滑动效果
主要在布局中使用 VerticalViewPager,其他附属控件主要是作为VerticalViewPager的子View,这些附属控件处理了滑动间的冲突问题,配套使用。一般两个不同页面用两个Fragment装载,当Fragment中使用WebView等控件时,请使用相对应的附属控件VerticalWebView等

VerticalViewPager使用姿势

```Java
public class VerticalActivity extends AppCompatActivity {

    VerticalViewPager verticalViewPager;
    OneFragment oneFragment;
    TwoFragment twoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verticalViewPager = new VerticalViewPager(this);
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(R.color.color_orange)));
        verticalViewPager.setAdapter(mPagerAdapter);
        verticalViewPager.setOnPageChangeListener(mPageChangeListener);

        setContentView(verticalViewPager);
        oneFragment = new OneFragment(this);
        twoFragment = new TwoFragment(this);
    }

    VerticalPagerAdapter mPagerAdapter = new VerticalPagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(position == 0 ? oneFragment : twoFragment);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(position == 0 ? oneFragment : twoFragment);
            return position == 0 ? oneFragment : twoFragment;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

    VerticalViewPager.OnPageChangeListener mPageChangeListener = new VerticalViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                oneFragment.setMoreText("向上滑动，查看更多详情");
                verticalViewPager.setDeviation(0);
            } else {
                oneFragment.setMoreText("向下滑动，查看项目");
                verticalViewPager.setDeviation(0);
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
```

##### SwipeLayout 滑动删除Item
```Java
<com.android.minlib.simplewidget.simple.SwipeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <LinearLayout
        android:id="@+id/target"
        android:layout_width="100dp"
        android:layout_height="50dp"
        android:orientation="horizontal">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorPrimary"
            android:text="删除"
            android:gravity="center"
            android:textColor="@color/color_white"
            android:textSize="22dp"/>
    </LinearLayout>
    <TextView
        android:id="@+id/item_main_title"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:textColor="@color/colorPrimary"
        android:textSize="18dp"
        android:gravity="center_vertical"/>

</com.android.minlib.simplewidget.simple.SwipeLayout>
```



#### Known Issues
---
暂时没有收到任何反馈，有任何疑问或需求，可提issue。
#### Support
---
黄顺波