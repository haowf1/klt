package com.hw.klt.util;

/**
 * Created by hao on 2017/12/6.
 */

public class MovieUtils {
    private static final String NEWS_ID_SUFFIX = ".html";
    private static final String NEWS_ITEM_SPECIAL = "special";
    private static final String NEWS_ITEM_MOVIE = "movie";

    public static boolean isMovieSet(String skipType) {
        return NEWS_ITEM_MOVIE.equals(skipType);
    }
}
