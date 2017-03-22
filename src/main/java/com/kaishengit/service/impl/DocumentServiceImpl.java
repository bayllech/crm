package com.kaishengit.service.impl;

import com.kaishengit.dao.DocumentDao;
import com.kaishengit.exception.ServiceException.ServiceException;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;
    @Value("${upload.path}")
    private String UploadPath;


    /**
     * 获取文件列表
     * @param fid
     * @return
     */
    @Override
    public List<Document> findDocumentByFid(Integer fid) {
        return documentDao.findAllDocumentByFid(fid);
    }

    /**
     * 新建文件夹
     * @param name
     * @param fid
     */
    @Override
    public void saveDir(String name, Integer fid) {

        Document document = new Document();
        document.setName(name);
        document.setFid(fid);
        document.setCreateuser(ShiroUtil.getCurrentUserName());
        document.setType(Document.TYPE_DIR);
        documentDao.save(document);

    }

    /**
     * 新建文件
     * @param file
     * @param fid 父id
     * originalFilename 源文件名
     */
    @Override
    public void saveFile(MultipartFile file, Integer fid) {
       String name = file.getOriginalFilename();

       String lastName = "";
       if(name.lastIndexOf(".") != -1) {
            lastName = name.substring(name.lastIndexOf("."));
       }
       String newName = UUID.randomUUID().toString() + name;
       try {
           File saveFile = new File(new File(UploadPath) , newName);
           FileOutputStream outputStream = new FileOutputStream(saveFile);
           InputStream inputStream = file.getInputStream();

           IOUtils.copy(inputStream , outputStream);
           outputStream.flush();
           outputStream.close();
           inputStream.close();


       } catch (IOException e) {
           throw new ServiceException("文件存储异常" , e);
       }

       Document document = new Document();
       document.setCreateuser(ShiroUtil.getCurrentUserName());
       document.setContexttype(file.getContentType());
       document.setName(name);
       document.setType(Document.TYPE_DOC);
       document.setFid(fid);
       document.setSize(FileUtils.byteCountToDisplaySize(file.getSize()));
       documentDao.save(document);

    }


    /**
     * 分页显示
     * @param pageNo  页码
     * @param queryParamList
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Document> findByPage(int pageNo, List<QueryParam> queryParamList) {
        return documentDao.findByPage(pageNo,10,queryParamList);
    }
}
