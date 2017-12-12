package com.hw.klt.dagger.Component;

import android.app.Activity;


import com.hw.klt.dagger.module.ActivityModule;
import com.hw.klt.dagger.module.PerActivity;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
