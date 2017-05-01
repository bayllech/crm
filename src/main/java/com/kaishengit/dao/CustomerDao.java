package com.kaishengit.dao;

import com.kaishengit.pojo.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bayllech on 2017/5/1.
 */
@Repository
public class CustomerDao extends BaseDao<Customer,Integer> {

}
