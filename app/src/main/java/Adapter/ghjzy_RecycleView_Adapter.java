package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joker.azh.R;

/**
 * Created by Joker on 2016/10/7.
 */

public class ghjzy_RecycleView_Adapter extends RecyclerView.Adapter {

    private Context mContext ;

    public ghjzy_RecycleView_Adapter(Context mContext){
        this.mContext = mContext ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder Holder  ;
        Holder =  new itemHolder(LayoutInflater.from(mContext).inflate(R.layout.ghjzy_item,parent,false));
        return Holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  itemHolder){
            itemHolder item = (itemHolder) holder;
            item.tv.setText("测试数据");
            item.iv.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class itemHolder extends RecyclerView.ViewHolder{
        TextView tv ;
        ImageView iv ;

        public itemHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
