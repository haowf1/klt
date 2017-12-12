package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.PerFragment;
import com.hw.klt.dagger.module.VideoHomeModule;
import com.hw.klt.ktlActivity.Fragement.movie.MovieFragement;

import dagger.Component;
/**
 * Created by hao on 2017/12/6.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = VideoHomeModule.class)
public interface MovieComponent {
    void inject(MovieFragement movieHomeActivity);
}
