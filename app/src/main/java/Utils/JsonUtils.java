package Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

import Bean.Zhrb_json_bean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Joker on 2016/10/1.
 */

public  class  JsonUtils {

    private String murl ;
    private Context context ;
    public Zhrb_json_bean zhrb_json_bean;

    private CallBackListener callBackListener ; //回调保存的本类对象


    /**
     *
     * @param url 发送网络请求的地址
     * @param context 上下文环境
     * @param callBackListener 回调想要注册的类
     */
    public JsonUtils(String url, Context context, CallBackListener callBackListener) {

        this.callBackListener = callBackListener;
        murl = url;
        this.context = context;
        getJsonFromUrl();
    }

    public JsonUtils(String url,Context context){
        murl = url;
        this.context = context ;
        getJsonFromUrl();
    }

    /*开启线程获得JSON数据*/
    private  void getJsonFromUrl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient Client = new OkHttpClient();

                final Request request = new Request.Builder().url(murl).build();

                Call call = Client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(context,"请检查网络连接是否正常",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            String ResponseData = response.body().string();
                            Message msg = new Message();
                            msg.obj = ResponseData;
                            msg.what = 0 ;
                            mHandler.sendMessage(msg);
                        }
                    }
                });
            }
        }){}.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                String json = msg.obj.toString();
//                getJson(json);
                //通知注册进来的类去更新界面
                callBackListener.Updata(json);
            }
        }
    };

//    /*
//    * 解析返回的JSON数据
//    * */
//    private void getJson(String json) {
//        Gson gson = new Gson();
//        Zhrb_json_bean zhrbData = gson.fromJson(json,Zhrb_json_bean.class);
//        callBackListener.Updata(zhrbData);
//
//    }

    //接口回调 发送数据


    //下面是关注者数据加载类的数据接口   这里Main_Activity 在关注数据
    public interface CallBackListener{
        void Updata(String json);
    }


}
