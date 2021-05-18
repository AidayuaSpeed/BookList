package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.common.exception.HttpException;
import com.common.util.StringUtils;
import com.mapper.BookPeriodicalsMapper;
import com.mapper.BookTypeMapper;
import com.model.BookType;
import com.service.IBookTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("bookTypeServiceImpl")
public class BookTypeServiceImpl implements IBookTypeService {
    @Resource
    private BookTypeMapper bookTypeMapper;
    @Resource
    private BookPeriodicalsMapper bookPeriodicalsMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=bookTypeMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<BookType> bookTypeList=bookTypeMapper.page(param);
        JSONArray array=new JSONArray();
        if(bookTypeList!=null&&!bookTypeList.isEmpty()){
            bookTypeList.forEach(bookType -> array.add(bookType.encode()));
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
    public Result<BookType> insert(JSONObject json) {
        String typeId= json.getString("TYPE_ID");
        BookType b = bookTypeMapper.qryById(typeId);
        if(b!=null){
            return new Result<>("9999","图书类型已存在，请修改");
        }
        BookType bookType = new BookType();
        bookType.setTypeId(typeId);
        bookType.setTypeName(json.getString("TYPE_NAME"));
        bookType.setTypeDes(json.getString("TYPE_DES"));
        bookType.setModifyTime(new Date());
        bookTypeMapper.insert(bookType);
        return new Result<>();
    }

    @Override
    public Result<BookType> update(JSONObject json) {
        String typeId=json.getString("TYPE_ID");
        if(StringUtils.isNotEmpty(typeId)){
            BookType bookType = bookTypeMapper.qryById(typeId);
            if(bookType!=null){
                bookType.setTypeName(json.getString("TYPE_NAME"));
                bookType.setTypeDes(json.getString("TYPE_DES"));
                bookType.setModifyTime(new Date());
                bookTypeMapper.update(bookType);
            }
        }
        return new Result<>();
    }

    @Override
    public Result<BookType> del(List<String> list) {
        Integer count = bookPeriodicalsMapper.qryByBookType(list);
        if(count==0){
            bookTypeMapper.del(list);
        }else{
            throw new HttpException("9999","书刊类型正在使用，不能删除");
        }
        return new Result<>();
    }

    @Override
    public List<BookType> listAll() {
        return bookTypeMapper.listAll();
    }
}
