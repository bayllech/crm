package com.kaishengit.service;

import com.kaishengit.pojo.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/1.
 */
public interface CustomerService {

    void save(Customer customer);

    void edit(Customer customer);

    void del(Integer id);

    List<Customer> findAllCompany();

    Long count();

    Long filterCount(Map<String, Object> map);

    Customer findById(Integer id);

    List<Customer> findAllByCompanyid(Integer id);

    List<Customer> findByParam(String start, String length, String keyword);

    String mkMeCard(Integer id);

    void openCustomer(Customer customer);

    void moveCustomer(Customer customer, Integer userid);

    List<Customer> findAllCustomer();
}
