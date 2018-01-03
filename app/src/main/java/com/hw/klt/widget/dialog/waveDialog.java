package com.hw.klt.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hw.klt.R;
import com.ktl.ifmsc.weight.WaveView;

import java.util.zip.Inflater;

/**
 * Created by hao on 17-9-15.
 */

public class waveDialog extends Dialog {
    public WaveView mWaveview;

    public Context mContext;
    public waveDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public waveDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context,themeResId);
        init(context);
    }

    protected waveDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(Context context){
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wave, null);
        mWaveview = (WaveView) view.findViewById(R.id.waveview);

//        WindowManager windowManager = ((Activity)context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = (int)(display.getWidth()); //设置宽度
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        mWaveview.init(mContext);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
        window.getDecorView().setPadding(0, 0, 0, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mWaveview.stop();
    }
}
