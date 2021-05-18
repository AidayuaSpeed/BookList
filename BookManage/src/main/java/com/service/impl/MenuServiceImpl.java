package com.service.impl;

import com.common.Result;
import com.mapper.MenuMapper;
import com.model.Menu;
import com.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("menuServiceImpl")
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Result<Menu> qryByUserId(String userId) {
        return new Result<>(menuMapper.qryByUserId(userId));
    }
}
