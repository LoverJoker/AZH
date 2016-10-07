package Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joker.azh.R;
import com.example.joker.azh.Zhrb_Onclick_Content_Activity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Adapter.zhrb_RecyclerView_Adapter;
import Bean.Zhrb_json_bean;
import Utils.JsonUtils;

/**
 * Created by Joker on 2016/10/6.
 */

public class Zhrb_Fragment extends Fragment implements JsonUtils.CallBackListener,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mZhrb_Rec_main;  //知乎日报的RecyclerView
    private List<String> BannerImgUrl ; //Banner图片的URL
    private String[] BannerTitltUrl; //Banner标题的URL
    private String[] NorItemUrl ; //正常item的 图片的URL
    private String[] NorItemTitle ; //正常item的 标题的URL
    private int[] NorItemContentId ; // 正常Item  的ID ；
    private Zhrb_json_bean zhrb_json_bean ;

    private int Now_positionData  = 5 ; //每次加载的Item条数
    private SwipeRefreshLayout mZhrb_main_swipeRefresh; //知乎 的主界面 下拉刷新的控件
    private int Nor_item_size; // 返回来的正常Item总条数
    private zhrb_RecyclerView_Adapter Zhrb_RecyclerView_adapter; //知乎 RecycleView 的适配器
    private Activity mRootActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootActivity = getActivity();
        View zhrb_view = inflater.inflate(R.layout.fragment_zhrb, container, false);
        mZhrb_Rec_main = (RecyclerView) zhrb_view.findViewById(R.id.Zhrb_Rec_main);
        mZhrb_main_swipeRefresh = (SwipeRefreshLayout) zhrb_view.findViewById(R.id.zhrb_main_SwipeRefresh);
        mZhrb_main_swipeRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        mZhrb_main_swipeRefresh.setOnRefreshListener(this);
//        if (Build.VERSION.SDK_INT >= 19){
//            Window window = mRootActivity.getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        JsonUtils jsonUtils = new JsonUtils("http://news-at.zhihu.com/api/4/news/latest", mRootActivity, this);
        return zhrb_view ;
    }


    //这里面拿到JSON返回的所有数据
    private void initdata() {
        Nor_item_size = zhrb_json_bean.getStories().size()-1;
        int Banner_size = zhrb_json_bean.getTop_stories().size()-1;
        if (zhrb_json_bean!=null){
            BannerImgUrl = new ArrayList<>();
            BannerTitltUrl = new String[Banner_size+1];
            NorItemTitle  = new String[Nor_item_size +1] ;
            NorItemUrl = new String[Nor_item_size +1];
            NorItemContentId = new int[Nor_item_size +1] ;
            //下面是给Banner填充数据
            for (int i = 0; i < Banner_size+1; i++) {
                String image = zhrb_json_bean.getTop_stories().get(i).getImage();
                String title = zhrb_json_bean.getTop_stories().get(i).getTitle();
                BannerImgUrl.add(image);
                BannerTitltUrl[i] = title ;
            }

            //下面是给每个Item填充数据

            for (int i = 0; i<Now_positionData ; i++){
                String title = zhrb_json_bean.getStories().get(i).getTitle();
                String image = zhrb_json_bean.getStories().get(i).getImages().get(0);
                int id = zhrb_json_bean.getStories().get(i).getId();
                NorItemContentId[i] = id ;
                NorItemUrl[i] = image ;
                NorItemTitle[i] = title ;
            }

        }
        Zhrb_RecyclerView_adapter = new zhrb_RecyclerView_Adapter(mRootActivity,BannerImgUrl,BannerTitltUrl,
                NorItemTitle,NorItemUrl,Now_positionData);
        //不加下面这句不会有界面 ，重要！！！！
        mZhrb_Rec_main.setLayoutManager(new LinearLayoutManager(mRootActivity));
        mZhrb_Rec_main.setAdapter(Zhrb_RecyclerView_adapter);


        Zhrb_RecyclerView_adapter.setMyItemClickListener(new zhrb_RecyclerView_Adapter.OnMyItemClickListener() {
            @Override
            public void onClick(View view, int Position) {
                Intent intent = new Intent(mRootActivity, Zhrb_Onclick_Content_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putIntArray("Content_ID",NorItemContentId);
                bundle.putInt("Click_Position",Position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    //接口回调的更新方法,这里拿到了通过网络请求拿回来的JSON数据，
    @Override
    public void Updata(String json) {
        getJson(json);
    }

    /*
    * 解析返回的JSON数据
    * */
    private void getJson(String json) {
        Gson gson = new Gson();
        Zhrb_json_bean zhrb_json_bean = gson.fromJson(json,Zhrb_json_bean.class);
        this.zhrb_json_bean = zhrb_json_bean ;
        initdata();
    }

    //下来刷新的回调方法
    @Override
    public void onRefresh() {
        if (Now_positionData <=Nor_item_size - 4){
            Now_positionData += 4;
        }else {
            Toast.makeText(mRootActivity,"已经没有更多数据了哦！",Toast.LENGTH_SHORT).show();
        }
        initdata();
        //取消掉上面那个加载小圆圈
        mZhrb_main_swipeRefresh.setRefreshing(false);
    }
}
