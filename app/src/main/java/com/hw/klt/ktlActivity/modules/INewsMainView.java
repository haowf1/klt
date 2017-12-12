package com.hw.klt.ktlActivity.modules;



import com.hw.klt.logcal.table.NewsTypeInfo;

import java.util.List;

/**
 * 主页接口
 */
public interface INewsMainView {

    /**
     * 显示数据
     * @param checkList     选中栏目
     */
    void loadData(List<NewsTypeInfo> checkList);
}
