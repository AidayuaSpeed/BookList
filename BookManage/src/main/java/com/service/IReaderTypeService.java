package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.ReaderType;

import java.util.List;
import java.util.Map;

public interface IReaderTypeService {
    Page qryPage(Map<String,Object> param, int pageNo, int pageSize);
    Result<ReaderType> insert(JSONObject json);
    Result<ReaderType> update(JSONObject json);
    Result<ReaderType> del(List<Integer> list);
    List<ReaderType> listAll();
}
