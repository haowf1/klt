package com.hw.klt.ktlActivity.presenter;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.config.RetrofitService;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.modules.bd.IBDPhotoView;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by hao on 17-8-9.
 */

public class BdPhotoPresenter implements IBasePresenter {
    private IBDPhotoView mView;
    private String mPhotoId;

    private int mPage = 0;

    public BdPhotoPresenter(IBDPhotoView view) {
        this.mView = view;
    }

    public void setPhotoId(String photoId){
        this.mPhotoId = photoId;
    }



    @Override
    public void getData(final boolean isRefresh) {
        mPage = 0;
        RetrofitService.getBDPhotoList(mPhotoId, mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .compose(mView.<BaiduPictureInfo>bindToLife())
                .subscribe(new Subscriber<BaiduPictureInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(BaiduPictureInfo bdPhotoinfo) {
                        List<BaiduPictureInfo.BDPhotoData> bdPhotoData = filterNullObject(bdPhotoinfo.getData());
                        mView.loadData(bdPhotoData);
                        mView.loadPhotoData(bdPhotoData);
                        mPage=mPage+bdPhotoData.size();
                    }

                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getBDPhotoList(mPhotoId, mPage)
                .compose(mView.<BaiduPictureInfo>bindToLife())
                .subscribe(new Subscriber<BaiduPictureInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(BaiduPictureInfo bdPhotoinfo) {
                        List<BaiduPictureInfo.BDPhotoData> bdPhotoData = filterNullObject(bdPhotoinfo.getData());
                        mView.loadMoreData(bdPhotoData);
                        mView.loadPhotoData(bdPhotoData);
                        mPage=mPage+bdPhotoData.size();
                    }
                });
    }

    /*
    * 检查数据的完整正确
    * */
    public List<BaiduPictureInfo.BDPhotoData> filterNullObject(List<BaiduPictureInfo.BDPhotoData> mbdPhotoList){
        for (BaiduPictureInfo.BDPhotoData mbdPhoto : mbdPhotoList){
            if (mbdPhoto.getThumbURL() == null){
                mbdPhotoList.remove(mbdPhoto);
            }

        }
      return mbdPhotoList;
    }
}
