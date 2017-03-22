package com.kaishengit.controller;

import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import com.kaishengit.service.UserService;
import com.kaishengit.shiro.ShiroUtil;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private UserService userService;
    @Value("${saltValue}")
    private String saltValue;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    /**
     * 登录页面
     */
    @PostMapping("/")
    public String login(String username, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //shiro方式登录
        Subject subject = SecurityUtils.getSubject();
        try {
            //密码加盐
            password = DigestUtils.md5Hex(saltValue + password);
            subject.login(new UsernamePasswordToken(username, password));
            //保存登录日志
            String ip = request.getRemoteAddr();
            userService.saveIp(ip, ShiroUtil.getCurrentUser());
            return "redirect:/home";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message", "账号或密码错误");
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        //安全退出
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","你已安全退出");
        return "redirect:/";
    }

    /**
     * 查看登录日志
     */
    @GetMapping("/loginLog")
    public String loginLog(Model model,
                           @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                           HttpServletRequest request) {
//        List<UserLog> userLogs = ShiroUtil.getCurrentUser().getUserLogList();
        Page<UserLog> userLogs = userService.findByPage(pageNo);

//        List<UserLog> userLogs = userService.findLog(ShiroUtil.getCurrentUser());
        model.addAttribute("page", userLogs);
        return "user/log";
    }

    @GetMapping("/newPassword")
    public String password() {
        return "user/setpassword";
    }

    /**
     * 修改密码
     * @param newpassword
     * @param oldpassword
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/newPassword")
    public String password(String newpassword,String oldpassword,RedirectAttributes redirectAttributes) {
        oldpassword = DigestUtils.md5Hex(saltValue + oldpassword);
        User user = ShiroUtil.getCurrentUser();
        if (user.getPassword().equals(oldpassword)) {
            user.setPassword(DigestUtils.md5Hex(saltValue + newpassword));
            userService.update(user);

            SecurityUtils.getSubject().logout();
            redirectAttributes.addFlashAttribute("message", "密码修改成功，请重新登录");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("message", "原始密码错误,请重新输入");
            return "redirect:/newPassword";
        }

    }

    //首页
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/403")
    public String role() {
        return "403";
    }


}

