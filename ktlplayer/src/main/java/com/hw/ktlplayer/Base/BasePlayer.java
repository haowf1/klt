package com.hw.ktlplayer.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hw.ktlplayer.playerConfiger;
import com.hw.ktlplayer.uilt.SwipeRefreshHelper;
import com.hw.ktlplayer.weight.PlayerErrorView;

import java.io.IOException;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by hao on 17-8-28.
 */

public abstract class BasePlayer extends AppCompatActivity implements IPlayerBaseView , PlayerErrorView.OnRetryListener {

    private PlayerErrorView mErrorView;
    private IjkMediaPlayer mediaPlayer;
    private SwipeRefreshLayout mSwipeRefresh;

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    public void onIdle(String url){
        if (mediaPlayer != null){
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 多窗口同时播放器
     * @return
     */
    public IjkMediaPlayer creatPlayer(){
        IjkMediaPlayer mediaPlayerChild = new IjkMediaPlayer();
        mediaPlayerChild.release();
        return mediaPlayerChild;
    }



    public void creatPlay(){
        if (mediaPlayer == null){
            mediaPlayer = new IjkMediaPlayer();
        }else{
            mediaPlayer.release();
            mediaPlayer = new IjkMediaPlayer();
        }
    }

    public IjkMediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        initViews();
        initSwipeRefresh();
        updateViews(false);
        creatPlay();
    }

    public void playerStart(){
        mediaPlayer.start();
    };

    public void playerPause(){
        mediaPlayer.pause();
    };

    public void playerStop(){
        mediaPlayer.stop();
    };

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);

    @Override
    public void showLoading() {
        if (mErrorView != null) {
            mErrorView.setEmptyStatus(mErrorView.STATUS_LOADING);
        }
    }


    @Override
    public void hideLoading() {
        if (mErrorView != null) {
            mErrorView.hide();
        }
    }

    @Override
    public void showNetError() {
        if (mErrorView != null) {
            mErrorView.setEmptyStatus(mErrorView.STATUS_NO_NET);
            mErrorView.setRetryListener(this);
        }
    }

    /**
     * 全局刷新
     */
    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }
}
