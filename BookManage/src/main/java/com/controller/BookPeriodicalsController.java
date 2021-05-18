package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.BookPeriodicals;
import com.service.IBookPeriodicalsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookPeriodicalsController {

    @Resource(name = "bookPeriodicalsServiceImpl")
    private IBookPeriodicalsService bookPeriodicalsService;
    /**
     * 查询所有书刊列表
     * */
    @RequestMapping(value = "/bookperiodicals/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String bookIsbn=request.getString("BOOK_ISBN");
        if(StringUtils.isNotEmpty(bookIsbn)){
            param.put("BOOK_ISBN",bookIsbn);
        }
        String bookName=request.getString("BOOK_NAME");
        if(StringUtils.isNotEmpty(bookName)){
            param.put("BOOK_NAME",bookName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return bookPeriodicalsService.qryPage(param,pageNo,pageSize).toJSONString();
    }

    @RequestMapping(value = "/bookperiodicals/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookPeriodicals> save(HttpRequest request) {
        return bookPeriodicalsService.insert(request.getJson());
    }

    @RequestMapping(value = "/bookperiodicals/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookPeriodicals> update(HttpRequest request) {
        return bookPeriodicalsService.update(request.getJson());
    }

    @RequestMapping(value = "/bookperiodicals/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookPeriodicals> del(HttpRequest request) {
        JSONArray bookIsbnList=request.getJSONArray("BOOK_ISBN_LIST");
        for(int i=0;i<bookIsbnList.size();i++){
            bookPeriodicalsService.del(bookIsbnList.getString(i));
        }
        return new Result<>();
    }
}
