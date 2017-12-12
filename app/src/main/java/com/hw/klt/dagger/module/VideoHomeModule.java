package com.hw.klt.dagger.module;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.MovieMultiListAdapter;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.Fragement.movie.MovieFragement;
import com.hw.klt.ktlActivity.presenter.MoviePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 2017/12/6.
 */
@Module
public class VideoHomeModule {
    private final MovieFragement mMovieHomeView;
    private String typeId;
    public VideoHomeModule(MovieFragement view,String type) {
        this.typeId = type;
        this.mMovieHomeView = view;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
            return new MoviePresenter(mMovieHomeView,typeId);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new MovieMultiListAdapter(mMovieHomeView.getContext());
    }
}
