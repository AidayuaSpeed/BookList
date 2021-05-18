package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.DateConst;
import com.common.Page;
import com.common.Result;
import com.common.session.SessionContext;
import com.common.util.DateUtils;
import com.common.util.RandomStringUtil;
import com.common.util.StringUtils;
import com.mapper.ReaderMapper;
import com.mapper.ReaderTypeMapper;
import com.model.Reader;
import com.model.ReaderType;
import com.service.IReaderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("readerServiceImpl")
public class ReaderServiceImpl implements IReaderService {

    @Resource
    private ReaderMapper readerMapper;
    @Resource
    private ReaderTypeMapper readerTypeMapper;

    @Override
    public Page qryPage(Map<String, Object> param,int pageNo,int pageSize) {
        int totalNum=readerMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<Reader> readerList=readerMapper.page(param);
        JSONArray array=new JSONArray();
        if(readerList!=null&&!readerList.isEmpty()){
            readerList.forEach(reader -> {
                ReaderType readerType = readerTypeMapper.qryByReaderType(reader.getReaderType());
                if(readerType!=null){
                    reader.setReaderTypeName(readerType.getReaderTypeName());
                }
                array.add(reader.encode());
            });
        }
        Page page = new Page();
        page.setSuccess(true);
        page.setTotalNum(totalNum);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(array);
        return page;
    }

    public Result<Reader> insert(JSONObject json){
        String readerId=json.getString("READER_ID");
        Date createTime=new Date();
        if(StringUtils.isEmpty(readerId)){
            readerId= DateUtils.parseDateToStr(createTime, DateConst.DATE_TIME_FORMAT_YY_MM_DD)+ RandomStringUtil.genRandomNum(4);
        }else{
            Reader reader = readerMapper.qryById(readerId);
            if(reader!=null){
                return new Result<>("9999","读者账号已存在，请修改");
            }
        }
        Reader reader = new Reader();
        reader.setReaderId(readerId);
        reader.setReaderName(json.getString("READER_NAME"));
        reader.setReaderSex(json.getInteger("READER_SEX"));
        reader.setReaderEmail(json.getString("READER_EMAIL"));
        reader.setReaderPhone(json.getString("READER_PHONE"));
        reader.setCreateTime(createTime);
        reader.setReaderType(json.getInteger("READER_TYPE"));
        reader.setOperatorId(SessionContext.get("USER_ID"));
        readerMapper.insert(reader);
        return new Result<>();
    }

    public Result<Reader> update(JSONObject json){
        String readerId=json.getString("READER_ID");
        if(StringUtils.isNotEmpty(readerId)){
            Reader reader = readerMapper.qryById(readerId);
            if(reader!=null){
                reader.setReaderName(json.getString("READER_NAME"));
                reader.setReaderSex(json.getInteger("READER_SEX"));
                reader.setReaderEmail(json.getString("READER_EMAIL"));
                reader.setReaderPhone(json.getString("READER_PHONE"));
                reader.setReaderType(json.getInteger("READER_TYPE"));
                readerMapper.update(reader);
            }
        }
        return new Result<>();
    }
    public Result<Reader> del(List<String> list){
        readerMapper.del(list);
        return new Result<>();
    }

    public List<Reader> listAll() {
        return readerMapper.listAll();
    }

}
