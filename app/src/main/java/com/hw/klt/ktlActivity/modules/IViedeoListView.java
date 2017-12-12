package com.hw.klt.ktlActivity.modules;

import com.hw.klt.adapter.item.NewsMultiItem;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.bean.VideoInfo;

import java.util.List;

/**
 * Created by hao on 2017/12/4.
 */

public interface IViedeoListView extends ILoadDataView<List<VideoMultiItem>> {
    /**
     * 加载广告数据
     *
     * @param videoBean 新闻
     */
    void loadAdData(VideoInfo videoBean);
}