package com.kaishengit.service.impl;

import com.kaishengit.dao.CustomerDao;
import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/1.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void save(Customer customer) {
        if(customer.getCompanyid() != null) {
            Customer company = customerDao.findById(customer.getCompanyid());
            customer.setCompanyname(company.getName());
        }
        customer.setUserid(ShiroUtil.getCurrentUserID());
        customer.setPinyin(Strings.toPinyin(customer.getName()));
        customerDao.save(customer);
    }

    @Override
    public void edit(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void del(Integer id) {
        customerDao.delete(id);
    }

    @Override
    public Object findAllCompany() {
        return customerDao.findAllCompany();
    }

    @Override
    public Long count() {
        return customerDao.count();
    }

    @Override
    public Long filterCount(Map<String, Object> map) {
        return customerDao.filterCount(map);
    }

    @Override
    public Customer findById(Integer id) {
            return customerDao.findById(id);
    }


    @Override
    public List<Customer> findAllByCompanyid(Integer id) {
        return customerDao.findAllByCompanyid(id);
    }

    @Override
    public List<Customer> findByParam(String start, String length, String keyword) {
        return customerDao.findByParam(start,length,keyword);
    }
}
