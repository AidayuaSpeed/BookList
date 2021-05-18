package com.mapper;

import com.model.Book;
import com.model.Bookcase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookMapper {
    Integer count(Map<String,Object> param);
    List<Book> page(Map<String,Object> param);
    void insert(Book book);
    void update(Book book);
    void del(@Param("BOOK_ISBN") String bookIsbn,@Param("BOOK_ID") String bookId);
    void delByBookIsbn(String bookIsbn);
    Book qryById(@Param("BOOK_ISBN") String bookIsbn,@Param("BOOK_ID") String bookId);
    List<Book> qryByBookIsbn(String bookIsbn);
    List<Book> listAll();
    Integer qryByBookcase(List<String> list);
    List<Book> selectposition(String bookcaseId);
}
