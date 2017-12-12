package com.hw.klt.ktlActivity.modules.bd;

import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.ktlActivity.modules.ILoadDataView;
import java.util.List;

/**
 * Created by hao on 17-8-9.
 */

public interface IBDPhotoView extends ILoadDataView<List<BaiduPictureInfo.BDPhotoData>> {
    /**
     * 加载数据
     * @param photoBean 图片
     */
    void loadPhotoData(List<BaiduPictureInfo.BDPhotoData> photoBean);

}
