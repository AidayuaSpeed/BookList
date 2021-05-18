package com.mapper;

import com.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    Integer count(Map<String,Object> param);
    List<User> page(Map<String,Object> param);
    void insert(User user);
    void update(User user);
    void del(List<String> list);
    User qryById(String userId);
}
