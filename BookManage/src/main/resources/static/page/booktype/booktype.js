var TYPE_ID=$("#TYPE_ID");//图书类型
var TYPE_NAME=$("#TYPE_NAME");//类型名称
var TYPE_DES=$("#TYPE_DES");//类型介绍

var table=new JS.Table("tablepanel",{
    url:"../../booktype/list",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'TYPE_ID'
    },{
        text: "图书类型",
        width: 100,
        dataIndex: 'TYPE_ID'
    },{
        text: "图书类型名称",
        width: 260,
        dataIndex: 'TYPE_NAME'
    },{
        text: "图书类型介绍",
        align:"left",
        dataIndex: 'TYPE_DES'
    },{
        text: "操作",
        width: 200,
        dataIndex: 'TYPE_ID',
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
                TYPE_ID.val(e.data.TYPE_ID);
                TYPE_ID.attr("disabled","disabled");
                TYPE_NAME.val(e.data.TYPE_NAME);
                TYPE_DES.val(e.data.TYPE_DES);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改图书类型");
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

//保存或者更新图书类型 函数
function saveOrUpdate(isAdd){
    if(TYPE_ID.val()===""||$.trim(TYPE_ID.val())===""){
        JS.MessageBox.alert("请输入图书类型");
        TYPE_ID.css("border","1px solid red");
        return;
    }else {
        TYPE_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(TYPE_NAME.val()===""||$.trim(TYPE_NAME.val())===""){
        JS.MessageBox.alert("请输入图书类型名称");
        TYPE_NAME.css("border","1px solid red");
        return;
    }else {
        TYPE_NAME.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(isAdd){
        JS.Ajax.request({
            url:"../../booktype/save",
            data:{
                TYPE_ID:TYPE_ID.val(),
                TYPE_NAME:TYPE_NAME.val(),
                TYPE_DES:TYPE_DES.val()
            },
            success:function(){
                JS.MessageBox.alert("添加图书类型成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }else{
        JS.Ajax.request({
            url:"../../booktype/update",
            data:{
                TYPE_ID:TYPE_ID.val(),
                TYPE_NAME:TYPE_NAME.val(),
                TYPE_DES:TYPE_DES.val()
            },
            success:function(){
                JS.MessageBox.alert("修改图书类型成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("TYPE_ID",$("#bookTypeIdCondition").val());
    table.setParam("TYPE_NAME",$("#bookTypeNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var TYPE_ID_LIST=[];
    for(var i=0;i<records.length;i++){
        TYPE_ID_LIST.push(records[i].TYPE_ID);
    }
    if(TYPE_ID_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的图书类型");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除图书类型",
        content:"确认删除"+TYPE_ID_LIST.length+"个图书类型？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../booktype/del",
                data:{
                    TYPE_ID_LIST:TYPE_ID_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除图书类型成功");
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
    $("#dialog .title").text("添加新图书类型");
    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");

    TYPE_ID.removeAttr("disabled");
    $("#dialog").css("display","");
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    TYPE_ID.val("");
    TYPE_NAME.val("");
    TYPE_DES.val("");
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