package com.hw.klt.ktlActivity.presenter;

import com.hw.klt.ktlActivity.modules.IChannelView;
import com.hw.klt.logcal.dao.MovieTypeDao;
import com.hw.klt.logcal.dao.NewsTypeDao;
import com.hw.klt.logcal.table.MovieTypeInfo;
import com.hw.klt.logcal.table.MovieTypeInfoDao;
import com.hw.klt.logcal.table.NewsTypeInfo;
import com.hw.klt.logcal.table.NewsTypeInfoDao;
import com.hw.klt.rxbus.RxBus;
import com.hw.klt.rxbus.event.ChannelEvent;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hao on 2017/12/8.
 */

public class ChannelMoviePresenter implements IChannelPresenter<MovieTypeInfo> {

    private final IChannelView mView;
    private final MovieTypeInfoDao mDbDao;
    private final RxBus mRxBus;

    public ChannelMoviePresenter(IChannelView view, MovieTypeInfoDao dbDao, RxBus rxBus) {
        mView = view;
        mDbDao = dbDao;
        mRxBus = rxBus;
    }


    @Override
    public void getData(boolean isRefresh) {
        // 从数据库获取
        final List<MovieTypeInfo> checkList = mDbDao.queryBuilder().list();
        final List<String> typeList = new ArrayList<>();
        for (MovieTypeInfo bean : checkList) {
            typeList.add(bean.getTypeId());
        }
        Observable.from(MovieTypeDao.getAllChannels())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<MovieTypeInfo, Boolean>() {
                    @Override
                    public Boolean call(MovieTypeInfo movieTypeBean) {
                        // 过滤掉已经选中的栏目
                        return !typeList.contains(movieTypeBean.getTypeId());
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<MovieTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(List<MovieTypeInfo> uncheckList) {
                        mView.loadMovieData(checkList, uncheckList);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }


    @Override
    public void insert(final MovieTypeInfo data) {
        final ChannelEvent channelEvents = new ChannelEvent(ChannelEvent.ADD_EVENT);
        channelEvents.setMovieTypeInfo(data);
        mDbDao.rx().insert(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MovieTypeInfo>() {
                    @Override
                    public void onCompleted() {
                        mRxBus.post(channelEvents);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(MovieTypeInfo newsTypeBean) {
                        Logger.w(newsTypeBean.toString());
                    }
                });
    }

    @Override
    public void delete(final MovieTypeInfo data) {
        final ChannelEvent channelEvent = new ChannelEvent(ChannelEvent.DEL_EVENT);
        channelEvent.setMovieTypeInfo(data);
        mDbDao.rx().delete(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        mRxBus.post(channelEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(Void aVoid) {
                    }
                });
    }

    @Override
    public void update(List<MovieTypeInfo> list) {
        // 这做法不太妥当，而且列表在交互位置时可能产生很多无用的交互没处理掉，暂时没想到简便的方法来处理，以后有想法再改。
        Observable.from(list)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        // 清空数据库
                        mDbDao.deleteAll();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MovieTypeInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(MovieTypeInfo newsTypeBean) {
                        // 把ID清除再加入数据库会从新按列表顺序递增ID
                        newsTypeBean.setId(null);
                        mDbDao.save(newsTypeBean);
                    }
                });
    }

    @Override
    public void swap(int fromPos, int toPos) {
        mRxBus.post(new ChannelEvent(ChannelEvent.SWAP_EVENT, fromPos, toPos));
    }

}
