package com.controller;

import com.common.Result;
import com.common.http.HttpRequest;
import com.common.session.SessionContext;
import com.model.Menu;
import com.service.IMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MenuController {
    
    @Resource(name = "menuServiceImpl")
    private IMenuService menuService;
    
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public Result<Menu> menu() {
        String userId=SessionContext.get("USER_ID");
        return menuService.qryByUserId(userId);
    }

    @RequestMapping(value = "/menu/qryByUserId", method = RequestMethod.POST)
    public Result<Menu> qryByUserId(HttpRequest request) {
        return menuService.qryByUserId(request.getString("USER_ID"));
    }
}
