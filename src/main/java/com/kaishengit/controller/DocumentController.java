package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.jboss.logging.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
                               @RequestParam(required = false , defaultValue = "0" ) Integer fid,
                               @RequestParam(required = false,defaultValue = "1") Integer p) {

        List<Document> documentList = documentService.findDocumentByFid(fid);
        Page<Document> documentPage = documentService.findByPage(p,documentList);

        model.addAttribute("page",documentPage);
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

        return "redirect:/document?fid="+fid;
    }


    /**
     * 保存文件
     * @param file
     * @param fid
     * @return
     * @throws IOException
     */
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


    /**
     * 下载文件
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping("/download/{id:\\d+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id)
            throws FileNotFoundException {
        InputStream inputStream = documentService.downloadFile(id);
        Document document = documentService.findById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachement" ,document.getName(), Charset.forName("UTF-8"));

        return new ResponseEntity<InputStreamResource>(new InputStreamResource(inputStream) , headers , HttpStatus.OK);

    }

    /**
     * 删除文件
     * @param id  根据文件id
     * @return    删除结果
     */
    @GetMapping("/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult del(@PathVariable Integer id) {
        documentService.delById(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

}
