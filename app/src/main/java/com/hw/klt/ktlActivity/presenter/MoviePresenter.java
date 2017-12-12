package com.hw.klt.ktlActivity.presenter;

import android.provider.MediaStore;
import android.util.Log;

import com.hw.klt.adapter.item.NewsMultiItem;
import com.hw.klt.adapter.item.VideoMultiItem;
import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.bean.JsoupBean.MovieJsoupManager;
import com.hw.klt.bean.MovieHomeInfo;
import com.hw.klt.bean.NewsInfo;
import com.hw.klt.bean.VideoInfo;
import com.hw.klt.config.RetrofitService;
import com.hw.klt.ktlActivity.Base.IBasePresenter;
import com.hw.klt.ktlActivity.modules.IMoviceView;
import com.hw.klt.util.NewsUtils;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by hao on 17-9-11.
 */

public class MoviePresenter implements IBasePresenter {
    private String TAG = "MoviePresenter";
    private IMoviceView mView;
    private int mPages = 1;
    private String typeId;

    public MoviePresenter(IMoviceView view, String typeId) {
        this.mView = view;
        this.typeId = typeId;
    }

    @Override
    public void getData(final boolean isRefresh) {
        if ("T1348647909527".equals(typeId)) {
            requestHome(isRefresh);
        }else if ("T1348647909528".equals(typeId)) {
            requestListChannal(isRefresh, "2", mPages);
        }else if ("T1348647909529".equals(typeId)) {
            requestHome(isRefresh, "17");
        }else if ("T1348647909530".equals(typeId)) {
            requestHome(isRefresh, "20");
        }else if ("T1348647909531".equals(typeId)) {
            requestHome(isRefresh, "40");
        }else if ("T1348647909526".equals(typeId)) {
            requestListChannal(isRefresh, "1", mPages);
        }else if ("T13486479045".equals(typeId)){
            requestListChannal(isRefresh, "45", mPages);
        }else if ("T13486479026".equals(typeId)){
            requestListChannal(isRefresh, "26", mPages);
        }else if ("T13486479027".equals(typeId)){
            requestListChannal(isRefresh, "27", mPages);
        }else if ("T13486479044".equals(typeId)){
            requestListChannal(isRefresh, "44", mPages);
        }else if ("T13486479023".equals(typeId)){
            requestListChannal(isRefresh, "23", mPages);
        }else if ("T13486479024".equals(typeId)){
            requestListChannal(isRefresh, "24", mPages);
        }else if ("T13486479021".equals(typeId)){
            requestListChannal(isRefresh, "21", mPages);
        }else if ("T13486479041".equals(typeId)){
            requestListChannal(isRefresh, "41", mPages);
        }else if ("T13486479042".equals(typeId)){
            requestListChannal(isRefresh, "42", mPages);
        }
    }

    public void requestListChannal(final boolean isRefresh, String type, int mPage) {
        if (mPage > 1){
            type = type + "-" + mPage;
        }
        RetrofitService.getChannelMovie(type)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<VideoMultiItem>>() {
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
                    public void onNext(List<VideoMultiItem> movieHomeInfo) {
                        Log.d(TAG, movieHomeInfo.toString());
                        mView.loadData(movieHomeInfo);
                        mPages++;
                    }
                });
    }

    public void requestMore(String type, int mPage){
        if (mPage > 1){
            type = type + "-" + mPage;
        }
        RetrofitService.getChannelMovie(type)
                .compose(mTransformer)
                .subscribe(new Subscriber<List<VideoMultiItem>>() {
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
                    public void onNext(List<VideoMultiItem> movieHomeInfo) {
                        if (movieHomeInfo.size()<1){
                            mView.loadNoData();
                        }else {
                            mView.loadMoreData(movieHomeInfo);
                            mPages++;
                        }

                    }
                });
    }


    public void requestHome(final boolean isRefresh,String type) {
        RetrofitService.getMovieHome(type)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<VideoMultiItem>>() {
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
                    public void onNext(List<VideoMultiItem> movieHomeInfo) {
                        Log.d(TAG, movieHomeInfo.toString());
                        mView.loadData(movieHomeInfo);
                        mPages++;
                    }
                });
    }


    public void requestHome(final boolean isRefresh) {
        RetrofitService.getMovieHome()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<VideoMultiItem>>() {
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
                    public void onNext(List<VideoMultiItem> movieHomeInfo) {
                        mView.loadData(movieHomeInfo);
                    }
                });
    }

    List<VideoMultiItem> mVideoItem = new ArrayList<>();
    /**
     * 统一变换
     */
    public Observable.Transformer<MovieHomeInfo, List<VideoMultiItem>> mTransformer = new Observable.Transformer<MovieHomeInfo, List<VideoMultiItem>>() {
        @Override
        public Observable<List<VideoMultiItem>> call(Observable<MovieHomeInfo> newsInfoObservable) {
            return newsInfoObservable.map(new Func1<MovieHomeInfo, List<VideoMultiItem>>() {
                @Override
                public List<VideoMultiItem> call(MovieHomeInfo movieHomeInfo) {
                    for (int i = 0; i < movieHomeInfo.mvideoInfos.size(); i++) {
                        mVideoItem.add(new VideoMultiItem(VideoMultiItem.ITEM_TYPE_MOVIE, movieHomeInfo.mvideoInfos.get(i)));
                    }
                    return mVideoItem;
                }
            }).compose(mView.<List<VideoMultiItem>>bindToLife());
        }
    };


    @Override
    public void getMoreData() {
        if ("T1348647909526".equals(typeId)) {
            requestMore("1", mPages);
        }else if ("T1348647909528".equals(typeId)) {
            requestMore("2", mPages);
        }else if ("T13486479045".equals(typeId)){
            requestMore("45", mPages);
        }else if ("T13486479026".equals(typeId)){
            requestMore("26", mPages);
        }else if ("T13486479027".equals(typeId)){
            requestMore("27", mPages);
        }else if ("T13486479044".equals(typeId)){
            requestMore("44", mPages);
        }else if ("T13486479023".equals(typeId)){
            requestMore("23", mPages);
        }else if ("T13486479024".equals(typeId)){
            requestMore("24", mPages);
        }else if ("T13486479021".equals(typeId)){
            requestMore("21", mPages);
        }else if ("T13486479041".equals(typeId)){
            requestMore("41", mPages);
        }else if ("T13486479042".equals(typeId)){
            requestMore("42", mPages);
        }else{
          mView.loadNoData();
        }
    }
}
