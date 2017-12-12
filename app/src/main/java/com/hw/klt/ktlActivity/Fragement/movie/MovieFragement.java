package com.hw.klt.ktlActivity.Fragement.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.hw.klt.R;
import com.hw.klt.adapter.ViewPagerAdapter;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.dagger.Component.DaggerMovieComponent;
import com.hw.klt.dagger.module.VideoHomeModule;
import com.hw.klt.ktlActivity.Base.BaseFragment;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.Fragement.news.NewsListFragment;
import com.hw.klt.ktlActivity.modules.IMoviceView;
import com.hw.klt.rxbus.IRxBusPresenter;
import com.hw.klt.rxbus.event.ChannelEvent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import rx.functions.Action1;

/**
 * Created by hao on 2017/12/7.
 */

public class MovieFragement extends BaseFragment<IBasePresenter> implements IMoviceView {
    private static final String Movie_TYPE_KEY = "MovieTypeKey";
    @BindView(R.id.moviehome_list)
    public RecyclerView moviehomeList;
    @Inject
    BaseQuickAdapter mAdapter;
    private String mMovieId;
    public static MovieFragement newInstance(String newsId) {
        MovieFragement fragment = new MovieFragement();
        Bundle bundle = new Bundle();
        bundle.putString(Movie_TYPE_KEY, newsId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getString(Movie_TYPE_KEY);
        }
    }

    @Override
    public void loadData(List<VideoMultiItem> newsList) {
        mAdapter.updateItems(newsList);
    }

    @Override
    public void loadMoreData(List<VideoMultiItem> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInjector() {
        DaggerMovieComponent.builder()
                .applicationComponent(getAppComponent())
                .videoHomeModule(new VideoHomeModule(this,mMovieId))
                .build()
                .inject(this);
    }
    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerViewV(getContext(), moviehomeList, true, new AlphaInAnimationAdapter(mAdapter));
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadNoData() {
        mAdapter.noMoreData();
    }
}
