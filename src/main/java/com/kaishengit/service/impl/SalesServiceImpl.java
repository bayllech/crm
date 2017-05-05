package com.kaishengit.service.impl;

import com.google.common.collect.Maps;
import com.kaishengit.dao.SalesDao;
import com.kaishengit.mapper.SalesMapper;
import com.kaishengit.pojo.Sales;
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
}
