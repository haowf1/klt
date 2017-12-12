package com.hw.klt.ktlActivity.home.movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hw.klt.R;
import com.hw.klt.ktlActivity.Base.BaseSwipeBackActivity;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.home.New.NewsArticleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2017/12/11.
 */

public class MovieActivity extends BaseSwipeBackActivity<IBasePresenter> {
    public static String MOVIE_ID_KEY = "movie_id_key";
    @BindView(R.id.movie_web)
    public WebView movie_web;

    private WebSettings mWebSettings;

    public static void launch(Context context, String newsId) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(MOVIE_ID_KEY, newsId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_web;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mWebSettings = movie_web.getSettings();
        movie_web.loadUrl("http://www.baidu.com/");
        //设置不用系统浏览器打开,直接显示在当前Webview
        movie_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onDestroy() {
        if (movie_web != null) {
            movie_web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            movie_web.clearHistory();

            ((ViewGroup) movie_web.getParent()).removeView(movie_web);
            movie_web.destroy();
            movie_web = null;
        }
        super.onDestroy();
    }

}
