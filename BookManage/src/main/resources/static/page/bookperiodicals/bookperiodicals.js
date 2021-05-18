var BOOK_ISBN=$("#BOOK_ISBN");
var BOOK_IMAGE=$("#BOOK_IMAGE");
var BOOK_NAME=$("#BOOK_NAME");
var BOOK_AUTHOR=$("#BOOK_AUTHOR");
var PUBLISHER=$("#PUBLISHER");
var PUBLISHING_TIME=$("#PUBLISHING_TIME");
var BOOK_PRICE=$("#BOOK_PRICE");
var TYPE_ID=$("#TYPE_ID");
var BOOK_ISBN_HIDDEN=$("#BOOK_ISBN_HIDDEN");
var BOOK_ID=$("#BOOK_ID");
var BOOKCASE_ID=$("#BOOKCASE_ID");
var BOOK_INTRO=$("#BOOK_INTRO");
var BOOK_LOCATION=$("#BOOK_LOCATION");

var bookcaseSign=false;
var bookcaseCache={};// 所有书架缓存
var bookTypeSign=false;
var bookTypeCache={};// 所有书刊类型缓存



var table=new JS.Table("tablepanel",{
    url:"../../bookperiodicals/list",
    trHeight:"120px",
    columns:[{
        type:"checkbok",
        width: 60,
        dataIndex: 'BOOK_ISBN'
    },{
        text: "图书图片",
        width: 100,
        dataIndex: 'BOOK_IMAGE',
        renderer:function(bookImage,td){
            var img = $("<img src='' alt=''>");
            img.css({
                "width":"100px",
                "height":"120px"
            });
            img.attr("src",bookImage);
            td.append(img);
        }
    },{
        text: "图书ISBN",
        width: 120,
        dataIndex: 'BOOK_ISBN'
    },{
        text: "名称",
        width: 200,
        dataIndex: 'BOOK_NAME',
        renderer:function(bookName,td){
            td.text(bookName);
            td.attr("title",bookName);
        }
    },{
        text: "作者",
        width: 100,
        dataIndex: 'BOOK_AUTHOR',
        renderer:function(bookAuthor,td){
            td.text(bookAuthor);
            td.attr("title",bookAuthor);
        }
    },{
        text: "出版社",
        width: 100,
        dataIndex: 'PUBLISHER',
        renderer:function(publisher,td){
            td.text(publisher);
            td.attr("title",publisher);
        }
    },{
        text: "出版时间",
        width: 80,
        dataIndex: 'PUBLISHING_TIME'
    },{
        text: "价格",
        width: 50,
        dataIndex: 'BOOK_PRICE'
    },{
        text: "类型",
        width: 80,
        dataIndex: 'TYPE_ID',
        renderer:function(typeId,td){
            var bookType=bookTypeCache[typeId];
            if(bookType){
                td.text(bookType.TYPE_NAME);
            }
        }
    },{
        text: "录入时间",
        width: 160,
        dataIndex: 'CREATE_TIME'
    },{
        text: "总册数",
        width: 60,
        dataIndex: 'BOOK_NUM'
    },{
        text: "借出书数",
        width: 60,
        dataIndex: 'BORROW_NUM'
    },{
        text: "还剩书数",
        width: 60,
        dataIndex: 'BORROW_NUM',
        renderer:function(borrowNum,td,record){
            td.text(record.BOOK_NUM-borrowNum);
        }
    },{
        text: "操作",
        width: 200,
        dataIndex: 'BOOK_ID',
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
                BOOK_ISBN.val(e.data.BOOK_ISBN);
                BOOK_ISBN_HIDDEN.val(e.data.BOOK_ISBN);
                BOOK_ISBN.attr("disabled","disabled");
                BOOK_NAME.val(e.data.BOOK_NAME);
                BOOK_AUTHOR.val(e.data.BOOK_AUTHOR);
                PUBLISHER.val(e.data.PUBLISHER);
                PUBLISHING_TIME.val(e.data.PUBLISHING_TIME);
                BOOK_PRICE.val(e.data.BOOK_PRICE);
                BOOK_IMAGE.attr("src",e.data.BOOK_IMAGE);
                TYPE_ID.find("option[value='"+e.data.TYPE_ID+"']").attr("selected",true);

                var tbody=$("#bookTablepanel tbody");
                tbody.html("<tr></tr>");
                JS.Ajax.request({
                    url:"../../book/qryByBookIsbn",
                    data:{
                        BOOK_ISBN:e.data.BOOK_ISBN
                    },
                    success:function(result){
                        if(result.DATA&&result.DATA.length>0){
                            for(var i=0;i<result.DATA.length;i++){
                                addBook(result.DATA[i]);
                            }
                        }
                    }
                });

                JS.MaskLayer.show();
                $("#dialog .title").text("修改书刊");
                $("#addConfirmBtn").css("display","none");
                $("#updateConfirmBtn").css("display","");
                $("#dialog").css("display","");
                e.preventDefault();
            });
            td.append(button);
        }
    }],
    afterrender:function(){
    }
});

//设置每页数量
table.setPageSize(20);
table.create();


//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("BOOK_ISBN",$("#bookIsbnCondition").val());
    table.setParam("BOOK_NAME",$("#bookNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//添加按钮绑定事件
$("#addBtn").click(function(){
    JS.MaskLayer.show();
    $("#dialog .title").text("添加新书刊");
    $("#addConfirmBtn").css("display","");
    $("#updateConfirmBtn").css("display","none");

    BOOK_IMAGE.attr("src","");
    $("#dialog").css("display","");
});
var searchBtnFlag=false;
//网络检索按钮事件
$("#searchBtn").click(function () {
    if(searchBtnFlag)return;
    searchBtnFlag=true;
    $("#mask").css("display","");
    BOOK_ISBN_HIDDEN.val(BOOK_ISBN.val());
    JS.Ajax.request({
        url:"../../isbn/info",
        data:{
            BOOK_ISBN:BOOK_ISBN.val()
        },
        success:function(result){
            BOOK_IMAGE.attr("src",result.BOOK_IMAGE);
            BOOK_NAME.val(result.BOOK_NAME);
            BOOK_AUTHOR.val(result.BOOK_AUTHOR);
            PUBLISHER.val(result.PUBLISHER);
            PUBLISHING_TIME.val(result.PUBLISHING_TIME);
            BOOK_PRICE.val(result.BOOK_PRICE);
            BOOK_INTRO.val(result.BOOK_INTRO);
            searchBtnFlag=false;
            $("#mask").css("display","none");
        },
        error:function (result) {
            BOOK_ISBN_HIDDEN.val("");
            JS.MessageBox.alert(result.RETURN_MSG);
            searchBtnFlag=false;
            $("#mask").css("display","none");
        }
    })
});

// dialog 关闭事件
$("#dialog .close").click(function () {
    $("#dialog").css("display","none");
    JS.MaskLayer.hidden();
    // 重置输入框
    BOOK_ISBN_HIDDEN.val("");
    BOOK_ISBN.val("");
    BOOK_NAME.val("");
    BOOK_AUTHOR.val("");
    PUBLISHER.val("");
    PUBLISHING_TIME.val("");
    BOOK_PRICE.val("");
    TYPE_ID.find("option[value='-1']").attr("selected",true);
    BOOK_ISBN.removeAttr("disabled");
    BOOK_IMAGE.attr("src","");
    var tbody=$("#bookTablepanel tbody");
    tbody.html("<tr></tr>");
});

// dialog 添加按钮事件
$("#addConfirmBtn").click(function () {
    saveOrUpdate(true);
});
// dialog 修改按钮事件
$("#updateConfirmBtn").click(function () {
    saveOrUpdate(false);
});

//保存或者更新书刊类型 函数
function saveOrUpdate(isAdd){
    if(BOOK_ISBN.val()===""||$.trim(BOOK_ISBN.val())===""){
        JS.MessageBox.alert("请输入图书ISBN编码");
        BOOK_ISBN.css("border","1px solid red");
        return;
    }else {
        BOOK_ISBN.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BOOK_ISBN_HIDDEN.val()===""||$.trim(BOOK_ISBN_HIDDEN.val())===""){
        JS.MessageBox.alert("没有图书信息，请重新查询");
        BOOK_ISBN.css("border","1px solid red");
        return;
    }else {
        BOOK_ISBN.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BOOK_ISBN.val()!==BOOK_ISBN_HIDDEN.val()){
        JS.MessageBox.alert("IBSN已经改变，请重新查询");
        BOOK_ISBN.css("border","1px solid red");
        return;
    }else{
        BOOK_ISBN.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(TYPE_ID.val()==="-1"){
        JS.MessageBox.alert("请选择书刊类型");
        TYPE_ID.css("border","1px solid red");
        return;
    }else{
        TYPE_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    var BOOK_LIST=[];
    var tbody=$("#bookTablepanel tbody");
    tbody.find("tr").each(function () {
        var book = $(this).data("BOOK");
        if(book){
            BOOK_LIST.push(book);
        }
    });

    if(isAdd){
        JS.Ajax.request({
            url:"../../bookperiodicals/save",
            data:{
                BOOK_ISBN:BOOK_ISBN.val(),
                BOOK_NAME:BOOK_NAME.val(),
                BOOK_AUTHOR:BOOK_AUTHOR.val(),
                PUBLISHER:PUBLISHER.val(),
                PUBLISHING_TIME:PUBLISHING_TIME.val(),
                BOOK_PRICE:BOOK_PRICE.val(),
                TYPE_ID:TYPE_ID.val(),
                BOOK_IMAGE:BOOK_IMAGE.attr("src"),
                BOOK_LIST:BOOK_LIST,
                BOOK_INTRO:BOOK_INTRO.val()
            },
            success:function(){
                JS.MessageBox.alert("添加书刊成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    } else {
        JS.Ajax.request({
            url:"../../bookperiodicals/update",
            data:{
                BOOK_ISBN:BOOK_ISBN.val(),
                BOOK_NAME:BOOK_NAME.val(),
                BOOK_AUTHOR:BOOK_AUTHOR.val(),
                PUBLISHER:PUBLISHER.val(),
                PUBLISHING_TIME:PUBLISHING_TIME.val(),
                BOOK_PRICE:BOOK_PRICE.val(),
                TYPE_ID:TYPE_ID.val(),
                BOOK_IMAGE:BOOK_IMAGE.attr("src"),
                BOOK_LIST:BOOK_LIST
            },
            success:function(){
                JS.MessageBox.alert("修改书刊成功");
                table.load();
                $("#dialog .close").trigger("click");
            }
        });
    }
}

//加载所有的书架
BOOKCASE_ID.append($("<option value='-1'>请选择书架</option>"));
JS.Ajax.request({
    url:"../../bookcase/listAll",
    success:function(result){
        if(result.DATA){
            for(var i=0;i<result.DATA.length;i++){
                var bookcase=result.DATA[i];
                bookcaseCache[bookcase.BOOKCASE_ID]=bookcase;
                BOOKCASE_ID.append($("<option value='"+bookcase.BOOKCASE_ID+"'>"+bookcase.BOOKCASE_NAME+"</option>"));
            }
        }
        bookcaseSign=true;
        if(bookcaseSign&&bookTypeSign){
            //加载第一页数据
            table.setPageNo(1);
            table.load();
        }
    }
});

//加载所有的书刊类型
TYPE_ID.append($("<option value='-1'>请选择书刊类型</option>"));
JS.Ajax.request({
    url:"../../booktype/listAll",
    success:function(result){
        if(result.DATA){
            for(var i=0;i<result.DATA.length;i++){
                var booktype=result.DATA[i];

                bookTypeCache[booktype.TYPE_ID]=booktype;
                TYPE_ID.append($("<option value='"+booktype.TYPE_ID+"'>"+booktype.TYPE_NAME+"</option>"));
            }
        }
        bookTypeSign=true;
        if(bookcaseSign&&bookTypeSign){
            //加载第一页数据
            table.setPageNo(1);
            table.load();
        }
    }
});

$("#addBookBtn").click(function(){
    $("#bookDialog").css("display","");
    $("#bookAddConfirmBtn").css("display","");
    $("#bookUpdateConfirmBtn").css("display","none");
});

// dialog 关闭事件
$("#bookDialog .close").click(function () {
    $("#bookDialog").css("display","none");
    // 重置输入框
    BOOK_ID.val("");
    BOOKCASE_ID.find("option[value='-1']").attr("selected",true);
});

$("#bookAddConfirmBtn").click(function(){
    if(BOOK_ID.val()!==BOOK_ID.val()){
        BOOK_ID.css("border","1px solid red");
        return;
    }else{
        BOOK_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    if(BOOKCASE_ID.val()==="-1"){
        BOOKCASE_ID.css("border","1px solid red");
        return;
    }else{
        BOOKCASE_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    }
    var tbody=$("#bookTablepanel tbody");
    var cc=false;
    tbody.find("tr").each(function () {
        var book = $(this).data("BOOK");
        if(book&&BOOK_ID.val()===book.BOOK_ID){
            BOOK_ID.css("border","1px solid red");
            cc=true;
        }
    });
    if(cc)return;
    BOOK_ID.css("border","1px solid rgba(255, 255, 255, 0.15)");
    addBook({
        BOOK_ID:BOOK_ID.val(),
        BOOKCASE_ID:BOOKCASE_ID.val(),
        BOOK_STATE:1
    });
    $("#bookDialog .close").trigger("click");
});
function addBook(book) {
    var tbody=$("#bookTablepanel tbody");
    var tr=$("<tr>");
    tr.data("BOOK",book);
    tr.css({
        "height":"40px"
    });
    var td1=$("<td>");
    td1.css({
        "text-align": "center"
    });
    td1.text(book.BOOK_ID);
    tr.append(td1);
    var td2=$("<td>");
    td2.css({
        "text-align": "center"
    });
    td2.text("可借");
    tr.append(td2);
    var td3=$("<td>");
    td3.css({
        "text-align": "center"
    });
    td3.text(bookcaseCache[book.BOOKCASE_ID].BOOKCASE_NAME);
    tr.append(td3);
    var td4=$("<td>");
    td4.css({
        "text-align": "center"
    });
    td4.text(DateTimeFormatters(new Date()));
    tr.append(td4);
    var td5=$("<td>");
    var button=$("<a>");
    button.addClass("button");
    button.css({
        "background-color":"#EEC900",
        "margin-left": "10px"
    });
    button.text("删除");
    button.click(function () {
        $(this).parent().parent().remove();
    });
    td5.append(button);
    tr.append(td5);
    tbody.find("tr:last").before(tr);
}
/**
 * @return {string}
 */
function DateTimeFormatters(date) {
    if (date === undefined) {
        return "";
    }
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + (m<10?('0'+m):m) + '-' + (d<10?('0'+d):d);
}
$("#bookTablepanel").niceScroll({
    cursorcolor: "#B5B5B5",
    cursoropacitymax: 1,
    touchbehavior: false,
    cursorwidth: "5px",
    cursorborder: "0",
    cursorborderradius: "5px",
    autohidemode: "cursor"
});

//批量按钮绑定事件
$("#batchDelBtn").click(function(){
    var records=table.getChecked(true);
    var BOOK_ISBN_LIST=[];
    for(var i=0;i<records.length;i++){
        BOOK_ISBN_LIST.push(records[i].BOOK_ISBN);
    }
    if(BOOK_ISBN_LIST.length===0){
        JS.MessageBox.alert("请选择要删除的书刊");
        return;
    }
    var win=new JS.MesaageWin({
        title:"确认删除书刊",
        content:"确认删除"+BOOK_ISBN_LIST.length+"个书刊？",
        confirm:function(){
            JS.Ajax.request({
                url:"../../bookperiodicals/del",
                data:{
                    BOOK_ISBN_LIST:BOOK_ISBN_LIST
                },
                success:function(){
                    JS.MessageBox.alert("删除书刊成功");
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

//选中了对应书架中的内容
//根据bookcaseCache[tx].BOOKCASE_NUM)获取书柜最大书籍数量
//查询目前已放置位置数组，于[1,128]取差集
function getSelect(tx){
       var arrbookLocation=new Array();
       var arrMost=new Array();
      for (var i =1;i<=bookcaseCache[tx].BOOKCASE_NUM;i++)
      {
         arrMost.push(i);
      }
      JS.Ajax.request({
                     url:"../../bookcase/selectposition",
                     data:{
                         BOOKCASE_ID:tx
                     },
                     success:function(result){
                        if(result.DATA){

                                 for(var i=0;i<result.DATA.length;i++){
                                       var bookLocation=result.DATA[i];
                                       arrbookLocation.push(bookLocation.BOOK_LOCATION);
                                 }
                                 alert(arrMost);
                                 var book_location=document.getElementById('BOOK_LOCATION');
                                      book_location.options.length=0;
                                      let diff = arrMost.filter(function (val)
                                      { return arrbookLocation.indexOf(val) === -1 })

                                        for(var i=0;i<diff.length;i++)
                                        {
                                          book_location.options.add(new Option(diff[i],diff[i]));
                                        }
                             }
                     }
                 });


}
