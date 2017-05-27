package com.kaishengit.service.impl;

import com.kaishengit.mapper.TaskMapper;
import com.kaishengit.pojo.Task;
import com.kaishengit.service.TaskService;
import com.kaishengit.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bayllech on 2017/5/27.
 */
@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> findAlltimeout() {
        String today = DateTime.now().toString("YY-MM-dd");
        return taskMapper.findTimeoutTasks(ShiroUtil.getCurrentUserID(),today);
    }

    @Override
    public List<Task> findByUserId(String start, String end) {

        return taskMapper.findByUserID(ShiroUtil.getCurrentUserID(),start,end);
    }
}
