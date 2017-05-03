package com.kaishengit.service.impl;

import com.kaishengit.dao.CustomerDao;
import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Strings;
import org.apache.commons.lang3.StringUtils;
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
    public List<Customer> findAllCompany() {
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

    @Override
    public String mkMeCard(Integer id) {
        Customer customer = customerDao.findById(id);
        StringBuilder mecard = new StringBuilder("MECARD:");
        if (StringUtils.isNotEmpty(customer.getName())) {
            mecard.append("N:" + customer.getName() + ";");
        }
        if (StringUtils.isNotEmpty(customer.getTel())) {
            mecard.append("TEL:" + customer.getTel() + ";");
        }
        if (StringUtils.isNotEmpty(customer.getEmail())) {
            mecard.append("EMAIL:" + customer.getEmail() + ";");
        }
        if (StringUtils.isNotEmpty(customer.getAddress())) {
            mecard.append("ADR:" + customer.getAddress() + ";");
        }
        if (StringUtils.isNotEmpty(customer.getCompanyname())) {
            mecard.append("ORG:" + customer.getCompanyname() + ";");
        }
        mecard.append(";");
        return mecard.toString();
    }

    @Override
    public void openCustomer(Customer customer) {
        customer.setUserid(null);
        customerDao.save(customer);
    }

    @Override
    public void moveCustomer(Customer customer, Integer userid) {
        customer.setUserid(userid);
        customerDao.save(customer);
    }
}
