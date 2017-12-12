package com.hw.ktlplayer.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.hw.ktlplayer.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Created by hao on 17-8-29.
 */

public class PlayerErrorView extends FrameLayout implements View.OnClickListener{
    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnRetryListener mOnRetryListener;
    private int mEmptyStatus = STATUS_LOADING;
    private int mBgColor;

    public TextView mTvEmptyMessage;
    public View mRlEmptyContainer;
    public SpinKitView mEmptyLoading;
    public FrameLayout mEmptyLayout;
    public PlayerErrorView(Context context) {
        this(context, null);
    }

    public PlayerErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        weightInit();
        init(attrs);

    }

    private void weightInit(){
        mEmptyLayout = (FrameLayout) findViewById(R.id.error_layout);
        mEmptyLoading = (SpinKitView) findViewById(R.id.preparing_loading);
        mRlEmptyContainer = findViewById(R.id.rl_error_container);
        mTvEmptyMessage = (TextView) findViewById(R.id.tv_nets_error);
        mTvEmptyMessage.setOnClickListener(this);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.playerErrorLayout);
        try {
            mBgColor = a.getColor(R.styleable.playerErrorLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.layout_playererror_loading, this);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     * @return  状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setEmptyMessage(String msg) {
        mTvEmptyMessage.setText(msg);
    }

    public void hideErrorIcon() {
        mTvEmptyMessage.setCompoundDrawables(null, null, null, null);
    }


    /**
     * 设置图标
     * @param drawable drawable
     */
    public void setEmptyIcon(Drawable drawable) {
        mTvEmptyMessage.setCompoundDrawables(null,drawable,null,null);
    }

    public void setLoadingIcon(Sprite d) {
        mEmptyLoading.setIndeterminateDrawable(d);
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }


    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_nets_error){
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry();
            }
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus{}
}
