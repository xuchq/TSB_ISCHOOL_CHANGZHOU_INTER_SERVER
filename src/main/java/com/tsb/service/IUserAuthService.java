package com.tsb.service;

import com.tsb.model.UserAuth;

public interface IUserAuthService {

     UserAuth selectUserAuth(String userId);
     void insertUserAuth(UserAuth userAuth);

}