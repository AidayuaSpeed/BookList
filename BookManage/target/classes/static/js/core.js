var JS = window.JS = JS || {};
(function(){
    /**当前登录用户信息*/
    JS.USER={
        USER_ID:undefined,
        USER_NAME:undefined,
        TOKEN:undefined,
        USER_ADMIN:undefined
    };
    
    /**主界面*/
    function View(viewId,option){
        this.$view = $("#"+viewId);
        this.customConfig=option||{};
    }
    /**主界面默认配置*/
    var config={
        headerHeight:60,// 标题高度
        menuWidth:220,//菜单宽度
        logo:"image/logo.png",// logo 图标
        title:"JAVA图书借阅管理系统", // 系统标题
        menus:{//菜单配置 
            
        }
    };
    View.prototype={
        constructor: View,
        // 初始化配置
        _initConfig: function _initConfig() {
            var target = {};
            this.config = $.extend(true, target, config, this.customConfig);
        },
        // 初始化 DOM
        _initDom: function _initDom() {
            var _this = this,$headerElem,$menuElem,$menuListElem;
            //标题展示区域 
            $headerElem=$("<div>");
            $headerElem.css({
                "width":"100%",
                "height":_this.config.headerHeight,
                "line-height":_this.config.headerHeight+"px",
                "border-bottom": "1px solid rgba(255,255,255,.15)"
            });
            // logo 展示
            var logoImg=$("<img src='' alt=''>");
            logoImg.css({
                "width": 40,
                "height": 40,
                "float": "left",
                "margin-left": "30px",
                "margin-top": (_this.config.headerHeight-40)/2+"px",
                "margin-right": "10px"
            });
            logoImg.attr("src",_this.config.logo);
            $headerElem.append(logoImg);
            // 标题
            var titleLabel=$("<a>");
            titleLabel.css({
                "height": _this.config.headerHeight,
                "line-height": _this.config.headerHeight+"px",
                "font-size": "18px",
                "float": "left"
            });
            titleLabel.text(_this.config.title);
            $headerElem.append(titleLabel);
            // 退出系统按钮
            var exitImg=$("<img src='' alt=''>");
            exitImg.css({
                "width": 20,
                "height": 20,
                "float": "right",
                "margin-top": (_this.config.headerHeight-20)/2+"px",
                "margin-right": "30px",
                "cursor":"pointer"
            });
            exitImg.attr("src","image/exit.png").attr("title","退出系统");
            exitImg.click(function(){
                location.reload();
            });
            $headerElem.append(exitImg);
            //欢迎登录系统
            var label=$("<a>");
            label.css({
                "height": _this.config.headerHeight,
                "line-height": _this.config.headerHeight+"px",
                "margin-right": "20px",
                "font-size": "12px",
                "float": "right"
            });
            label.text("hi~, "+JS.USER.USER_NAME);
            $headerElem.append(label);
            // 菜单和框架共有部分
            var $centerPanel=$("<div>");
            $centerPanel.css({
                "width":"100%",
                "height":"calc(100vh - "+(_this.config.headerHeight+1)+"px)"
            });
            //功能菜单区域 
            $menuElem=$("<div>");
            $menuElem.css({
                "width":_this.config.menuWidth,
                "max-width": _this.config.menuWidth,
                "height":"calc(100vh - "+(_this.config.headerHeight+1)+"px)",
                "float": "left",
                "border-right": "1px solid rgba(255,255,255,.15)"
            });
            //列表所在元素
            $menuListElem=$("<ul>");
            $menuListElem.css({
                "width":_this.config.menuWidth,
                "max-width": _this.config.menuWidth,
                "height":"calc(100vh - "+(_this.config.headerHeight+11)+"px)",
                "padding-left": "0",
                "list-style": "none",
                "padding-top": "10px"
            });
            $menuListElem.addClass("menu");
            $menuElem.append($menuListElem);
            $centerPanel.append($menuElem);
            
            // iframe 界面
            var $contentElem=$("<div>");
            $contentElem.css({
                "width":"calc(100vw - "+(_this.config.menuWidth+1)+"px)",
                "height":"calc(100vh - "+(_this.config.headerHeight+1)+"px)",
                "float": "left"
            });
            // 内容标题
            var $contentTitleElem=$("<div>");
            $contentTitleElem.css({
                "width":"calc(100vw - "+(_this.config.menuWidth+1)+"px)",
                "height":"40px",
                "border-bottom": "1px solid rgba(255,255,255,.15)"
            });
            var $title=$("<div>");
            $title.css({
                "height":"40px",
                "line-height":"40px",
                "float": "left",
                "margin-left": "15px",
                "font-size":"14px"
            });
            $contentTitleElem.append($title);
            $contentElem.append($contentTitleElem);
            // 打开界面的容器
            var $containerElem=$("<div>");
            $containerElem.css({
                "width":"calc(100vw - "+(_this.config.menuWidth+1)+"px)",
                "height":"calc(100vh - "+(_this.config.headerHeight+42)+"px)"
            });
            $contentElem.append($containerElem);
            
            $centerPanel.append($contentElem);
            this.$view.append($headerElem).append($centerPanel);
            // 记录属性
            this.$menuListElem = $menuListElem;
            this.$title = $title;
            this.$containerElem = $containerElem;
        },
        _initMenu:function _initMenu(){
            var _this=this;
            JS.Ajax.request({
                url:"menu",
                success:function(result){
                    var menuList=result.DATA;
                    menuList.forEach(function (menu) {
                        var li=$("<li>");
                        var a=$("<a>");
                        a.css({
                            "width":_this.config.menuWidth-40,
                            "padding": "18px 20px",
                            "cursor":"pointer"
                        });
                        a.text(menu.MENU_NAME);
                        li.bind('click',menu,function(e){
                            _this.$menuListElem.find("li").removeClass("active");
                            $(this).addClass("active");
                            _this.openMenu(e.data);
                        });
                        li.append(a);
                        _this.$menuListElem.append(li);
                    });
                    _this.$menuListElem.find("li:first").trigger("click");
                }
            });
        },
        /**打开菜单*/
        openMenu:function openMenu(menu){
            this.$title.text(menu.MENU_NAME);
            var iframe = $("<iframe>");
            iframe.attr("frameborder",0).attr("height","100%").attr("width","100%").attr("scrolling","no");
            iframe.attr("marginheight","0").attr("marginwidth","0");
            iframe.attr("name",menu.MENU_ID);
            iframe.attr("src",menu.MENU_URL);
            this.$containerElem.find("iframe").remove();
            this.$containerElem.append(iframe);
        },
        /**创建主界面*/
        create: function create() {
            // 初始化配置
            this._initConfig();
            // 初始化 DOM
            this._initDom();
            // 初始化菜单
            this._initMenu();
        }
    };
    /**生成表格*/
    function Table(tableId,option){
        this.$tablePanel = $("#"+tableId);
        this._option=option;
        /**查询参数*/
        this.params={};
        this.url=option.url;
    }
    Table.prototype={
        constructor: Table,
        /**设置页码*/
        setPageNo:function setPageNo(pageNo){
            this.pageNo=pageNo;
        },
        /**设置总条数*/
        setTotalNum:function setTotalNum(totalNum){
            this.totalNum=totalNum;
        },
        /**设置每页大小*/
        setPageSize:function setPageSize(pageSize){
            this.pageSize=pageSize;
        },
        /**初始化 DOM*/
        _initDom: function _initDom(){
            //表头所在div
            var $tableThead=$("<div>");
            $tableThead.css({
                "width" :"100%",
                "height":"40px",
                "background": "rgba(45, 48, 53,.5)"
            });
            this.$tablePanel.append($tableThead);
            //内容展示区域
            var $tableTbody=$("<div>");
            $tableTbody.css({
                "width" :"100%",
                "height": "calc(100vh - 140px)"
            });
            // 表格列表
            var $tableList=$("<table>");
            $tableList.css({
                "width" :"100%"
            });
            $tableTbody.append($tableList);
            // 分页
            var $pageElem=$("<div>");
            $pageElem.css({
                "width" :"100%",
                "height": "50px",
                "margin-bottom": "20px",
                "margin-top": "20px",
                "text-align": "center"
            });

            $tableTbody.append($pageElem);
            
            this.$tablePanel.append($tableTbody);
            
            // 记录属性
            this.$tableThead=$tableThead;
            this.$tableList=$tableList;
            this.$tableTbody=$tableTbody;
            this.$pageElem=$pageElem;

            $tableTbody.niceScroll({
                cursorcolor: "#B5B5B5",
                cursoropacitymax: 1,
                touchbehavior: false,
                cursorwidth: "5px",
                cursorborder: "0",
                cursorborderradius: "5px",
                autohidemode: "cursor"
            });
        },
        /**初始化所有列*/
        _initColumns:function _initColumns(){
            if(!this._option.columns||this._option.columns.length===0){
                throw "请添加列数据配置";
            }
            this.columns=[];
            for(var i=0;i<this._option.columns.length;i++){
                var column=new Column(this._option.columns[i]);
                column.setTable(this);
                this.columns.push(column);
            }
        },
        /**初始化表头*/
        _initHeader:function _initHeader(){
            var table=$("<table>");
            table.css({
                "width" :"100%",
                "height":"40px"
            });
            var thead=$("<thead>");
            var tr=$("<tr>");
            this.columns.forEach(function (column) {
                tr.append(column.getTh());
            });
            thead.append(tr);
            table.append(thead);
            this.$tableThead.append(table);
        },
        /**创建表格*/
        create: function create(){
            // 初始化 DOM
            this._initDom();
            //初始化列
            this._initColumns();
            // 初始化表头
            this._initHeader();
        },
        /**设置查询参数*/
        setParam:function setParam(key,value){
            this.params[key]=value;
        },
        /**加载数据*/
        load:function load(){
            var _this=this;
            this.params.PAGE_NO=this.pageNo;
            this.params.PAGE_SIZE=this.pageSize;
            JS.Ajax.request({
                url:this.url,
                data:this.params,
                success:function(result){
                    _this._renderer(result);
                    _this.setPageNo(result.PAGE_NO);
                    _this.setTotalNum(result.TOTAL_NUM);
                    _this.pageRefresh();
                    if(_this._option.afterrender){
                        _this._option.afterrender();
                    }
                }
            });
        },
        /**渲染展示数据*/
        _renderer:function _renderer(result) {
            this.$tableThead.find("input[type='checkbox']").prop("checked",false);
            this.$tableList.empty();
            var list=result.DATA;
            if(list){
                for(var i=0;i<list.length;i++){
                    var record=list[i];
                    var tr=$("<tr>");
                    tr.css({
                        "width" :"100%",
                        "height":this._option.trHeight===undefined?"50px":this._option.trHeight
                    });
                    this.columns.forEach(function (column) {
                        tr.append(column.getTd(record));
                    });
                    this.$tableList.append(tr);
                }
                this.$tableList.append($("<tr>"));
            }
            this.$tableTbody.getNiceScroll(0).resize();
            this.$tableTbody.getNiceScroll(0).doScrollTop(0);
        },
        /**
         * 分页显示
         * 最多显示10个分页页码按钮
         * 以7为分割
         * */
        pageRefresh:function(){
            var _this=this;
            this.$pageElem.empty();
            var pagePanel=$("<div>");
            pagePanel.addClass("page");
            pagePanel.css({
                "max-width": "800px",
                "text-align": "center",
                "margin-bottom": "30px",
                "display": "inline-block",
                "user-select": "none"
            });
            /**总页数*/
            var pageNum=this.totalNum%this.pageSize===0?(this.totalNum/this.pageSize):(parseInt((this.totalNum/this.pageSize)+1+""));
            /**页码开始页  结束页*/
            var startPageNo,endPageNo;
            if(this.pageNo<=8){
                startPageNo=1;
            }else{
                startPageNo=this.pageNo-8;
            }
            if(pageNum<=10){
                startPageNo=1;
                endPageNo=pageNum;
            }else{
                endPageNo=this.pageNo+1;
                if(endPageNo>pageNum){
                    startPageNo=pageNum-9;
                    endPageNo=pageNum;
                }else{
                    if(endPageNo<10){
                        endPageNo=10;
                    }
                }
            }
            // 首页
            var pgFirst=$("<a>");
            pgFirst.addClass("pg-first");
            pgFirst.text("<<");
            pgFirst.click(function () {
                if(_this.pageNo!==1){
                    _this.setPageNo(1);
                    _this.load();
                }
            });
            pagePanel.append(pgFirst);
            //上一页
            var pgPrev=$("<a>");
            pgPrev.addClass("pg-pg-prev");
            pgPrev.text("<");
            pgPrev.click(function(){
                if(_this.pageNo>1){
                    _this.setPageNo(_this.pageNo-1);
                    _this.load();
                }
            });
            pagePanel.append(pgPrev);
            
            for(var i=startPageNo;i<=endPageNo;i++){
                if(i===this.pageNo){
                    pagePanel.append($('<span class="current">'+i+'</span>'));
                }else{
                    var a=$("<a>");
                    a.text(i);
                    a.attr("page-id",i);
                    a.click(function(){
                        _this.setPageNo($(this).attr("page-id")-0);
                        _this.load();
                    });
                    pagePanel.append(a);
                }
            }
            //下一页
            var pgNext=$("<a>");
            pgNext.addClass("pg-next");
            pgNext.text(">");
            pgNext.click(function(){
                if(pageNum>1&&_this.pageNo<pageNum){
                    _this.setPageNo(_this.pageNo+1);
                    _this.load();
                }
            });
            pagePanel.append(pgNext);
            //尾页
            var pgLast=$("<a>");
            pgLast.addClass("pg-last");
            pgLast.text(">>");
            pgLast.click(function(){
                if(_this.pageNo!==pageNum){
                    _this.setPageNo(pageNum);
                    _this.load();
                }
            });
            pagePanel.append(pgLast);

            this.$pageElem.append(pagePanel);
        },
        /**修改所有checkbox 状态*/
        checkedAll:function(checked){
            this.$tableList.find("input[type='checkbox']").prop("checked",checked);
        },
        /**关联 标题上的复选框*/
        checked:function(){
            if(this.$tableList.find("input[type='checkbox']").length===this.$tableList.find("input[type='checkbox']:checked").length){
                this.$tableThead.find("input[type='checkbox']").prop("checked",true);
            }else{
                this.$tableThead.find("input[type='checkbox']").prop("checked",false);
            }
        },
        /**
         * 获取输入框被勾选 或 未勾选的节点集合
         * checked = true 表示获取 被勾选 的节点集合
         * checked = false 表示获取 未勾选 的节点集合
         * 
         * 返回值Array(JSON)
         * 返回全部符合要求的节点集合 Array
         * */
        getChecked:function(checked){
            var list=this.$tableList.find("input[type='checkbox']");
            var array=[];
            for(var i=0;i<list.length;i++){
                var checkbox=$(list[i]);
                if(checkbox.prop("checked")===checked){
                    array.push(checkbox.data("record"));
                }
            }
            return array;
        }
    };
    /**表格列*/
    function Column(option){
        /** 
         * 列类型  text 文件
         *         checkbok 复选框
         * */
        var type=option.type||"text";
        var text=option.text||"";
        var width=option.width||"";
        var dataIndex=option.dataIndex||"";
        var align=option.align||"center";
        /**
         * 自定义渲染器
         * 第一个参数  列对象 td 
         * 第二个参数  当前列数据
         * */
        this.renderer=option.renderer;
        
        this.setTable=function(table){
            this.table=table;
        };
        
        this.getText=function getText(){
            return text;
        };
        /**返回 th 表头*/
        this.getTh=function getTh(){
            var th=$("<th>");
            th.css("padding","0px 5px");
            if(width){
                th.attr("width",width);
            }
            switch (type) {
                case "text": th.text(text);break;
                case "checkbok":
                    var checkbok=$("<input>");
                    checkbok.attr("type","checkbox");
                    checkbok.click(function(){
                        var checked=$(this).prop("checked");
                        if(table){
                            table.checkedAll(checked);
                        }
                    });
                    th.append(checkbok);
                    break;
            }
            return th;
        };
        /**返回表格td 数据*/
        this.getTd=function getTd(record){
            var td=$("<td>");
            td.css("padding","0px 5px");
            if(width){
                td.attr("width",width);
            }
            if(align){
                td.attr("align",align);
            }
            if(this.renderer){
                this.renderer(record[dataIndex],td,record);
            }else{
                switch (type) {
                    case "text": td.text(record[dataIndex]);break;
                    case "checkbok":
                        var checkbok=$("<input>");
                        checkbok.attr("type","checkbox");
                        checkbok.data("record",record);
                        checkbok.click(function(){
                            $(this).prop("checked");
                            if(table){
                                table.checked();
                            }
                        });
                        td.append(checkbok);
                        break;
                }
            }
            return td;
        }
    }
    /**
     * 消息确认弹框
     * option.title 弹出框标题
     * option.content 弹出框内容
     * option.confirm 确认回调函数
     * option.scope option.confirm函数指向this
     * */
    function MesaageWin(option){
        this.width=460;
        this.height=300;
        this.title=option.title;
        this.content=option.content;
        this.confirm=option.confirm;
        this.scope=option.scope;
        
        this._initWin();
        this._initTitlePanel();
        this._initCenterPanel();
        this._initButtonPanel();
    }
    MesaageWin.prototype={
        constructor: MesaageWin,
        /**初始化*/
        _initWin:function _initWin(){
            var $win=$("<div>");
            $win.addClass("dialog");
            $win.css({
                "display":"none",
                "width":this.width,
                "height":this.height,
                "margin-top":"-"+this.height/4*3+"px",
                "margin-left":"-"+this.width/2+"px",
                "background-color":"#EBF2F9"
            });
            $("body").append($win);
            this.$win=$win;
        },
        /**初始化标题栏*/
        _initTitlePanel:function _initTitlePanel(){
            var _this=this;
            var titlePanel=$("<div>");
            titlePanel.css({
                "width":this.width,
                "height":30,
                "line-height":"30px"
            });
            /**标题*/
            var title = $("<span>");
            title.addClass("title");
            title.text(this.title);
            titlePanel.append(title);
            /**关闭按钮*/
            var close=$("<div>");
            close.addClass("close");
            close.html("×");
            close.click(function(){
                _this.close();
            });
            titlePanel.append(close);
            
            this.$win.append(titlePanel);
        },
        /**初始化内容*/
        _initCenterPanel:function _initCenterPanel(){
            var centerDiv=$("<div>");
            centerDiv.css({
                "width":this.width,
                "height":this.height-100,
                "line-height":(this.height-100)+"px",
                "text-align": "center",
                "font-size": "18px",
                "color":"#2c3133"
            });
            centerDiv.text(this.content);
            this.$win.append(centerDiv);
        },
        /**初始化按钮*/
        _initButtonPanel:function _initButtonPanel(){
            var _this=this;
            var buttonDiv=$("<div>");
            buttonDiv.css({
                "width":this.width,
                "height":70
            });

            var colseButton=$("<button>");
            colseButton.css({
                "background-color":"#696969",
                "margin-top":"10px",
                "margin-right":"15px",
                "float": "right"
            });
            colseButton.text("取消");
            colseButton.click(function(){
                _this.close();
            });
            buttonDiv.append(colseButton);

            var saveButton=$("<button>");
            saveButton.css({
                "margin-top":"10px",
                "margin-right":"15px",
                "float": "right"
            });
            saveButton.text("确定");
            saveButton.click(function(){
                _this.confirmEvent();
            });
            buttonDiv.append(saveButton);

            this.$win.append(buttonDiv);
        },
        /**展示弹出框*/
        show:function show(){
            JS.MaskLayer.show();
            this.$win.css("display","");
        },
        /**隐藏弹出框*/
        hidden:function hidden(){
            this.$win.css("display","none");
            JS.MaskLayer.hidden();
        },
        /**关闭按钮和右上角X的 事件*/
        close:function close(){
            this.hidden();
            this.$win.remove();
        },
        /**确认按钮事件*/
        confirmEvent:function confirmEvent() {
            if(this.scope){
                if(this.confirm)this.confirm.call(this.scope);
            }else{
                if(this.confirm)this.confirm.call(this);
            }
            this.close();
        }
    };
    JS.View=View;
    JS.Table=Table;
    JS.Column=Column;
    JS.MesaageWin=MesaageWin;

    /**用于处理所有的网络请求*/
    JS.Ajax= {
        request: function (option) {
            $.ajax({
                type : option.type===undefined?"POST":option.type,
                url : option.url,
                data:JSON.stringify(option.data),
                contentType: "application/json",
                dataType: "json",
                async:option.async===undefined?true:option.async,
                success:function(result){
                    if(result.SUCCESS){
                        option.success(result);
                    }else{
                        if(option.error){
                            option.error(result);
                        }else{
                            if(result.RETURN_MSG){
                                JS.MessageBox.alert(result.RETURN_MSG);
                            }
                        }
                    }
                },
                error:function(result){
                    if(result){
                        var json=$.parseJSON(result.responseText);
                        if(json){
                            JS.MessageBox.alert(json.message);
                        }
                    }
                }
            });
        }
    };
    /**告警弹框*/
    JS.MessageBox={
        /**
         * 告警提示框
         * 第一个参数是提示信息
         * 第二个(可选)参数是显示的时间，默认2000
         * */
        timer: undefined,
        alert:function(msg, time){
            window.clearTimeout(this.timer);
            var alertPanel=$("#alertPanel");
            alertPanel.text(msg);
            var width = alertPanel.width();
            alertPanel.css({
                'margin-left': -(width+30) / 2 + 'px',
                "display": ""
            });
            this.timer = setTimeout(function() {
                alertPanel.css({
                    'display': 'none'
                });
            }, time===undefined?2000:time);
        }
    };
    /**透明的遮罩层*/
    JS.MaskLayer={
        show:function(){
            $("#maskLayer").css("display","");
        },
        hidden:function(){
            $("#maskLayer").css("display","none");
        }
    };
    /**界面加载完成执行函数
     * 1.账户输入框 密码输入框 绑定回车事件
     * 2.绑定登录按钮事件
     * 3.禁止右键事件
     * */
    JS.load=function(){
        //1.账户输入框 密码输入框 绑定回车事件
        $("#userId").keypress(function(e){
            if(e.which===13){
                $("#userPwd").focus();
            }
        });
        $("#userPwd").keypress(function(e){
            if(e.which===13){
                $("#loginButton").trigger("click");
            }
        });
        //2.绑定登录按钮事件
        $("#loginButton").click(function(){
            var userId=$("#userId").val();
            var userPwd=$("#userPwd").val();
            if(userId===""||userPwd===""){
                JS.MessageBox.alert("请输入账号或者密码");
            }else{
                JS.Ajax.request({
                    url:"login",
                    data:{
                        USER_ID:userId,
                        USER_PWD:userPwd
                    },
                    success:function(result){
                        $("#login").css("display","none");
                        
                        JS.USER.USER_ID=result.USER_ID;
                        JS.USER.USER_NAME=result.USER_NAME;
                        JS.USER.TOKEN=result.TOKEN;
                        JS.USER.USER_ADMIN=result.USER_ADMIN;
                        //初始化主界面
                        window.view=new View("view",{});
                        window.view.create();
                        $("#view").css("display","");
                    }
                });
            }
        });
        //3.禁止右键事件
        $(document).bind('contextmenu', function() {
            return false;
        });
    };
})();