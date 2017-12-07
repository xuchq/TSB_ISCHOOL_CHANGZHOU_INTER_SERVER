package com.tsb.model;

/**
 * 用于查询是否在"服务体系"认证过,对应表t_s_user_auth
 * @author xuchq 2017.11.21
 */
public class UserAuth {

    private String id;//主键id
    private String c_userid;//用户id
    private String c_state;//认证状态 0 未认证 1认证（数据库默认1）
    private String isUse;//是否可用
    private String localSession;//用于提供给"服务体系"，方便入参

    public UserAuth() {
    }

    public String getLocalSession() {
        return localSession;
    }

    public void setLocalSession(String localSession) {
        this.localSession = localSession;
    }

    public UserAuth(String c_userid) {
        this.c_userid=c_userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getC_userid() {
        return c_userid;
    }

    public void setC_userid(String c_userid) {
        this.c_userid = c_userid;
    }

    public String getC_state() {
        return c_state;
    }

    public void setC_state(String c_state) {
        this.c_state = c_state;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }
}