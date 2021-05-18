package com.controller;

import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.Book;
import com.model.Bookcase;
import com.service.IBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Resource(name = "bookServiceImpl")
    private IBookService bookService;
    /**
     * 查询图书列表
     * */
    @RequestMapping(value = "/book/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String bookIsbn=request.getString("BOOK_ISBN");
        if(StringUtils.isNotEmpty(bookIsbn)){
            param.put("BOOK_ISBN",bookIsbn);
        }
        String bookId=request.getString("BOOK_ID");
        if(StringUtils.isNotEmpty(bookId)){
            param.put("BOOK_ID",bookId);
        }
        String bookName=request.getString("BOOK_NAME");
        if(StringUtils.isNotEmpty(bookName)){
            param.put("BOOK_NAME",bookName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return bookService.qryPage(param,pageNo,pageSize).toJSONString();
    }
    /**
     * 根据bookIsbn 查询图书复本列表
     * */
    @RequestMapping(value = "/book/qryByBookIsbn",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Book> qryByBookIsbn(HttpRequest request) {
        return bookService.qryByBookIsbn(request.getJson());
    }
    /**
     * 查询所有图书
     * */
    @RequestMapping(value = "/book/listAll",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Book> listAll() {
        return new Result<>(bookService.listAll());
    }
    /**
     * 查询某个数据的剩余书格数
     * */
    @RequestMapping(value = "/bookcase/selectposition",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Book> selectposition(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String bookcaseId=request.getString("BOOKCASE_ID");
        if(StringUtils.isNotEmpty(bookcaseId)){
            param.put("BOOKCASE_ID",bookcaseId);
        }
         List<Book> bookList=bookService.selectposition(bookcaseId);
         return new Result<>(bookList);



    }

}
