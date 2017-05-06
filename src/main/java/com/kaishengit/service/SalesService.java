package com.kaishengit.service;

import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.SalesFile;
import com.kaishengit.pojo.SalesLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */
public interface SalesService {

    List<Sales> findByParam(Map<String, Object> params);

    Long count();

    Long countByParam(Map<String, Object> params);

    void saveSales(Sales sales);

    Sales findById(Integer id);

    List<SalesLog> findSalesLogBySalesId(Integer id);

    void update(Sales sales);

    void saveLog(Integer salesid, String content);

    void updateFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer salesid) throws IOException;

    List<SalesFile> findAllFile(Integer id);

    SalesFile findFileById(Integer id);

    void del(Integer id);
}
