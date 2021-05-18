package com.model;

import com.alibaba.fastjson.JSONObject;
import com.common.Model;
/**
 * 与功能菜单<MENU>对应
 * */
public class Menu implements Model {
    private static final long serialVersionUID = -4951571170930364223L;
    /**菜单标识(MENU_ID)*/
    private String menuId;
    /**菜单名称(MENU_NAME)*/
    private String menuName;
    /**菜单路径(MENU_URL)*/
    private String menuUrl;

    @Override
    public JSONObject encode() {
        JSONObject json=new JSONObject();
        json.put("MENU_ID",menuId);
        json.put("MENU_NAME",menuName);
        json.put("MENU_URL",menuUrl);
        return json;
    }

    @Override
    public void decode(JSONObject json) {

    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }


}
