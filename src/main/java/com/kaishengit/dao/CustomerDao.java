package com.kaishengit.dao;

import com.kaishengit.pojo.Customer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/1.
 */
@Repository
public class CustomerDao extends BaseDao<Customer,Integer> {

    //查询所有公司
    public Object findAllCompany() {
        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("type","company"));
        return criteria.list();
    }

    public Long filterCount(Map<String, Object> map) {
        Criteria criteria = getSession().createCriteria(Customer.class);
        if (map.get("keyword") != "") {
            criteria.add(Restrictions.eq("name", map.get("keyword")));
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public List<Customer> findByParam(String start,String length,String keyword) {
        Criteria criteria = getSession().createCriteria(Customer.class);
        if (keyword != "") {
            criteria.add(Restrictions.eq("name", keyword));
        }
        criteria.setFirstResult(Integer.parseInt(start));
        criteria.setMaxResults(Integer.parseInt(length));

        return criteria.list();
    }

    public List<Customer> findAllByCompanyid(Integer id) {
        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("companyid", id));
        return criteria.list();
    }
}
