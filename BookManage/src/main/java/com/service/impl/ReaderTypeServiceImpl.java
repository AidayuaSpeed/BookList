package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.common.exception.HttpException;
import com.mapper.ReaderMapper;
import com.mapper.ReaderTypeMapper;
import com.model.ReaderType;
import com.service.IReaderTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("readerTypeServiceImpl")
public class ReaderTypeServiceImpl implements IReaderTypeService {

    @Resource
    private ReaderMapper readerMapper;
    @Resource
    private ReaderTypeMapper readerTypeMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=readerTypeMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<ReaderType> readerTypeList=readerTypeMapper.page(param);
        JSONArray array=new JSONArray();
        if(readerTypeList!=null&&!readerTypeList.isEmpty()){
            readerTypeList.forEach(reader -> array.add(reader.encode()));
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
    public Result<ReaderType> insert(JSONObject json) {
        ReaderType readerType = new ReaderType();
        readerType.setReaderTypeName(json.getString("READER_TYPE_NAME"));
        readerType.setBookNum(json.getInteger("BOOK_NUM"));
        readerType.setBorrowDays(json.getInteger("BORROW_DAYS"));
        readerType.setReletDays(json.getInteger("RELET_DAYS"));
        readerTypeMapper.insert(readerType);
        return new Result<ReaderType>();
    }

    @Override
    public Result<ReaderType> update(JSONObject json) {
        Integer readerType=json.getInteger("READER_TYPE");
        if(readerType!=0){
            ReaderType reader = readerTypeMapper.qryByReaderType(readerType);
            if(reader!=null){
                reader.setReaderTypeName(json.getString("READER_TYPE_NAME"));
                reader.setBookNum(json.getInteger("BOOK_NUM"));
                reader.setBorrowDays(json.getInteger("BORROW_DAYS"));
                reader.setReletDays(json.getInteger("RELET_DAYS"));
                readerTypeMapper.update(reader);
            }
        }
        return new Result<ReaderType>();
    }

    @Override
    public Result<ReaderType> del(List<Integer> list) {
        //判断读者类型是否可以删除
        //是否已经添加对应的读者
        Integer count = readerMapper.qryByReaderType(list);
        if(count==0){
            readerTypeMapper.del(list);
        }else{
            throw new HttpException("9999","读者类型正在使用，不能删除");
        }
        return new Result<>();
    }

    @Override
    public List<ReaderType> listAll() {
        return readerTypeMapper.listAll();
    }
}
