package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Model;
import com.common.util.DateUtils;

import java.util.Date;

/**
 * 与图书表<BOOK>对应
 * */
public class Book implements Model {

    private static final long serialVersionUID = 935006634075625895L;
    /**图书ISBN(BOOK_ISBN)*/
    private String bookIsbn;
    /**图书编码(BOOK_ID)*/
    private String bookId;
    /**图书名称(BOOK_NAME)*/
    private String bookName;
    /**书架标识(BOOKCASE_ID)*/
    private String bookcaseId;
    /**
     * 图书状态(BOOK_STATE)
     * 1 可借 2 已借出
     * */
    private int bookState;
    /**创建时间(CREATE_TIME)*/
    private Date createTime;
    /**图书作者(BOOK_AUTHOR)*/
    private String bookAuthor;
    /**出版社(PUBLISHER)*/
    private String publisher;
    /**出版时间(PUBLISHING_TIME)*/
    private String publishingTime;
    /**图书价格(BOOK_PRICE)*/
    private String bookPrice;
    /**图书类型(TYPE_ID)*/
    private String typeId;
    /**图书所在书架位置(BOOK_LOCATION)*/
    private int bookLocation;


    @Override
    public JSONObject encode() {
        JSONObject json = new JSONObject(15);
        json.put("BOOK_ISBN",bookIsbn);
        json.put("BOOK_ID",bookId);
        json.put("BOOK_NAME",bookName);
        json.put("BOOK_AUTHOR",bookAuthor);
        json.put("PUBLISHER",publisher);
        json.put("PUBLISHING_TIME",publishingTime);
        json.put("BOOK_PRICE",bookPrice);
        json.put("TYPE_ID",typeId);
        json.put("BOOKCASE_ID",bookcaseId);
        json.put("BOOK_STATE",bookState);
        json.put("CREATE_TIME", DateUtils.parseDateToStr(createTime, DateConst.DATE_TIME_FORMAT_YY_MM_DD1));
        json.put("BOOK_LOCATION",bookLocation);
        return json;
    }
    @Override
    public void decode(JSONObject json) {

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getBookcaseId() {
        return bookcaseId;
    }
    public void setBookcaseId(String bookcaseId) {
        this.bookcaseId = bookcaseId;
    }
    public int getBookState() {
        return bookState;
    }
    public void setBookState(int bookState) {
        this.bookState = bookState;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }


    public int getBookLocation() { return bookLocation;}

    public void setBookLocation(int bookLocation) {this.bookLocation = bookLocation; }
}
