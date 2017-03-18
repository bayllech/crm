package com.kaishengit.dao;

import com.kaishengit.pojo.Document;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */

/**
 * 公用dao
 * @param <T>  泛型
 * @param <PK> 主键id
 */
public class BaseDao<T , PK extends Serializable>{

    @Autowired
    private SessionFactory sessionFactory;
    private Class clazz;

    public BaseDao () {
        //调用子类会先创建父类，通过子类对象调用父类时，父类中的this是指子类对象
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //接收子类Class
        clazz = (Class) type.getActualTypeArguments()[0];
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }




}
