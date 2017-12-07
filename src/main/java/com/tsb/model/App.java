package com.tsb.model;

/**
 * 用于对接"服务体系"后，展示应用
 * @author xuchq 2017.11.23
 * {
 * "appId": "lqU7de0Z6GJVz4srDwzIEmXvFDKXS5Ei",
 * "appName": "一师一优课",
 * "appUrl": "http://system.eduyun.cn:80/bmp-web/application?appId=lqU7de0Z6GJVz4srDwzIEmXvFDKXS5Ei",
 * "personScope": "0,1,2,3",
 * "appLvl": "0",
 * "appDesc": "一师一优课 一课一名师 活动",
 * "appImage": "http://system.eduyun.cn:80/bmp-web/spApp/icon/20170719/5433359122_png",
 * "appIcon1": "http://system.eduyun.cn:80/bmp-web/spApp/icon/20170719/2076786766_jpg",
 * "appIcon2": "http://system.eduyun.cn:80/bmp-web/spApp/icon/20170719/1697886266_jpg",
 * "intoSpace": "1",
 * "eduType": "edu_type_0",
 * "appClassification": "app_classify_02",
 * "providerName": "中央电化教育馆",
 * "providerId": "TCtVrW8jUYi8yuxfobP8GqpzRGE2w9d5"
 * }
 */
public class App {
    private String appId;
    private String appName;
    private String appUrl;
    private String personScope;
    private String appLvl;
    private String appDesc;
    private String appImage;
    private String appIcon1;
    private String appIcon2;
    private String intoSpace;
    private String eduType;
    private String appClassification;
    private String providerName;
    private String providerId;

    public App(String appId, String appName, String appUrl, String personScope, String appLvl, String appDesc, String appImage, String appIcon1, String appIcon2, String intoSpace, String eduType, String appClassification, String providerName, String providerId) {
        this.appId = appId;
        this.appName = appName;
        this.appUrl = appUrl;
        this.personScope = personScope;
        this.appLvl = appLvl;
        this.appDesc = appDesc;
        this.appImage = appImage;
        this.appIcon1 = appIcon1;
        this.appIcon2 = appIcon2;
        this.intoSpace = intoSpace;
        this.eduType = eduType;
        this.appClassification = appClassification;
        this.providerName = providerName;
        this.providerId = providerId;
    }

    public App() {

    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getPersonScope() {
        return personScope;
    }

    public void setPersonScope(String personScope) {
        this.personScope = personScope;
    }

    public String getAppLvl() {
        return appLvl;
    }

    public void setAppLvl(String appLvl) {
        this.appLvl = appLvl;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppImage() {
        return appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    public String getAppIcon1() {
        return appIcon1;
    }

    public void setAppIcon1(String appIcon1) {
        this.appIcon1 = appIcon1;
    }

    public String getAppIcon2() {
        return appIcon2;
    }

    public void setAppIcon2(String appIcon2) {
        this.appIcon2 = appIcon2;
    }

    public String getIntoSpace() {
        return intoSpace;
    }

    public void setIntoSpace(String intoSpace) {
        this.intoSpace = intoSpace;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }

    public String getAppClassification() {
        return appClassification;
    }

    public void setAppClassification(String appClassification) {
        this.appClassification = appClassification;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "App{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", personScope='" + personScope + '\'' +
                ", appLvl='" + appLvl + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", appImage='" + appImage + '\'' +
                ", appIcon1='" + appIcon1 + '\'' +
                ", appIcon2='" + appIcon2 + '\'' +
                ", intoSpace='" + intoSpace + '\'' +
                ", eduType='" + eduType + '\'' +
                ", appClassification='" + appClassification + '\'' +
                ", providerName='" + providerName + '\'' +
                ", providerId='" + providerId + '\'' +
                '}';
    }
}
