package com.hw.klt.dagger.Component;

import android.content.Context;



import com.hw.klt.dagger.module.ApplicationModule;
import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hao on 17-8-9.
 * Application Component
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
    RxBus getRxBus();
    DaoSession getDaoSession();
}
