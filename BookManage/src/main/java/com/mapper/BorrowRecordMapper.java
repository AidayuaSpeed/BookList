package com.mapper;

import com.model.BorrowRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BorrowRecordMapper {
    Integer count(Map<String,Object> param);
    List<BorrowRecord> page(Map<String,Object> param);
    void insert(BorrowRecord borrowRecord);
    void update(BorrowRecord borrowRecord);
    BorrowRecord qryById(int recordId);
    Integer qryBorrowNum(@Param("READER_ID") String readerId,@Param("STATE") int state);
}
