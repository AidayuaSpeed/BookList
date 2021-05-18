package com.common;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public interface Model extends Serializable{
	
	JSONObject encode();
	
	void decode(JSONObject json);
}
