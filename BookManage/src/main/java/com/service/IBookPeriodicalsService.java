package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.BookPeriodicals;

import java.util.Map;

public interface IBookPeriodicalsService {
    Page qryPage(Map<String, Object> param, int pageNo, int pageSize);
    Result<BookPeriodicals> insert(JSONObject json);
    Result<BookPeriodicals> update(JSONObject json);
    Result<BookPeriodicals> del(String bookIsbn);
}
