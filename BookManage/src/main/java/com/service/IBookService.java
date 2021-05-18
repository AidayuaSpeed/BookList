package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.Book;
import com.model.Bookcase;

import java.util.List;
import java.util.Map;

public interface IBookService {
    Page qryPage(Map<String, Object> param, int pageNo, int pageSize);
    Result<Book> qryByBookIsbn(JSONObject json);
    List<Book> listAll();

    //新增方法，获取某个书架上的剩余书格数
    List<Book> selectposition(String bookcaseId);
}
