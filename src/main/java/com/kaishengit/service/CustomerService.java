package com.kaishengit.service;

import com.kaishengit.pojo.Customer;
import org.springframework.stereotype.Service;

/**
 * Created by bayllech on 2017/5/1.
 */
public interface CustomerService {

    void save(Customer customer);

    void edit(Customer customer);

    void del(Integer id);
}
