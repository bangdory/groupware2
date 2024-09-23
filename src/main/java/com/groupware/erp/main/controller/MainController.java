package com.groupware.erp.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인Page
    @GetMapping(value = "/")
    public String index() {
        return "main/index";
    }
}
