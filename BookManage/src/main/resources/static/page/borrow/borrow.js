var bookIdCondition=$("#bookIdCondition");
var bookIdInputWin=$("#bookIdInputWin");
var readerIdcondition=$("#readerIdcondition");
var readerIdInputWin=$("#readerIdInputWin");
// 已选择图书列表
var selectedBooks=$("#selectedBooks");

var READER_ID=$("#READER_ID");
var READER_NAME=$("#READER_NAME");
var READER_EMAIL=$("#READER_EMAIL");
var READER_PHONE=$("#READER_PHONE");
//图书列表
var BOOK_LIST=[];
//读者列表
var READER_LIST=[];
//获取图书列表
JS.Ajax.request({
    url:"../../book/listAll",
    success:function(result){
        for(var i=0;i<result.DATA.length;i++){
            BOOK_LIST.push(result.DATA[i]);
        }
        bookIdCondition.autocomplete(BOOK_LIST, {
            minChars: 0,//最少输入字条
            max: 12,
            autoFill: false,//是否选多个,用","分开
            mustMatch: false,//是否全匹配, 如数据中没有此数据,将无法输入
            matchContains: true,//是否全文搜索,否则只是前面作为标准
            scrollHeight: 300,
            width:405,
            multiple:false,
            formatItem: function(row) {//显示格式
                return "<span style='width:100px'>"+row.BOOK_ISBN+"</span><span style='width:80px'>"+row.BOOK_ID+"</span><span style='width:200px'>"+row.BOOK_NAME+"</span>";
            },
            formatMatch: function(row) {//以什么数据作为搜索关键词,可包括中文,
                return row.BOOK_NAME+"("+row.BOOK_ISBN+")"+"("+row.BOOK_ID+")";

            },
            formatResult: function(row) {//返回结果
                return row.key;
            }
        });
        bookIdCondition.result(function(event, row) {
            bookIdCondition.data("BOOK_ID",row.BOOK_ID);
        });

        bookIdInputWin.autocomplete(BOOK_LIST, {
            minChars: 0,//最少输入字条
            max: 12,
            autoFill: false,//是否选多个,用","分开
            mustMatch: false,//是否全匹配, 如数据中没有此数据,将无法输入
            matchContains: true,//是否全文搜索,否则只是前面作为标准
            scrollHeight: 300,
            width:405,
            multiple:false,
            formatItem: function(row) {//显示格式
                return "<span style='width:100px'>"+row.BOOK_ISBN+"</span><span style='width:80px'>"+row.BOOK_ID+"</span><span style='width:200px'>"+row.BOOK_NAME+"</span>";
            },
            formatMatch: function(row) {//以什么数据作为搜索关键词,可包括中文,
                return row.BOOK_NAME+"("+row.BOOK_ISBN+")"+"("+row.BOOK_ID+")";
            },
            formatResult: function(row) {//返回结果
                return row.key;
            }
        });
        bookIdInputWin.result(function(event, row) {
            var cc=false;
            selectedBooks.find("tr").each(function () {
                var book = $(this).data("BOOK");
                if(book&&row.BOOK_ISBN===book.BOOK_ISBN){
                    cc=true;
                }
            });
            if(cc)return;

            var tr=$("<tr>");
            tr.data("BOOK",row);
            var td1=$("<td>");
            td1.css({
                "width":"100px",
                "text-align":"center"
            });
            td1.text(row.BOOK_ID);
            tr.append(td1);

            var td2=$("<td>");
            td2.css({
                "text-align":"center"
            });
            td2.text(row.BOOK_NAME);
            tr.append(td2);

            var td3=$("<td>");
            td3.css({
                "width":"60px",
                "text-align":"center"
            });
            if(row.BOOK_STATE===1){
                td3.text("可借");
            }else{
                td3.text("已借出");
            }

            tr.append(td3);

            var td4=$("<td>");
            td4.css({
                "width":"100px",
                "text-align":"center"
            });
            var button = $("<a>");
            button.addClass("button");
            button.css({
                "background-color":"#EEC900",
                "margin-left": "10px"
            });
            button.text("删除");
            button.click(function () {
                $(this).parent().parent().remove();
            });
            td4.append(button);
            tr.append(td4);
            selectedBooks.append(tr);
        });
    }
});
// 获取读者列表
JS.Ajax.request({
    url: "../../reader/listAll",
    success: function (result) {
        for (var i = 0; i < result.DATA.length; i++) {
            READER_LIST.push(result.DATA[i]);
        }
        // 条件输入框
        readerIdcondition.autocomplete(READER_LIST, {
            minChars: 0,					//最少输入字条
            max: 12,
            autoFill: false,				//是否选多个,用","分开
            mustMatch: false,				//是否全匹配, 如数据中没有此数据,将无法输入
            matchContains: true,			//是否全文搜索,否则只是前面作为标准
            scrollHeight: 220,
            width:270,
            multiple:false,
            formatItem: function(row) {					//显示格式
                return "<span style='width:140px'>"+row.READER_ID+"</span><span style='width:120px'>"+row.READER_NAME+"</span>";
            },
            formatMatch: function(row) {				//以什么数据作为搜索关键词,可包括中文,
                return row.READER_NAME+"("+row.READER_ID+")";

            },
            formatResult: function(row) {						//返回结果
                return row.key;
            }
        });
        readerIdcondition.result(function(event, row) {
            readerIdcondition.data("READER_ID",row.READER_ID);
        });
        // 弹出框
        readerIdInputWin.autocomplete(READER_LIST, {
            minChars: 0,//最少输入字条
            max: 12,
            autoFill: false,//是否选多个,用","分开
            mustMatch: false,//是否全匹配, 如数据中没有此数据,将无法输入
            matchContains: true,//是否全文搜索,否则只是前面作为标准
            scrollHeight: 220,
            width:270,
            multiple:false,
            formatItem: function(row) {//显示格式
                return "<span style='width:140px'>"+row.READER_ID+"</span><span style='width:120px'>"+row.READER_NAME+"</span>";
            },
            formatMatch: function(row) {//以什么数据作为搜索关键词,可包括中文,
                return row.READER_NAME+"("+row.READER_ID+")";

            },
            formatResult: function(row) {//返回结果
                return row.key;
            }
        });
        readerIdInputWin.result(function(event, row) {
            READER_ID.val(row.READER_ID);
            READER_NAME.val(row.READER_NAME);
            READER_EMAIL.val(row.READER_EMAIL);
            READER_PHONE.val(row.READER_PHONE);
            readerIdInputWin.val("");
        });
    }
});

var table=new JS.Table("tablepanel",{
    url:"../../borrow/list",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'RECORD_ID'
    },{
        text: "图书名称",
        dataIndex: 'BOOK_NAME'
    },{
        text: "读者昵称",
        width: 100,
        dataIndex: 'READER_NAME'
    },{
        text: "借阅时间",
        width: 140,
        dataIndex: 'BORROW_TIME'
    },{
        text: "应还时间",
        width: 140,
        dataIndex: 'BACK_TIME'
    },{
        text: "借阅操作员",
        width: 100,
        dataIndex: 'BORROW_OPERATOR_NAME'
    },{
        text: "借阅状态",
        width: 80,
        dataIndex: 'STATE',
        renderer:function(state,td){
            if(state===1){
                td.text("借出");
            }else if(state===2){
                td.text("归还");
            }else{
                td.text("");
            }
        }
    },{
        text: "归还时间",
        width: 140,
        dataIndex: 'GIVEBACK_TIME'
    },{
        text: "归还操作员",
        width: 100,
        dataIndex: 'GIVEBACK_OPERATOR_NAME'
    },{
        text: "操作",
        width: 160,
        dataIndex: 'RECORD_ID',
        align:"left",
        renderer:function(recordId,td,record){
            var backBtn=$("<a>");
            backBtn.addClass("button");
            backBtn.text("还书");
            backBtn.bind("click",record,function(e){
                if(e.data.STATE===1){
                    backBooks([e.data.RECORD_ID]);
                }else if(e.data.STATE===2){
                    JS.MessageBox.alert(e.data.BOOK_NAME+"已还书");
                }
            });
            td.append(backBtn);
        }
    }]
});
//设置每页数量
table.setPageSize(20);
table.create();
//还书函数
function backBooks(recordIds){
    JS.Ajax.request({
        url:"../../borrow/back",
        data:{
            RECORD_ID_LIST:recordIds
        },
        success:function(){
            table.load();
            JS.MessageBox.alert("还书成功");
        }
    });
}
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("BOOK_ID",bookIdCondition.data("BOOK_ID"));
    table.setParam("READER_ID",readerIdcondition.data("READER_ID"));
    table.setPageNo(1);
    table.load();
    this.blur();
});
//借书按钮绑定事件
$("#borrowBtn").click(function(){
    JS.MaskLayer.show();
    $("#dialog .title").text("借书");
    $("#dialog").css("display","");
});
//还书按钮绑定事件
$("#backBtn").click(function(){
    var records=table.getChecked(true);
    var RECORD_ID_LIST=[];
    var str="";
    for(var i=0;i<records.length;i++){
        if(records[i].STATE===2){
            str+=records[i].BOOK_NAME+",";
        }else{
            RECORD_ID_LIST.push(records[i].RECORD_ID);
        }
    }
    if(str===""){
        backBooks(RECORD_ID_LIST);
    }else{
        JS.MessageBox.alert(str.substring(0,str.length-1)+"已还书");
    }
});
// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    READER_ID.val("");
    READER_NAME.val("");
    READER_EMAIL.val("");
    READER_PHONE.val("");
    selectedBooks.empty();
    readerIdInputWin.val("");
    bookIdInputWin.val("");
});
//加载第一页数据
table.setPageNo(1);
table.load();
//弹出框 保存按钮事件
$("#save").click(function () {
    if(READER_ID.val()===""||$.trim(READER_ID.val())===""){
        JS.MessageBox.alert("请输入读者信息");
        return;
    }
    var BOOK_LIST=[];
    selectedBooks.find("tr").each(function () {
        var book = $(this).data("BOOK");
        if(book){
            BOOK_LIST.push({
                BOOK_ISBN:book.BOOK_ISBN,
                BOOK_ID:book.BOOK_ID
            });
        }
    });
    if(BOOK_LIST.length===0){
        JS.MessageBox.alert("请选择图书");
    }else{
        JS.Ajax.request({
            url:"../../borrow/borrow",
            data:{
                READER_ID:READER_ID.val(),
                BOOK_LIST:BOOK_LIST
            },
            success:function(){
                JS.MessageBox.alert("借阅成功成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
});