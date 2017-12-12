package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.ChannelMovieModule;
import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.ktlActivity.home.Channal.ChannelActivity;

import dagger.Component;

/**
 * Created by hao on 2017/12/8.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ChannelMovieModule.class)
public interface ChannelMovieComponent {
    void inject(ChannelActivity activity);
}
