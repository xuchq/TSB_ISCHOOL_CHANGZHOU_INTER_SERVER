package com.tsb.dao;

import com.tsb.model.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrganizationDao {

    void insertOrg(Organization organization);
    List<Organization> selectOrg(Organization organization);

}