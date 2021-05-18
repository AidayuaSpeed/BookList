package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.Reader;

import java.util.List;
import java.util.Map;

public interface IReaderService {
    Page qryPage(Map<String,Object> param,int pageNo,int pageSize);
    Result<Reader> insert(JSONObject json);
    Result<Reader> update(JSONObject json);
    Result<Reader> del(List<String> list);
    List<Reader> listAll();
}
