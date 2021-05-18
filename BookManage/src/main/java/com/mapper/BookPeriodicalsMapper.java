package com.mapper;

import com.model.BookPeriodicals;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookPeriodicalsMapper {
    Integer count(Map<String,Object> param);
    List<BookPeriodicals> page(Map<String,Object> param);
    void insert(BookPeriodicals bookPeriodicals);
    void update(BookPeriodicals bookPeriodicals);
    void del(String bookIsbn);
    BookPeriodicals qryById(String bookIsbn);
    Integer qryByBookType(List<String> list);
}
