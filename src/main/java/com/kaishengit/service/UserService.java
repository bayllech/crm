package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.UserLogDao;
import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLogDao userLogDao;

    @Transactional(readOnly = true)
    public User login(String username,String password) {
        return userDao.login(username,password);
    }

    /**
     * 保存登录Ip
     * @param ip
     * @param userid
     */
    public void saveIp(String ip,User user) {
        UserLog userLog = new UserLog();
        userLog.setUser(user);
        userLog.setLoginip(ip);
        userLog.setLogintime(new DateTime().toString("yyyy-MM-dd HH:mm"));
        userLogDao.save(userLog);
    }

    /**
     * 查询登录日志
     * @param id
     * @return
     */
    public List<UserLog> findLog(User user) {
        return userLogDao.findLog(user);
    }

    public void update(User user) {
        userDao.save(user);
    }
}
