package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.common.exception.HttpException;
import com.common.session.SessionContext;
import com.common.util.DateUtils;
import com.common.util.StringUtils;
import com.mapper.BookMapper;
import com.mapper.BookPeriodicalsMapper;
import com.mapper.BorrowRecordMapper;
import com.mapper.ReaderMapper;
import com.mapper.ReaderTypeMapper;
import com.mapper.UserMapper;
import com.model.Book;
import com.model.BookPeriodicals;
import com.model.BorrowRecord;
import com.model.Reader;
import com.model.ReaderType;
import com.model.User;
import com.service.IBorrowRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("borrowRecordServiceImpl")
public class BorrowRecordServiceImpl implements IBorrowRecordService {

    @Resource
    private BorrowRecordMapper borrowRecordMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookPeriodicalsMapper bookPeriodicalsMapper;
    @Resource
    private ReaderMapper readerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ReaderTypeMapper readerTypeMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=borrowRecordMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<BorrowRecord> borrowRecordList=borrowRecordMapper.page(param);
        JSONArray array=new JSONArray();
        if(borrowRecordList!=null&&!borrowRecordList.isEmpty()){
            borrowRecordList.forEach(borrowRecord -> {
                Book book=bookMapper.qryById(borrowRecord.getBookIsbn(),borrowRecord.getBookId());
                if(book!=null){
                    borrowRecord.setBookName(book.getBookName());
                }
                Reader reader=readerMapper.qryById(borrowRecord.getReaderId());
                if(reader!=null){
                    borrowRecord.setReaderName(reader.getReaderName());
                }
                User borrowUser=userMapper.qryById(borrowRecord.getBorrowOperatorId());
                if(borrowUser!=null){
                    borrowRecord.setBorrowOperatorName(borrowUser.getUserName());
                }
                if(StringUtils.isNotEmpty(borrowRecord.getGiveBackOperatorId())){
                    User giveUser=userMapper.qryById(borrowRecord.getGiveBackOperatorId());
                    if(giveUser!=null){
                        borrowRecord.setGiveBackOperatorName(giveUser.getUserName());
                    }
                }
                array.add(borrowRecord.encode());
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

    @Override
    public Result<BorrowRecord> back(JSONObject json) {
        JSONArray recordIdList = json.getJSONArray("RECORD_ID_LIST");
        for(int i=0;i<recordIdList.size();i++){
            Integer recordId=recordIdList.getInteger(i);
            BorrowRecord borrowRecord=borrowRecordMapper.qryById(recordId);
            if(borrowRecord!=null&&borrowRecord.getState()==1){
                Date date = new Date();
                borrowRecord.setState(2);
                borrowRecord.setGiveBackTime(date);
                borrowRecord.setGiveBackOperatorId(SessionContext.get("USER_ID"));
                borrowRecord.setUpdateTime(date);
                borrowRecordMapper.update(borrowRecord);
                //更新图书状态
                Book book = bookMapper.qryById(borrowRecord.getBookIsbn(),borrowRecord.getBookId());
                if(book==null){
                    throw new HttpException("9999","查询读者信息失败");
                }
                book.setBookState(1);
                bookMapper.update(book);
                //更新图书借出册数
                BookPeriodicals bookPeriodicals = bookPeriodicalsMapper.qryById(borrowRecord.getBookIsbn());
                if(bookPeriodicals!=null){
                    bookPeriodicals.setBorrowNum(bookPeriodicals.getBookNum()-1);
                    bookPeriodicalsMapper.update(bookPeriodicals);
                }
            }
        }
        return new Result<>();
    }

    @Override
    public Result<BorrowRecord> borrow(JSONObject json) {
        Date date = new Date();
        JSONArray bookList = json.getJSONArray("BOOK_LIST");
        String readerId = json.getString("READER_ID");
        // 查询读者信息
        Reader reader = readerMapper.qryById(readerId);
        if(reader==null){
            throw new HttpException("9999","查询读者信息失败");
        }
        ReaderType readerType = readerTypeMapper.qryByReaderType(reader.getReaderType());
        if(readerType==null){
            throw new HttpException("9999","查询读者信息失败");
        }
        // 查询未还图书册数
        int borrowNum = borrowRecordMapper.qryBorrowNum(reader.getReaderId(),1);
        if((bookList.size()+borrowNum)>readerType.getBookNum()){
            throw new HttpException("9999","已经超过当前读者能借阅图书最大限制！！！");
        }
        for(int i=0;i<bookList.size();i++){
            String bookIsbn = bookList.getJSONObject(i).getString("BOOK_ISBN");
            String bookId = bookList.getJSONObject(i).getString("BOOK_ID");
            Book book = bookMapper.qryById(bookIsbn,bookId);
            if(book==null){
                throw new HttpException("9999","未查询该图书");
            }
            if(book.getBookState()==2){
                throw new HttpException("9999",book.getBookName()+" 图书已借出");
            }
            // 更新图书状态
            book.setBookState(2);
            bookMapper.update(book);
            //添加借阅记录
            BorrowRecord record = new BorrowRecord();
            record.setBookIsbn(bookIsbn);
            record.setBookId(bookId);
            record.setBookName(book.getBookName());
            record.setReaderId(readerId);
            record.setBorrowTime(date);
            record.setBackTime(DateUtils.addDays(date,readerType.getBorrowDays()));//
            record.setBorrowOperatorId(SessionContext.get("USER_ID"));
            record.setState(1);
            record.setUpdateTime(date);
            borrowRecordMapper.insert(record);
            //更新图书借出册数
            BookPeriodicals bookPeriodicals = bookPeriodicalsMapper.qryById(bookIsbn);
            if(bookPeriodicals!=null){
                bookPeriodicals.setBorrowNum(bookPeriodicals.getBookNum()+1);
                bookPeriodicalsMapper.update(bookPeriodicals);
            }
        }
        return new Result<>();
    }
}
