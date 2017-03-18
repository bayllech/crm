package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jiahao0 on 2017/3/18.
 */
@Controller
@RequestMapping
public class UserController {

    @PostMapping
    public String login() {
        return "home";
    }

}
