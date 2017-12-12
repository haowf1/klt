package com.hw.klt.ktlActivity.modules;

import com.hw.klt.adapter.item.NewsMultiItem;
import com.hw.klt.bean.NewsInfo;

import java.util.List;

/**
 * 新闻列表视图接口
 */
public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {

    /**
     * 加载广告数据
     * @param newsBean 新闻
     */
    void loadAdData(NewsInfo newsBean);
}
