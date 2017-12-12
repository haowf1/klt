package com.hw.klt.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 17-9-12.
 */

public class MovieHomeInfo {

    private String GroupName;
    private String GroupId;
    private String Name;
    private String eRef;
    private String Picture;
    private String actor;
    private String releaseTime;
    public List<VideoInfo> mvideoInfos = new ArrayList<VideoInfo>();
    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public void seteRef(String eRef) {
        this.eRef = eRef;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getName() {
        return Name;
    }

    public String geteRef() {
        return eRef;
    }

    public String getPicture() {
        return Picture;
    }

    public String getActor() {
        return actor;
    }

    public String getReleaseTime() {
        return releaseTime;
    }



}
