package com.kaishengit.controller;

import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by jiahao0 on 2017/3/18.
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    /**
     * 根据fid查找文件和文件夹
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

    /**
     * 创建新文件夹
     * @return
     */
    @PostMapping("/dir/new")
    public String saveDir(String name , Integer fid) {
        documentService.saveDir(name , fid);

        return "redirect:/doc:fid" + fid;
    }


    @PostMapping("/file/upload")
    @ResponseBody
    public String saveFile (MultipartFile file , Integer fid) throws IOException {

        if(file.isEmpty()) {
            throw new NotFoundException();
        } else {
           documentService.saveFile(file ,fid);
            return "success";
        }
    }

}
