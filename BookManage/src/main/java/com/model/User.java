package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Model;
import com.common.util.DateUtils;

import java.util.Date;
/**
 * 与用户表<USER>对应
 * */
public class User implements Model {
    private static final long serialVersionUID = 401206359614801417L;
    /**用户标识(USER_ID)*/
    private String userId;
    /**用户昵称(USER_NAME)*/
    private String userName;
    /**用户密码(USER_PWD)*/
    private String userPwd;
    /**创建时间(CREATE_TIME)*/
    private Date createTime;
    /**创建时间(USER_ADMIN)*/
    private int userAdmin;
    @Override
    public JSONObject encode() {
        JSONObject json=new JSONObject(10);
        json.put("USER_ID",userId);
        json.put("USER_NAME",userName);
        json.put("CREATE_TIME", DateUtils.parseDateToStr(createTime, DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        json.put("USER_ADMIN",userAdmin);
        return json;
    }

    @Override
    public void decode(JSONObject json) {
        
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(int userAdmin) {
        this.userAdmin = userAdmin;
    }
}
