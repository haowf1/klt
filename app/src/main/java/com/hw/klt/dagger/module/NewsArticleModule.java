package com.hw.klt.dagger.module;

import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.home.New.NewsArticleActivity;
import com.hw.klt.ktlActivity.presenter.NewsArticlePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 17-8-3.
 */
@Module
public class NewsArticleModule {
    private final String mNewsId;
    private final NewsArticleActivity mView;

    public NewsArticleModule(NewsArticleActivity view, String newsId) {
        mNewsId = newsId;
        mView = view;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new NewsArticlePresenter(mNewsId, mView);
    }


}
