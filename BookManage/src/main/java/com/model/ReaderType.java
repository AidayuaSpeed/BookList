package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.Model;

public class ReaderType implements Model {

    /**读者类型(READER_TYPE)*/
    private int readerType;
    /**读者类型名称(READER_TYPE_NAME)*/
    private String readerTypeName;
    /**可借书数(BOOK_NUM)*/
    private int bookNum;
    /**可借天数(BORROW_DAYS)*/
    private int borrowDays;
    /**每次可续借天数(RELET_DAYS)*/
    private int reletDays;

    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(20);
        json.put("READER_TYPE",readerType);
        json.put("READER_TYPE_NAME",readerTypeName);
        json.put("BOOK_NUM",bookNum);
        json.put("BORROW_DAYS",borrowDays);
        json.put("RELET_DAYS",reletDays);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

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

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public int getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(int borrowDays) {
        this.borrowDays = borrowDays;
    }

    public int getReletDays() {
        return reletDays;
    }

    public void setReletDays(int reletDays) {
        this.reletDays = reletDays;
    }
}
