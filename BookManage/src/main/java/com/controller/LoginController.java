package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.util.GetRequestJsonUtil;
import com.common.util.StringUtils;
import com.common.util.TokenUtils;
import com.model.User;
import com.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @Resource(name = "userServiceImpl")
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        String jsonStr = GetRequestJsonUtil.getRequestJsonString(request);
        if(StringUtils.isNotEmpty(jsonStr)) {
            JSONObject json=JSONObject.parseObject(jsonStr);
            String userId=json.getString("USER_ID");
            String userPwd=json.getString("USER_PWD");
            User user=userService.qryById(userId);
            if(user==null){
                result.put("SUCCESS", false);
                result.put("RETURN_MSG", "账号不存在");
            }else{
                // 验证用户密码
                if(userPwd.equals(user.getUserPwd())){
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("USER_ID", String.valueOf(user.getUserId()));
                    claims.put("USER_NAME", user.getUserName());
                    claims.put("USER_ADMIN", user.getUserAdmin());
                    String token= TokenUtils.createToken(claims);

                    result.put("SUCCESS", true);
                    result.put("USER_ID", user.getUserId());
                    result.put("USER_NAME", user.getUserName());
                    result.put("USER_ADMIN", user.getUserAdmin());
                    result.put("TOKEN", token);
                    HttpSession session=request.getSession();
                    session.setMaxInactiveInterval(3600);
                    session.setAttribute("TOKEN",token);
                    log.info("用户 {}({}) 通过 {} 登录系统",user.getUserName(),user.getUserId(),"浏览器");
                }else{
                    result.put("SUCCESS", false);
                    result.put("RETURN_MSG", "账号或者密码错误");
                }
            }
        }else {
            result.put("SUCCESS", false);
            result.put("RETURN_MSG", "账号或者密码错误");
        }
        return result.toJSONString();
    }
}
