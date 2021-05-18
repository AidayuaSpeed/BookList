package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Result;
import com.common.http.HttpRequest;
import com.common.session.SessionContext;
import com.common.util.StringUtils;
import com.model.User;
import com.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Resource(name="userServiceImpl")
    private IUserService userService;
    /**
     * 查询用户列表
     * */
    @RequestMapping(value = "/user/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String userId=request.getString("USER_ID");
        if(StringUtils.isNotEmpty(userId)){
            param.put("USER_ID",userId);
        }
        String userName=request.getString("USER_NAME");
        if(StringUtils.isNotEmpty(userName)){
            param.put("USER_NAME",userName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return userService.qryPage(param,pageNo,pageSize).toJSONString();
    }
    /**
     * 添加用户
     * */
    @RequestMapping(value = "/user/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> save(HttpRequest request) {
        return userService.insert(request.getJson());
    }
    /**
     * 更新用户
     * */
    @RequestMapping(value = "/user/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> update(HttpRequest request) {
        return userService.update(request.getJson());
    }
    /**
     * 超级管理员更新用户密码
     * */
    @RequestMapping(value = "/user/pwdUpdate",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> pwdUpdate(HttpRequest request) {
        return userService.updatePwd(request.getJson());
    }
    /**
     * 批量用户 根据用户账号删除
     * */
    @RequestMapping(value = "/user/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> del(HttpRequest request) {
        JSONArray userIdList=request.getJSONArray("USER_ID_LIST");
        List<String> list= new ArrayList<>();
        for(int i=0;i<userIdList.size();i++){
            list.add(userIdList.getString(i));
        }
        userService.del(list);
        return new Result<>();
    }
    /**
     * 当前用户修改密码
     * */
    @RequestMapping(value = "/user/pwdchange",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> pwdChage(HttpRequest request) {
        String userId= SessionContext.get("USER_ID");
        JSONObject json = request.getJson();
        json.put("USER_ID",userId);
        return userService.updatePwd(json);
    }
    /**
     * 更新用户授权
     * */
    @RequestMapping(value = "/user/auth",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<User> auth(HttpRequest request) {
        return userService.auth(request.getJson());
    }
}
