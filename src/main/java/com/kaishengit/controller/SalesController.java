package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.exception.ForbiddenException;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.SalesFile;
import com.kaishengit.pojo.SalesLog;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.SalesService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/5.
 */
@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;
    @Autowired
    private CustomerService customerService;
    @Value("${savePath}")
    private String filePath;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customerList", customerService.findAllCustomer());
        return "sales/list";
    }

    //异步加载所有销售机会及搜索
    @RequestMapping(method = RequestMethod.GET,value = "/load")
    @ResponseBody
    public DataTablesResult<Sales> load(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        String name = request.getParameter("name");
        name = Strings.toUTF8(name);
        String progress = request.getParameter("progress");
        progress = Strings.toUTF8(progress);
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");

        Map<String,Object> params = Maps.newHashMap();
        params.put("start",start);
        params.put("length",length);
        params.put("name",name);
        params.put("progress",progress);
        params.put("startdate",startDate);
        params.put("enddate",endDate);

        List<Sales> salesList = salesService.findByParam(params);
        Long count = salesService.count();
        Long countParam = salesService.countByParam(params);

        return new DataTablesResult<>(draw,salesList,count,countParam);

    }

    //新增销售机会
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String save(Sales sales) {
        salesService.saveSales(sales);
        return "success";
    }

    //显示销售项目详情
    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String view(@PathVariable Integer id,Model model) {
        Sales sales = salesService.findById(id);
        if(sales == null) {
            throw new NotFoundException();
        }
        if(!sales.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManage()) {
            throw new ForbiddenException();
        }
        model.addAttribute("sales",sales);

        //加载项目跟进日志详情
        List<SalesLog> salesLogList = salesService.findSalesLogBySalesId(id);
//        model.addAttribute("salesLogList", salesLogList);
        model.addAttribute(salesLogList);

        //加载资料列表
        List<SalesFile> salesFileList = salesService.findAllFile(id);
        model.addAttribute(salesFileList);
        return "sales/view";
    }

    //修改项目进度
    @RequestMapping(value = "/progress/edit",method = RequestMethod.POST)
    public String editProgress(Integer id,String progress) {
        Sales sales = salesService.findById(id);
        if (sales == null) {
            throw new NotFoundException();
        }
        sales.setProgress(progress);
        sales.setLasttime(DateTime.now().toString("YY-MM-DD"));
        salesService.update(sales);

        return "redirect:/sales/" + id;
    }

    //新增跟进记录
    @RequestMapping(value = "/log/new",method = RequestMethod.POST)
    public String newLog(Integer salesid,String context) {
        salesService.saveLog(salesid,context);
        return "redirect:/sales/"+salesid;
    }

    //文件上传
    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file,Integer salesid) throws IOException {
        salesService.updateFile(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),file.getSize(),salesid);
        return "success";
    }

    //合同下载
    @RequestMapping(value = "/file/{id:\\d+}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable Integer id) throws FileNotFoundException {
        SalesFile salesFile = salesService.findFileById(id);
        if (salesFile == null) {
            throw new NotFoundException();
        }
        File file = new File("F:/tempFile", salesFile.getFilename());
        if (!file.exists()) {
            throw new NotFoundException();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        String fileName = salesFile.getName();
        fileName = Strings.toUTF8(fileName);

        return ResponseEntity
                .ok()
                .contentLength(salesFile.getSize())
                .contentType(MediaType.parseMediaType(salesFile.getContenttype()))
                .header("Content-Disposition","attachment;filename=\""+fileName+"\"")
                .body(new InputStreamResource(fileInputStream));
    }


}
