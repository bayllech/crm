package com.kaishengit.dao;

import com.kaishengit.pojo.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends BaseDao<User,Integer>{



    //<editor-fold desc="getSession">
    @Autowired
    private SessionFactory sessionFactory;
    private Session session(){
        return sessionFactory.getCurrentSession();
    }
    //</editor-fold>

    public User login(String username, String password) {
        Criteria criteria = session().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.eq("password", password));
        return (User) criteria.uniqueResult();
    }

    public User findByUserName(String username) {
        Criteria criteria = session().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    public List<User> findByParam(String start,String length) {
        Criteria criteria = session().createCriteria(User.class);
        criteria.setFirstResult(Integer.valueOf(start));
        criteria.setMaxResults(Integer.valueOf(length));

        return criteria.list();
    }
}
