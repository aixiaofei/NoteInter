package com.ai.dao;

import com.ai.domain.user.User;
import com.ai.domain.loveRelation.LoveRelation;
import org.springframework.stereotype.Repository;

@Repository
public class LoveRelationDao extends BaseDao<LoveRelation>{

    public User getLoveInfo(int userId){
        return getTemplate().selectOne(getEntityClass().getName()+ ".getLoveInfo", userId);
    }

    public LoveRelation getLoveNumber(int userId){
        return getTemplate().selectOne(getEntityClass().getName()+ ".getLoveNumber", userId);
    }
}