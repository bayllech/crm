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

    public static Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * 判断当前登录对象是否为开发部员工
     * @return
     */
   /* public static boolean isSales() {
        return SecurityUtils.getSubject().hasRole("role_dev");
    }*/

}
