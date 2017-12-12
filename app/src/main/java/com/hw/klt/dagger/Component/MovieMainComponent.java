package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.ApplicationModule;
import com.hw.klt.dagger.module.MovieMainModule;
import com.hw.klt.dagger.module.NewsMainModule;
import com.hw.klt.dagger.module.PerFragment;
import com.hw.klt.ktlActivity.Fragement.movie.MovieMainFragment;
import com.hw.klt.ktlActivity.Fragement.news.NewsMainFragment;

import dagger.Component;

/**
 * Created by hao on 2017/12/8.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = MovieMainModule.class)
public interface MovieMainComponent {
    void inject(MovieMainFragment movieMainFragment);
}
