package com.hw.klt.ktlActivity.modules;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.ManageAdapter;
import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.ktlActivity.home.Channal.ChannelActivity;
import com.hw.klt.ktlActivity.presenter.ChannelPresenter;
import com.hw.klt.ktlActivity.presenter.IChannelPresenter;
import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.rxbus.RxBus;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelModule {

    private final ChannelActivity mView;

    public ChannelModule(ChannelActivity view) {
        mView = view;
    }

    @Provides
    public BaseQuickAdapter provideManageAdapter() {
        return new ManageAdapter(mView);
    }

    @PerActivity
    @Provides
    public IChannelPresenter provideManagePresenter(DaoSession daoSession, RxBus rxBus) {
        return new ChannelPresenter(mView, daoSession.getNewsTypeInfoDao(), rxBus);
    }
}
