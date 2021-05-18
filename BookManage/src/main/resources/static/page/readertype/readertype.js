var READER_TYPE=$("#READER_TYPE");//读者类型
var READER_TYPE_NAME=$("#READER_TYPE_NAME");//读者类型名称
var BOOK_NUM=$("#BOOK_NUM");// 可借书数
var BORROW_DAYS=$("#BORROW_DAYS");// 可借天数
var RELET_DAYS=$("#RELET_DAYS");// 每次可续借天数
// 生成表格
var table=new JS.Table("tablepanel",{
    url:"../../readertype/list",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'READER_TYPE'
    },{
        text: "读者类型名称",
        width: 200,
        dataIndex: 'READER_TYPE_NAME'
    },{
        text: "可借书数",
        width: 80,
        dataIndex: 'BOOK_NUM'
    },{
        text: "可借天数",
        width: 200,
        dataIndex: 'BORROW_DAYS'
    },{
        text: "续借天数",
        width: 200,
        dataIndex: 'RELET_DAYS'
    },{
        text: "操作",
        dataIndex: 'READER_TYPE',
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
                READER_TYPE.val(e.data.READER_TYPE);
                READER_TYPE_NAME.val(e.data.READER_TYPE_NAME);
                BOOK_NUM.val(e.data.BOOK_NUM);
                BORROW_DAYS.val(e.data.BORROW_DAYS);
                RELET_DAYS.val(e.data.RELET_DAYS);

                JS.MaskLayer.show();
                $("#dialog .title").text("修改读者类型");
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
//保存读者类型或者更新读者类型 函数
function saveOrUpdate(isAdd){
    if(READER_TYPE_NAME.val()===""||$.trim(READER_TYPE_NAME.val())===""){
        JS.MessageBox.alert("请输入读者类型昵称");
        READER_TYPE_NAME.css("border","1px solid red");
        return;
    }else {
        READER_TYPE_NAME.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BOOK_NUM.val()===""||$.trim(BOOK_NUM.val())===""){
        JS.MessageBox.alert("可借书数");
        BOOK_NUM.css("border","1px solid red");
        return;
    }else {
        BOOK_NUM.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BORROW_DAYS.val()===""||$.trim(BORROW_DAYS.val())===""){
        JS.MessageBox.alert("可借天数");
        BORROW_DAYS.css("border","1px solid red");
        return;
    }else {
        BORROW_DAYS.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(RELET_DAYS.val()===""||$.trim(RELET_DAYS.val())===""){
        JS.MessageBox.alert("每次可续借天数");
        RELET_DAYS.css("border","1px solid red");
        return;
    }else {
        RELET_DAYS.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(isAdd){
        JS.Ajax.request({
            url:"../../readertype/save",
            data:{
                READER_TYPE_NAME:READER_TYPE_NAME.val(),
                BOOK_NUM:BOOK_NUM.val(),
                BORROW_DAYS:BORROW_DAYS.val(),
                RELET_DAYS:RELET_DAYS.val()
            },
            success:function(){
                JS.MessageBox.alert("读者添类型加成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }else{
        JS.Ajax.request({
            url:"../../readertype/update",
            data:{
                READER_TYPE:READER_TYPE.val(),
                READER_TYPE_NAME:READER_TYPE_NAME.val(),
                BOOK_NUM:BOOK_NUM.val(),
                BORROW_DAYS:BORROW_DAYS.val(),
                RELET_DAYS:RELET_DAYS.val()
            },
            success:function(){
                JS.MessageBox.alert("读者属性修改成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}
//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var READER_TYPE_LIST=[];
    for(var i=0;i<records.length;i++){
        READER_TYPE_LIST.push(records[i].READER_TYPE);
    }
    if(READER_TYPE_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的读者类型");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除读者类型",
        content:"确认删除"+READER_TYPE_LIST.length+"个读者类型？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../readertype/del",
                data:{
                    READER_TYPE_LIST:READER_TYPE_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除读者类型成功");
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
    $("#dialog .title").text("添加读者类型");
    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");
    $("#dialog").css("display","");
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    READER_TYPE.val("");
    READER_TYPE_NAME.val("");
    BOOK_NUM.val("");
    BORROW_DAYS.val("");
    RELET_DAYS.val("");
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