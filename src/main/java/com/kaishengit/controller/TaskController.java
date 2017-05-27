package com.kaishengit.controller;

import com.kaishengit.pojo.Task;
import com.kaishengit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by bayllech on 2017/5/27.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<Task> timeoutTaskList = taskService.findAlltimeout();
        model.addAttribute("timeoutTaskList",timeoutTaskList);
        return "tasks/list";


    }

    @RequestMapping(value = "/load",method = RequestMethod.GET)
    public List<Task> load(String start,String end) {
        return taskService.findByUserId(start,end);
    }
}
