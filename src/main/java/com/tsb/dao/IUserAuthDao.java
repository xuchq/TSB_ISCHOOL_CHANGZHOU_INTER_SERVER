package com.tsb.dao;

import com.tsb.model.UserAuth;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAuthDao {

    UserAuth selectUserAuth(String id);

    void insertUserAuth(UserAuth userAuth);
}