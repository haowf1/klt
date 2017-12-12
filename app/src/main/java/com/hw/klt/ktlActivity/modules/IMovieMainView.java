package com.hw.klt.ktlActivity.modules;

import com.hw.klt.logcal.table.MovieTypeInfo;
import com.hw.klt.logcal.table.NewsTypeInfo;

import java.util.List;

/**
 * Created by hao on 2017/12/7.
 */

public interface IMovieMainView {
    /**
     * 显示数据
     * @param checkList     选中栏目
     */
    void loadData(List<MovieTypeInfo> checkList);
}
