package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.Model;

import java.util.Date;

/**
 * 与图书书架表<BOOKCASE>对应
 * */
public class Bookcase implements Model {

    private static final long serialVersionUID = 4537798494446099986L;

    /**书架编码(BOOKCASE_ID)*/
    private String bookcaseId;
    /**书架名称(BOOKCASE_NAME)*/
    private String bookcaseName;
    /**书架介绍(BOOKCASE_DES)*/
    private String bookcaseDes;
    /**修改时间(MODIFY_TIME)*/
    private Date modifyTime;

    /**书架书籍数量(MODIFY_TIME)*/
    private int bookcaseNum;



    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(10);
        json.put("BOOKCASE_ID",bookcaseId);
        json.put("BOOKCASE_NAME",bookcaseName);
        json.put("BOOKCASE_DES",bookcaseDes);
        json.put("BOOKCASE_NUM",bookcaseNum);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public String getBookcaseId() {
        return bookcaseId;
    }

    public void setBookcaseId(String bookcaseId) {
        this.bookcaseId = bookcaseId;
    }

    public String getBookcaseName() {
        return bookcaseName;
    }

    public void setBookcaseName(String bookcaseName) {
        this.bookcaseName = bookcaseName;
    }

    public String getBookcaseDes() {
        return bookcaseDes;
    }

    public void setBookcaseDes(String bookcaseDes) {
        this.bookcaseDes = bookcaseDes;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getBookcaseNum() { return bookcaseNum; }

    public void setBookcaseNum(int bookcaseNum) {this.bookcaseNum = bookcaseNum; }
}
