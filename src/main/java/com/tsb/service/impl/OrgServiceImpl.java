package com.tsb.service.impl;

import com.tsb.dao.IOrganizationDao;
import com.tsb.model.Organization;
import com.tsb.model.UserAuth;
import com.tsb.service.IOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orgService")
public class OrgServiceImpl implements IOrgService {

    @Resource
    private IOrganizationDao organizationDao;

    public List<Organization> selectOrg(Organization organization) {
        return this.organizationDao.selectOrg(organization);
    }

}