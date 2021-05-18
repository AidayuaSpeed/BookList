package com;

import com.common.aspect.LogInterceptor;
import com.common.config.TransactionManagerConfig;
import com.common.listener.CloseListener;
import com.common.listener.StartListener;
import com.common.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@Import({SpringUtil.class,  LogInterceptor.class, TransactionManagerConfig.class})
@MapperScan("com.mapper")
@ComponentScan(basePackages= {"com.mapper","com.service","com.common.advice","com.controller"})
@ServletComponentScan(basePackages= {"com.common.filter"})
public class BookServer {
    /**
     * 程序启动类
     * StartListener 在程序启动后执行
     * CloseListener 在程序关闭执行
     * */
    public static void main( String[] args ){
        SpringApplication server=new SpringApplication(BookServer.class);
        server.addListeners(new StartListener());
        server.addListeners(new CloseListener());
        server.run(args);
    }

}
