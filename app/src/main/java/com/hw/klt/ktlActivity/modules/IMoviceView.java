package com.hw.klt.ktlActivity.modules;


import com.hw.klt.adapter.item.NewsMultiItem;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.ktlActivity.Base.IBaseView;

import java.util.List;

/**
 * Created by hao on 17-9-11.
 */

public interface IMoviceView extends ILoadDataView<List<VideoMultiItem>>  {
    /**
     * 加载视频列表资源
     * @param movice
     */
    void loadData(List<VideoMultiItem> movice);
}
