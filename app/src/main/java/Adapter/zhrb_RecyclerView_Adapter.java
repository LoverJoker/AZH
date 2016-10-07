package Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joker.azh.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.List;


/**
 * Created by Joker on 2016/10/1.
 */

public class zhrb_RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int Banner_item = 0 ;
    private int Normal_item = 1;
    private List<String> BannerImgUrl ; // 这个是放Banner图片的数据
    private String[] BannerTitleUrl ; // 这个是放Banner图片的数据
    private String[] NorItemUrl ; //正常item的 图片的URL
    private String[] NorItemTitle ; //正常item的 标题的URL
    private Context mContext ;
    private int Now_Position ; //每次加载的条目数量
    private OnMyItemClickListener listener  ;//在本类中保留一个点击事件回调接口的引用


    //NorMal点击事件的回调方法
    public interface OnMyItemClickListener {
        void onClick(View view , int Position);
    }


    public void setMyItemClickListener(OnMyItemClickListener recycleViewItemOnClickListener){
        this.listener = recycleViewItemOnClickListener;
    }



    public zhrb_RecyclerView_Adapter(Context context, List<String> BannerImgUrl, String[] BannerTitleUrl,
                                     String[] NorItemTitle, String[] NorItemUrl, int Now_Position
                                     ){
        mContext = context ;
        this.NorItemTitle = NorItemTitle;
        this.NorItemUrl = NorItemUrl ;
        this.BannerImgUrl = BannerImgUrl ;
        this.BannerTitleUrl = BannerTitleUrl ;
        this.Now_Position = Now_Position;
    }

    /*
    * 创建相应的ViewHolder*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == Banner_item){
            holder = new BannerHolder(LayoutInflater.from(mContext).inflate(R.layout.zhrb_item_banner,parent,false));
        }else {
            holder = new NormalHolder(LayoutInflater.from(mContext).inflate(R.layout.zhrb_item_normal,parent,false));
        }
        return holder;
    }

    //绑定ViewPager  并且 设置相关的数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerHolder ){
            final BannerHolder bannerHolder = (BannerHolder) holder;
            bannerHolder.item_banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            bannerHolder.item_banner.setImages(BannerImgUrl);
            bannerHolder.item_banner.setBannerTitle(BannerTitleUrl);
            bannerHolder.item_banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    listener.onClick(bannerHolder.itemView,position);
                }
            });
        }else if (holder instanceof NormalHolder){
            NormalHolder normalHolder = (NormalHolder) holder;
            normalHolder.tv_normalitem.setText("  "+NorItemTitle[position]);
            normalHolder.fecebook_swimg.setImageURI(NorItemUrl[position]);

            if (listener !=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(v,position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return Now_Position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return Banner_item;
        }else {
            return Normal_item ;
        }
    }

    class BannerHolder extends  RecyclerView.ViewHolder{
        Banner item_banner;
        public BannerHolder(View itemView) {
            super(itemView);
            item_banner = (Banner) itemView.findViewById(R.id.item_banner);
        }

    }

    class NormalHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView fecebook_swimg;
        TextView tv_normalitem;
        public NormalHolder(View itemView) {
            super(itemView);
            fecebook_swimg = (SimpleDraweeView) itemView.findViewById(R.id.fecebook_swimg);
            tv_normalitem = (TextView) itemView.findViewById(com.example.joker.azh.R.id.tv_normalitem);
        }
    }


}
