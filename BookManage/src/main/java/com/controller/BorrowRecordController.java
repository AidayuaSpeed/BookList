package com.controller;

import com.common.Result;
import com.common.http.HttpRequest;
import com.common.util.StringUtils;
import com.model.BorrowRecord;
import com.service.IBorrowRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BorrowRecordController {

    @Resource(name = "borrowRecordServiceImpl")
    private IBorrowRecordService borrowRecordService;

    /**
     * 查询最近的借阅记录
     * */
    @RequestMapping(value = "/borrow/list",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public String list(HttpRequest request) {
        Map<String,Object> param = new HashMap<>();
        String bookId=request.getString("BOOK_ID");
        if(StringUtils.isNotEmpty(bookId)){
            param.put("BOOK_ID",bookId);
        }
        String readerId=request.getString("READER_ID");
        if(StringUtils.isNotEmpty(readerId)){
            param.put("READER_ID",readerId);
        }
        int pageNo= request.getInteger("PAGE_NO");
        int pageSize= request.getInteger("PAGE_SIZE");
        return borrowRecordService.qryPage(param,pageNo,pageSize).toJSONString();
    }
    /**
     * 还书方法
     * */
    @RequestMapping(value = "/borrow/back",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BorrowRecord> back(HttpRequest request) {
        return borrowRecordService.back(request.getJson());
    }
    /**
     * 借书函数
     * */
    @RequestMapping(value = "/borrow/borrow",method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    public Result<BorrowRecord> borrow(HttpRequest request) {
        return borrowRecordService.borrow(request.getJson());
    }

}
