package com.tsb.service;

import com.tsb.model.Organization;

import java.util.List;

public interface IOrgService {

    public List<Organization> selectOrg(Organization organization);

}