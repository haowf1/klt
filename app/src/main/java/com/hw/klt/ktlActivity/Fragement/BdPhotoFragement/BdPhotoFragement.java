package com.hw.klt.ktlActivity.Fragement.BdPhotoFragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SearchView;

import com.dl7.recycler.listener.OnRequestDataListener;
import com.hw.klt.R;
import com.hw.klt.adapter.BdPhotoMultiListAdapter;
import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.dagger.Component.DaggerBDPhotoComponent;
import com.hw.klt.dagger.module.BDPhotoModule;
import com.hw.klt.ktlActivity.Base.BaseFragment;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.modules.bd.IBDPhotoView;
import com.hw.klt.ktlActivity.presenter.BdPhotoPresenter;
import com.hw.klt.widget.Contourfalls.ContourFallsLayoutManager;
import com.hw.klt.widget.Contourfalls.ContourSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by hao on 17-8-9.
 */

public class BdPhotoFragement extends BaseFragment<IBasePresenter> implements IBDPhotoView{
    private static final String BD_TYPE_KEY = "BdPhotoTypeKey";
    @BindView(R.id.rv_bdphoto_list)
    public RecyclerView bdPhotoList;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.search_bar)
    SearchView mSearchView;
    @Inject
    BdPhotoMultiListAdapter mAdapter;
    //recycle的间隔
    private ContourSpacingItemDecoration mcontoursapace;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragement_bdphoto_list;
    }


    @Override
    protected void initInjector() {
        DaggerBDPhotoComponent.builder()
                .applicationComponent(getAppComponent())
                .bDPhotoModule(new BDPhotoModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar, true, "图片");
        mcontoursapace = new ContourSpacingItemDecoration(4);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
        bdPhotoList.addItemDecoration(mcontoursapace);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((BdPhotoPresenter) mPresenter).setPhotoId(query);
                updateViews(true);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<BaiduPictureInfo.BDPhotoData> data) {
        mAdapter.updateItems(data);
        addData();
    }

    @Override
    public void loadMoreData(List<BaiduPictureInfo.BDPhotoData> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    public void loadNoData() {
        mAdapter.loadAbnormal();
    }

    @Override
    public void loadPhotoData(List<BaiduPictureInfo.BDPhotoData> photoBean) {
        mAdapter.calculateImageAspectRatios();
    }

    public void addData(){
        final ContourFallsLayoutManager layoutManager = new ContourFallsLayoutManager(mAdapter);
        layoutManager.setMaxRowHeight(450);
        bdPhotoList.setLayoutManager(layoutManager);
        bdPhotoList.setAdapter(mAdapter);
        //设置item之间的间隔

    }
}
