package com.ai.dao;

import com.ai.domain.loveLittle.LoveLittle;
import org.springframework.stereotype.Repository;

@Repository
public class LoveLittleDao extends BaseDao<LoveLittle>{
    public void changeLoveLittleStatus(LoveLittle loveLittle) {
        getTemplate().update(getEntityClass().getName() + ".changeStatus", loveLittle);
    }
}