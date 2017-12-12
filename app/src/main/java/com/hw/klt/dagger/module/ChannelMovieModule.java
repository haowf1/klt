package com.hw.klt.dagger.module;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.ChannelMovieAdapter;
import com.hw.klt.adapter.ManageAdapter;
import com.hw.klt.ktlActivity.home.Channal.ChannelActivity;
import com.hw.klt.ktlActivity.presenter.ChannelMoviePresenter;
import com.hw.klt.ktlActivity.presenter.IChannelPresenter;
import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.rxbus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 2017/12/8.
 */
@Module
public class ChannelMovieModule {

    private final ChannelActivity mView;

    public ChannelMovieModule(ChannelActivity view) {
        mView = view;
    }

    @Provides
    public BaseQuickAdapter provideManageAdapter() {
        return new ChannelMovieAdapter(mView);
    }

    @PerActivity
    @Provides
    public IChannelPresenter provideManagePresenter(DaoSession daoSession, RxBus rxBus) {
        return new ChannelMoviePresenter(mView, daoSession.getMovieTypeInfoDao(), rxBus);
    }
}