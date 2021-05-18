package com.mapper;

import com.model.ReaderType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReaderTypeMapper {
    Integer count(Map<String,Object> param);
    List<ReaderType> page(Map<String,Object> param);
    void insert(ReaderType readerType);
    void update(ReaderType readerType);
    void del(List<Integer> list);
    ReaderType qryByReaderType(Integer readerType);
    List<ReaderType> listAll();
}
