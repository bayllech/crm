package com.kaishengit.controller;

import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 内部网盘文件列表
     * @return
     */
    @GetMapping
    public String documentList(Model model,
                               @RequestParam(required = false , defaultValue = "0" ) Integer fid) {

        List<Document> documentList = documentService.findDocumentByFid(fid);
        model.addAttribute("documentList",documentList);
        model.addAttribute("fid" , fid);
        return "document/list";


    }

}
