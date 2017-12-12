package com.hw.ktlplayer.Render;

import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by hao on 17-8-29.
 */

public interface IRenderView {
    int PLAY_ASPECT_FIT_PARENT = 0; // without clip
    int PLAY_ASPECT_FILL_PARENT = 1; // may clip
    int PLAY_ASPECT_WRAP_CONTENT = 2;
    int PLAY_MATCH_PARENT = 3;
    int PLAY_16_9_FIT_PARENT = 4;
    int PLAY_4_3_FIT_PARENT = 5;

    public static final int SPECT[] = {
     PLAY_ASPECT_FIT_PARENT , // without clip
     PLAY_ASPECT_FILL_PARENT , // may clip
     PLAY_ASPECT_WRAP_CONTENT ,
     PLAY_MATCH_PARENT,
     PLAY_16_9_FIT_PARENT,
     PLAY_4_3_FIT_PARENT
    };

    View getView();

    boolean shouldWaitForResize();

    void setVideoSize(int videoWidth, int videoHeight);

    void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen);

    void setVideoRotation(int degree);

    void setAspectRatio(int pal);

    void addRenderCallback(@NonNull IRenderCallback callback);

    void removeRenderCallback(@NonNull IRenderCallback callback);

    interface ISurfaceHolder {
        void bindToMediaPlayer(IMediaPlayer mp);

        @NonNull
        IRenderView getRenderView();

        @Nullable
        SurfaceHolder getSurfaceHolder();

        @Nullable
        Surface openSurface();

        @Nullable
        SurfaceTexture getSurfaceTexture();
    }

    interface IRenderCallback {
        /**
         * @param holder
         * @param width  could be 0
         * @param height could be 0
         */
        void onSurfaceCreated(@NonNull ISurfaceHolder holder, int width, int height);

        /**
         * @param holder
         * @param format could be 0
         * @param width
         * @param height
         */
        void onSurfaceChanged(@NonNull ISurfaceHolder holder, int format, int width, int height);

        void onSurfaceDestroyed(@NonNull ISurfaceHolder holder);
    }
}
