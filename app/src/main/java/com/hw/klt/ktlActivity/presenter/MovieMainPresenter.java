package com.hw.klt.ktlActivity.presenter;

import com.hw.klt.ktlActivity.modules.IMoviceView;
import com.hw.klt.ktlActivity.modules.IMovieMainView;
import com.hw.klt.ktlActivity.modules.INewsMainView;
import com.hw.klt.logcal.table.MovieTypeInfo;
import com.hw.klt.logcal.table.MovieTypeInfoDao;
import com.hw.klt.logcal.table.NewsTypeInfo;
import com.hw.klt.logcal.table.NewsTypeInfoDao;
import com.hw.klt.rxbus.IRxBusPresenter;
import com.hw.klt.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hao on 2017/12/7.
 */

public class MovieMainPresenter implements IRxBusPresenter {

    private final IMovieMainView mView;
    private final MovieTypeInfoDao mDbDao;
    private final RxBus mRxBus;

    public MovieMainPresenter(IMovieMainView view, MovieTypeInfoDao dbDao, RxBus rxBus) {
        mView = view;
        mDbDao = dbDao;
        mRxBus = rxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        mDbDao.queryBuilder().rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MovieTypeInfo>>() {
                    @Override
                    public void call(List<MovieTypeInfo> movieTypeBeen) {
                        mView.loadData(movieTypeBeen);
                    }
                });
    }

    @Override
    public void getMoreData() {
    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
