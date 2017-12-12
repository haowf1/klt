package com.hw.klt.dagger.Component;

import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.ktlActivity.home.Channal.ChannelActivity;
import com.hw.klt.ktlActivity.modules.ChannelModule;

import dagger.Component;
/**
 * Created by hao on 17-8-9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ChannelModule.class)
public interface ManageComponent {
    void inject(ChannelActivity activity);
}
