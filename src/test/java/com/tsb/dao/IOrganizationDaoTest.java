package com.tsb.dao;

import com.tsb.model.Organization;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;

import static com.tsb.utils.ChangZhouUtil.getOrgCode;

// 加载spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring-mybatis.xml"})
public class IOrganizationDaoTest {

    @Autowired
    private IOrganizationDao organizationDao;


    @Test
    public void insertOrg() throws Exception {
        /*String accessToken = ChangZhouUtil.getAccessToken();
        if (!"".equals(accessToken)) {
            analyseOrg(accessToken);
        }*/
    }
    /**
     * 获取江苏省全省机构+orgCode,写入t_s_org_code表
     *
     * @param accessToken
     */
    public  void analyseOrg(String accessToken) {
        JSONObject orgResult = getOrgCode("320000", "", "", "", accessToken);
        String retCode = orgResult.getString("retCode");
        Organization organization = null;
        if (retCode.equals("000000")) {
            String count = JSONObject.fromObject(orgResult.getString("data")).getString("count");
            JSONArray orgList = JSONObject.fromObject(orgResult.getString("data")).getJSONArray("dataList");
            Iterator iterator = orgList.iterator();
            int i=0;
            while (iterator.hasNext()) {
                JSONObject o = (JSONObject) iterator.next();
                String orgId = o.getString("orgId");
                String orgCode = o.getString("orgCode");
                String orgName = o.getString("orgName");
                System.out.println("orgId:"+orgId+",orgCode:"+orgCode+",orgName:"+orgName);
                organization=new Organization(orgId,orgCode,orgName);
                organizationDao.insertOrg(organization);
                System.out.println("i:"+i+",orgId:"+orgId);
                i++;
            }
            System.out.println("获取机构数量："+i);
            System.out.println("获取机构总数量："+count);
        }
    }
}