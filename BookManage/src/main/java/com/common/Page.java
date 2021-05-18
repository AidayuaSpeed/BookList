package com.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Page {

    private boolean success;
    private int totalNum;
    private int pageNo;
    private int pageSize;
    private JSONArray data;

    public String toJSONString(){
        JSONObject result=new JSONObject();
        result.put("SUCCESS", true);
        result.put("TOTAL_NUM", totalNum);
        result.put("PAGE_NO", pageNo);// 当前页码
        result.put("PAGE_SIZE", pageSize);// 每页条数
        result.put("DATA", data==null?new JSONArray():data);
        return result.toJSONString();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
