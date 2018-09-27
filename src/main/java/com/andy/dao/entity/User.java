package com.andy.dao.entity;

public class User {
    private int id;
    private int homeId;
    private String nickName;
    private int sex;
    private String headUrl;
    private String weiXinOpenId;
    private String weiXinAccessToken;
    private String weiXinRefreshToken;
    private String qqOpenId;
    private String qqAccessToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getWeiXinOpenId() {
        return weiXinOpenId;
    }

    public void setWeiXinOpenId(String weiXinOpenId) {
        this.weiXinOpenId = weiXinOpenId;
    }

    public String getWeiXinAccessToken() {
        return weiXinAccessToken;
    }

    public void setWeiXinAccessToken(String weiXinAccessToken) {
        this.weiXinAccessToken = weiXinAccessToken;
    }

    public String getWeiXinRefreshToken() {
        return weiXinRefreshToken;
    }

    public void setWeiXinRefreshToken(String weiXinRefreshToken) {
        this.weiXinRefreshToken = weiXinRefreshToken;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getQqAccessToken() {
        return qqAccessToken;
    }

    public void setQqAccessToken(String qqAccessToken) {
        this.qqAccessToken = qqAccessToken;
    }
}
