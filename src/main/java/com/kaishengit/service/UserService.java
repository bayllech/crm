package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.UserLogDao;
import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import com.kaishengit.util.Page;
import com.kaishengit.util.QueryParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLogDao userLogDao;
    @Value("${saltValue}")
    private String saltValue;

    @Transactional(readOnly = true)
    public User login(String username,String password) {
        return userDao.login(username,password);
    }

    /**
     * 保存登录Ip
     * @param ip
     * @param
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
     * @param
     * @return
     */
    public List<UserLog> findLog(User user) {
        return userLogDao.findLog(user);
    }

    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 查找所有用户
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    //统计总用户数量
    public Long count() {
        return userDao.count();
    }

    public List<User> findByParam(String start,String length) {
        return userDao.findByParam(start,length);
    }

    public Page<UserLog> findByPage(Integer pageNo) {
        return userLogDao.findByPage(pageNo,10,"logintime","desc");
    }

    public Page<User> findByPage(Integer pageNo,List<QueryParam> queryParamList) {
        return userDao.findByPage(pageNo,5,queryParamList);
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public void resetUserPassword(User user) {
        user.setPassword(DigestUtils.md5Hex(saltValue + user.getPassword()));
        //todo 微信注册账号
        userDao.save(user);
    }

    public void resetPassword(Integer id) {
        User user = userDao.findById(id);
        user.setPassword(DigestUtils.md5Hex(saltValue + "000000"));
        userDao.save(user);
    }

    public User findById(Integer id) {
        return userDao.findById(id);
    }

    public void editUser(User user) {
        User newUser = userDao.findById(user.getId());
        newUser.setWeixin(user.getWeixin());
        newUser.setEnable(user.getEnable());
        newUser.setRole(user.getRole());
        newUser.setRealname(user.getRealname());

        userDao.save(newUser);

    }
}
