package com.hw.klt.bean;

/**
 * Created by hao on 2017/12/4.
 */

public class VideoInfo {
    //视频名字
    public String videoName;
    //视频详情地址
    public String videoposter;
    //视频海报
    public String videodetiale;
    //演员名字
    public String actorName;
    //视频类型如高清还是枪版
    public String videoSourceType;

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setVideoSourceType(String videoSource) {
        this.videoSourceType = videoSource;
    }

    public String getActorName() {
        return actorName;
    }

    public String getVideoSourceType() {
        return videoSourceType;
    }

    public void setSkipType(String skipType) {
        SkipType = skipType;
    }

    public String getSkipType() {
        return SkipType;
    }

    public String SkipType;

    public void setVideodetiale(String videodetiale) {
        this.videodetiale = videodetiale;
    }

    public String getVideodetiale() {
        return videodetiale;
    }


    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setVideoposter(String videoposter) {
        this.videoposter = videoposter;
    }


    public String getVideoName() {
        return videoName;
    }

    public String getVideoposter() {
        return videoposter;
    }


}
