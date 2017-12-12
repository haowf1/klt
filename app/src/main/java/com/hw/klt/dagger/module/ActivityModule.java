package com.hw.klt.dagger.module;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;

/**
 * Activity Module
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @PerActivity
    @Provides
    Activity getActivity() {
        return mActivity;
    }
}
