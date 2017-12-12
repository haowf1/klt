package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.BDPhotoModule;
import com.hw.klt.dagger.module.NewsListModule;
import com.hw.klt.dagger.module.PerFragment;
import com.hw.klt.ktlActivity.Fragement.BdPhotoFragement.BdPhotoFragement;
import com.hw.klt.ktlActivity.Fragement.news.NewsListFragment;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = BDPhotoModule.class)
public interface BDPhotoComponent {
    void inject(BdPhotoFragement fragment);
}
