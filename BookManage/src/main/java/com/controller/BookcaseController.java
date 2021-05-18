package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.Bookcase;
import com.service.IBookcaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookcaseController {

    @Resource(name = "bookcaseServiceImpl")
    private IBookcaseService bookcaseService;
    /**
     * 查询书架列表
     * */
    @RequestMapping(value = "/bookcase/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String bookcaseId=request.getString("BOOKCASE_ID");
        if(StringUtils.isNotEmpty(bookcaseId)){
            param.put("BOOKCASE_ID",bookcaseId);
        }
        String bookcaseName=request.getString("BOOKCASE_NAME");
        if(StringUtils.isNotEmpty(bookcaseName)){
            param.put("BOOKCASE_NAME",bookcaseName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return bookcaseService.qryPage(param,pageNo,pageSize).toJSONString();
    }
    /**
     * 添加新的书架
     * */
    @RequestMapping(value = "/bookcase/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Bookcase> save(HttpRequest request) {
        return bookcaseService.insert(request.getJson());
    }

    /**
     * 修改书架
     * */
    @RequestMapping(value = "/bookcase/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Bookcase> update(HttpRequest request) {
        return bookcaseService.update(request.getJson());
    }
    /**
     * 批量删除书架 根据书架编码
     * */
    @RequestMapping(value = "/bookcase/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Bookcase> del(HttpRequest request) {
        JSONArray bookcaseIdList=request.getJSONArray("BOOKCASE_ID_LIST");
        List<String> list= new ArrayList<>();
        for(int i=0;i<bookcaseIdList.size();i++){
            list.add(bookcaseIdList.getString(i));
        }
        bookcaseService.del(list);
        return new Result<>();
    }
    /**查询所有书架*/
    @RequestMapping(value = "/bookcase/listAll",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Bookcase> listAll() {
        List<Bookcase> bookcaseList=bookcaseService.listAll();
        return new Result<>(bookcaseList);
    }





}
