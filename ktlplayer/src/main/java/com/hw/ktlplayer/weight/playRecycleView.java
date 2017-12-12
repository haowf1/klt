package com.hw.ktlplayer.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hao on 17-9-7.
 */

public class playRecycleView extends RecyclerView {

    //true == toTop and false == toBotton
    public boolean moveState;


    public int playVideoPosition;

    public playRecycleView(Context context) {
        super(context);
    }

    public playRecycleView(Context context, @Nullable AttributeSet attr){
        super(context,attr);
        init();
    }

    private void init() {
        playVideo(0);
    }

    private void playVideo(int SCROLL_STATE) {


    }

    public playRecycleView(Context context,@Nullable AttributeSet attr,int defStyle){
        super(context,attr,defStyle);
    }
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    moveState = false;
                }else {
                    moveState = true;
                }
            }
        });
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }


}
