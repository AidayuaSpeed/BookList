package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Model;
import com.common.util.DateUtils;

import java.util.Date;

/**
 * 与借阅记录表<BORROW_RECORD>对应
 * */
public class BorrowRecord implements Model {

    private static final long serialVersionUID = -5226148687511507195L;
    /**记录标识(RECORD_ID)*/
    private int recordId;
    /**图书编码(BOOK_ISBN)*/
    private String bookIsbn;
    /**图书编码(BOOK_ID)*/
    private String bookId;
    /**图书名称(BOOK_NAME)*/
    private String bookName;
    /**读者标识(READER_ID)*/
    private String readerId;
    /**读者名称(READER_NAME)*/
    private String readerName;
    /**借阅时间(BORROW_TIME)*/
    private Date borrowTime;
    /**应还时间(BACK_TIME)*/
    private Date backTime;
    /**借阅操作员(BORROW_OPERATOR_ID)*/
    private String borrowOperatorId;
    /**借阅操作员昵称(BORROW_OPERATOR_NAME)*/
    private String borrowOperatorName;
    /**
     * 借阅状态(STATE)
     * 1 借出 2 归还
     * */
    private int state;
    /**归还时间(GIVEBACK_TIME)*/
    private Date giveBackTime;
    /**归还操作员(GIVEBACK_OPERATOR_ID)*/
    private String giveBackOperatorId;
    /**归还操作员名称(GIVEBACK_OPERATOR_NAME)*/
    private String giveBackOperatorName;
    /**更新时间(UPDATE_TIME)*/
    private Date updateTime;

    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(15);
        json.put("RECORD_ID",recordId);
        json.put("BOOK_ISBN",bookIsbn);
        json.put("BOOK_ID",bookId);
        json.put("BOOK_NAME",bookName);
        json.put("READER_ID",readerId);
        json.put("READER_NAME",readerName);
        json.put("BORROW_TIME", DateUtils.parseDateToStr(borrowTime, DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        json.put("BACK_TIME",DateUtils.parseDateToStr(backTime,DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        json.put("BORROW_OPERATOR_ID",borrowOperatorId);
        json.put("BORROW_OPERATOR_NAME",borrowOperatorName);
        json.put("STATE",state);
        if(giveBackTime!=null){
            json.put("GIVEBACK_TIME",DateUtils.parseDateToStr(giveBackTime,DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            json.put("GIVEBACK_OPERATOR_NAME",giveBackOperatorName);
        }
        if(giveBackOperatorId!=null)json.put("GIVEBACK_OPERATOR_ID",giveBackOperatorId);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getBorrowOperatorId() {
        return borrowOperatorId;
    }

    public void setBorrowOperatorId(String borrowOperatorId) {
        this.borrowOperatorId = borrowOperatorId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getGiveBackTime() {
        return giveBackTime;
    }

    public void setGiveBackTime(Date giveBackTime) {
        this.giveBackTime = giveBackTime;
    }

    public String getGiveBackOperatorId() {
        return giveBackOperatorId;
    }

    public void setGiveBackOperatorId(String giveBackOperatorId) {
        this.giveBackOperatorId = giveBackOperatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBorrowOperatorName() {
        return borrowOperatorName;
    }

    public void setBorrowOperatorName(String borrowOperatorName) {
        this.borrowOperatorName = borrowOperatorName;
    }

    public String getGiveBackOperatorName() {
        return giveBackOperatorName;
    }

    public void setGiveBackOperatorName(String giveBackOperatorName) {
        this.giveBackOperatorName = giveBackOperatorName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }
}
