package com.hw.klt.dagger.Component;


import com.hw.klt.dagger.module.PerActivity;
import com.hw.klt.dagger.module.SpecialModule;
import com.hw.klt.ktlActivity.home.New.SpecialActivity;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 */
@PerActivity
@Component(modules = SpecialModule.class)
public interface SpecialComponent {
    void inject(SpecialActivity activity);
}
