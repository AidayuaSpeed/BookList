package com.mapper;

import com.model.BookType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookTypeMapper {
    Integer count(Map<String,Object> param);
    List<BookType> page(Map<String,Object> param);
    void insert(BookType bookType);
    void update(BookType bookType);
    void del(List<String> list);
    BookType qryById(String typeId);
    List<BookType> listAll();
}
