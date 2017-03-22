package com.kaishengit.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import com.kaishengit.dao.DocumentDao;
import com.kaishengit.exception.ServiceException.ServiceException;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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

        //判断文件夹是否存在，不存在创建
        File filePath = new File(UploadPath);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }


        String name = file.getOriginalFilename();

        String lastName = "";
        if(name.lastIndexOf(".") != -1) {
            lastName = name.substring(name.lastIndexOf("."));
        }
        String newName = UUID.randomUUID().toString() + lastName;
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
        document.setFilename(newName);
        document.setSize(FileUtils.byteCountToDisplaySize(file.getSize()));
        documentDao.save(document);

    }


    /**
     * 分页显示
     * @param pageNo  页码
     * @param documentList
     * @return
     */
    //TODO
    @Transactional(readOnly = true)
    public Page<Document> findByPage(int pageNo, List<Document> documentList) {
        return documentDao.findByPage(pageNo,5);
    }


    /**
     * 下载文件
     * @param id  根据id查找
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public InputStream downloadFile(Integer id) throws FileNotFoundException {
        Document document = findById(id);
        if(document == null || Document.TYPE_DIR.equals(document.getType())) {
            return null;
        } else {
            FileInputStream inputStream =
                    new FileInputStream(new File(new File(UploadPath) , document.getFilename()));
            return inputStream;

        }

    }

    @Override
    public Document findById(Integer id) {
        return documentDao.findById(id);
    }

    @Override
    @Transactional
    public void delById(Integer id) {

        Document document = findById(id);
        if(document != null) {
            if(Document.TYPE_DOC.equalsIgnoreCase(document.getType())) {
                //删除文件
                File file = new File(UploadPath,document.getFilename());
                file.delete();
                //删除数据库记录
                documentDao.delete(id);

            }else {

                List<Document> documentList = documentDao.findAll();
                List<Integer> delIdList = Lists.newArrayList();
                findDelId(documentList,delIdList,id);
                delIdList.add(id);
                for(Integer delId : delIdList) {
                    documentDao.delete(delId);
                }

            }

        }


    }

    /**
     * 递归删除文件夹文件
     * @param documentList
     * @param delIdList  文件夹id集合
     * @param id
     */
    private void findDelId(List<Document> documentList, List<Integer> delIdList, Integer id) {

        for(Document document : documentList) {
            if(document.getFid().equals(id)){
                delIdList.add(document.getId());
                if(document.getType().equals(Document.TYPE_DIR)) {
                    findDelId(documentList, delIdList, document.getId());
                }else {
                    File file = new File(UploadPath , document.getFilename());
                    file.delete();
                }

            }

        }

    }


}
