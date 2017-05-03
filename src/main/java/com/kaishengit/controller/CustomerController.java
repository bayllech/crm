package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.exception.ForbiddenException;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bayllech on 2017/5/1.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String list(Model model) {
//        model.addAttribute("componyList", customerService.findAllCompany());
        return "customer/list";
    }

    //显示所有客户和公司信息
    @GetMapping("/load")
    @ResponseBody
    public DataTablesResult<Customer> loadCustomer(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUTF8(keyword);
        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("start", start);
        map.put("length", length);
        map.put("keyword", keyword);
        Long count = customerService.count();
        Long filterCount = customerService.filterCount(map);
        List<Customer> customerList = customerService.findByParam(start,length,keyword);
        return new DataTablesResult<>(draw,customerList,count,filterCount);
    }

    //新增客户
    @PostMapping("/new")
    @ResponseBody
    public String newCustomer(Customer customer) {
        customerService.save(customer);
        return "success";
    }

    //编辑客户
    @PostMapping("/edit")
    @ResponseBody
    public  String edit(Customer customer) {
        customerService.edit(customer);
        return "success";
    }

    //删除客户
    @GetMapping("/del/{id:\\d+}")
    @ResponseBody
    public String del(@PathVariable Integer id) {
        customerService.del(id);
        return "success";
    }

    //显示所有公司
    @GetMapping("/company.json")
    @ResponseBody
    public List<Customer> showAllCompany() {
        return customerService.findAllCompany();
    }

    //编辑时获取相应客户和公司信息
    @GetMapping("/edit/{id:\\d+}.json")
    @ResponseBody
    public AjaxResult editShow(@PathVariable Integer id) {
        List<Customer> companyList = customerService.findAllCompany();
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new AjaxResult("error", "找不到相应的客户");
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("companyList", companyList);
            result.put("customer", customer);
            return new AjaxResult("success",result);
        }
    }

    //跳转到显示客户或某公司所有客户页面
    @GetMapping("/{id:\\d+}")
    public String view(@PathVariable Integer id,Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManage()) {
            throw new ForbiddenException();
        }
        model.addAttribute("customer", customer);
        if ("company".equals(customer.getType())) {
            List<Customer> customerList = customerService.findAllByCompanyid(id);
            model.addAttribute("customerList", customerList);
        }

        return "customer/view";
    }

    //生成客户信息二维码
    @GetMapping("/qrcode/{id:\\d+}.png")
    public void mkQrCode(@PathVariable Integer id, HttpServletResponse response) throws WriterException, IOException {
        String meCard = customerService.mkMeCard(id);

        Map<EncodeHintType,String> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(meCard, BarcodeFormat.QR_CODE, 200, 200, hints);
        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

        outputStream.flush();
        outputStream.close();

    }
}
