package com.kaishengit.dao;

import com.kaishengit.pojo.SalesFile;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bayllech on 2017/5/6.
 */
@Repository
public class SalesFileDao extends BaseDao<SalesFile,Integer> {

    public List<SalesFile> findBySalesId(Integer salesid) {
        Criteria criteria = getSession().createCriteria(SalesFile.class);
        criteria.add(Restrictions.eq("salesid", salesid));
        return criteria.list();
    }
}
