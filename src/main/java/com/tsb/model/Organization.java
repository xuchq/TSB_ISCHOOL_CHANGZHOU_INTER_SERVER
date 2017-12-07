package com.tsb.model;


/**
 * {"orgType":"0","orgId":"0b6c0d7e2c4b4cb89b74c6db5fdf1690",
 * "areaCode":"320322","orgCode":"2132006532",
 * "provinceCode":"320000","cityCode":"320300",
 * "orgName":"沛县龙固镇三里小学"}
 */
public class Organization {

    public Organization() {}

    public Organization(String id, String orgCode, String orgName) {
        this.id = id;
        this.orgCode = orgCode;
        this.orgName = orgName;
    }

    public Organization(String orgName) {
        this.orgName = orgName;
    }

    private String id;
    private String orgCode;
    private String orgName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}