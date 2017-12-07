####此项目用于常州青果与"服务体系"对接
#####一、上线步骤：
#####1.修改数据源
修改src/main/resources/conf/jdbc.properties
#####2.新增两张表
运行sql/db.sql
#####3.向刚刚新建的t_s_org_code插入数据
找到src/test/java/com/tsb/dao/IOrganizationTest.java，打开如下注释运行
```
    /**
     * 获取江苏省全省机构+orgCode,写入t_s_org_code表
     * @throws Exception
     */
    @Test
    public void insertOrg() throws Exception {
        /*String accessToken = ChangZhouUtil.getAccessToken();
        if (!"".equals(accessToken)) {
            analyseOrg(accessToken);
        }*/
    }
```
####二、项目依赖天仕博接口如下
#####1.通过获取的token返回userid
```
getuseridUrl=http://localhost:8080/TSB_ISCHOOL_SNS_SERVER/validate/getuserid
```
#####2.根据userid获取用户信息
```
getuserUrl=http://localhost:8082/TSB_ISCHOOL_DOCKING_SERVER/user/getUser
```
#####3.更新用户接口
```
updateuserUrl=http://localhost:8082/TSB_ISCHOOL_DOCKING_SERVER/user/updateUser
```