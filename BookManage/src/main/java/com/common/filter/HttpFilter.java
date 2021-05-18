package com.common.filter;

import com.common.exception.HttpException;
import com.common.http.HttpRequest;
import com.common.session.SessionContext;
import com.common.util.StringUtils;
import com.common.util.TokenUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(asyncSupported = true, filterName = "HttpFilter", urlPatterns = { "*" })
public class HttpFilter implements Filter {
    
    private Logger log = LoggerFactory.getLogger(HttpFilter.class);

    /**后缀*/
    private Set<String> suffixs;
    /**过滤请求*/
    private Set<String> unFilter;
    /**不升级请求*/
    private Set<String> unUpgradeRequest;

    public void init(FilterConfig config) throws ServletException {
        suffixs=new HashSet<String>(100);
        suffixs.add("css");
        suffixs.add("js");
        suffixs.add("png");
        suffixs.add("jpg");
        suffixs.add("ico");
        suffixs.add("html");

        unFilter=new HashSet<String>(100);
        unFilter.add("/");
        unFilter.add("/login");
        unFilter.add("/register");
        unFilter.add("/console");
        unFilter.add("/test");

        unUpgradeRequest=new HashSet<String>(100);
        unUpgradeRequest.add("/file/upload");
        unUpgradeRequest.add("/file/download");
        unUpgradeRequest.add("/image/upload");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if(req instanceof HttpServletRequest) {
            HttpServletRequest request=(HttpServletRequest) req;
            HttpServletResponse response=(HttpServletResponse) resp;
            String url = request.getRequestURI();

            String suffix=getSuffix(url);
            if(suffixs.contains(suffix)){
                chain.doFilter(request, response);
                return;
            }
            if(unFilter.contains(url)) {
                chain.doFilter(request, response);
                return;
            }
            log.debug("过滤url:{}",url);
            String token=this.getToken(request);
            if(StringUtils.isEmpty(token)) {
                throw new HttpException("9998","获取token失败");
            }
            //解析token
            Claims claims=null;
            try {
                claims = TokenUtils.parseToken(token);
            } catch (Exception e) {
                throw new HttpException("9997","token解析错误");
            }
            SessionContext.set("USER_ID", claims.get("USER_ID", String.class));
            SessionContext.set("USER_NAME", claims.get("USER_NAME", String.class));
            SessionContext.set("ROLE_ID", claims.get("ROLE_ID", String.class));
            
            if(unUpgradeRequest.contains(url)) {
                chain.doFilter(request, response);
                return;
            }

            HttpRequest httpRequest =new HttpRequest(request);
            chain.doFilter(httpRequest, response);
        }
    }

    public void destroy() {

    }

    private String getToken(HttpServletRequest request) {
        String token=request.getHeader("TOKEN");
        if(StringUtils.isEmpty(token)) {
            token=request.getParameter("TOKEN");
        }
        if(StringUtils.isEmpty(token)) {
            HttpSession session=request.getSession(false);
            if(session!=null){
                token=String.valueOf(session.getAttribute("TOKEN"));
            }
        }
        return token;
    }

    private String getSuffix(String url){
        int index=0;
        if((index=url.lastIndexOf("."))>0){
            return url.substring(index+1, url.length());
        }else{
            return null;
        }
    }
}
