package com.oilchem.trade.bean;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 中间参数据类
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-13
 * Time: 下午11:26
 * To change this template use File | Settings | File Templates.
 */
public class DetailCriteria{
    //大表的字段名
    private String fieldName;
    //大表的字段对应的实例类类型
    private Class idEntityClass;
    //大表的字段对应的实例的dao的类型
    private Class daoClass;
    //大表的字段对应的实例的dao的findBy方法
    private Method findByMethod;
    //大表的字段对应的dao
    private Object dao;
    //大表的字段对应的表中的记录
    private volatile Set<String> retName;

    public DetailCriteria() {
    }

    public DetailCriteria(String fieldName, Class idEntityClass,
                          Class daoClass, Method findByMethod, Object dao) {
        this.fieldName = fieldName;
        this.idEntityClass = idEntityClass;
        this.daoClass = daoClass;
        this.findByMethod = findByMethod;
        this.dao = dao;
    }

    public DetailCriteria(String fieldName, Class idEntityClass, Class daoClass,
                          Method findByMethod, Object dao, Set<String> retName) {
        this.fieldName = fieldName;
        this.idEntityClass = idEntityClass;
        this.daoClass = daoClass;
        this.findByMethod = findByMethod;
        this.dao = dao;
        this.retName = retName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getIdEntityClass() {
        return idEntityClass;
    }

    public void setIdEntityClass(Class idEntityClass) {
        this.idEntityClass = idEntityClass;
    }

    public Class getDaoClass() {
        return daoClass;
    }

    public void setDaoClass(Class daoClass) {
        this.daoClass = daoClass;
    }

    public Method getFindByMethod() {
        return findByMethod;
    }

    public void setFindByMethod(Method findByMethod) {
        this.findByMethod = findByMethod;
    }

    public Object getDao() {
        return dao;
    }

    public void setDao(Object dao) {
        this.dao = dao;
    }

    public Set<String> getRetName() {
        return retName;
    }

    public void setRetName(Set<String> retName) {
        this.retName = retName;
    }
}
