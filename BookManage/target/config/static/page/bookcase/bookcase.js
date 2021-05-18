var BOOKCASE_ID=$("#BOOKCASE_ID");//书架编码
var BOOKCASE_NAME=$("#BOOKCASE_NAME");//书架名称
var BOOKCASE_DES=$("#BOOKCASE_DES");//书架名称
var BOOKCASE_NUM=$("#BOOKCASE_NUM");//书架最大书籍数量
var table=new JS.Table("tablepanel",{
    url:"../../bookcase",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'BOOKCASE_ID'
    },{
        text: "书架编码",
        width: 100,
        dataIndex: 'BOOKCASE_ID'
    },{
        text: "书架名称",
        width: 260,
        dataIndex: 'BOOKCASE_NAME'
    },{
             text: "书架最大书籍数量",
             width: 260,
             dataIndex: 'BOOKCASE_NUM'
    },{
        text: "书架介绍",
        align:"left",
        dataIndex: 'BOOKCASE_DES'
    },{
        text: "操作",
        width: 200,
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
                BOOKCASE_ID.val(e.data.BOOKCASE_ID);
                BOOKCASE_ID.attr("disabled","disabled");
                BOOKCASE_NAME.val(e.data.BOOKCASE_NAME);
                BOOKCASE_DES.val(e.data.BOOKCASE_DES);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改书架属性");
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

//保存书架 或者更新书架 函数
function saveOrUpdate(isAdd){
    if(BOOKCASE_ID.val()===""||$.trim(BOOKCASE_ID.val())===""){
        JS.MessageBox.alert("请输入书架编码");
        BOOKCASE_ID.css("border","1px solid red");
        return;
    }else {
        BOOKCASE_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BOOKCASE_NAME.val()===""||$.trim(BOOKCASE_NAME.val())===""){
        JS.MessageBox.alert("请输入书架名称");
        BOOKCASE_NAME.css("border","1px solid red");
        return;
    }else {
        BOOKCASE_NAME.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(isAdd){
        JS.Ajax.request({
            url:"../../bookcase/save",
            data:{
                BOOKCASE_ID:BOOKCASE_ID.val(),
                BOOKCASE_NAME:BOOKCASE_NAME.val(),
                BOOKCASE_DES:BOOKCASE_DES.val(),
                BOOKCASE_NUM:BOOKCASE_NUM.val()
            },
            success:function(){
                JS.MessageBox.alert("添加图书书架成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }else{
        JS.Ajax.request({
            url:"../../bookcase/update",
            data:{
                BOOKCASE_ID:BOOKCASE_ID.val(),
                BOOKCASE_NAME:BOOKCASE_NAME.val(),
                BOOKCASE_DES:BOOKCASE_DES.val(),
                BOOKCASE_NUM:BOOKCASE_NUM.val()
            },
            success:function(){
                JS.MessageBox.alert("修改图书书架属性成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("BOOKCASE_ID",$("#bookcaseIdCondition").val());
    table.setParam("BOOKCASE_NAME",$("#bookcaseNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var BOOKCASE_ID_LIST=[];
    for(var i=0;i<records.length;i++){
        BOOKCASE_ID_LIST.push(records[i].BOOKCASE_ID);
    }
    if(BOOKCASE_ID_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的书架");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除书架",
        content:"确认删除"+BOOKCASE_ID_LIST.length+"个书架？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../bookcase/del",
                data:{
                    BOOKCASE_ID_LIST:BOOKCASE_ID_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除书架成功");
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
    $("#dialog .title").text("添加新书架");
    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");

    BOOKCASE_ID.removeAttr("disabled");
    $("#dialog").css("display","");
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    BOOKCASE_ID.val("");
    BOOKCASE_NAME.val("");
    BOOKCASE_DES.val("");
});
// dialog 添加按钮事件
$("#addConfirmBtn").click(function () {
    saveOrUpdate(true);
});
// dialog 修改按钮事件
$("#updateConfirmBtn").click(function () {
    saveOrUpdate(false);
});
//加载第一页数据
table.setPageNo(1);
table.load();