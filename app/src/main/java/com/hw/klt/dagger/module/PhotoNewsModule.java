package com.hw.klt.dagger.module;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.PhotoListAdapter;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.Fragement.news.PhotoNewsFragment;
import com.hw.klt.ktlActivity.presenter.PhotoNewsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 17-8-9.
 * 图片新闻列表 Module
 */
@Module
public class PhotoNewsModule {

    private final PhotoNewsFragment mView;

    public PhotoNewsModule(PhotoNewsFragment view) {
        this.mView = view;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new PhotoNewsPresenter(mView);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new PhotoListAdapter(mView.getContext());
    }
}
