package com.hw.klt.dagger.Component;


import com.hw.klt.dagger.module.NewsArticleModule;
import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.ktlActivity.home.New.NewsArticleActivity;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 */
@PerActivity
@Component(modules = NewsArticleModule.class)
public interface NewsArticleComponent {
    void inject(NewsArticleActivity activity);
}
