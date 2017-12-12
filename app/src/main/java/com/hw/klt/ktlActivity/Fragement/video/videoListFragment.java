package com.hw.klt.ktlActivity.Fragement.video;

import com.hw.klt.R;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.bean.VideoInfo;
import com.hw.klt.ktlActivity.Base.BaseFragment;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.modules.IViedeoListView;

import java.util.List;

/**
 * Created by hao on 2017/12/4.
 */

public class videoListFragment extends BaseFragment<IBasePresenter> implements IViedeoListView {


    @Override
    public void loadData(List<VideoMultiItem> data) {

    }

    @Override
    public void loadMoreData(List<VideoMultiItem> data) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    public void loadAdData(VideoInfo videoBean) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
