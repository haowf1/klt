package com.hw.klt.dagger.module;

import com.hw.klt.adapter.ViewPagerAdapter;
import com.hw.klt.ktlActivity.Fragement.movie.MovieMainFragment;
import com.hw.klt.ktlActivity.Fragement.news.NewsMainFragment;
import com.hw.klt.ktlActivity.presenter.MovieMainPresenter;
import com.hw.klt.ktlActivity.presenter.NewsMainPresenter;
import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.rxbus.IRxBusPresenter;
import com.hw.klt.rxbus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 2017/12/7.
 */
@Module
public class MovieMainModule {
    private final MovieMainFragment mView;

    public MovieMainModule(MovieMainFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public IRxBusPresenter provideMainPresenter(DaoSession daoSession, RxBus rxBus) {
        return new MovieMainPresenter(mView, daoSession.getMovieTypeInfoDao(), rxBus);
    }

    @PerFragment
    @Provides
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(mView.getChildFragmentManager());
    }
}
