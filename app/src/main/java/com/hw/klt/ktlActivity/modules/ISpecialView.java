package com.hw.klt.ktlActivity.modules;

import com.hw.klt.adapter.item.SpecialItem;
import com.hw.klt.bean.SpecialInfo;
import com.hw.klt.ktlActivity.Base.IBaseView;

import java.util.List;

/**
 * 专题View接口
 */
public interface ISpecialView extends IBaseView {

    /**
     * 显示数据
     * @param specialItems 新闻
     */
    void loadData(List<SpecialItem> specialItems);

    /**
     * 添加头部
     * @param specialBean
     */
    void loadBanner(SpecialInfo specialBean);
}
