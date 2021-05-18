package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.mapper.BookMapper;
import com.mapper.BookPeriodicalsMapper;
import com.model.Book;
import com.model.BookPeriodicals;
import com.service.IBookPeriodicalsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookPeriodicalsServiceImpl")
public class BookPeriodicalsServiceImpl implements IBookPeriodicalsService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookPeriodicalsMapper bookPeriodicalsMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=bookPeriodicalsMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<BookPeriodicals> bookPeriodicalsList=bookPeriodicalsMapper.page(param);
        JSONArray array=new JSONArray();
        if(bookPeriodicalsList!=null&&!bookPeriodicalsList.isEmpty()){
            bookPeriodicalsList.forEach(bookPeriodicals -> array.add(bookPeriodicals.encode()));
        }
        Page page = new Page();
        page.setSuccess(true);
        page.setTotalNum(totalNum);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(array);
        return page;
    }

    @Override
    public Result<BookPeriodicals> insert(JSONObject json) {
        JSONArray bookList = json.getJSONArray("BOOK_LIST");
        String bookIsbn = json.getString("BOOK_ISBN");
        for(int i=0;i<bookList.size();i++){
            JSONObject bookJson = bookList.getJSONObject(i);
            Book book = new Book();
            book.setBookIsbn(bookIsbn);
            book.setBookId(bookJson.getString("BOOK_ID"));
            book.setBookName(json.getString("BOOK_NAME"));
            book.setBookcaseId(bookJson.getString("BOOKCASE_ID"));
            book.setBookState(1);
            book.setCreateTime(new Date());
            bookMapper.insert(book);
        }
        BookPeriodicals bookPeriodicals = new BookPeriodicals();
        bookPeriodicals.setBookIsbn(bookIsbn);
        bookPeriodicals.setBookName(json.getString("BOOK_NAME"));
        bookPeriodicals.setBookAuthor(json.getString("BOOK_AUTHOR"));
        bookPeriodicals.setPublisher(json.getString("PUBLISHER"));
        bookPeriodicals.setPublishingTime(json.getString("PUBLISHING_TIME"));
        bookPeriodicals.setBookImage(json.getString("BOOK_IMAGE"));
        bookPeriodicals.setBookPrice(json.getString("BOOK_PRICE"));
        bookPeriodicals.setTypeId(json.getString("TYPE_ID"));
        bookPeriodicals.setCreateTime(new Date());
        bookPeriodicals.setBookNum(bookList.size());
        bookPeriodicals.setBorrowNum(0);
        bookPeriodicalsMapper.insert(bookPeriodicals);
        return new Result<>();
    }

    @Override
    public Result<BookPeriodicals> update(JSONObject json) {
        String bookIsbn=json.getString("BOOK_ISBN");
        // 查询所有复本
        List<Book> bookList = bookMapper.qryByBookIsbn(bookIsbn);
        // 已经存在的图书复本
        Map<String,Book> bookMap = new HashMap<>();
        // 新添加的复本
        Map<String,Integer> bookMapNew = new HashMap<>();
        if(bookList!=null&&!bookList.isEmpty()){
            bookList.forEach(book -> {
                bookMap.put(book.getBookId(),book);
            });
        }
        JSONArray books = json.getJSONArray("BOOK_LIST");
        for(int i=0;i< books.size();i++){
            JSONObject bookJson = books.getJSONObject(i);
            String bookId = bookJson.getString("BOOK_ID");
            if(bookMap.containsKey(bookId)){// 更新复本信息
                Book book = new Book();
                book.setBookIsbn(bookIsbn);
                book.setBookName(json.getString("BOOK_NAME"));
                book.setBookId(bookId);
                book.setBookcaseId(bookJson.getString("BOOKCASE_ID"));
                bookMapper.update(book);
            }else{
                Book book = new Book();
                book.setBookIsbn(bookIsbn);
                book.setBookId(bookId);
                book.setBookName(json.getString("BOOK_NAME"));
                book.setBookcaseId(bookJson.getString("BOOKCASE_ID"));
                book.setBookState(1);
                book.setCreateTime(new Date());
                bookMapper.insert(book);
            }
            bookMapNew.put(bookId,1);
        }
        if(bookList!=null&&!bookList.isEmpty()){
            bookList.forEach(book -> {
                if(!bookMapNew.containsKey(book.getBookId())){
                    bookMapper.del(bookIsbn,book.getBookId());
                }
            });
        }

        BookPeriodicals bookPeriodicals = bookPeriodicalsMapper.qryById(bookIsbn);
        if(bookPeriodicals!=null){
            bookPeriodicals.setBookIsbn(json.getString("BOOK_ISBN"));
            bookPeriodicals.setBookName(json.getString("BOOK_NAME"));
            bookPeriodicals.setBookAuthor(json.getString("BOOK_AUTHOR"));
            bookPeriodicals.setPublisher(json.getString("PUBLISHER"));
            bookPeriodicals.setPublishingTime(json.getString("PUBLISHING_TIME"));
            bookPeriodicals.setBookImage(json.getString("BOOK_IMAGE"));
            bookPeriodicals.setBookPrice(json.getString("BOOK_PRICE"));
            bookPeriodicals.setTypeId(json.getString("TYPE_ID"));
            bookPeriodicals.setCreateTime(new Date());
            bookPeriodicals.setBookNum(bookMapNew.size());
            bookPeriodicalsMapper.update(bookPeriodicals);
        }
        return new Result<>();
    }

    @Override
    public Result<BookPeriodicals> del(String bookIsbn) {
        bookPeriodicalsMapper.del(bookIsbn);
        bookMapper.delByBookIsbn(bookIsbn);
        return new Result<>();
    }


}
