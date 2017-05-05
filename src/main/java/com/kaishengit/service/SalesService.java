package com.kaishengit.service;

import com.kaishengit.pojo.Sales;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */
public interface SalesService {

    List<Sales> findByParam(Map<String, Object> params);

    Long count();

    Long countByParam(Map<String, Object> params);
}
