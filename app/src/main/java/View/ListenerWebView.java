package View;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Joker on 2016/10/5.
 */

public class ListenerWebView extends WebView {

    private OnScrollChangedCallBack mOnScrollChangedCallBack ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListenerWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ListenerWebView(Context context) {
        super(context);
    }

    public ListenerWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mOnScrollChangedCallBack.OnScroll(t,oldt);

    }



    public void setOnScrollListener(OnScrollChangedCallBack onScrollChangedCallBack){
        mOnScrollChangedCallBack = onScrollChangedCallBack ;
    }

    public static interface OnScrollChangedCallBack{

        /**
         *
         * @param t 向下滑动的时候相对于屏幕顶端Y的值
         */
        void OnScroll(int t,int oldt);
    }
}
