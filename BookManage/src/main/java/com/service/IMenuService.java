package com.service;

import com.common.Result;
import com.model.Menu;

public interface IMenuService {
    Result<Menu> qryByUserId(String userId);
}
