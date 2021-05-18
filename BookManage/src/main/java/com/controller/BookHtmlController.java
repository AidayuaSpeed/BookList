package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * JAVA图书借阅管理系统 界面
 * */
@Controller
public class BookHtmlController {
    
    @RequestMapping(value="/")
    public String bookHtml() throws Exception {
        return "book.html";
    }
    
}
