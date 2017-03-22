package com.kaishengit.service;

import com.kaishengit.dao.DocumentDao;
import com.kaishengit.pojo.Document;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */

public interface DocumentService {


    void saveFile(MultipartFile file, Integer fid);

    List<Document> findDocumentByFid(Integer fid);

    void saveDir(String name, Integer fid);
}
