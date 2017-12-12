package com.hw.klt.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import java.util.zip.Inflater;

/**
 * Created by hao on 17-9-15.
 */

public class waveDialog extends Dialog {
    public waveDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public waveDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected waveDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wave, null);
        //设置dialog大小，这里是一个小赠送，模块好的控件大小设置
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);
        //设置对话框位置
        Display d = manager.getDefaultDisplay();
        // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整 dialogWindow.setAttributes(params);

    }
}
