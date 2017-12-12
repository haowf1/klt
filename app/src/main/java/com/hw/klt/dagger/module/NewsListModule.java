package com.hw.klt.dagger.module;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.NewsMultiListAdapter;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.Fragement.news.NewsListFragment;
import com.hw.klt.ktlActivity.presenter.NewsListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsListModule {

    private final NewsListFragment mNewsListView;
    private final String mNewsId;

    public NewsListModule(NewsListFragment view, String newsId) {
        this.mNewsListView = view;
        this.mNewsId = newsId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new NewsListPresenter(mNewsListView, mNewsId);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new NewsMultiListAdapter(mNewsListView.getContext());
    }
}
