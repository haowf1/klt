package com.hw.klt.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hao on 17-7-25.
 */

public class MeasureUtils {

    private MeasureUtils(){
        throw new AssertionError();
    }

    public static float dp2px(Context context,float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context,float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }

    public static int getMeasureWidthWithMargins(View child){
        final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
    }
    //获取屏幕的高和宽的实例
    public static DisplayMetrics getDisplayMetrics(Context context){
        if (context == null){
            return null ;
        }

        return context.getResources().getDisplayMetrics();
    }

    public static int[] getViewLocation(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return  location;
    }
}
