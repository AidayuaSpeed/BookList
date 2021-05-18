package com.controller;

import com.common.http.HttpRequest;
import com.common.util.ISBNUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ISBNController {
    /**
     * 使用isbn 调用网路接口查询图书信息
     * */
    @RequestMapping(value = "/isbn/info",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String info(HttpRequest request) {
        return ISBNUtil.search(request.getString("BOOK_ISBN")).toJSONString();
    }
}
