package com.kaishengit.controller;

import com.google.gson.Gson;
import com.kaishengit.dao.RoleDao;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by bayllech on 2017/3/19.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleDao roleDao;

    @GetMapping("/user")
    public String list(Model model,
                       @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                       HttpServletRequest request) {
//        model.addAttribute("userList", userService.findAll());
        List<QueryParam> queryParamList = QueryParam.builderQueryParamByRequest(request);
        Page<User> userList = userService.findByPage(pageNo,queryParamList);

        model.addAttribute("page", userList);
        model.addAttribute("roleList", roleDao.findAll());
        return "admin/userLists";
    }

    /*@GetMapping("/users/load")
    @ResponseBody
    public DataTablesResult<User> usersLoad(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        *//*Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("start", start);
        queryParam.put("length", length);*//*

        List<User> userList = userService.findByParam(start,length);
        Long count = userService.count();

        return new DataTablesResult<>(draw,userList,count,count);
    }*/

    //验证用户名是否可用
    @GetMapping("/user/checkusername")
    @ResponseBody
    public String checkUserName(String username) {
        User user = userService.findByUserName(username);
        if(user == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 保存新用户
     * @param user
     * @return
     */
    @PostMapping("/users/new")
    @ResponseBody
    public String newUser(User user) {
        userService.resetUserPassword(user);
        return "success";
    }

    /**
     * 重置密码为000000
     */
    @PostMapping("users/resetpassword")
    @ResponseBody
    public String resetPassword(Integer id) {
        userService.resetPassword(id);
        return "success";
    }

    /**
     * 编辑用户
     */
    @PostMapping("/users/edit")
    @ResponseBody
    public String editUser(User user) {
        userService.editUser(user);
        return "success";
    }

    /**
     * 根据用户的ID显示用户信息
     * @return
     */
    @GetMapping("/users/{id:\\d+}.json")
    @ResponseBody
    public AjaxResult showUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return new AjaxResult("找不到"+id+"此用户");
        } else {
            return new AjaxResult("success",user);
        }

    }
}
