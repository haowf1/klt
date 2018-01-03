package com.ktl.ifmsc.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.ktl.ifmsc.R;

/**
 * Created by hao on 17-9-14.
 */

public class WaveView extends View {
    public static String TAG = "VolunceView";
    private Thread thread;
    private float targetHeight;
    private Paint paint;
    private Path path;
    private float waveHeight;
    private float waveWidth;
    private int waveColor;
    private float waveOffsetX;
    private int waveAmount;
    private float waveSpeed;
    private int wavecrest;

    public void setWaveHeight(float waveHeight) {
        this.waveHeight = waveHeight;
    }

    public void setWaveWidth(float waveWidth) {
        this.waveWidth = waveWidth;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
    }

    public void setWaveOffsetX(float waveOffsetX) {
        this.waveOffsetX = waveOffsetX;
    }

    public void setWaveAmount(int waveAmount) {
        this.waveAmount = waveAmount;
    }

    public void setWaveSpeed(float waveSpeed) {
        this.waveSpeed = waveSpeed;
    }

    public void setWavecrest(int wavecrest) {
        this.wavecrest = wavecrest;
    }



    public WaveView(Context context) {
        super(context);
//        init(context);
    }

    public WaveView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.WaveView);
        waveAmount = array.getInteger( R.styleable.WaveView_waveAmount,4);
        waveHeight = array.getFloat( R.styleable.WaveView_waveHeight,0.3f);
        waveWidth = array.getFloat( R.styleable.WaveView_waveWidth,5f);
        waveColor = array.getColor( R.styleable.WaveView_waveColor, Color.WHITE);
        waveOffsetX= array.getInteger( R.styleable.WaveView_waveOffsetX,0);
        waveSpeed = array.getFloat( R.styleable.WaveView_waveSpeed,0.4f);
//        init(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//        init(context);
    }

    public void init(Context context){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
        waveSpeed = 0.1f;
        waveOffsetX = 0f;
        waveAmount = 4;
        waveColor = Color.WHITE;
        waveHeight = 1f;
        targetHeight = 0f;
        waveWidth = 5f;
        thread = new Thread(runnable);
        thread.start();
    }

    public void stop(){
        thread.interrupt();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                invalidate();
            }
        }
    };

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                while (true) {
                    handler.sendEmptyMessage(1);
                    Thread.sleep((int) (1 / waveSpeed));
                    wavecrest += 1;
                    if (wavecrest >= (20 * waveAmount)) {
                        wavecrest = 0;
                    }
                    if (waveHeight < targetHeight) {
                        waveHeight += 0.01f;
                    } else {
                        waveHeight -= 0.01f;
                    }


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float line = canvas.getWidth() / waveAmount;
        path.reset();
        paint.setColor(waveColor);
        paint.setStrokeWidth(waveWidth);
        float h;
        for (int i = 0; i < (waveAmount * 2); i++) {
            float x1 = (-canvas.getWidth() + line * i) + line * waveOffsetX;
            float x2 = (-canvas.getWidth() + line * (i + 0.5f)) + line
                    * waveOffsetX;
            float x3 = (-canvas.getWidth() + line * (i + 1f)) + line
                    * waveOffsetX;

            if (i % 2 == 0) {
                h = (float) (canvas.getHeight() / 2 + canvas.getHeight() / 2
                        * waveHeight);
            } else {
                h = (float) (canvas.getHeight() / 2 - canvas.getHeight() / 2
                        * waveHeight);
            }
            path.moveTo(x1 + canvas.getWidth() * wavecrest / (20 * waveAmount),
                    canvas.getHeight() / 2);
            path.quadTo(x2 + canvas.getWidth() * wavecrest / (20 * waveAmount),
                    h, x3 + canvas.getWidth() * wavecrest / (20 * waveAmount),
                    canvas.getHeight() / 2);
        }
        canvas.drawPath(path, paint);
    }



}
