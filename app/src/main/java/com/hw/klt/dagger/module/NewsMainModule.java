package com.hw.klt.dagger.module;

import com.hw.klt.adapter.ViewPagerAdapter;

import com.hw.klt.ktlActivity.Fragement.news.NewsMainFragment;
import com.hw.klt.ktlActivity.presenter.NewsMainPresenter;
import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.rxbus.IRxBusPresenter;
import com.hw.klt.rxbus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by long on 2016/12/20.
 * 新闻主页 Module
 */
@Module
public class NewsMainModule {

    private final NewsMainFragment mView;

    public NewsMainModule(NewsMainFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public IRxBusPresenter provideMainPresenter(DaoSession daoSession, RxBus rxBus) {
        return new NewsMainPresenter(mView, daoSession.getNewsTypeInfoDao(), rxBus);
    }

    @PerFragment
    @Provides
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(mView.getChildFragmentManager());
    }
}
