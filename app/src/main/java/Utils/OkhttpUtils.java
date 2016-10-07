package Utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Joker on 2016/10/5.
 */

public class OkhttpUtils {

    private String Url;
    private HttpCallBackListener httpCallBackListener ;

    public interface HttpCallBackListener{
        void getData(String data);
    }


    public OkhttpUtils(String Url,HttpCallBackListener httpCallBackListener){
        this.Url = Url ;
        this.httpCallBackListener = httpCallBackListener ;
        GetData();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1 ){
                String data = (String) msg.obj;
                httpCallBackListener.getData(data);
            }
        }
    };

    public void GetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient Client = new OkHttpClient();
                Request request = new Request.Builder().url(Url).build();
                Call call = Client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("来自OKhttpUtils的网络请求失败了");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            String string = response.body().string();
                            Message msg = new Message();
                            msg.obj = string ;
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    }
                });
            }
        }){}.start();
    }

}
