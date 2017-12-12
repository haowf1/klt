package com.hw.klt.dagger.Component;


import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.dagger.module.PhotoSetModule;
import com.hw.klt.ktlActivity.home.New.PhotoSetActivity;

import dagger.Component;
/**
 * Created by hao on 17-8-9.
 */
@PerActivity
@Component(modules = PhotoSetModule.class)
public interface PhotoSetComponent {
    void inject(PhotoSetActivity activity);
}
