package com.groupware.erp.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {



    @GetMapping
    public String showReceipt() {
        return "/market/recepit";
    }
}
