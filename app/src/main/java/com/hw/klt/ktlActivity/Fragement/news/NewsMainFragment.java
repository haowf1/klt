package com.hw.klt.ktlActivity.Fragement.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hw.klt.R;
import com.hw.klt.adapter.ViewPagerAdapter;

import com.hw.klt.dagger.Component.DaggerNewsMainComponent;
import com.hw.klt.dagger.module.NewsMainModule;
import com.hw.klt.ktlActivity.Base.BaseFragment;
import com.hw.klt.ktlActivity.home.Channal.ChannelActivity;
import com.hw.klt.ktlActivity.modules.INewsMainView;
import com.hw.klt.logcal.table.NewsTypeInfo;
import com.hw.klt.rxbus.IRxBusPresenter;
import com.hw.klt.rxbus.event.ChannelEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 新闻主界面
 */
public class NewsMainFragment extends BaseFragment<IRxBusPresenter> implements INewsMainView {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Inject
    ViewPagerAdapter mPagerAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initInjector() {
        DaggerNewsMainComponent.builder()
                .applicationComponent(getAppComponent())
                .newsMainModule(new NewsMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar, true, "新闻");
        setHasOptionsMenu(true);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mPresenter.registerRxBus(ChannelEvent.class, new Action1<ChannelEvent>() {
            @Override
            public void call(ChannelEvent channelEvent) {
                _handleChannelEvent(channelEvent);
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (NewsTypeInfo bean : checkList) {
            titles.add(bean.getName());
            fragments.add(NewsListFragment.newInstance(bean.getTypeId()));
        }
        mPagerAdapter.setItems(fragments, titles);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterRxBus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_channel, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_channel) {
            ChannelActivity.launch(mContext,ChannelActivity.NEW);
            return true;
        }
        return false;
    }

    /**
     * 处理频道事件
     * @param channelEvent
     */
    private void _handleChannelEvent(ChannelEvent channelEvent) {
        switch (channelEvent.eventType) {
            case ChannelEvent.ADD_EVENT:
                mPagerAdapter.addItem(NewsListFragment.newInstance(channelEvent.newsInfo.getTypeId()), channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.DEL_EVENT:
                // 如果是删除操作直接切换第一项，不然容易出现加载到不存在的Fragment
                mViewPager.setCurrentItem(0);
                mPagerAdapter.delItem(channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.SWAP_EVENT:
                mPagerAdapter.swapItems(channelEvent.fromPos, channelEvent.toPos);
                break;
        }
    }
}
