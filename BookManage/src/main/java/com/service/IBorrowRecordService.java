package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.BorrowRecord;

import java.util.Map;

public interface IBorrowRecordService {
    Page qryPage(Map<String,Object> param, int pageNo, int pageSize);
    Result<BorrowRecord> back(JSONObject json);
    Result<BorrowRecord> borrow(JSONObject json);
}
