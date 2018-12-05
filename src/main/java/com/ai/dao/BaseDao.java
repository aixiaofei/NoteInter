package com.ai.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BaseDao<T> {
    private Class entityClass;

    @Autowired
    private SqlSessionTemplate template;

    BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public void update(T object) {
        getTemplate().update(getEntityClass().getName() + ".update", object);
    }

    public void update(List<T> object) {
        getTemplate().update(getEntityClass().getName() + ".batchUpdate", object);
    }

    public void delete(T object){
        getTemplate().delete(getEntityClass().getName()+ ".delete", object);
    }

    public int save(T object) {
        return getTemplate().insert(getEntityClass().getName() + ".save", object);
    }

    public int save(List<T> object) {
        return getTemplate().insert(getEntityClass().getName() + ".batchSave", object);
    }

    public T get(T object) {
        return getTemplate().selectOne(getEntityClass().getName() + ".getOne", object);
    }

    public List<T> getList(T object) {
        return getTemplate().selectList(getEntityClass().getName() + ".getList", object);
    }

    public Page getDataByPage(Map object) {
        int totalCount = getTemplate().selectOne(getEntityClass().getName() + ".count", object);
        int pageSize = Integer.parseInt(String.valueOf(object.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(object.get("pageNum")));
        Page page = new Page(pageNum, totalCount, pageSize, null);
        object.put("start", page.getStart());
        object.put("pageSize", pageSize);
        if (page.isPage()) {
            List<T> resData = getTemplate().selectList(getEntityClass().getName() + ".selectData", object);
            page.setData(resData);
        }
        return page;
    }

    public SqlSessionTemplate getTemplate() {
        return template;
    }

    public Class getEntityClass() {
        return entityClass;
    }
}
