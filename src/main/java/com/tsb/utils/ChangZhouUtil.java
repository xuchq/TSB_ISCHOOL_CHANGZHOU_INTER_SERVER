package com.tsb.utils;

import com.alibaba.fastjson.JSON;
import com.tsb.model.App;
import com.tsb.model.Organization;
import com.tsb.model.UserInfo;
import com.whty.apigateway.security.EncryptionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class ChangZhouUtil {
    /**
     * 获取接口访问令牌
     *
     * @return
     */
    public static String getAccessToken() {
        String accessTokenUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("accessTokenUrl");
        String appId = PropertyUtil.getProperty("appId");
        String appkey = PropertyUtil.getProperty("appkey");
        String sysCode = PropertyUtil.getProperty("sysCode");
        String ts = String.valueOf(System.currentTimeMillis());
//        System.out.println("ts:" + ts);
        String paramValues = appId + appkey + ts;
        byte[] hmacSHA = EncryptionUtils.getHmacSHA1(paramValues, appkey);
        String digest = EncryptionUtils.bytesToHexString(hmacSHA);
        digest = digest.toUpperCase();
//        System.out.println("digest:" + digest);
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("appId", appId);
        paramsFinal.put("keyInfo", digest);
        paramsFinal.put("timeStamp", ts);
        paramsFinal.put("sysCode", sysCode);
//        ClientRequestUtil requestUtil = new ClientRequestUtil();
        String result = HttpRequest.sendPost(accessTokenUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        String retCode = resultObj.getString("retCode");
//        String retDesc = resultObj.getString("retDesc");
        System.out.printf("获取接口访问令牌accessTokenUrl:"+accessTokenUrl+",参数："+JSON.toJSONString(paramsFinal).toString()+",结果："+result);
//        System.out.print(result);
//        System.out.printf("retCode:" + retCode);
//        System.out.printf("retDesc:" + retDesc);
        if (retCode.equals("000000")) {
            JSONObject dataObj = JSONObject.fromObject(resultObj.getString("data"));
            return dataObj.getString("accessToken");
        } else {
            System.out.printf("---------getAccessToken fail---------");
            return "";
        }
    }

    /**
     * 机构编码是教育部统一下发给各个机构或者学校的唯一编号，
     * 区域平台实名认证界面中机构编码的数据来源可以从体系获得。
     *
     * @param provinceCode
     * @param cityCode
     * @param areaCode
     * @param orgName
     * @param accessToken
     * @return
     */
    public static JSONObject getOrgCode(String provinceCode, String cityCode, String areaCode, String orgName, String accessToken) {
        String orgListUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("orgListUrl") + accessToken;
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        if (!"".equals(provinceCode)) {
            paramsFinal.put("provinceCode", provinceCode);
        }
        if (!"".equals(cityCode)) {
            paramsFinal.put("cityCode", cityCode);
        }
        if (!"".equals(areaCode)) {
            paramsFinal.put("areaCode", areaCode);
        }
        if (!"".equals(orgName)) {
            paramsFinal.put("orgName", orgName);
        }
        paramsFinal.put("pageNo", 1);
        paramsFinal.put("pageSize", 16000);
        String result = HttpRequest.sendPost(orgListUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        return resultObj;
        /*String retCode = resultObj.getString("retCode");
        if (retCode.equals("000000")) {
            String count = JSONObject.fromObject(resultObj.getString("data")).getString("count");
            String dataList = JSONObject.fromObject(resultObj.getString("data")).getString("dataList");
            JSONObject.fromObject(dataList);
        }
        return null;*/
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    public static UserInfo getUserByUserid(String userId) {
        String getuserUrl = PropertyUtil.getProperty("getuserUrl");
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("userid", userId);
        String result = HttpRequest.sendPost(getuserUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        JSONObject userInfo = JSONObject.fromObject(resultObj.getString("data"));
        String sid = userInfo.getString("sid");
        String lgnname = userInfo.getString("lgnname");
        if (null == lgnname || "null".equals(lgnname) || "".equals(lgnname)) {
            lgnname = userInfo.getString("iiNum");
        }
        String byname = userInfo.getString("byname");
        String forename = userInfo.getString("forename");
        String certificateid = userInfo.getString("certificateid");
        String schoolname = userInfo.getString("schoolname");
        String mobile = userInfo.getString("mobile");
        int occupation = Integer.parseInt(userInfo.getString("occupation"));
        String juese = "学生";
        Map<String, Object> jueseMap = switchOccupation(occupation, juese);
        occupation = (int) jueseMap.get("occupation");
        UserInfo myUserInfo = new UserInfo();
        myUserInfo.setSid(sid);
        myUserInfo.setOccupation(occupation + "");
        if (!CheckUtil.checkEmptyObject(byname)) {
            myUserInfo.setByname(byname);
        }
        if (!CheckUtil.checkEmptyObject(lgnname)) {
            myUserInfo.setLgnname(lgnname);
        }
        if (!CheckUtil.checkEmptyObject(forename)) {
            myUserInfo.setForename(forename);
        }
        if (!CheckUtil.checkEmptyObject(certificateid)) {
            myUserInfo.setCertificateid(certificateid);
        }
        if (!CheckUtil.checkEmptyObject(schoolname)) {
            myUserInfo.setSchoolname(schoolname);
        }
        if (!CheckUtil.checkEmptyObject(mobile)) {
            myUserInfo.setMobile(mobile);
        }
        myUserInfo.setJuese((String) jueseMap.get("juese"));
        return myUserInfo;
    }

    private static Map<String, Object> switchOccupation(int occupation, String juese) {
        //        身份类型<规则参考天世博编码规范 <1=小学生,2=初中生,3=高中生,4=职高生,5=中专生,6=大专生,7=大学生,8=研究生,9=专任教师,10=职工,11=家长>>
//        userIdentity:身份类型(0:学生，1:老师，2:家长，3:学校工作人员:4:机构工作人员;5:其他)
        if ((occupation - 1 > 0) && (occupation - 9 < 0)) {
            occupation = 0;
        }
        if (occupation == 9) {
            occupation = 1;
            juese = "老师";
        }
        if (occupation == 10) {
            occupation = 3;
            juese = "学校工作人员";
        }
        if (occupation == 11) {
            occupation = 2;
            juese = "家长";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("occupation", occupation);
        result.put("juese", juese);
        return result;
    }

    /**
     * 通过token获取userid
     *
     * @param tsbToken
     * @return
     */
    public static String getUseridByToken(String tsbToken) {
        try {
            String getuseridUrl = PropertyUtil.getProperty("getuseridUrl");
            Map<String, Object> paramsFinal = new HashMap<String, Object>();
            paramsFinal.put("tokenid", tsbToken);
            String result = HttpRequest.sendPost(getuseridUrl, JSON.toJSONString(paramsFinal).toString());
            JSONObject resultObj = JSONObject.fromObject(result);
            return JSONObject.fromObject(resultObj.getString("data")).getString("userid");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 发送手机验证码
     *
     * @param phoneNum
     * @return
     */
    public static String getPhoneVerifyCode(String phoneNum, String ACCESS_TOKEN) {
        String accessApiUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("phoneVerifyUrl") + ACCESS_TOKEN;
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("phone", phoneNum);
        String result = HttpRequest.sendPost(accessApiUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        String retCode = resultObj.getString("retCode");
        System.out.printf("调用短信接口地址:" + accessApiUrl);
        System.out.printf("调用短信接口参数:" + JSON.toJSONString(paramsFinal).toString());
        System.out.printf("调用短信接口结果:" + result);
        if ("000000".equals(retCode)) {
            return "发送短信成功";
        } else if ("300020".equals(retCode)) {
            return "接收验证码到达每日上限，请更换手机号或改天重试";
        } else {
            return "发送短信失败，请稍后重试";
        }
    }

    /**
     * 认证用户
     * {
     * "retCode": "000000",
     * "retDesc": "成功",
     * "data": {
     * "userId": "5781235041634be28123504163abe296"
     * }
     * }
     * 重复调用返回如下：
     * {
     * "retCode": "300017",
     * "retDesc": "该用户已实名认证",
     * "data": {
     * "userId": "5781235041634be28123504163abe296"
     * }
     * }
     * {"retCode":"300007","retDesc":"验证码不匹配"}
     *
     * @return
     */
    public static String validateUser(UserInfo userInfo, String ACCESS_TOKEN) {
        String accessApiUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("validateUserUrl") + ACCESS_TOKEN;
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("idCardNo", userInfo.getCertificateid());
        paramsFinal.put("loginAccount", userInfo.getLgnname());
        paramsFinal.put("name", userInfo.getName());
        paramsFinal.put("orgCode", userInfo.getOrgCode());
        paramsFinal.put("phone", userInfo.getMobile());
        paramsFinal.put("userIdentity", userInfo.getOccupation());
        paramsFinal.put("verifyCode", userInfo.getVerifyCode());
        String result = HttpRequest.sendPost(accessApiUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        String retCode = resultObj.getString("retCode");
//        String retDesc = resultObj.getString("retDesc");
//        System.out.printf("retCode:" + retCode + "retDesc" + retDesc);
        System.out.printf("用户认证：accessApiUrl：" + accessApiUrl + ",参数：" + JSON.toJSONString(paramsFinal).toString());
        System.out.printf("认证结果：" + result);
        return retCode;
    }

    /**
     * 修改用户信息接口   TSB_ISCHOOL_DOCKING_SERVER/user/updateUser
     * 参数{"sid":"0033061af3db4a6f9fa73521072fc183","schoolname":"学校名称","mobile":"手机",
     * "certificateid":"证件号","byname":"姓","forename":"名"}
     *
     * @param userInfo
     * @return
     */
    public static String updateUser(UserInfo userInfo) {
        String accessApiUrl = PropertyUtil.getProperty("updateuserUrl");
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("sid", userInfo.getSid());
//        paramsFinal.put("schoolname", userInfo.getSchoolname());先不修改学校名，防止出现数据冲突 by xuchq 2017.12.5
        paramsFinal.put("mobile", userInfo.getMobile());
        paramsFinal.put("certificateid", userInfo.getCertificateid());
        paramsFinal.put("byname", userInfo.getName().substring(0, 1));
        paramsFinal.put("forename", userInfo.getName().substring(1));
        String result = HttpRequest.sendPost(accessApiUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        //{"code":1,"errorCode":0,"data":1,"errorMessage":""}
        String code = resultObj.getString("code");
        String errorMessage = resultObj.getString("errorMessage");
        System.out.println("code:" + code + ",errorMessage:" + errorMessage);
        return code;
    }

    /**
     * 第三方会话交换
     *
     * @param localSession
     * @param accessToken
     */
    public static Map<String, String> sessionInterchange(String localSession, String accessToken) {
        Map<String, String> resultMap = new HashMap<>();
        String usessionId = "usessionId_fail";
        String accessApiUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("sessionInterUrl") + accessToken;
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("localSession", localSession);
        String result = HttpRequest.sendPost(accessApiUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        System.out.printf("accessApiUrl：" + accessApiUrl + "param：" + JSON.toJSONString(paramsFinal).toString() + "-----第三方会话交换result:" + result);
        String retCode = resultObj.getString("retCode");
        if ("000000".equals(retCode)) {
            usessionId = JSONObject.fromObject(resultObj.getString("data")).getString("usessionId");
            String ticket = JSONObject.fromObject(resultObj.getString("data")).getString("ticket");
            resultMap.put("ticket", ticket);
        }
        resultMap.put("usessionId", usessionId);
        return resultMap;
    }

    /**
     * 获取应用信息列表
     * pageNo可选     获取记录的页数，默认 1
     * pageSize可选     获取每页的条数，默认 10
     * usessionId可选
     * 服务体系用户会话 id,不传则获取区域和机构选用过的应用;传递则查询 登录用户可以查看到的应用。
     *
     * @param usessionId
     * @param accessToken
     */
    public static List<App> getAppList(String usessionId, String accessToken) {
        List<App> apps = new ArrayList<>();
        String accessApiUrl = PropertyUtil.getProperty("targetUrl") + PropertyUtil.getProperty("getAppListUrl") + accessToken;
        Map<String, Object> paramsFinal = new HashMap<String, Object>();
        paramsFinal.put("usessionId", usessionId);
        paramsFinal.put("pageNo", 1);
        paramsFinal.put("pageSize", Integer.MAX_VALUE);
        String result = HttpRequest.sendPost(accessApiUrl, JSON.toJSONString(paramsFinal).toString());
        JSONObject resultObj = JSONObject.fromObject(result);
        System.out.println("accessApiUrl:" + accessApiUrl);
        System.out.println("params:" + JSON.toJSONString(paramsFinal).toString());
        System.out.printf("获取应用信息列表:" + result + "》》》》》》》》》》》》》》》》》");
        String retCode = resultObj.getString("retCode");
//        String sysCode = PropertyUtil.getProperty("sysCode");
        if ("000000".equals(retCode)) {
            JSONArray appList = JSONObject.fromObject(resultObj.getString("data")).getJSONArray("dataList");
            Iterator iterator = appList.iterator();
            App app;
//            ticket = "&ticket=" + ticket + "&sysCode=" + sysCode;
            while (iterator.hasNext()) {
                JSONObject o = (JSONObject) iterator.next();
                app = generateApp(o);
                System.out.println("app:" + app);
                apps.add(app);
            }
        }
        return apps;
    }

    private static App generateApp(JSONObject o) {
        String appId = o.getString("appId");
        String appName = o.getString("appName");
        String appUrl = o.getString("appUrl");
        String personScope = o.getString("personScope");
        String appLvl = o.getString("appLvl");
        String appDesc = o.getString("appDesc");
        String appImage = o.getString("appImage");
        String appIcon1 = o.getString("appIcon1");
        String appIcon2 = o.getString("appIcon2");
        String intoSpace = o.getString("intoSpace");
        String eduType = o.getString("eduType");
        String appClassification = o.getString("appClassification");
        String providerName = o.getString("providerName");
        String providerId = o.getString("providerId");
        App app = new App(appId, appName, appUrl, personScope, appLvl, appDesc, appImage, appIcon1,
                appIcon2, intoSpace, eduType, appClassification, providerName, providerId);
        return app;
    }

    public static void main(String[] args) {
        List a = new ArrayList();
        a.add(1);
    }
}
