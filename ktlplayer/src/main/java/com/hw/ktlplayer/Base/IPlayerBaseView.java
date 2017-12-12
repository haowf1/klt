package com.hw.ktlplayer.Base;

interface IPlayerBaseView{
    /**
     * 显示加载动画
     */
    public void  showLoading();

    /**
     * 隐藏加载动画
     */
    public void hideLoading();

    /**
     * 显示网络错误
     */
    public void showNetError();

    /**
     * 完成刷新，新增量
     */
    public void finishRefresh();

}
