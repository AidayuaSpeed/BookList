package com.common.advice;

import com.common.Result;
import com.common.exception.HttpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception ex) {
    	ex.printStackTrace();
    	HttpException httpException;
    	if(ex instanceof HttpException) {
            httpException = (HttpException) ex;
    	}else {
            httpException = new HttpException(ex);
    	}
        return new Result<>(httpException).encode().toJSONString();
    }
}
