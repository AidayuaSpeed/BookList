package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.BookType;
import com.service.IBookTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookTypeController {

    @Resource(name="bookTypeServiceImpl")
    private IBookTypeService bookTypeService;

    /**
     * 查询图书类型列表
     * */
    @RequestMapping(value = "/booktype/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String typeId=request.getString("TYPE_ID");
        if(StringUtils.isNotEmpty(typeId)){
            param.put("TYPE_ID",typeId);
        }
        String typeName=request.getString("TYPE_NAME");
        if(StringUtils.isNotEmpty(typeName)){
            param.put("TYPE_NAME",typeName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return bookTypeService.qryPage(param,pageNo,pageSize).toJSONString();
    }
    /**
     * 添加新的图书类型
     * */
    @RequestMapping(value = "/booktype/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookType> save(HttpRequest request) {
        return bookTypeService.insert(request.getJson());
    }

    /**
     * 修改图书类型
     * */
    @RequestMapping(value = "/booktype/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookType> update(HttpRequest request) {
        return bookTypeService.update(request.getJson());
    }
    /**
     * 批量删除图书类型 根据图书类型编码
     * */
    @RequestMapping(value = "/booktype/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookType> del(HttpRequest request) {
        JSONArray typeIdList=request.getJSONArray("TYPE_ID_LIST");
        List<String> list= new ArrayList<>();
        for(int i=0;i<typeIdList.size();i++){
            list.add(typeIdList.getString(i));
        }
        bookTypeService.del(list);
        return new Result<>();
    }
    /**查询所有图书类型*/
    @RequestMapping(value = "/booktype/listAll",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BookType> listAll() {
        return new Result<>(bookTypeService.listAll());
    }

}
