package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.PerFragment;
import com.hw.klt.dagger.module.PhotoNewsModule;
import com.hw.klt.ktlActivity.Fragement.news.PhotoNewsFragment;

import dagger.Component;

/**
 * Created by hao on 17-8-7.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = PhotoNewsModule.class)
public interface PhotoNewsComponent {
    void inject(PhotoNewsFragment fragment);
}
