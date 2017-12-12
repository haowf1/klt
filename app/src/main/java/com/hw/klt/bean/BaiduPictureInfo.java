package com.hw.klt.bean;

/**
 * Created by hao on 17-8-8.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.dl7.recycler.entity.MultiItemEntity;

import java.util.List;
public class BaiduPictureInfo {

    private String queryEnc;
    private String queryExt;
    private int listNum;
    private int displayNum;
    private String gsm;
    private String bdFmtDispNum;
    private String bdSearchTime;
    private int isNeedAsyncRequest;
    private String bdIsClustered;
    private List<BDPhotoData> data;
    public void setQueryEnc(String queryEnc) {
        this.queryEnc = queryEnc;
    }
    public String getQueryEnc() {
        return queryEnc;
    }

    public void setQueryExt(String queryExt) {
        this.queryExt = queryExt;
    }
    public String getQueryExt() {
        return queryExt;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }
    public int getListNum() {
        return listNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }
    public int getDisplayNum() {
        return displayNum;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }
    public String getGsm() {
        return gsm;
    }

    public void setBdFmtDispNum(String bdFmtDispNum) {
        this.bdFmtDispNum = bdFmtDispNum;
    }
    public String getBdFmtDispNum() {
        return bdFmtDispNum;
    }

    public void setBdSearchTime(String bdSearchTime) {
        this.bdSearchTime = bdSearchTime;
    }
    public String getBdSearchTime() {
        return bdSearchTime;
    }

    public void setIsNeedAsyncRequest(int isNeedAsyncRequest) {
        this.isNeedAsyncRequest = isNeedAsyncRequest;
    }
    public int getIsNeedAsyncRequest() {
        return isNeedAsyncRequest;
    }

    public void setBdIsClustered(String bdIsClustered) {
        this.bdIsClustered = bdIsClustered;
    }
    public String getBdIsClustered() {
        return bdIsClustered;
    }

    public void setData(List<BDPhotoData> data) {
        this.data = data;
    }
    public List<BDPhotoData> getData() {
        return data;
    }
    public class ReplaceUrl {

        private String ObjURL;
        private String FromURL;
        public void setObjURL(String ObjURL) {
            this.ObjURL = ObjURL;
        }
        public String getObjURL() {
            return ObjURL;
        }

        public void setFromURL(String FromURL) {
            this.FromURL = FromURL;
        }
        public String getFromURL() {
            return FromURL;
        }

    }
    public final class BDPhotoData implements Parcelable {

        private String adType;
        private String hasAspData;
        private String thumbURL;
        private String middleURL;
        private String largeTnImageUrl;
        private int hasLarge;
        private String hoverURL;
        private int pageNum;
        private String objURL;
        private String fromURL;
        private String fromURLHost;
        private String currentIndex;
        private int width;
        private int height;
        private String type;
        private int is_gif;
        private String filesize;
        private String bdSrcType;
        private String di;
        private String pi;
        private String is;
        private List<ReplaceUrl> replaceUrl;
        private String hasThumbData;
        private int bdSetImgNum;
        private int partnerId;
        private int spn;
        private String bdImgnewsDate;
        private String fromPageTitle;
        private String fromPageTitleEnc;
        private String bdSourceName;
        private String bdFromPageTitlePrefix;
        private int isAspDianjing;
        private String token;
        private String imgType;
        private String cs;
        private String os;
        private String simid;
        private String personalized;
        private String simid_info;
        private String face_info;
        private String xiangshi_info;
        private String adPicId;
        private String source_type;

        protected BDPhotoData(Parcel in) {
            adType = in.readString();
            hasAspData = in.readString();
            thumbURL = in.readString();
            middleURL = in.readString();
            largeTnImageUrl = in.readString();
            hasLarge = in.readInt();
            hoverURL = in.readString();
            pageNum = in.readInt();
            objURL = in.readString();
            fromURL = in.readString();
            fromURLHost = in.readString();
            currentIndex = in.readString();
            width = in.readInt();
            height = in.readInt();
            type = in.readString();
            is_gif = in.readInt();
            filesize = in.readString();
            bdSrcType = in.readString();
            di = in.readString();
            pi = in.readString();
            is = in.readString();
            hasThumbData = in.readString();
            bdSetImgNum = in.readInt();
            partnerId = in.readInt();
            spn = in.readInt();
            bdImgnewsDate = in.readString();
            fromPageTitle = in.readString();
            fromPageTitleEnc = in.readString();
            bdSourceName = in.readString();
            bdFromPageTitlePrefix = in.readString();
            isAspDianjing = in.readInt();
            token = in.readString();
            imgType = in.readString();
            cs = in.readString();
            os = in.readString();
            simid = in.readString();
            personalized = in.readString();
            simid_info = in.readString();
            face_info = in.readString();
            xiangshi_info = in.readString();
            adPicId = in.readString();
            source_type = in.readString();
        }

        public final Creator<BDPhotoData> CREATOR = new Creator<BDPhotoData>() {
            @Override
            public BDPhotoData createFromParcel(Parcel in) {
                return new BDPhotoData(in);
            }

            @Override
            public BDPhotoData[] newArray(int size) {
                return new BDPhotoData[size];
            }
        };

        public void setAdType(String adType) {
            this.adType = adType;
        }
        public String getAdType() {
            return adType;
        }

        public void setHasAspData(String hasAspData) {
            this.hasAspData = hasAspData;
        }
        public String getHasAspData() {
            return hasAspData;
        }

        public void setThumbURL(String thumbURL) {
            this.thumbURL = thumbURL;
        }
        public String getThumbURL() {
            return thumbURL;
        }

        public void setMiddleURL(String middleURL) {
            this.middleURL = middleURL;
        }
        public String getMiddleURL() {
            return middleURL;
        }

        public void setLargeTnImageUrl(String largeTnImageUrl) {
            this.largeTnImageUrl = largeTnImageUrl;
        }
        public String getLargeTnImageUrl() {
            return largeTnImageUrl;
        }

        public void setHasLarge(int hasLarge) {
            this.hasLarge = hasLarge;
        }
        public int getHasLarge() {
            return hasLarge;
        }

        public void setHoverURL(String hoverURL) {
            this.hoverURL = hoverURL;
        }
        public String getHoverURL() {
            return hoverURL;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
        public int getPageNum() {
            return pageNum;
        }

        public void setObjURL(String objURL) {
            this.objURL = objURL;
        }
        public String getObjURL() {
            return objURL;
        }

        public void setFromURL(String fromURL) {
            this.fromURL = fromURL;
        }
        public String getFromURL() {
            return fromURL;
        }

        public void setFromURLHost(String fromURLHost) {
            this.fromURLHost = fromURLHost;
        }
        public String getFromURLHost() {
            return fromURLHost;
        }

        public void setCurrentIndex(String currentIndex) {
            this.currentIndex = currentIndex;
        }
        public String getCurrentIndex() {
            return currentIndex;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return height;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setIs_gif(int is_gif) {
            this.is_gif = is_gif;
        }
        public int getIs_gif() {
            return is_gif;
        }

        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }
        public String getFilesize() {
            return filesize;
        }

        public void setBdSrcType(String bdSrcType) {
            this.bdSrcType = bdSrcType;
        }
        public String getBdSrcType() {
            return bdSrcType;
        }

        public void setDi(String di) {
            this.di = di;
        }
        public String getDi() {
            return di;
        }

        public void setPi(String pi) {
            this.pi = pi;
        }
        public String getPi() {
            return pi;
        }

        public void setIs(String is) {
            this.is = is;
        }
        public String getIs() {
            return is;
        }

        public void setReplaceUrl(List<ReplaceUrl> replaceUrl) {
            this.replaceUrl = replaceUrl;
        }
        public List<ReplaceUrl> getReplaceUrl() {
            return replaceUrl;
        }

        public void setHasThumbData(String hasThumbData) {
            this.hasThumbData = hasThumbData;
        }
        public String getHasThumbData() {
            return hasThumbData;
        }

        public void setBdSetImgNum(int bdSetImgNum) {
            this.bdSetImgNum = bdSetImgNum;
        }
        public int getBdSetImgNum() {
            return bdSetImgNum;
        }

        public void setPartnerId(int partnerId) {
            this.partnerId = partnerId;
        }
        public int getPartnerId() {
            return partnerId;
        }

        public void setSpn(int spn) {
            this.spn = spn;
        }
        public int getSpn() {
            return spn;
        }

        public void setBdImgnewsDate(String bdImgnewsDate) {
            this.bdImgnewsDate = bdImgnewsDate;
        }
        public String getBdImgnewsDate() {
            return bdImgnewsDate;
        }

        public void setFromPageTitle(String fromPageTitle) {
            this.fromPageTitle = fromPageTitle;
        }
        public String getFromPageTitle() {
            return fromPageTitle;
        }

        public void setFromPageTitleEnc(String fromPageTitleEnc) {
            this.fromPageTitleEnc = fromPageTitleEnc;
        }
        public String getFromPageTitleEnc() {
            return fromPageTitleEnc;
        }

        public void setBdSourceName(String bdSourceName) {
            this.bdSourceName = bdSourceName;
        }
        public String getBdSourceName() {
            return bdSourceName;
        }

        public void setBdFromPageTitlePrefix(String bdFromPageTitlePrefix) {
            this.bdFromPageTitlePrefix = bdFromPageTitlePrefix;
        }
        public String getBdFromPageTitlePrefix() {
            return bdFromPageTitlePrefix;
        }

        public void setIsAspDianjing(int isAspDianjing) {
            this.isAspDianjing = isAspDianjing;
        }
        public int getIsAspDianjing() {
            return isAspDianjing;
        }

        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setImgType(String imgType) {
            this.imgType = imgType;
        }
        public String getImgType() {
            return imgType;
        }

        public void setCs(String cs) {
            this.cs = cs;
        }
        public String getCs() {
            return cs;
        }

        public void setOs(String os) {
            this.os = os;
        }
        public String getOs() {
            return os;
        }

        public void setSimid(String simid) {
            this.simid = simid;
        }
        public String getSimid() {
            return simid;
        }

        public void setPersonalized(String personalized) {
            this.personalized = personalized;
        }
        public String getPersonalized() {
            return personalized;
        }

        public void setSimid_info(String simid_info) {
            this.simid_info = simid_info;
        }
        public String getSimid_info() {
            return simid_info;
        }

        public void setFace_info(String face_info) {
            this.face_info = face_info;
        }
        public String getFace_info() {
            return face_info;
        }

        public void setXiangshi_info(String xiangshi_info) {
            this.xiangshi_info = xiangshi_info;
        }
        public String getXiangshi_info() {
            return xiangshi_info;
        }

        public void setAdPicId(String adPicId) {
            this.adPicId = adPicId;
        }
        public String getAdPicId() {
            return adPicId;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }
        public String getSource_type() {
            return source_type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(adType);
            dest.writeString(hasAspData);
            dest.writeString(thumbURL);
            dest.writeString(middleURL);
            dest.writeString(largeTnImageUrl);
            dest.writeInt(hasLarge);
            dest.writeString(hoverURL);
            dest.writeInt(pageNum);
            dest.writeString(objURL);
            dest.writeString(fromURL);
            dest.writeString(fromURLHost);
            dest.writeString(currentIndex);
            dest.writeInt(width);
            dest.writeInt(height);
            dest.writeString(type);
            dest.writeInt(is_gif);
            dest.writeString(filesize);
            dest.writeString(bdSrcType);
            dest.writeString(di);
            dest.writeString(pi);
            dest.writeString(is);
            dest.writeString(hasThumbData);
            dest.writeInt(bdSetImgNum);
            dest.writeInt(partnerId);
            dest.writeInt(spn);
            dest.writeString(bdImgnewsDate);
            dest.writeString(fromPageTitle);
            dest.writeString(fromPageTitleEnc);
            dest.writeString(bdSourceName);
            dest.writeString(bdFromPageTitlePrefix);
            dest.writeInt(isAspDianjing);
            dest.writeString(token);
            dest.writeString(imgType);
            dest.writeString(cs);
            dest.writeString(os);
            dest.writeString(simid);
            dest.writeString(personalized);
            dest.writeString(simid_info);
            dest.writeString(face_info);
            dest.writeString(xiangshi_info);
            dest.writeString(adPicId);
            dest.writeString(source_type);
        }
    }

}
