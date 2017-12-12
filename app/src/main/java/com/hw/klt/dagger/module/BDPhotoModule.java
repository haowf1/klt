package com.hw.klt.dagger.module;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.hw.klt.adapter.BdPhotoMultiListAdapter;
import com.hw.klt.adapter.NewsMultiListAdapter;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.Fragement.BdPhotoFragement.BdPhotoFragement;
import com.hw.klt.ktlActivity.presenter.BdPhotoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 17-8-9.
 */
@Module
public class BDPhotoModule {

    private final BdPhotoFragement mBdPhotoView;

    public BDPhotoModule(BdPhotoFragement view) {
        this.mBdPhotoView = view;
    }



    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new BdPhotoPresenter(mBdPhotoView);
    }

    @PerFragment
    @Provides
    public BdPhotoMultiListAdapter provideAdapter() {
        return new BdPhotoMultiListAdapter(mBdPhotoView.getContext());
    }
}
