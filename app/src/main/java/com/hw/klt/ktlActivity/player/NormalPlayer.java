package com.hw.klt.ktlActivity.player;

import android.util.Log;

import com.hw.klt.R;
import com.hw.ktlplayer.Base.BasePlayer;
import com.hw.ktlplayer.media.AndroidMediaController;
import com.hw.ktlplayer.weight.PlayerVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by hao on 17-9-4.
 */

public class NormalPlayer extends BasePlayer {
    public static final String TAG = "NormalPlayer";
    private boolean mBackPressed;
//    String mVideoPath = "/storage/474A-0819/full_screen_google.mp4";
    String mVideoPath = "http://video.duoduotv.bid/vod/v2/MjU5LzgvMzgvYmNsb3VkLzE0L3Zlcl8wMF8yMi0xMTA1NDcxNDAzLWF2Yy0xOTkwMTMtYWFjLTQ4MDAwLTc3ODI3ODMtMjUyMTk1OTc3LTgwOWRkMDc4MTFlMzhhM2VmMDQ0OGNmZmQxYmQ5NWMyLTE0OTcxMzg4MDAwNjcubXA0?b=259&mmsid=237784288&tm=1504857649&pip=5dd86f745cd039856615d60f2bcde20a&key=14d75ed1a38a1875711d036aa1b7972f&platid=21&splatid=2109&payff=0&tss=ios&vtype=108021&dur=7782&p1=3&p2=30&p3=300&cf=c4731245b7b41f46b92b12fd3d353223c0b6679b302665eb4cbe5d570029cf7f992b558bd945c2dfd5d35876b0ee69f6b99eb4dd3c592165147c7aaa0ed6b49a15a6bee9e0456affdbeeae0da9ee79d600bddf3cd25093b6818bdbd29f4e0ac005eb8e852c1252b28f68881df7148e50eef7c459dffeae3f74017401eec7c0b454c9cf88a5a5f41145406157e64fdfee63b52945e5cd16ccdc01179e0de9693251da9167be6b0560e084a1d5a67f06243a2744c161af27cdafa4ff4931e86070071752dec292eea2ef2f96351cb5c431cf95097e61ea209d568df43d5065266a85a4d2f71e4cc1f57de0241e2cf52dddd7ff07f64fed40751ecd9b2f54577065fd00cfafdd21ba2d4d1984a95fec1b727941dced1ef7df251490d3a606ced242a94fbe4ef7f44377b9fa4a858561ebb100fcf955831708a9a557805a28b9d560bd411c509d7b5e6375acfcd890413638c4cf3f0ac38a830bbdf8e19b427c3fe600c8f81d74e11e30351e4eb43ca4a5cdcdc9aa9b166dab032ad5e67aef3dfef26abafc20eeeea3cb5030c232b63bd2bfbbad187b6efe75dc3f97366f1648ffda6b95029f564d96aadb759a4e96ea35fce4fd9c001ba46e7b74cb4c1fbd14c3976135290eca0184db6bcfa0d08560ca387aba3ab9e5644341340ee92f9509ff484d4aebcc2d9aae7bf782f7849173c81c05af10a5bcff62e90c5d4a0353ee6faaa0f95a01e03bdb13f1a1b13f0a2813c3bc9371947c089792f23cf4e745f0ec92/flash&p=120";
    @BindView(R.id.normal_player)
    public PlayerVideo mplayVideoView;
    @Override
    protected void initViews() {

        ButterKnife.bind(this);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        // prefer mVideoPath
        AndroidMediaController mMediaController = new AndroidMediaController(this, false);
        mplayVideoView.setMediaController(mMediaController);
        mplayVideoView.setVideoPath(mVideoPath);
        mplayVideoView.start();

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_play_normal;
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed) {
            mplayVideoView.stopPlayback();
            mplayVideoView.release(true);
        }
        IjkMediaPlayer.native_profileEnd();
    }


    @Override
    public void onRetry() {

    }
}