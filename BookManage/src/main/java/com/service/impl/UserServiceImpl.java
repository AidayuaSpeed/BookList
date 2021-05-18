package com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Page;
import com.common.Result;
import com.common.session.SessionContext;
import com.common.util.StringUtils;
import com.mapper.MenuMapper;
import com.mapper.UserMapper;
import com.model.Menu;
import com.model.User;
import com.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page qryPage(Map<String, Object> param, int pageNo, int pageSize) {
        int totalNum=userMapper.count(param);
        param.put("OFFSET",(pageNo-1)*pageSize);// 查询分页
        param.put("PAGE_SIZE",pageSize);
        List<User> userList=userMapper.page(param);
        JSONArray array=new JSONArray();
        if(userList!=null&&!userList.isEmpty()){
            userList.forEach(user -> array.add(user.encode()));
        }
        Page page = new Page();
        page.setSuccess(true);
        page.setTotalNum(totalNum);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(array);
        return page;
    }

    public User qryById(String userId) {
        return userMapper.qryById(userId);
    }

    @Override
    public Result<User> insert(JSONObject json) {
        String userId=json.getString("USER_ID");
        Date createDate=new Date();
        if(StringUtils.isEmpty(userId)){
            return new Result<>("9999","请输入用户账号");
        }else{
            User user = userMapper.qryById(userId);
            if(user!=null){
                return new Result<>("9999","用户账号已存在，请修改");
            }
        }
        User user = new User();
        user.setUserId(userId);
        user.setUserName(json.getString("USER_NAME"));
        String userPwd=json.getString("USER_PWD");
        user.setUserPwd(userPwd);
        user.setCreateTime(createDate);
        userMapper.insert(user);
        menuMapper.insertRef(userId,"1000");
        menuMapper.insertRef(userId,"1009");
        return new Result<>();
    }

    @Override
    public Result<User> update(JSONObject json) {
        String userId=json.getString("USER_ID");
        if(StringUtils.isNotEmpty(userId)){
            User user = userMapper.qryById(userId);
            if(user!=null){
                user.setUserName(json.getString("USER_NAME"));
                userMapper.update(user);
            }
        }
        return new Result<>();
    }

    public Result<User> updatePwd(JSONObject json){
        String userId=json.getString("USER_ID");
        if(StringUtils.isNotEmpty(userId)){
            User user = userMapper.qryById(userId);
            if(user!=null){
                String oldPwd=json.getString("OLD_USER_PWD");
                if(user.getUserPwd().equals(oldPwd)){
                    String userPwd=json.getString("USER_PWD");
                    user.setUserPwd(userPwd);
                    userMapper.update(user);
                }else{
                    return new Result<>("9999","旧密码错误");
                }
            }
        }
        return new Result<>();
    }

    @Override
    public Result<User> del(List<String> list) {
        userMapper.del(list);
        list.forEach(userId -> menuMapper.delByUserId(userId));
        return new Result<>();
    }

    @Override
    public Result<User> auth(JSONObject json) {
        // 判断当前用户是否有权限修改
        User cuser = userMapper.qryById(SessionContext.get("USER_ID"));
        if(cuser ==  null || cuser.getUserAdmin() == 0){
            return new Result<>("9999","您没有修改权限");
        }
        // 查询需要修改用户 原有授权
        // 跟原有授权比对
        String userId = json.getString("USER_ID");
        List<Menu> menuList = menuMapper.qryByUserId(userId);
        Map<String,Menu> menuMap = new HashMap<>();
        if(menuList!=null&&!menuList.isEmpty()){
            menuList.forEach(menu -> menuMap.put(menu.getMenuId(),menu));
        }
        JSONArray menus = json.getJSONArray("MENU_LIST");
        Set<String> set = new HashSet<>();
        if(menus!=null&&!menus.isEmpty()){
            for(int i=0;i<menus.size();i++){
                String menuId = menus.getString(i);
                if(!menuMap.containsKey(menuId)){// 添加新的授权
                    menuMapper.insertRef(userId,menuId);
                }
                set.add(menuId);// 记录授权菜单
            }
        }
        set.add("1000");
        set.add("1009");
        // 删除剔除权限
        for(Map.Entry<String,Menu> entry:menuMap.entrySet()){
            if(!set.contains(entry.getKey())){
                menuMapper.delRef(userId,entry.getKey());
            }
        }
        // 更新用户管理员权限
        User user = userMapper.qryById(userId);
        user.setUserAdmin(json.getBoolean("USER_ADMIN")?1:0);
        userMapper.update(user);
        return new Result<>();
    }
}
