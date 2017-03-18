package com.kaishengit.dao;

import com.kaishengit.pojo.Document;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */
@Repository
public class DocumentDao extends BaseDao<Document , Integer> {

    public List<Document> findAllDocumentByFid(Integer fid) {

        String hql = "from Document where fid = ?";
        Query query = getSession().createQuery(hql);

        query.setString(0,"fid");

        List<Document> documentList = query.list();
        return documentList;
    }
}
