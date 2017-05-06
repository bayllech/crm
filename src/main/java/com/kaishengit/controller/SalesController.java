package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dao.SalesLogDao;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.exception.ForbiddenException;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.SalesLog;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.SalesService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/log/new",method = RequestMethod.POST)
    public String newLog(Integer salesid,String context) {
        salesService.saveLog(salesid,context);
        return "redirect:/sales/"+salesid;
    }


}
