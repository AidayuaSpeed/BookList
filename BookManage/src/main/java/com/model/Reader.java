package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Model;
import com.common.util.DateUtils;

import java.util.Date;

/**
 * 与读者表<READER>对应
 * */
public class Reader implements Model {
    private static final long serialVersionUID = -4331387240858967689L;

    /**读者标识(READER_ID)*/	
    private String readerId;
    /**读者昵称(READER_NAME)*/
    private String readerName;
    /**读者性别(READER_SEX)
     * 1 男 2女
     */
    private int readerSex;
    /**读者邮箱(READER_EMAIL)*/	
    private String readerEmail;
    /**手机号码(READER_PHONE)*/
    private String readerPhone;
    /**创建时间(CREATE_TIME)*/
    private Date createTime;
    /**读者类型(READER_TYPE)*/
    private int readerType;
    /**读者类型(READER_TYPE_NAME)*/
    private String readerTypeName;
    /**操作员标识(OPERATOR_ID)*/
    private String operatorId;

    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(20);
        json.put("READER_ID",readerId);
        json.put("READER_NAME",readerName);
        json.put("READER_SEX",readerSex);
        json.put("READER_EMAIL",readerEmail);
        json.put("READER_PHONE",readerPhone);
        json.put("CREATE_TIME", DateUtils.parseDateToStr(createTime, DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        json.put("READER_TYPE",readerType);
        json.put("READER_TYPE_NAME",readerTypeName);
        json.put("OPERATOR_ID",operatorId);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public int getReaderSex() {
        return readerSex;
    }

    public void setReaderSex(int readerSex) {
        this.readerSex = readerSex;
    }

    public String getReaderEmail() {
        return readerEmail;
    }

    public void setReaderEmail(String readerEmail) {
        this.readerEmail = readerEmail;
    }

    public String getReaderPhone() {
        return readerPhone;
    }

    public void setReaderPhone(String readerPhone) {
        this.readerPhone = readerPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getReaderType() {
        return readerType;
    }

    public void setReaderType(int readerType) {
        this.readerType = readerType;
    }

    public String getReaderTypeName() {
        return readerTypeName;
    }

    public void setReaderTypeName(String readerTypeName) {
        this.readerTypeName = readerTypeName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
