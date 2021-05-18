package com.common.listener;

import com.common.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目启动初始化数据到内存中
 * */
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger log = LoggerFactory.getLogger(StartListener.class);
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //预热TOKEN 生成函数
        Map<String, Object> claims = new HashMap<>();
        claims.put("USER_ID", String.valueOf(1000));
        TokenUtils.createToken(claims);
        log.info("启动完成");
    }
}