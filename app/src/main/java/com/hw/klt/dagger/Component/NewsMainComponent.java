package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.NewsMainModule;
import com.hw.klt.dagger.module.PerFragment;
import com.hw.klt.ktlActivity.Fragement.news.NewsMainFragment;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = NewsMainModule.class)
public interface NewsMainComponent {
    void inject(NewsMainFragment fragment);
}
