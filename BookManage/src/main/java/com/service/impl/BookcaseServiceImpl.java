package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.common.exception.HttpException;
import com.common.util.StringUtils;
import com.mapper.BookMapper;
import com.mapper.BookcaseMapper;
import com.model.Bookcase;
import com.service.IBookcaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("bookcaseServiceImpl")
public class BookcaseServiceImpl implements IBookcaseService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookcaseMapper bookcaseMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=bookcaseMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<Bookcase> bookcaseList=bookcaseMapper.page(param);
        JSONArray array=new JSONArray();
        if(bookcaseList!=null&&!bookcaseList.isEmpty()){
            bookcaseList.forEach(bookcase -> array.add(bookcase.encode()));
        }
        Page page = new Page();
        page.setSuccess(true);
        page.setTotalNum(totalNum);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(array);
        return page;
    }

    @Override
    public Result<Bookcase> insert(JSONObject json) {
        String bookcaseId= json.getString("BOOKCASE_ID");
        Bookcase b = bookcaseMapper.qryById(bookcaseId);
        if(b!=null){
            return new Result<>("9999","书架编号已存在，请修改");
        }
        Bookcase bookcase = new Bookcase();
        bookcase.setBookcaseId(bookcaseId);
        bookcase.setBookcaseName(json.getString("BOOKCASE_NAME"));
        bookcase.setBookcaseDes(json.getString("BOOKCASE_DES"));
        bookcase.setModifyTime(new Date());
        bookcase.setBookcaseNum(json.getIntValue("BOOKCASE_NUM"));
        bookcaseMapper.insert(bookcase);
        return new Result<>();
    }

    @Override
    public Result<Bookcase> update(JSONObject json) {
        String bookcaseId=json.getString("BOOKCASE_ID");
        if(StringUtils.isNotEmpty(bookcaseId)){
            Bookcase bookcase = bookcaseMapper.qryById(bookcaseId);
            if(bookcase!=null){
                bookcase.setBookcaseName(json.getString("BOOKCASE_NAME"));
                bookcase.setBookcaseDes(json.getString("BOOKCASE_DES"));
                bookcase.setModifyTime(new Date());
                bookcase.setBookcaseNum(json.getIntValue("BOOKCASE_NUM"));
                bookcaseMapper.update(bookcase);
            }
        }
        return new Result<>();
    }

    @Override
    public Result<Bookcase> del(List<String> list) {
        Integer count = bookMapper.qryByBookcase(list);
        if(count==0){
            bookcaseMapper.del(list);
        }else{
            throw new HttpException("9999","书架正在使用，不能删除");
        }
        return new Result<>();
    }

    @Override
    public List<Bookcase> listAll() {
        return bookcaseMapper.listAll();
    }

}