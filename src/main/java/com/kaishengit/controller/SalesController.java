package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.pojo.Sales;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.SalesService;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customerList", customerService.findAllCustomer());
        return "sales/list";
    }

    //异步加载所有销售机会及搜索
    @RequestMapping(method = RequestMethod.GET,value = "/load")
    @ResponseBody
    public DataTablesResult<Sales> load(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        String name = request.getParameter("name");
        name = Strings.toUTF8(name);
        String progress = request.getParameter("progress");
        progress = Strings.toUTF8(progress);
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");

        Map<String,Object> params = Maps.newHashMap();
        params.put("start",start);
        params.put("length",length);
        params.put("name",name);
        params.put("progress",progress);
        params.put("startdate",startDate);
        params.put("enddate",endDate);

        List<Sales> salesList = salesService.findByParam(params);
        Long count = salesService.count();
        Long countParam = salesService.countByParam(params);

        return new DataTablesResult<>(draw,salesList,count,countParam);

    }

    //新增销售机会
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String save(Sales sales) {
        salesService.saveSales(sales);
        return "success";
    }


}
