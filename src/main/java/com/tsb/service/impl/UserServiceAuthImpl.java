package com.tsb.service.impl;

import com.tsb.dao.IUserAuthDao;
import com.tsb.model.UserAuth;
import com.tsb.service.IUserAuthService;
import com.tsb.utils.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceAuthImpl implements IUserAuthService {

    @Resource
    private IUserAuthDao userAuthDao;

    @Override
    public UserAuth selectUserAuth(String userId) {
        return this.userAuthDao.selectUserAuth(userId);
    }

    @Override
    public void insertUserAuth(UserAuth userAuth) {
        userAuth.setC_state("1");
        userAuth.setId(UuidUtil.generateUUID());
        userAuthDao.insertUserAuth(userAuth);
    }

}