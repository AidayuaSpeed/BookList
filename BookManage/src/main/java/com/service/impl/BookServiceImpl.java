package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.mapper.BookMapper;
import com.mapper.BookPeriodicalsMapper;
import com.model.Book;
import com.model.BookPeriodicals;
import com.model.Bookcase;
import com.service.IBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("bookServiceImpl")
public class BookServiceImpl implements IBookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookPeriodicalsMapper bookPeriodicalsMapper;
    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=bookMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<Book> bookList=bookMapper.page(param);
        JSONArray array=new JSONArray();
        if(bookList!=null&&!bookList.isEmpty()){
            bookList.forEach(book -> {
                BookPeriodicals bookPeriodicals = bookPeriodicalsMapper.qryById(book.getBookIsbn());
                if(bookPeriodicals!=null){
                    book.setBookAuthor(bookPeriodicals.getBookAuthor());
                    book.setPublisher(bookPeriodicals.getPublisher());
                    book.setPublishingTime(bookPeriodicals.getPublishingTime());
                    book.setBookPrice(bookPeriodicals.getBookPrice());
                    book.setTypeId(bookPeriodicals.getTypeId());
                }
                array.add(book.encode());
            });
        }
        Page page = new Page();
        page.setSuccess(true);
        page.setTotalNum(totalNum);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(array);
        return page;
    }

    public Result<Book> qryByBookIsbn(JSONObject json){
        List<Book> bookList = bookMapper.qryByBookIsbn(json.getString("BOOK_ISBN"));
        return new Result<>(bookList);
    }

    public List<Book> listAll(){
        return bookMapper.listAll();
    }

    @Override
    public List<Book> selectposition(String bookcaseId) {
        return bookMapper.selectposition(bookcaseId);
    }

}
