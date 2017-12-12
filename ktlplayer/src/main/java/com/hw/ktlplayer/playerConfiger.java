package com.hw.ktlplayer;

/**
 * Created by hao on 17-8-28.
 */

public class playerConfiger {
    /**
     * player-opts : start-on-prepared            = 1
     player-opts : overlay-format               = fcc-i420
     player-opts : max-fps                      = 60
     player-opts : opensles                     = 1
     player-opts : framedrop                    = 0
     player-opts : videotoolbox-max-frame-width = 960
     player-opts : videotoolbox                 = 1
     player-opts : video-pictq-size             = 3
     format-opts : ijkinject-opaque             = 140449007406288
     format-opts : user-agent                   = ijkplayer
     format-opts : auto_convert                 = 0
     format-opts : timeout                      = 30000000
     format-opts : reconnect                    = 1
     format-opts : safe                         = 0
     codec-opts  : skip_frame                   = 0
     codec-opts  : skip_loop_filter             = 0
     */

    //默认开启硬解码播放
    public static int hardWareDecode = 1;
    //默认支持横竖屏切换
    public static int autorotate = 1;
    //默认支持横竖屏切换
    public static int mediacodecHandleResolutionChange = 1;
    //默认支持opengles
    public static int opengles = 1;
    //默认支持跳帧，当cup性能不足的情况下跳跃播放
    public static int framedrop = 1;
    //缓冲结束开始播放
    public static int startPrepared = 0;
    //循环播放
    public static int httpDetectRangeSupport = 1;
    //环路滤波消除方块效应
    public static int skipLoopFilter = 0;
    //-------------------------
    // Extend: Render
    //-------------------------
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;
    //player播放器
    public static final int PV_PLAYER__Auto = 0;
    public static final int PV_PLAYER__AndroidMediaPlayer = 1;
    public static final int PV_PLAYER__IjkMediaPlayer = 2;
    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;



}
