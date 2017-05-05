package com.kaishengit.dao;

import com.kaishengit.pojo.SalesLog;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bayllech on 2017/5/5.
 */
@Repository
public class SalesLogDao extends BaseDao<SalesLog,Integer> {
    public List<SalesLog> findBySalesId(Integer id) {
        Criteria criteria = getSession().createCriteria(SalesLog.class);
        criteria.add(Restrictions.eq("salesid", id));
        return criteria.list();
    }
}
