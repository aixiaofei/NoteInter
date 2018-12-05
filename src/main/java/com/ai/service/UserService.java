package com.ai.service;

import com.ai.dao.LoveRelationDao;
import com.ai.dao.UserDao;
import com.ai.domain.user.User;
import com.ai.domain.loveRelation.LoveRelation;
import com.ai.service.connection.connectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends BaseService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoveRelationDao loveRelationDao;

    @Autowired
    private connectionHandler handler;

    public User getUserByName(String name){
        return userDao.findUserByName(name);
    }

    public User getUser(User user){
        return userDao.findUser(user);
    }

    @Transactional
    public void saveUser(User user, User loveUser){
        userDao.save(user);
        if(loveUser != null){
            userDao.changeSingle(loveUser);

            LoveRelation relation1 = new LoveRelation();
            relation1.setMyUserId(user.getUserId());
            relation1.setLoveUserId(loveUser.getUserId());
            relation1.setMyLoveLock(user.getMyLoveLock());
            relation1.setLoveLock(loveUser.getMyLoveLock());

            LoveRelation relation2 = new LoveRelation();
            relation2.setMyUserId(loveUser.getUserId());
            relation2.setLoveUserId(user.getUserId());
            relation2.setMyLoveLock(loveUser.getMyLoveLock());
            relation2.setLoveLock(user.getMyLoveLock());

            List<LoveRelation> relation = new ArrayList<>();
            relation.add(relation1);
            relation.add(relation2);

            loveRelationDao.save(relation);
        }
    }

    @Transactional
    public void updateUser(User user){
        userDao.update(user);
    }

    public User getLoveLock(User user){
        return userDao.findLoveLock(user);
    }

    public boolean checkLoveOnline(int userId){
        return handler.checkUserOnline(userId);
    }
}
