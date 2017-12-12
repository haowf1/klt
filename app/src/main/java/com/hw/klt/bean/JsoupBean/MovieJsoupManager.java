package com.hw.klt.bean.JsoupBean;

import android.graphics.Movie;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hw.klt.bean.MovieHomeInfo;
import com.hw.klt.bean.VideoInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.hw.klt.widget.Contourfalls.ContourFallsLayoutManager.TAG;

/**
 * Created by hao on 17-9-12.
 */

public class MovieJsoupManager {

    public static MovieJsoupManager mMoviewJsoupManager;

    public static MovieJsoupManager getJsoupManager() {
        if (mMoviewJsoupManager == null) {
            mMoviewJsoupManager = new MovieJsoupManager();
        }
        return mMoviewJsoupManager;
    }

    /**
     * http://s.lol5s.com/ 解析
     *
     * @param body
     */
    public MovieHomeInfo jsoupMovieHome(String body) {
        Document movieJsoup = Jsoup.parseBodyFragment(body);
        Elements movieTitles = movieJsoup.select("span.sMark");
        Elements movielist = movieJsoup.select("div.tb_a");
        MovieHomeInfo movieHomeInfo = new MovieHomeInfo();
        for (int i = 0; i < movielist.size(); i++) {
            Elements movie = movielist.get(i).getElementsByTag("a");
            for (int j = 0; j < movie.size(); j++) {
                Elements moviepicture = movie.get(j).select("div.picsize");
                Elements movieName = movie.get(j).select("span.sTit");
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setVideoposter(moviepicture.select("img").attr("data-src"));
                videoInfo.setVideoName(movieName.get(0).text());
                videoInfo.setVideodetiale(movie.get(j).attr("href"));
                videoInfo.setActorName(movie.get(j).select("span.sDes").get(0).text());
                videoInfo.setVideoSourceType(moviepicture.select("span").get(0).text());
                movieHomeInfo.mvideoInfos.add(videoInfo);
            }
        }
        Log.d(TAG, "end of html presion");
        return movieHomeInfo;
    }

    /**
     * https://s.lol5s.com/tv/4.html解析
     *
     * @param body
     */
    public MovieHomeInfo jsoupMovieChannel(String body) {
        Document movieJsoup = Jsoup.parseBodyFragment(body);
        Elements movielist = movieJsoup.getElementsByClass("con");
        MovieHomeInfo movieHomeInfo = new MovieHomeInfo();
        for (int i = 0; i < movielist.size(); i++) {
            Elements movie = movielist.get(i).getElementsByTag("a");
            if (i!=1){
                Elements moviepicture = movie.first().select("div.picsize");
                Elements movieName = movie.first().select("span.sTit");
                if (moviepicture.size()>0){
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setVideodetiale(movie.first().attr("href"));
                    videoInfo.setVideoposter(moviepicture.first().select("img").attr("data-src"));
                    videoInfo.setVideoName(movieName.first().text());
                    videoInfo.setActorName(movie.first().select("span.sDes").first().text());
                    videoInfo.setVideoSourceType(moviepicture.first().select("span").first().text());
                    movieHomeInfo.mvideoInfos.add(videoInfo);
                }
            }

        }
        return movieHomeInfo;
    }
}
