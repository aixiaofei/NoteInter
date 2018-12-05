package com.ai.dao;

import com.ai.domain.user.User;
import com.ai.web.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User>{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public User findUserByName(String name){
        return getTemplate().selectOne(getEntityClass().getName()+".findUserByName", name);
    }

    public User findUser(User user){
        return getTemplate().selectOne(getEntityClass().getName()+".findUser", user);

    }

    public User findLoveLock(User user){
        return getTemplate().selectOne(getEntityClass().getName()+ ".findLoveLock", user);
    }

    public void changeSingle(User user){
        getTemplate().update(getEntityClass().getName()+ ".updateSingle", user);
    }
}
