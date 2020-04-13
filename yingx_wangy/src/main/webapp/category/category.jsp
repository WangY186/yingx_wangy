<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
        $(function(){
            pageInit();
            $("#delBtn").click(function(){
                delCate();
            })
            $("#addBtn").click(function(){
                addData();
            })
            $("#addSubmit").click(function(){
                add();
            })

        });
        function pageInit(){
            $("#cateTable").jqGrid(
                {
                    url:"${path}/category/showAll?levels=1",
                    editurl:"${path}/category/option",
                    styleUI:"Bootstrap",
                    autowidth:true,
                    height:"auto",
                    datatype:"json",
                    pager:"#catePage",
                    rowNum:5,
                    rowList:[5,10,20],
                    viewrecords:true,
                    colNames : [ 'ID', '类别名称', '级别'],
                    colModel : [
                        {name : 'id', width : 55},
                        {name : 'cateName',width : 90,editable:true},
                        {name : 'levels',width : 100}
                    ],
                    subGrid : true,
                    subGridRowExpanded : function(subgridId, rowId) {
                        addSubGrid(subgridId, rowId);
                    }
                }).jqGrid('navGrid', '#catePage', {edittext : '修改', addtext : '添加', deltext : '删除'},
                {closeAfterEdit:true},
                {closeAfterAdd:true},
                {
                   closeAfterDel:true,
                   afterSubmit:function(response) {
                       //向警告框添加内容
                       $("#showMsg").html(response.responseJSON.message);
                       //显示警告框
                       $("#delMsg").show();
                       //展示几秒后关闭
                       setTimeout(function(){
                           $("#delMsg").hide();
                       },3000);
                       return "ok";
                   }
                });
        }
        function addSubGrid(subgridId, rowId){
            var subgridTableId, pagerId;
            subgridTableId = subgridId + "Table";
            pagerId = subgridId+"Page";
            $("#" + subgridId).html(
                "<table id='" + subgridTableId + "' class='scroll'></table>" +
                "<div id='" + pagerId + "' class='scroll'></div>");
            $("#" + subgridTableId).jqGrid(
                {
                    url : "${path}/category/showAll?levels=2&parentId=" + rowId,
                    editurl:"${path}/category/option?parentId="+rowId,
                    styleUI:"Bootstrap",
                    autowidth:true,
                    height:"auto",
                    datatype:"json",
                    pager:"#"+pagerId,
                    rowNum:3,
                    rowList:[3,5,10],
                    viewrecords:true,
                    colNames : [ 'ID', '类别名称', '级别','上级类别'],
                    colModel : [
                        {name : 'id', width : 55},
                        {name : 'cateName',width : 90,editable:true},
                        {name : 'levels',width : 100},
                        {name : 'parentId',width : 100}
                    ]
                }).jqGrid('navGrid',
                "#" + pagerId, {
                    edittext : '修改',
                    addtext : '添加',
                    deltext : '删除',
                },{closeAfterEdit:true},{closeAfterAdd:true},
                {
                    closeAfterDel:true,
                    afterSubmit:function(response) {
                        //向警告框添加内容
                        $("#showMsg").html(response.responseJSON.message);
                        //显示警告框
                        $("#delMsg").show();
                        //展示几秒后关闭
                        setTimeout(function(){
                            $("#delMsg").hide();
                        },3000);
                        return "ok";
                    }
                }
                );
        }
        function delCate(){
            var rowId = $("#cateTable").jqGrid("getGridParam","selrow");
            if (rowId != null){
                $.post("${path}/category/option",{"id":rowId,"oper":"del"},function(data){
                    //向警告框添加内容
                    $("#showMsg").html(data.message);
                    //显示警告框
                    $("#delMsg").show();
                    //展示几秒后关闭
                    setTimeout(function(){
                        $("#delMsg").hide();
                    },3000);
                },"json")
            }
            else
                alert("请选择删除的数据");
        }
        //给添加模态框中的select动态添加数据
       function addData(){
            $.ajax({
                url:"${path}/category/queryOne",
                dataType:"json",
                type:"post",
                success:function(data){
                    $.each(data,function(index,category){
                        $("#parentId").append("<option value='"+category.id+"'>"+category.cateName+"</option>")
                    })
                }
            })
       }

       //自定义添加数据
    function add(){
        var cateName = $("#cateName").val();
        var parentId=$("#parentId").val();
        $.ajax({
            url:"${path}/category/option",
            data:{"cateName":cateName,"parentId":parentId,"oper":"add"},
            type:"post",
            success:function(){
                $("#cateTable").trigger("reloadGrid");
            }
        })
        $("#myModal").modal('hide');
    }
</script>
<div class="panel panel-success">
    <!-- 面板头部信息 -->
    <div class="panel-heading"><h2>类别信息</h2></div>
    <br>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">类别管理</a></li>
    </ul>
    <div id="delMsg" class="alert alert-warning alert-dismissible" role="alert" style="width:300px;display: none">
        <span id="showMsg"/>
    </div>
    <br>
    <div class="panel panel-body">
        <button class="btn btn-danger" id="delBtn">删除类别</button>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="addBtn">
            添加类别
        </button>
        <!-- 修改模态框-->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">添加类别</h4>
                    </div>
                    <div class="modal-body">
                        <form id="addForm" method="post" class="form-horizontal">
                                <div class="form-group">
                                    <label for="cateName" class="col-sm-2 control-label">类别名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="cateName" placeholder="类别名称" name="cateName">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">上级类别</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="parentId" name="parentId">
                                           <option></option>
                                        </select>
                                    </div>
                                </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="addSubmit">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--展示用户信息表-->
    <table  id="cateTable">
    </table>
    <div id="catePage"></div>
</div>


