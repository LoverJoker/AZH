package com.example.joker.azh;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import Fragments.Ghjzy_Fragment;
import Fragments.Zhrb_Fragment;

public class main_Activity extends SlidingActivity  {

    private Button mBt_zhrb;
    private Button mBt_ghjzy;
    private Button mBt_hqxrb;
    private int ZHRB_FRAGMENT = 2 *16 ;
    private int GHJZY_FRAGMRNT = 3* 16;
    private int HQXRB_FRAGMENT = 4 *16 ;
    private int Now_Fragment ; //判断在哪个fragment里面
    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);
        Fresco.initialize(this);
        initSlidingMenu();
        initFragments();
        initView();
        initLeftOnClick();
    }

    /*获得控件方法*/
    private void initView() {
        mBt_zhrb = (Button) findViewById(R.id.bt_zhrb);
        mBt_ghjzy = (Button) findViewById(R.id.bt_ghjzy);
        mBt_hqxrb = (Button) findViewById(R.id.bt_hqxrb);
    }

    /*
    * 侧边栏的点击事件
    * */
    private void initLeftOnClick() {
        mBt_zhrb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Now_Fragment != ZHRB_FRAGMENT) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fl_main,new Zhrb_Fragment());
                    transaction.commit() ;
                    Now_Fragment = ZHRB_FRAGMENT ;
                }
                ToogleSlidingMdnu();
            }
        });

        mBt_ghjzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Now_Fragment != GHJZY_FRAGMRNT){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fl_main,new Ghjzy_Fragment());
                    transaction.commit();
                    Now_Fragment = GHJZY_FRAGMRNT;
                }
                ToogleSlidingMdnu();
            }
        });
    }

    /*
    * 初始化Fragment
    * */
    private void initFragments() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_main,new Zhrb_Fragment());
        transaction.commit();
    }

    /*
    * 初始化侧边栏
    * */
    private void initSlidingMenu() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setBehindOffset(40+width / 2);
    }

    /*
    * 控制侧边栏收放开关
    * */
    private void ToogleSlidingMdnu(){
        mSlidingMenu.toggle();
    }



}
