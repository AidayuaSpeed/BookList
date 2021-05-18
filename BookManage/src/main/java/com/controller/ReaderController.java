package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.Page;
import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.Reader;
import com.service.IReaderService;
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
public class ReaderController {

    @Resource(name = "readerServiceImpl")
    private IReaderService readerService;
    /**
     * 查询读者列表
     * */
    @RequestMapping(value = "/reader/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String readerId=request.getString("READER_ID");
        if(StringUtils.isNotEmpty(readerId)){
            param.put("READER_ID",readerId);
        }
        String readerName=request.getString("READER_NAME");
        if(StringUtils.isNotEmpty(readerName)){
            param.put("READER_NAME",readerName);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        Page page=readerService.qryPage(param,pageNo,pageSize);
        return page.toJSONString();
    }
    /**
     * 添加新的读者
     * */
    @RequestMapping(value = "/reader/save",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Reader> save(HttpRequest request) {
        return readerService.insert(request.getJson());
    }
    /**
     * 更新读者信息
     * */
    @RequestMapping(value = "/reader/update",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Reader> update(HttpRequest request) {
        return readerService.update(request.getJson());
    }
    /**
     * 批量删除读者 根据读者账号
     * */
    @RequestMapping(value = "/reader/del",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Reader> del(HttpRequest request) {
        JSONArray readerIdList=request.getJSONArray("READER_ID_LIST");
        List<String> list= new ArrayList<>();
        for(int i=0;i<readerIdList.size();i++){
            list.add(readerIdList.getString(i));
        }
        readerService.del(list);
        return new Result<>();
    }

    @RequestMapping(value = "/reader/listAll",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<Reader> listAll() {
        return new Result<>(readerService.listAll());
    }
}
