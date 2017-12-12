package com.hw.ktlplayer.PlayerInfo;

/**
 * Created by hao on 17-8-28.
 */

public class playerControl {
    //播放器缓冲开始
    public static final int PLAYBUFFERINGSTART = 20000;
    //播放器缓冲结束
    public static final int PLAYBUFFERINGEND = PLAYBUFFERINGSTART + 1;
    //播放下一个视频
    public static final int PLAYNEXT = PLAYBUFFERINGSTART + 2;
    //播放上一个视频
    public static final int PLAYPREV = PLAYBUFFERINGSTART + 3;
    //下一个视频开始播放
    public static final int PLAYNEXTSTAR = PLAYBUFFERINGSTART + 4;
    //重新播放
    public static final int RESETVIEV = PLAYBUFFERINGSTART + 5;
    //隐藏播放控制台
    public static final int HIDERESETVIEV = PLAYBUFFERINGSTART + 6;
    //跳跃播放到指定位置
    public static final int PLAYCUR = PLAYBUFFERINGSTART + 7;

    // all possible internal states
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PLAYBACK_COMPLETED = 5;
}
