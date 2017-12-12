package com.hw.klt.ktlActivity.modules;


import com.hw.klt.bean.NewsDetailInfo;
import com.hw.klt.ktlActivity.Base.IBaseView;

/**
 * Created by long on 2017/2/3.
 * 新闻详情接口
 */
public interface INewsArticleView extends IBaseView {

    /**
     * 显示数据
     * @param newsDetailBean 新闻详情
     */
    void loadData(NewsDetailInfo newsDetailBean);
}

