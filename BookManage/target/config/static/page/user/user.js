var USER_ID=$("#USER_ID");//用户账号
var USER_NAME=$("#USER_NAME");//用户昵称
var OLD_USER_PWD=$("#OLD_USER_PWD");//用户旧密码
var USER_PWD=$("#USER_PWD");//用户密码
var USER_PWD_CONFIRM=$("#USER_PWD_CONFIRM");//确认密码

var USER_ID_ITEM=$("#USER_ID_ITEM");//用户密码
var USER_NAME_ITEM=$("#USER_NAME_ITEM");//用户密码
var OLD_USER_PWD_ITEM=$("#OLD_USER_PWD_ITEM");//用户旧密码item
var USER_PWD_ITEM=$("#USER_PWD_ITEM");//用户密码item
var USER_PWD_CONFIRM_ITEM=$("#USER_PWD_CONFIRM_ITEM");//用户密码item
var USER_ADMIN=$("#USER_ADMIN");
var AUTH_USER_ID=$("#AUTH_USER_ID");

var table=new JS.Table("tablepanel",{
    url:"../../user/list",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'USER_ID'
    },{
        text: "用户账号",
        width: 100,
        dataIndex: 'USER_ID'
    },{
        text: "用户昵称",
        width: 260,
        dataIndex: 'USER_NAME'
    },{
        text: "创建时间",
        width: 200,
        dataIndex: 'CREATE_TIME'
    },{
        text: "操作",
        width: 200,
        dataIndex: 'USER_ID',
        align:"left",
        renderer:function(id,td,record){
            if(record.USER_ID===window.parent.JS.USER.USER_ID){
                return;
            }
            var button=$("<a>");
            button.addClass("button");
            button.css({
                "background-color":"#EEC900",
                "margin-left": "10px"
            });
            button.text("修改");
            button.bind("click",record,function(e){
                USER_ID.val(e.data.USER_ID);
                USER_ID.attr("disabled","disabled");
                USER_NAME.val(e.data.USER_NAME);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改管理员用户");

                USER_ID_ITEM.css("display","");
                USER_NAME_ITEM.css("display","");
                OLD_USER_PWD_ITEM.css("display","none");
                USER_PWD_ITEM.css("display","none");
                USER_PWD_CONFIRM_ITEM.css("display","none");

                $("#addConfirmBtn").css("display","none");
                $("#updateConfirmBtn").css("display","");
                $("#pwdConfirmBtn").css("display","none");

                $("#dialog").css("display","");
                e.preventDefault();
            });
            td.append(button);

            // 密码修改
            var pwdBtn=$("<button>");
            pwdBtn.addClass("button");
            pwdBtn.css({
                "background-color":"#6967ee",
                "margin-left": "10px"
            });
            pwdBtn.text("密码修改");
            pwdBtn.bind("click",record,function(e){
                USER_ID.val(e.data.USER_ID);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改管理员用户密码");

                USER_ID_ITEM.css("display","none");
                USER_NAME_ITEM.css("display","none");
                OLD_USER_PWD_ITEM.css("display","");
                USER_PWD_ITEM.css("display","");
                USER_PWD_CONFIRM_ITEM.css("display","");

                $("#addConfirmBtn").css("display","none");
                $("#updateConfirmBtn").css("display","none");
                $("#pwdConfirmBtn").css("display","");

                $("#dialog").css("display","");
                e.preventDefault();
            });
            td.append(pwdBtn);

            // 密码修改
            var authorizeBtn=$("<button>");
            authorizeBtn.addClass("button");
            authorizeBtn.css({
                "background-color":"#6967ee",
                "margin-left": "10px"
            });
            authorizeBtn.text("授权");
            authorizeBtn.bind("click",record,function(e){
                AUTH_USER_ID.val(e.data.USER_ID);
                USER_ADMIN.prop("checked",e.data.USER_ADMIN===1);
                JS.Ajax.request({
                    url:"../../menu/qryByUserId",
                    data:{
                        USER_ID:e.data.USER_ID
                    },
                    success:function(result){
                        var MENU_LIST = $("#MENU_LIST");
                        if(result.DATA&&result.DATA.length){
                            for(var i=0;i<result.DATA.length;i++){
                                var menu=result.DATA[i];
                                MENU_LIST.find("input[MENU_ID="+menu.MENU_ID+"]").prop("checked",true);
                            }
                        }
                    }
                });
                $("#authorizeDialog").css("display","");
            });
            td.append(authorizeBtn);
        }
    }]
});
//设置每页数量
table.setPageSize(20);
table.create();

//保存 或者更新 函数
function saveOrUpdate(isAdd){
    if(USER_ID.val()===""||$.trim(USER_ID.val())===""){
        JS.MessageBox.alert("请输入用户账号");
        USER_ID.css("border","1px solid red");
        return;
    }else {
        USER_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(USER_NAME.val()===""||$.trim(USER_NAME.val())===""){
        JS.MessageBox.alert("请输入用户昵称");
        USER_NAME.css("border","1px solid red");
        return;
    }else {
        USER_NAME.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    // 添加用户校验密码  或者 密码修改
    if(isAdd){
        if(USER_PWD.val()===""||$.trim(USER_PWD.val())===""){
            JS.MessageBox.alert("请输入密码");
            USER_PWD.css("border","1px solid red");
            return;
        }else{
            if(USER_PWD.val().length<6){
                JS.MessageBox.alert("密码长度太短");
                USER_PWD.css("border","1px solid red");
                return;
            }else{
                USER_PWD.css("border","1px solid rgba(255, 255, 255, 0.15)");
            }
        }
        if(USER_PWD.val()!==USER_PWD_CONFIRM.val()){
            JS.MessageBox.alert("两次密码不一致");
            USER_PWD_CONFIRM.css("border","1px solid red");
            return;
        }else{
            USER_PWD_CONFIRM.css("border","1px solid rgba(255, 255, 255, 0.15)");
        }
    }
    if(isAdd){
        JS.Ajax.request({
            url:"../../user/save",
            data:{
                USER_ID:USER_ID.val(),
                USER_NAME:USER_NAME.val(),
                USER_PWD:USER_PWD.val()
            },
            success:function(){
                JS.MessageBox.alert("添加管理员用户成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }else{
        JS.Ajax.request({
            url:"../../user/update",
            data:{
                USER_ID:USER_ID.val(),
                USER_NAME:USER_NAME.val(),
                USER_PWD:USER_PWD.val()
            },
            success:function(){
                JS.MessageBox.alert("修改管理员用户成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("USER_ID",$("#userIdCondition").val());
    table.setParam("USER_NAME",$("#userNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var USER_ID_LIST=[];
    for(var i=0;i<records.length;i++){
        USER_ID_LIST.push(records[i].USER_ID);
    }
    if(USER_ID_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的管理员用户用户");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除图书类型",
        content:"确认删除"+USER_ID_LIST.length+"个管理员用户？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../user/del",
                data:{
                    USER_ID_LIST:USER_ID_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除管理员用户成功");
                    table.load();
                }
            });
        },
        //confirm函数  this指向对象
        scope:table
    });
    win.show();
    this.blur();
});
//添加按钮绑定事件
$("#addBtn").click(function(){
    JS.MaskLayer.show();
    $("#dialog .title").text("添加新管理员用户");

    USER_ID_ITEM.css("display","");
    USER_NAME_ITEM.css("display","");
    OLD_USER_PWD_ITEM.css("display","none");
    USER_PWD_ITEM.css("display","");
    USER_PWD_CONFIRM_ITEM.css("display","");

    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");
    $("#pwdConfirmBtn").css("display","none");

    USER_ID.removeAttr("disabled");
    $("#dialog").css("display","");
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    USER_ID.val("");
    USER_NAME.val("");
    OLD_USER_PWD.val("");
    USER_PWD.val("");
    USER_PWD_CONFIRM.val("");
});
// dialog 添加按钮事件
$("#addConfirmBtn").click(function () {
    saveOrUpdate(true);
});
// dialog 修改按钮事件
$("#updateConfirmBtn").click(function () {
    saveOrUpdate(false);
});
// dialog 修改密码按钮事件
$("#pwdConfirmBtn").click(function () {
    if(OLD_USER_PWD.val()===""||$.trim(OLD_USER_PWD.val())===""){
        JS.MessageBox.alert("请输入用户旧密码");
        OLD_USER_PWD.css("border","1px solid red");
        return;
    }else {
        OLD_USER_PWD.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(USER_PWD.val()===""||$.trim(USER_PWD.val())===""){
        JS.MessageBox.alert("请输入密码");
        USER_PWD.css("border","1px solid red");
        return;
    }else{
        if(USER_PWD.val().length<6){
            JS.MessageBox.alert("密码长度太短");
            USER_PWD.css("border","1px solid red");
            return;
        }else{
            USER_PWD.css("border","1px solid rgba(255, 255, 255, 0.15)");
        }
    }
    if(USER_PWD.val()!==USER_PWD_CONFIRM.val()){
        JS.MessageBox.alert("两次密码不一致");
        USER_PWD_CONFIRM.css("border","1px solid red");
        return;
    }else{
        USER_PWD_CONFIRM.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    JS.Ajax.request({
        url:"../../user/pwdUpdate",
        data:{
            USER_ID:USER_ID.val(),
            OLD_USER_PWD:OLD_USER_PWD.val(),
            USER_PWD:USER_PWD.val()
        },
        success:function(){
            JS.MessageBox.alert("用户密码修改成功");
            table.load();
            $("#dialog .close").trigger("click");
        }
    });
});
//加载第一页数据
table.setPageNo(1);
table.load();


// dialog 关闭事件
$("#authorizeDialog .close").click(function () {
    $("#authorizeDialog").css("display","none");
    JS.MaskLayer.hidden();
});

$("#authorizeSaveBtn").click(function(){
    var data={};
    data.USER_ADMIN=$("#USER_ADMIN").prop("checked");
    data.MENU_LIST=[];
    data.USER_ID=AUTH_USER_ID.val();
    $("#MENU_LIST").find("input").each(function () {
        if($(this).prop("checked")){
            data.MENU_LIST.push($(this).attr("MENU_ID"));
        }
    });
    JS.Ajax.request({
        url:"../../user/auth",
        data:data,
        success:function(){
            JS.MessageBox.alert("用户授权成功");
            table.load();
            $("#authorizeDialog .close").trigger("click");
        }
    });
});