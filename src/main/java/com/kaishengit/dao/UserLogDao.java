package com.kaishengit.dao;

import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import com.kaishengit.shiro.ShiroUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLogDao extends BaseDao<UserLog,Integer> {

    /**
     * 查询登录日志
     * @param user
     * @return
     */
    public List<UserLog> findLog(User user) {
        Criteria criteria = getSession().createCriteria(UserLog.class);
        criteria.add(Restrictions.eq("user", user));
        return criteria.list();
    }
}
