var READER_ID=$("#READER_ID");//读者账号
var READER_NAME=$("#READER_NAME");//读者昵称
var READER_SEX_BOY=$("#READER_SEX_BOY");// 读者性别男
var READER_SEX_GIRL=$("#READER_SEX_GIRL");// 读者性别女
var READER_EMAIL=$("#READER_EMAIL");//读者邮箱
var READER_PHONE=$("#READER_PHONE");//读者电话
var READER_TYPE=$("#READER_TYPE");//读者类型
// 生成表格
var table=new JS.Table("tablepanel",{
    url:"../../reader/list",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'READER_ID'
    },{
        text: "读者账号",
        width: 200,
        dataIndex: 'READER_ID'
    },{
        text: "读者昵称",
        width: 200,
        dataIndex: 'READER_NAME'
    },{
        text: "读者性别",
        width: 80,
        dataIndex: 'READER_SEX',
        renderer:function(readerSex,td){
            td.text(readerSex===1?"男":"女");
        }
    },{
        text: "读者邮箱",
        width: 200,
        dataIndex: 'READER_EMAIL'
    },{
        text: "读者电话",
        width: 200,
        dataIndex: 'READER_PHONE'
    },{
        text: "创建时间",
        width: 200,
        dataIndex: 'CREATE_TIME'
    },{
        text: "读者类型",
        width: 200,
        dataIndex: 'READER_TYPE_NAME'
    },{
        text: "操作",
        dataIndex: 'READER_ID',
        align:"left",
        renderer:function(id,td,record){
            var button=$("<a>");
            button.addClass("button");
            button.css({
                "background-color":"#EEC900",
                "margin-left": "10px"
            });
            button.text("修改");
            button.bind("click",record,function(e){
                READER_ID.val(e.data.READER_ID);
                READER_ID.attr("disabled","disabled");
                READER_NAME.val(e.data.READER_NAME);
                if(e.data.READER_SEX===1){
                    READER_SEX_BOY.prop("checked",true);
                }
                if(e.data.READER_SEX===2){
                    READER_SEX_BOY.prop("checked",true);
                }
                READER_EMAIL.val(e.data.READER_EMAIL);
                READER_PHONE.val(e.data.READER_PHONE);
                READER_TYPE.find("option[value='"+e.data.READER_TYPE+"']").attr("selected",true);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改读者");
                $("#addConfirmBtn").css("display","none");
                $("#updateConfirmBtn").css("display","");
                $("#dialog").css("display","");
                e.preventDefault();
            });
            td.append(button);
        }
    }]
});
//设置每页数量
table.setPageSize(20);
table.create();

//保存读者 或者更新读者 函数
function saveOrUpdate(isAdd){
    if(READER_NAME.val()===""||$.trim(READER_NAME.val())===""){
        JS.MessageBox.alert("请输入读者昵称");
        READER_NAME.css("border","1px solid red");
        return;
    }else {
        READER_NAME.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    var READER_SEX = 0;
    if(READER_SEX_BOY.prop("checked")){
        READER_SEX = 1;
    }
    if(READER_SEX_GIRL.prop("checked")){
        READER_SEX = 2;
    }
    if(READER_SEX===0){
        JS.MessageBox.alert("请选择读者性别");
        return;
    }
    var emailReg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    if(emailReg.test(READER_EMAIL.val())){
        READER_EMAIL.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }else{
        JS.MessageBox.alert("邮箱格式不正确");
        READER_EMAIL.css("border","1px solid red");
        return;
    }

    var readerPhone=READER_PHONE.val();
    if(readerPhone.length===0){
        JS.MessageBox.alert("请输入电话号码");
        READER_PHONE.css("border","1px solid red");
        return;
    }
    if(readerPhone.length!==11){
        JS.MessageBox.alert("请输入有效的电话号码");
        READER_PHONE.css("border","1px solid red");
        return;
    }
    var phoneReg = /^[1][3-9][0-9]{9}$/;
    if(!phoneReg.test(readerPhone)){
        JS.MessageBox.alert("请输入有效的电话号码");
        READER_PHONE.css("border","1px solid red");
        return;
    }
    READER_PHONE.css("border","1px solid rgba(255, 255, 255, 0.15)");
    if(READER_TYPE.val()==="-1"){
        JS.MessageBox.alert("请选择读者类型");
        READER_TYPE.css("border","1px solid red");
        return;
    }else{
        READER_TYPE.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }

    if(isAdd){
        JS.Ajax.request({
            url:"../../reader/save",
            data:{
                READER_ID:READER_ID.val(),
                READER_NAME:READER_NAME.val(),
                READER_SEX:READER_SEX,
                READER_EMAIL:READER_EMAIL.val(),
                READER_PHONE:READER_PHONE.val(),
                READER_TYPE:READER_TYPE.val()
            },
            success:function(){
                JS.MessageBox.alert("读者添加成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }else{
        JS.Ajax.request({
            url:"../../reader/update",
            data:{
                READER_ID:READER_ID.val(),
                READER_NAME:READER_NAME.val(),
                READER_SEX:READER_SEX,
                READER_EMAIL:READER_EMAIL.val(),
                READER_PHONE:READER_PHONE.val(),
                READER_TYPE:READER_TYPE.val()
            },
            success:function(){
                JS.MessageBox.alert("读者属性修改成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("READER_ID",$("#readerIdCondition").val());
    table.setParam("READER_NAME",$("#readerNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var READER_ID_LIST=[];
    for(var i=0;i<records.length;i++){
        READER_ID_LIST.push(records[i].READER_ID);
    }
    if(READER_ID_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的读者");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除读者",
        content:"确认删除"+READER_ID_LIST.length+"个读者？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../reader/del",
                data:{
                    READER_ID_LIST:READER_ID_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除读者成功");
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
    $("#dialog .title").text("添加读者");
    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");
    READER_ID.removeAttr("disabled");
    $("#dialog").css("display","");
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    READER_ID.val("");
    READER_NAME.val("");
    READER_SEX_BOY.prop("checked",false);
    READER_SEX_GIRL.prop("checked",false);
    READER_EMAIL.val("");
    READER_PHONE.val("");
    READER_TYPE.find("option[value='-1']").attr("selected",true);
});
// dialog 添加按钮事件
$("#addConfirmBtn").click(function () {
    saveOrUpdate(true);
});
// dialog 修改按钮事件
$("#updateConfirmBtn").click(function () {
    saveOrUpdate(false);
});
// 男 女 按钮绑定事件
READER_SEX_BOY.click(function(){
    if(READER_SEX_BOY.prop('checked')){
        READER_SEX_GIRL.prop('checked',false);
    }
});
READER_SEX_GIRL.click(function(){
    if(READER_SEX_GIRL.prop('checked')){
        READER_SEX_BOY.prop('checked',false);
    }
});
//加载第一页数据
table.setPageNo(1);
table.load();
//加载所有的读者类型
READER_TYPE.append($("<option value='-1'>请选择读者类型</option>"));
JS.Ajax.request({
    url:"../../readertype/listAll",
    success:function(result){
        if(result&&result.DATA&&result.DATA.length){
            for(var i=0;i<result.DATA.length;i++){
                var readerType=result.DATA[i];
                READER_TYPE.append($("<option value='"+readerType.READER_TYPE+"'>"+readerType.READER_TYPE_NAME+"</option>"));
            }
        }
    }
});
