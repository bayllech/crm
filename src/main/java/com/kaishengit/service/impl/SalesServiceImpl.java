package com.kaishengit.service.impl;

import com.google.common.collect.Maps;
import com.kaishengit.dao.CustomerDao;
import com.kaishengit.dao.SalesDao;
import com.kaishengit.dao.SalesLogDao;
import com.kaishengit.mapper.SalesMapper;
import com.kaishengit.pojo.Customer;
import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.SalesLog;
import com.kaishengit.service.SalesService;
import com.kaishengit.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesDao salesDao;
    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private SalesLogDao salesLogDao;
    @Override
    public List<Sales> findByParam(Map<String, Object> params) {
        return salesMapper.findByParam(params);
    }

    @Override
    public Long count() {
        Map<String,Object> params = Maps.newHashMap();
        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
        return salesMapper.countByParam(params);
    }

    @Override
    public Long countByParam(Map<String, Object> params) {
        if(ShiroUtil.isEmployee()) {
            params.put("userid",ShiroUtil.getCurrentUserID());
        }
        return salesMapper.countByParam(params);
    }

    @Override
    public void saveSales(Sales sales) {
        Customer customer = customerDao.findById(sales.getCustid());
        sales.setCustname(customer.getName());
        sales.setUserid(ShiroUtil.getCurrentUserID());
        sales.setUsername(ShiroUtil.getCurrentUser().getRealname());

        salesMapper.save(sales);
        //自动生成创建日志
        SalesLog salesLog = new SalesLog();
        salesLog.setType("AUTO");
        salesLog.setContext(ShiroUtil.getCurrentUser().getRealname() + "  创建了新的销售机会");
        salesLog.setSalesid(sales.getId());
        salesLogDao.save(salesLog);

    }

    @Override
    public Sales findById(Integer id) {
        return salesMapper.findById(id);
    }

    @Override
    public List<SalesLog> findSalesLogBySalesId(Integer id) {
        return salesLogDao.findBySalesId(id);
    }
}
