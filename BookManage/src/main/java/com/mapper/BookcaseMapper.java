package com.mapper;

import com.common.Result;
import com.model.Bookcase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookcaseMapper {
    Integer count(Map<String,Object> param);
    List<Bookcase> page(Map<String,Object> param);
    void insert(Bookcase bookcase);
    void update(Bookcase bookcase);
    void del(List<String> list);
    Bookcase qryById(String bookcaseId);
    List<Bookcase> listAll();

}
