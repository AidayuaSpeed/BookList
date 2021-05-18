var OLD_USER_PWD=$("#OLD_USER_PWD");
var USER_PWD=$("#USER_PWD");
var USER_PWD_CONFIRM=$("#USER_PWD_CONFIRM");

$("#confirmBtn").click(function () {
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
        url:"../../user/pwdchange",
        data:{
            OLD_USER_PWD:OLD_USER_PWD.val(),
            USER_PWD:USER_PWD.val()
        },
        success:function(){
            JS.MessageBox.alert("密码修改成功");
            OLD_USER_PWD.val("");
            USER_PWD.val("");
            USER_PWD_CONFIRM.val("");
        }
    });
});