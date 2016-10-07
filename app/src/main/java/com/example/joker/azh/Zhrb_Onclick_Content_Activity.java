package com.example.joker.azh;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import Bean.Zhrb_content_bean;
import Utils.JsonUtils;
import Utils.OkhttpUtils;
import View.ListenerWebView;

public class    Zhrb_Onclick_Content_Activity extends AppCompatActivity  implements JsonUtils.CallBackListener , OkhttpUtils.HttpCallBackListener{

//    private WebView mWv_zhrb_onclick_content; //通过Item点击进去后详情页面的WebView
    private String Zhrb_content_htmlbody; //知乎日报点击进去HTML页面标签
    private String zhrb_content_htmlCss;   //知乎日报点击进去的Css
    private ListenerWebView mWv_zhrb_onclick_content;
    private String contentimage; //详情页面那张图片的URl
    private SimpleDraweeView mContentimage; //点击进入详情页面后 的那张图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhrb__onclick__content);
        if (Build.VERSION.SDK_INT >= 19){
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initData();

    }

    public void initData(){
        Intent intent = getIntent();
        int Click_Position = intent.getIntExtra("Click_Position", 1);
        int[] content_ids = intent.getIntArrayExtra("Content_ID");  //这个是数据ID集合
        System.out.println("now_position----->"+content_ids[Click_Position]);
        String Contenturl  = "http://news-at.zhihu.com/api/4/news/"+content_ids[Click_Position] ;
        JsonUtils jsonUtils = new JsonUtils(Contenturl,this,this);

    }

    //这里初始化了布局 进行了一些简单的逻辑操作
    private void initView() {

        mWv_zhrb_onclick_content = (ListenerWebView) findViewById(R.id.Wv_Zhrb_Onclick_Content);
        final LinearLayout Zhrb_content_LL = (LinearLayout) findViewById(R.id.Zhrb_content_LL);
        mWv_zhrb_onclick_content.setOnScrollListener(new ListenerWebView.OnScrollChangedCallBack() {
            int mark =1 ;//一个标记,使得下面动画只有在顶部的时候才执行
            int end = 1 ;
            @Override
            public void OnScroll (int t,int oldt) {
               System.out.println("位置oolt---->"+oldt);
                System.out.println("位置t------------>"+t);
                if (t<2500&&mark==1&&t>oldt){
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(Zhrb_content_LL, "alpha", 1f, 0f);
                    alpha.setDuration(700)
                            .start();
                    mark = 2 ;
                    end =1 ;
                }
                if (oldt<500&&end ==1&&oldt>t){
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(Zhrb_content_LL, "alpha", 0f, 1f);
                    alpha.setDuration(700)
                            .start();
                    end =2 ;
                    mark =1 ;
                }

            }
        });
        mContentimage = (SimpleDraweeView) findViewById(R.id.Content_Image);
    }


    @Override
    public void Updata(String json) {
        Gson gson = new Gson();
        Zhrb_content_bean zhrb_content_bean = gson.fromJson(json, Zhrb_content_bean.class);
        Zhrb_content_htmlbody = zhrb_content_bean.getBody();
        contentimage = zhrb_content_bean.getImage();
        zhrb_content_htmlCss = zhrb_content_bean.getCss().get(0);
        OkhttpUtils okhttpUtils = new OkhttpUtils(zhrb_content_htmlCss,this);
    }


    private void initWeb(String css) {
        mContentimage.setImageURI(contentimage);
        String html = "<head>\r\n"+"<style type=\"text/css\">\r\n"+css+"\r\n</style>\r\n"+"</head>\r\n"+ Zhrb_content_htmlbody   ;
        System.out.print(html);
        mWv_zhrb_onclick_content.getSettings().setJavaScriptEnabled(true);
        mWv_zhrb_onclick_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWv_zhrb_onclick_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWv_zhrb_onclick_content.loadDataWithBaseURL(null,html, "text/html","UTF-8", null);
    }

    @Override
    public void getData(String data) {
        initWeb(data);
    }
}
