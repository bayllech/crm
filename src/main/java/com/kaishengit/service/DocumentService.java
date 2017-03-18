package com.kaishengit.service;

import com.kaishengit.dao.DocumentDao;
import com.kaishengit.pojo.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */
@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentDao documentDao;
    @Value("${upload.path}")
    private String UploadPath;


    public List<Document> findDocumentByFid(Integer fid) {
        return documentDao.findAllDocumentByFid(fid);
    }
}
