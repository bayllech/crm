package com.kaishengit.service;

import com.kaishengit.pojo.Task;

import java.util.List;

/**
 * Created by bayllech on 2017/5/27.
 */
public interface TaskService {
    List<Task> findAlltimeout();

    List<Task> findByUserId(String start, String end);
}
