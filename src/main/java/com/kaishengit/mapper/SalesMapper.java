package com.kaishengit.mapper;

import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.SalesFile;
import com.kaishengit.pojo.SalesLog;

import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */

public interface SalesMapper {
    List<Sales> findByParam(Map<String, Object> params);

    Long countByParam(Map<String, Object> params);

    void save(Sales sales);

    Sales findById(Integer id);

    void update(Sales sales);

    void del(Integer id);

    void delSalesFile(List<SalesFile> salesFileList);

    void delSalesLog(List<SalesLog> salesLogList);
}
