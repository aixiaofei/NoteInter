package com.ai.service;

import com.ai.dao.LoginDao;
import com.ai.domain.login.LoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService extends BaseService{

    @Autowired
    private LoginDao loginDao;

    @Transactional
    public void saveLoginData(LoginData data){
        loginDao.save(data);
    }
}
