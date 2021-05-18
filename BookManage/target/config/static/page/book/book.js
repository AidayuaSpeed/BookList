var bookcaseSign=false;
var bookcaseCache={};// 所有书架缓存
var bookTypeSign=false;
var bookTypeCache={};// 所有图书类型缓存

var table=new JS.Table("tablepanel",{
    url:"../../book/list",
    columns:[{
        text: "图书ISBN编码",
        width: 160,
        dataIndex: 'BOOK_ISBN'
    },{
        text: "图书名称",
        dataIndex: 'BOOK_NAME'
    },{
        text: "图书编码",
        width: 160,
        dataIndex: 'BOOK_ID'
    },{
        text: "图书作者",
        width: 100,
        dataIndex: 'BOOK_AUTHOR'
    },{
        text: "出版社",
        width: 100,
        dataIndex: 'PUBLISHER'
    },{
        text: "出版时间",
        width: 100,
        dataIndex: 'PUBLISHING_TIME'
    },{
        text: "图书价格",
        width: 80,
        dataIndex: 'BOOK_PRICE'
    },{
        text: "所属书架",
        width: 100,
        dataIndex: 'BOOKCASE_ID',
        renderer:function(bookcaseId,td){
            var bookcase=bookcaseCache[bookcaseId];
            if(bookcase){
                td.text(bookcase.BOOKCASE_NAME);
            }
        }
    },{
        text: "图书类型",
        width: 100,
        dataIndex: 'TYPE_ID',
        renderer:function(typeId,td){
            var bookType=bookTypeCache[typeId];
            if(bookType){
                td.text(bookType.TYPE_NAME);
            }
        }
    },{
        text: "图书状态",
        width: 60,
        dataIndex: 'BOOK_STATE'
    },{
        text: "创建时间",
        width: 160,
        dataIndex: 'CREATE_TIME'
    }]
});

//设置每页数量
table.setPageSize(20);
table.create();
//绑定查询按钮事件
$("#qryBtn").click(function(){
    table.setParam("BOOK_ISBN",$("#bookIsbnCondition").val());
    table.setParam("BOOK_ID",$("#bookIdCondition").val());
    table.setParam("BOOK_NAME",$("#bookNameCondition").val());
    table.setPageNo(1);
    table.load();
    this.blur();
});
//加载所有的书架
JS.Ajax.request({
    url:"../../bookcase/listAll",
    success:function(result){
        if(result.DATA){
            for(var i=0;i<result.DATA.length;i++){
                var bookcase=result.DATA[i];
                bookcaseCache[bookcase.BOOKCASE_ID]=bookcase;
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
//加载所有的图书类型
JS.Ajax.request({
    url:"../../booktype/listAll",
    success:function(result){
        if(result.DATA){
            for(var i=0;i<result.DATA.length;i++){
                var booktype=result.DATA[i];
                bookTypeCache[booktype.TYPE_ID]=booktype;
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