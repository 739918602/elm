package com.zys.elm.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: ZhangYuSai
 * @Date: 2018/5/7
 * @Time: 15:20
 */
@Document(collection = "videos")
public class Video {

    /**
     * _id : 5aecb033e3e41aa9f2fb1afe
     * url : http://zuidazy.com/?m=vod-detail-id-47498.html
     * subhead : null
     * name : onlytease g1e-11977hd-1
     * region : 台湾
     * img : http://tupian.tupianzy.com/pic/upload/vod/2018-04-14/201804141523692035.png
     * actor : null
     * score : 0.0
     * director : null
     * type : 福利
     * language : 国语
     * releaseDate : 2018
     * updatedTime : 2018-04-14 15:47:38
     * description : null
     * eps : [{"url":"http://cn2.zuidadianying.com/20180413/AvgwH8RC/index.m3u8","name":"第01集"}]
     */

    private String _id;
    private String url;
    private Object subhead;
    private String name;
    private String region;
    private String img;
    private Object actor;
    private String score;
    private Object director;
    private String type;
    private String language;
    private String releaseDate;
    private String updatedTime;
    private Object description;
    private List<EpsBean> eps;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getSubhead() {
        return subhead;
    }

    public void setSubhead(Object subhead) {
        this.subhead = subhead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getActor() {
        return actor;
    }

    public void setActor(Object actor) {
        this.actor = actor;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Object getDirector() {
        return director;
    }

    public void setDirector(Object director) {
        this.director = director;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public List<EpsBean> getEps() {
        return eps;
    }

    public void setEps(List<EpsBean> eps) {
        this.eps = eps;
    }

    public static class EpsBean {
        /**
         * url : http://cn2.zuidadianying.com/20180413/AvgwH8RC/index.m3u8
         * name : 第01集
         */

        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}