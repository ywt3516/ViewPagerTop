package com.example.ywt.viewpagertop;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ywt.viewpagertop.fragment.ActivityCollectionFragment;
import com.example.ywt.viewpagertop.fragment.ArticleCollectionFragment;
import com.example.ywt.viewpagertop.fragment.GoodsCollectionFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private TextView tv_goods;
    private TextView tv_activity;
    private TextView tv_article;

    //作为指示标签的按钮
    private ImageView image_cursor;
    //标志指示标签的横坐标
    float cursorX = 0;
    //所有按钮的宽度的集合
    private int[] widthArgs;
    //所有TextView的集合
    private TextView[] tvArgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tv_goods = findViewById(R.id.tv_goods);
        tv_activity = findViewById(R.id.tv_activity);
        tv_article = findViewById(R.id.tv_article);
        image_cursor = findViewById(R.id.image_cursor);

        initView();
    }

    private void initView() {
        viewPager.setOnPageChangeListener(this);
        tv_goods.setOnClickListener(this);
        tv_activity.setOnClickListener(this);
        tv_article.setOnClickListener(this);

        fragments = new ArrayList<Fragment>();
        fragments.add(new GoodsCollectionFragment());
        fragments.add(new ActivityCollectionFragment());
        fragments.add(new ArticleCollectionFragment());
        Adapter adapter = new Adapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);


        tvArgs = new TextView[]{tv_goods, tv_activity, tv_article};
        image_cursor.setBackgroundColor(getResources().getColor(R.color.white));
        //通过此方法设置指示器的初始大小和位置
        tv_goods.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image_cursor.getLayoutParams();
                //减去边距*2，以对齐标题栏文字
                lp.width = tv_goods.getWidth() - tv_goods.getPaddingLeft() * 2;
                image_cursor.setLayoutParams(lp);
                image_cursor.setX(tv_goods.getPaddingLeft());
            }
        });


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (widthArgs == null) {
            widthArgs = new int[]{tv_goods.getWidth(), tv_activity.getWidth(), tv_article.getWidth()};
        }
        //每次滑动首先重置所有按钮的颜色
        cursorAnim(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods:
                viewPager.setCurrentItem(0);
                cursorAnim(0);
                break;
            case R.id.tv_activity:
                viewPager.setCurrentItem(1);
                cursorAnim(1);
                break;
            case R.id.tv_article:
                viewPager.setCurrentItem(2);
                cursorAnim(2);
                break;
        }
    }

    private void cursorAnim(int curItem) {
        //每次调用，就将指示器的横坐标设置为0，即开始的位置
        cursorX = 0;
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image_cursor.getLayoutParams();
        //减去边距*2，以对齐标题栏文字
        lp.width = widthArgs[curItem] - tvArgs[0].getPaddingLeft() * 2;
        image_cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for (int i = 0; i < curItem; i++) {
            cursorX = cursorX + tvArgs[i].getWidth();
        }
        //再加上当前页面的左边距，即为指示器当前应处的位置
        image_cursor.setX(cursorX + tvArgs[curItem].getPaddingLeft());
    }
}
















