package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.BookType;

import java.util.List;
import java.util.Map;

public interface IBookTypeService {
    Page qryPage(Map<String,Object> param, int pageNo, int pageSize);
    Result<BookType> insert(JSONObject json);
    Result<BookType> update(JSONObject json);
    Result<BookType> del(List<String> list);
    List<BookType> listAll();
}
