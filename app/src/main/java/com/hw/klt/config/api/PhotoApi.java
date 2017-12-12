package com.hw.klt.config.api;

import com.hw.klt.bean.BaiduPictureInfo;
import com.hw.klt.bean.PhotoInfo;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.hw.klt.config.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by hao on 17-8-9.
 */

public interface PhotoApi {
    /* 获取百度图片列表
     *  pn 第几张图片
     *  word 搜索关键字
     *  eg:https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&is=&fp=result&ie=utf-8&adpicid=&st=-1&z=&ic=0&word=%E5%91%A8%E6%9D%B0%E4%BC%A6&face=0&istype=2&pn=3&rn=30&1502178759985=
     *  @return
     *  */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&is=&fp=result&ie=utf-8&adpicid=&st=-1&z=&ic=0&word=()A6&&face=0&istype=2&pn=()&rn=30&1502178759985=")
    Observable<BaiduPictureInfo> getBDPhotoList(@Query("word") String photoId, @Query("pn") int startPage);
}
