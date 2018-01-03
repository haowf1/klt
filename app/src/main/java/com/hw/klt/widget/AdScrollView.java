package com.hw.klt.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by hao on 18-1-3.
 */

public class AdScrollView extends android.support.v7.widget.AppCompatImageView {
    private String TAG = "AdScrollView";
    private int windowHeight;
    int[] location;
    public AdScrollView(Context context) {
        this(context,null);
    }

    public AdScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowHeight = wm.getDefaultDisplay().getHeight();
        location = new int[2];
    }

    @Override
    protected void onDraw(Canvas canvas) {

        getLocationOnScreen(location);
        Log.d(TAG,"LocationOnScreen:Y"+location[1]);

        int y = location[1];
        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, w, h);
        //图片显示区
        int startH = (windowHeight - h)/2;
        int endH = windowHeight - startH;

        if (y>startH && y<endH-getBottom()){
            canvas.translate(0, -(y-startH));
        }else if (y>=endH-getBottom()){
            canvas.translate(0, getBottom()-h);
        }
        super.onDraw(canvas);
        invalidate();
    }
}
