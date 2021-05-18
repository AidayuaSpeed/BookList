package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.Bookcase;

import java.util.List;
import java.util.Map;

public interface IBookcaseService {
    Page qryPage(Map<String,Object> param, int pageNo, int pageSize);
    Result<Bookcase> insert(JSONObject json);
    Result<Bookcase> update(JSONObject json);
    Result<Bookcase> del(List<String> list);
    List<Bookcase> listAll();


}
