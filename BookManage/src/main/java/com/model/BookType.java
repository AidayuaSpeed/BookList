package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.Model;

import java.util.Date;

/**
 * 与图书类型<BOOKTYPE>对应
 * */
public class BookType implements Model {

    private static final long serialVersionUID = -5861891769517614832L;
    /**图书类型(TYPE_ID)*/
    private String typeId;
    /**类型名称(TYPE_NAME)*/
    private String typeName;
    /**类型介绍(TYPE_DES)*/
    private String typeDes;
    /**修改时间(MODIFY_TIME)*/
    private Date modifyTime;

    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(10);
        json.put("TYPE_ID",typeId);
        json.put("TYPE_NAME",typeName);
        json.put("TYPE_DES",typeDes);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
