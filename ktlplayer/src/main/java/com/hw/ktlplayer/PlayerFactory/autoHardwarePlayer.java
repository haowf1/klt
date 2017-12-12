package com.hw.ktlplayer.PlayerFactory;

import android.util.Log;

import com.hw.ktlplayer.Base.BasePlayer;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by hao on 17-8-28.
 */

public class autoHardwarePlayer extends BasePlayer implements IMediaPlayer.OnInfoListener{
    private IjkMediaPlayer mediaPlayer;
    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int arg1, int arg2) {
        switch (arg1) {
            case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                Log.i("IjkPlayer", "MEDIA_INFO_VIDEO_TRACK_LAGGING:  " + mediaPlayer.getCurrentPosition());
                break;
            //视频开始播放
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                Log.i("IjkPlayer", "MEDIA_INFO_VIDEO_RENDERING_START:  " + mediaPlayer.getCurrentPosition());
                break;
            //开始视频缓冲
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                //发布事件
                Log.i("IjkPlayer", "MEDIA_INFO_BUFFERING_START  " + mediaPlayer.getCurrentPosition());
                break;
            //停止视频缓冲
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                Log.i("IjkPlayer", "MEDIA_INFO_BAD_INTERLEAVING:  " + mediaPlayer.getCurrentPosition());
                break;
            case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                Log.i("IjkPlayer", "MEDIA_INFO_BAD_INTERLEAVING:  " + mediaPlayer.getCurrentPosition());
                break;
            case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                Log.i("IjkPlayer", "MEDIA_INFO_NOT_SEEKABLE:  " + mediaPlayer.getCurrentPosition());
                break;
            case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                Log.i("IjkPlayer", "MEDIA_INFO_METADATA_UPDATE:  " + mediaPlayer.getCurrentPosition());
                break;
        }
        return true;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void onRetry() {

    }
}
