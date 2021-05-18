package com.mapper;

import com.model.Reader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReaderMapper {
    Integer count(Map<String,Object> param);
    List<Reader> page(Map<String,Object> param);
    void insert(Reader reader);
    void update(Reader reader);
    void del(List<String> list);
    Reader qryById(String readerId);
    List<Reader> listAll();
    Integer qryByReaderType(List<Integer> list);
}
