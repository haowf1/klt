package com.hw.klt.ktlActivity.presenter;


import com.hw.klt.ktlActivity.Base.ILocalPresenter;

/**
 * 频道 Presenter 接口
 */
public interface IChannelPresenter<T> extends ILocalPresenter<T> {

    /**
     * 交换
     * @param fromPos
     * @param toPos
     */
    void swap(int fromPos, int toPos);
}
