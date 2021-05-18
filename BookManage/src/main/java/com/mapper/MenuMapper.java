package com.mapper;

import com.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    List<Menu> qryByUserId(String userId);
    void insertRef(@Param("USER_ID") String userId, @Param("MENU_ID") String menuId);
    void delRef(@Param("USER_ID") String userId, @Param("MENU_ID") String menuId);
    void delByUserId(String userId);
}
