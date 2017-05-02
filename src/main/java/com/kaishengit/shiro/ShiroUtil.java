package com.kaishengit.shiro;

import com.kaishengit.pojo.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }

    public static Integer getCurrentUserID() {
        return getCurrentUser().getId();
    }

    public static Boolean isManage() {
        return getCurrentUser().getRole().getRolename().equals("经理");
    }

    public static Boolean isAdmin() {
        return getCurrentUser().getRole().getRolename().equals("管理员");
    }
}
