package com.hw.klt.adapter.item;

import android.support.annotation.IntDef;

import com.dl7.recycler.entity.MultiItemEntity;
import com.hw.klt.bean.NewsInfo;
import com.hw.klt.bean.VideoInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hao on 2017/12/4.
 */

public class VideoMultiItem extends MultiItemEntity {

    public static final int ITEM_TYPE_MOVIE = 1;
    public static final int ITEM_TYPE_TV = 2;
    public static final int ITEM_TYPE_VARIETY = 3;
    public static final int ITEM_TYPE_AD = 4;
    private VideoInfo mVideoBean;

    public VideoMultiItem(@VideoMultiItem.VideoItemType int itemType, VideoInfo videoBean) {
        super(itemType);
        mVideoBean = videoBean;
    }

    public VideoInfo getVideoBean() {
        return mVideoBean;
    }

    public void setVideoBean(VideoInfo videoBean) {
        mVideoBean = videoBean;
    }

    @Override
    public void setItemType(@VideoMultiItem.VideoItemType int itemType) {
        super.setItemType(itemType);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ITEM_TYPE_MOVIE, ITEM_TYPE_TV,ITEM_TYPE_VARIETY,ITEM_TYPE_AD})
    public @interface VideoItemType {}
}
