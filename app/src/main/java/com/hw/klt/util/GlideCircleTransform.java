package com.hw.klt.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by pc on 2017/3/30.
 */

public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int width = 200;
        float roundPx = 99;
        float dst_left =0;
        float dst_top = 0;
        float dst_right = width;
        float dst_bottom = width;
        Paint paint = new Paint();
        final android.graphics.Rect dst = new android.graphics.Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst_left + 2, dst_top + 2, dst_right - 2, dst_bottom - 2);

        Bitmap srcput = Bitmap.createBitmap(width,
                width, Bitmap.Config.ARGB_8888);
        Canvas srccanvas = new Canvas(srcput);

        paint.setAntiAlias(true);
        srccanvas.drawARGB(0, 0, 0, 0);
        srccanvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        srccanvas.drawBitmap(source, null, dst, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        paint.setColor(Color.WHITE);
        srccanvas.drawCircle(100,100,100,paint);
        return srcput;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
