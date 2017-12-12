package com.hw.klt.ktlActivity.home;

import android.content.Intent;

import com.hw.klt.R;
import com.hw.klt.ktlActivity.Base.BaseActivity;
import com.hw.klt.util.RxHelper;
import com.hw.klt.widget.CountDownView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by hao on 17-7-26.
 */

public class InitADActivity extends BaseActivity {
    @BindView(R.id.cd_skip)
    CountDownView mSbSkip;

    private boolean mIsSkip = false;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_init_ad;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        //异步线程
        RxHelper.countdown(5)
                .compose(this.<Integer>bindToLife())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSbSkip.setText("跳过 " + integer);
                    }
                });

    }
    private void doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(InitADActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

    @OnClick(R.id.cd_skip)
    public void onClick() {
        doSkip();
    }
}
