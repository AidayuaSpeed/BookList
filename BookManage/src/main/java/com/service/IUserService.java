package com.service;

import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    Page qryPage(Map<String,Object> param, int pageNo, int pageSize);
    User qryById(String userId);
    Result<User> insert(JSONObject json);
    Result<User> update(JSONObject json);
    Result<User> del(List<String> list);
    Result<User> updatePwd(JSONObject json);
    Result<User> auth(JSONObject json);
}
