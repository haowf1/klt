package com.hw.klt.ktlActivity.modules;

import com.hw.klt.bean.PhotoSetInfo;
import com.hw.klt.ktlActivity.Base.IBaseView;

/**
 * 图集界面接口
 */
public interface IPhotoSetView extends IBaseView {

    /**
     * 显示数据
     * @param photoSetBean 图集
     */
    void loadData(PhotoSetInfo photoSetBean);
}
