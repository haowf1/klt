package com.hw.klt.logcal.dao;

import android.content.Context;

import com.hw.klt.logcal.table.DaoSession;
import com.hw.klt.logcal.table.MovieTypeInfo;
import com.hw.klt.logcal.table.MovieTypeInfoDao;
import com.hw.klt.logcal.table.NewsTypeInfo;
import com.hw.klt.logcal.table.NewsTypeInfoDao;
import com.hw.klt.util.AssetsHelper;
import com.hw.klt.util.GsonHelper;

import java.util.List;
/**
 * Created by hao on 2017/12/8.
 */

public class MovieTypeDao {
    // 所有栏目
    private static List<MovieTypeInfo> sMovieChannels;

    private MovieTypeDao() {
    }

    /**
     * 更新本地数据，如果数据库影视栏目为 0 则添加头 3 个栏目
     * @param context
     * @param daoSession
     */
    public static void updateLocalData(Context context, DaoSession daoSession) {
        sMovieChannels = GsonHelper.convertEntities(AssetsHelper.readData(context, "MovieChannel"), MovieTypeInfo.class);
        MovieTypeInfoDao beanDao = daoSession.getMovieTypeInfoDao();
        if (beanDao.count() == 0) {
            beanDao.insertInTx(sMovieChannels.subList(0, 3));
        }
    }

    /**
     * 获取所有栏目
     * @return
     */
    public static List<MovieTypeInfo> getAllChannels() {
        return sMovieChannels;
    }
}

