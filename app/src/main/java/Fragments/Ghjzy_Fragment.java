package Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joker.azh.R;

import Adapter.ghjzy_RecycleView_Adapter;

/**
 * Created by Joker on 2016/10/6.
 */

public class Ghjzy_Fragment extends Fragment {

    private Activity mRootActivity;
    private RecyclerView mGhjzy_rec_main; //干货集中营主界面的RecycleView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootActivity = getActivity();
        View ghjzy_view = inflater.inflate(R.layout.fragment_ghjzy, container, false);
        mGhjzy_rec_main = (RecyclerView) ghjzy_view.findViewById(R.id.ghjzy_Rec_main);
        mGhjzy_rec_main.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mGhjzy_rec_main.addItemDecoration(decoration);
        mGhjzy_rec_main.setAdapter(new ghjzy_RecycleView_Adapter(mRootActivity));
        return ghjzy_view;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
}
