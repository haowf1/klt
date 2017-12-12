package com.hw.klt.dagger.module;


import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.home.New.PhotoSetActivity;
import com.hw.klt.ktlActivity.presenter.PhotoSetPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hao on 17-8-9.
 * 图集 Module
 */
@Module
public class PhotoSetModule {

    private final PhotoSetActivity mView;
    private final String mPhotoSetId;

    public PhotoSetModule(PhotoSetActivity view, String photoSetId) {
        mView = view;
        mPhotoSetId = photoSetId;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePhotoSetPresenter() {
        return new PhotoSetPresenter(mView, mPhotoSetId);
    }
}
