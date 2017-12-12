package com.hw.klt.ktlActivity.modules;

import com.hw.klt.logcal.table.MovieTypeInfo;
import com.hw.klt.logcal.table.NewsTypeInfo;

import java.util.List;

/**
 * 栏目管理接口
 */
public interface IChannelView {

    /**
     * 显示数据
     * @param checkList     选中栏目
     * @param uncheckList   未选中栏目
     */
    void loadNewData(List<NewsTypeInfo> checkList, List<NewsTypeInfo> uncheckList);
    /**
     * 显示数据
     * @param checkList     选中栏目
     * @param uncheckList   未选中栏目
     */
    void loadMovieData(List<MovieTypeInfo> checkList, List<MovieTypeInfo> uncheckList);
}
