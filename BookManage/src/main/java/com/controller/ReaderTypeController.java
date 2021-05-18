package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.Page;
import com.common.Result;
import com.common.http.HttpRequest;
import com.model.ReaderType;
import com.service.IReaderTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读者类型管理
 * */
@RestController
public class ReaderTypeController {

    @Resource(name="readerTypeServiceImpl")
    private IReaderTypeService readerTypeService;
    /**
     * 查询所有读者类型列表
     * */
    @RequestMapping(value = "/readertype/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        Page page=readerTypeService.qryPage(param,pageNo,pageSize);
        return page.toJSONString();
    }
    /**
     * 添加新的读者类型
     * */
    @RequestMapping(value = "/readertype/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<ReaderType> save(HttpRequest request) {
        return readerTypeService.insert(request.getJson());
    }
    /**
     * 更新读者类型信息
     * */
    @RequestMapping(value = "/readertype/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<ReaderType> update(HttpRequest request)  {
        return readerTypeService.update(request.getJson());
    }
    /**
     * 批量删除读者类型 根据读者类型
     * */
    @RequestMapping(value = "/readertype/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<ReaderType> del(HttpRequest request) {
        JSONArray readerTypeList=request.getJSONArray("READER_TYPE_LIST");
        List<Integer> list= new ArrayList<>();
        for(int i=0;i<readerTypeList.size();i++){
            list.add(readerTypeList.getInteger(i));
        }
        readerTypeService.del(list);
        return new Result<>();
    }

    @RequestMapping(value = "/readertype/listAll",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<ReaderType> listAll() {
        return new Result<>(readerTypeService.listAll());
    }
}
