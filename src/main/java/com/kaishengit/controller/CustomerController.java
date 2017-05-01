package com.kaishengit.controller;

import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bayllech on 2017/5/1.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String list() {

        return "customer/list";
    }

    //新增客户
    @PostMapping("/new")
    @ResponseBody
    public String newCustomer(Customer customer) {
        customerService.save(customer);
        return "success";
    }

    //编辑客户
    @PostMapping("/edit")
    @ResponseBody
    public  String edit(Customer customer) {
        customerService.edit(customer);
        return "success";
    }

    //删除客户
    @GetMapping("/del/{id:\\+}")
    @ResponseBody
    public String del(@PathVariable Integer id) {
        customerService.del(id);
        return "success";
    }
}
