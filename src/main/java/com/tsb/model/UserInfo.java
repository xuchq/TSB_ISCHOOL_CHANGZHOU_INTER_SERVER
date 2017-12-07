package com.tsb.model;

/**
 * 用于根据userid查询用户信息和更新用户信息（从TSB_ISCHOOL_SNS_SERVER系统）
 *
 * @author xuchq 2017.11.21
 */
public class UserInfo {
    private String sid;//用户id
    private String lgnname;//登录名
    private String name;//姓名
    private String byname;//姓
    private String forename;//名
    private String certificateid;//身份证
    private String schoolname;//学校名
    private String orgCode;//全国唯一机构编码
    private String mobile;//手机号
    private String verifyCode;//验证码
    private String juese;//用于前台显示角色
    private String occupation;//身份类型<规则参考天世博编码规范 <1=小学生,2=初中生,3=高中生,4=职高生,5=中专生,6=大专生,7=大学生,8=研究生,9=专任教师,10=职工,11=家长>>
    public UserInfo() {
    }

    public UserInfo(String sid, String lgnname, String name, String certificateid, String occupation,String schoolname,String orgCode, String mobile, String verifyCode) {
        this.sid = sid;
        this.lgnname = lgnname;
        this.name = name;
        this.certificateid = certificateid;
        this.schoolname = schoolname;
        this.orgCode = orgCode;
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.occupation = occupation;
    }
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getJuese() {
        return juese;
    }

    public void setJuese(String juese) {
        this.juese = juese;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLgnname() {
        return lgnname;
    }

    public void setLgnname(String lgnname) {
        this.lgnname = lgnname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getByname() {
        return byname;
    }

    public void setByname(String byname) {
        this.byname = byname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getCertificateid() {
        return certificateid;
    }

    public void setCertificateid(String certificateid) {
        this.certificateid = certificateid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
