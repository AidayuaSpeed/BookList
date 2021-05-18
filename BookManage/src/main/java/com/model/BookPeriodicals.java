package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Model;
import com.common.util.DateUtils;

import java.util.Date;

/**
 * 与书刊表<BOOK_PERIODICALS>对应
 * */
public class BookPeriodicals implements Model {

    /**图书ISBN(BOOK_ISBN)*/
    private String bookIsbn;
    /**图书名称(BOOK_NAME)*/
    private String bookName;
    /**图书作者(BOOK_AUTHOR)*/
    private String bookAuthor;
    /**出版社(PUBLISHER)*/
    private String publisher;
    /**出版时间(PUBLISHING_TIME)*/
    private String publishingTime;
    /**图书图片(BOOK_IMAGE)*/
    private String bookImage;
    /**图书价格(BOOK_PRICE)*/
    private String bookPrice;
    /**图书类型(TYPE_ID)*/
    private String typeId;
    /**创建时间(CREATE_TIME)*/
    private Date createTime;
    /**总册数(BOOK_NUM)*/
    private int bookNum;
    /**借出书(BORROW_NUM)*/
    private int borrowNum;
    /**书籍简介(Book_INTRO)*/
    private String bookIntro;

    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(30);
        json.put("BOOK_ISBN",bookIsbn);
        json.put("BOOK_NAME",bookName);
        json.put("BOOK_AUTHOR",bookAuthor);
        json.put("PUBLISHER",publisher);
        json.put("PUBLISHING_TIME",publishingTime);
        json.put("BOOK_IMAGE",bookImage);
        json.put("BOOK_PRICE",bookPrice);
        json.put("TYPE_ID",typeId);
        json.put("CREATE_TIME",DateUtils.parseDateToStr(createTime, DateConst.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        json.put("BOOK_NUM",bookNum);
        json.put("BORROW_NUM",borrowNum);
        json.put("BOOK_INTRO",bookIntro);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishingTime() {
        return publishingTime;
    }

    public void setPublishingTime(String publishingTime) {
        this.publishingTime = publishingTime;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public int getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(int borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }


}
