package com.hw.klt.config.api;

import com.hw.klt.bean.NewsDetailInfo;
import com.hw.klt.bean.VideoInfo;

import java.util.Map;

import retrofit2.http.Path;
import rx.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


import static com.hw.klt.config.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by hao on 17-9-11.
 */

public interface IMovieApi {
    /**
     * 获取视频网址和图片
     * https://s.lol5s.com/
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET(" ")
    Observable<String> getMovie();
    /**
     * 获取视频网址和图片
     * https://s.lol5s.com/tv/1-2.html
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("tv/{specialId}.html")
    Observable<String> getChannelsMovie(@Path("specialId") String specialIde);
    /**
     * 获取视频网址和图片
     * https://s.lol5s.com/tv/1-2.html
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("tv/{specialId}.html")
    Observable<String> getChannelMovie(@Path("specialId") String specialIde);
}
