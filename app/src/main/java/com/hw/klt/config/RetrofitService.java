package com.hw.klt.config;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.hw.klt.AndroidApplication;
import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.bean.JsoupBean.MovieJsoupManager;
import com.hw.klt.bean.MovieHomeInfo;
import com.hw.klt.bean.NewsDetailInfo;
import com.hw.klt.bean.NewsInfo;
import com.hw.klt.bean.PhotoInfo;
import com.hw.klt.bean.PhotoSetInfo;
import com.hw.klt.bean.SpecialInfo;
import com.hw.klt.bean.VideoInfo;
import com.hw.klt.bean.WelfarePhotoInfo;
import com.hw.klt.bean.WelfarePhotoList;
import com.hw.klt.config.api.IMovieApi;
import com.hw.klt.config.api.INewsApi;
import com.hw.klt.config.api.IWelfareApi;
import com.hw.klt.config.api.PhotoApi;
import com.hw.klt.util.NetUtil;
import com.hw.klt.util.StringUtils;
import com.hw.klt.util.ToStringConverterFactory;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */
public class RetrofitService {

    private static final String HEAD_LINE_NEWS = "T1348647909107";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    public static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    public static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static final String NEWS_HOST = "http://c.3g.163.com/";
    private static final String WELFARE_HOST = "http://gank.io/";
    private static final String BD_PICTURE = "https://image.baidu.com/";
    public static final String MOVIE_DATA = "https://s.lol5s.com/";


    private static INewsApi sNewsService;
    private static IWelfareApi sWelfareService;
    private static PhotoApi sPhotoService;

    private static IMovieApi sMoviwService;
    // 递增页码
    private static final int INCREASE_PAGE = 20;


    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网易新闻网络通信服务
     */
    public static void initNew() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(AndroidApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NEWS_HOST)
                .build();
        sNewsService = retrofit.create(INewsApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(WELFARE_HOST)
                .build();
        sWelfareService = retrofit.create(IWelfareApi.class);



        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BD_PICTURE)
                .build();
        sPhotoService = retrofit.create(PhotoApi.class);


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ToStringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MOVIE_DATA)
                .build();
        sMoviwService = retrofit.create(IMovieApi.class);
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(AndroidApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(AndroidApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /************************************ API *******************************************/

    /**
     * 获取新闻列表
     * @return
     */
    public static Observable<NewsInfo> getNewsList(String newsId, int page) {
        String type;
        if (newsId.equals(HEAD_LINE_NEWS)) {
            type = "headline";
        } else {
            type = "list";
        }
        return sNewsService.getNewsList(type, newsId, page * INCREASE_PAGE)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapNews(newsId));
    }

    /**
     * 获取专题数据
     * @param specialId
     * @return
     */
    public static Observable<SpecialInfo> getSpecial(String specialId) {
        return sNewsService.getSpecial(specialId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapSpecial(specialId));
    }

    /**
     * 获取新闻详情
     * @param newsId 新闻ID
     * @return
     */
    public static Observable<NewsDetailInfo> getNewsDetail(final String newsId) {
        return sNewsService.getNewsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, NewsDetailInfo>, Observable<NewsDetailInfo>>() {
                    @Override
                    public Observable<NewsDetailInfo> call(Map<String, NewsDetailInfo> newsDetailMap) {
                        return Observable.just(newsDetailMap.get(newsId));
                    }
                });
    }

    /**
     * 获取图集
     * @param photoId 图集ID
     * @return
     */
    public static Observable<PhotoSetInfo> getPhotoSet(String photoId) {
        return sNewsService.getPhotoSet(StringUtils.clipPhotoSetId(photoId))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取图片列表
     * @return
     */
    public static Observable<List<PhotoInfo>> getPhotoList() {
        return sNewsService.getPhotoList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取更多图片列表
     * @return
     */
    public static Observable<List<PhotoInfo>> getPhotoMoreList(String setId) {
        return sNewsService.getPhotoMoreList(setId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取百度图片列表
     * @return
     */
    public static Observable<BaiduPictureInfo> getBDPhotoList(String photoId, int pagenear) {
        return sPhotoService.getBDPhotoList(photoId,pagenear)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /******************************影视**********************************************/
    /**
     * 获取首页电影
     * @return
     */
    public static Observable<MovieHomeInfo> getMovieHome() {
        return sMoviwService.getMovie()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapVideo());
    }

    /**
     * 获取首页电影
     * @return
     */
    public static Observable<MovieHomeInfo> getMovieHome(String typeId) {
        return sMoviwService.getChannelsMovie(typeId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapVideo());
    }

    public static Observable<MovieHomeInfo> getChannelMovie(String typeId){
        return sMoviwService.getChannelMovie(typeId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapVideoChannel());
    }

    private static Func1<String, Observable<MovieHomeInfo>> _flatMapVideoChannel() {
        return new Func1<String, Observable<MovieHomeInfo>>() {
            @Override
            public Observable<MovieHomeInfo> call(String videomap) {
                MovieHomeInfo movieHomeInfo = MovieJsoupManager.getJsoupManager().jsoupMovieChannel(videomap);
                return Observable.just(movieHomeInfo);
            }
        };
    }


    private static Func1<String, Observable<MovieHomeInfo>> _flatMapVideo() {
        return new Func1<String, Observable<MovieHomeInfo>>() {
            @Override
            public Observable<MovieHomeInfo> call(String videomap) {
                MovieHomeInfo movieHomeInfo = MovieJsoupManager.getJsoupManager().jsoupMovieHome(videomap);
                return Observable.just(movieHomeInfo);
            }
        };
    }



    /******************************************* 转换器 **********************************************/

    /**
     * 转换器，因为 Key 是动态变动，所以用这种不太合适
     * @param <T>
     */
    @Deprecated
    public static class FlatMapTransformer<T> implements Observable.Transformer<Map<String, List<T>>, T> {

        private String mMapKey;

        public FlatMapTransformer<T> setMapKey(String mapKey) {
            mMapKey = mapKey;
            return this;
        }

        @Override
        public Observable<T> call(Observable<Map<String, List<T>>> mapObservable) {
            return  mapObservable.flatMap(new Func1<Map<String, List<T>>, Observable<T>>() {
                @Override
                public Observable<T> call(Map<String, List<T>> stringListMap) {
                    if (TextUtils.isEmpty(mMapKey)) {
                        return Observable.error(new Throwable("Map Key is empty"));
                    }
                    return Observable.from(stringListMap.get(mMapKey));
                }
            }).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     * 类型转换
     * @param typeStr 新闻类型
     * @return
     */
    private static Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>> _flatMapNews(final String typeStr) {
        return new Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
            @Override
            public Observable<NewsInfo> call(Map<String, List<NewsInfo>> newsListMap) {
                return Observable.from(newsListMap.get(typeStr));
            }
        };
    }


    /**
     * 类型转换
     * @param typeStr 新闻类型
     * @return
     */
    private static Func1<Map<String, List<BaiduPictureInfo.BDPhotoData>>, Observable<BaiduPictureInfo.BDPhotoData>> _flatMapBdPhoto(final String typeStr) {
        return new Func1<Map<String, List<BaiduPictureInfo.BDPhotoData>>, Observable<BaiduPictureInfo.BDPhotoData>>() {
            @Override
            public Observable<BaiduPictureInfo.BDPhotoData> call(Map<String, List<BaiduPictureInfo.BDPhotoData>> BdPhotoListMap) {
                return Observable.from(BdPhotoListMap.get(typeStr));
            }
        };
    }

//    /**
//     * 类型转换
//     * @param typeStr 视频类型
//     * @return
//     */
//    private static Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>> _flatMapVideo(final String typeStr) {
//        return new Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>>() {
//            @Override
//            public Observable<List<VideoInfo>> call(Map<String, List<VideoInfo>> newsListMap) {
//                return Observable.just(newsListMap.get(typeStr));
//            }
//        };
//    }
//
    /**
     * 类型转换
     * @param specialId 专题id
     * @return
     */
    private static Func1<Map<String, SpecialInfo>, Observable<SpecialInfo>> _flatMapSpecial(final String specialId) {
        return new Func1<Map<String, SpecialInfo>, Observable<SpecialInfo>>() {
            @Override
            public Observable<SpecialInfo> call(Map<String, SpecialInfo> specialMap) {
                return Observable.just(specialMap.get(specialId));
            }
        };
    }
//
//    /**
//     * 类型转换
//     * @return
//     */
//    private static Func1<Map<String, List<BeautyPhotoInfo>>, Observable<List<BeautyPhotoInfo>>> _flatMapPhotos() {
//        return new Func1<Map<String, List<BeautyPhotoInfo>>, Observable<List<BeautyPhotoInfo>>>() {
//            @Override
//            public Observable<List<BeautyPhotoInfo>> call(Map<String, List<BeautyPhotoInfo>> newsListMap) {
//                return Observable.just(newsListMap.get("美女"));
//            }
//        };
//    }

    /**
     * 类型转换
     * @return
     */
    private static Func1<WelfarePhotoList, Observable<WelfarePhotoInfo>> _flatMapWelfarePhotos() {
        return new Func1<WelfarePhotoList, Observable<WelfarePhotoInfo>>() {
            @Override
            public Observable<WelfarePhotoInfo> call(WelfarePhotoList welfarePhotoList) {
                if (welfarePhotoList.getResults().size() == 0) {
                    return Observable.empty();
                }
                return Observable.from(welfarePhotoList.getResults());
            }
        };
    }
}
