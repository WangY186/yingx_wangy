<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

        $(function(){
            $("#myTable").jqGrid({
                url:"${path}/user/showAll",
                editurl:"${path}/user/option",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                datatype:"json",
                pager:"#pager",
                rowNum:3,
                rowList:[3,5,10],
                viewrecords:true,
                multiselect:true,
                colNames:["ID","头像","名字","简介","状态","手机号","注册时间","微信","性别","城市"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"headImg",editable:true,align:"center",edittype:'file',
                        formatter:function(cellvalue, options, rowObject){
                           return "<img src='${path}"+cellvalue+"'/>";
                        }
                    },
                    {name:"username",editable:true,align:"center"},
                    {name:"sign",editable:true,align:"center"},
                    {name:"status",align:"center",edittype:'button',
                        formatter:function(cellvalue, options, rowObject){
                           //判断0：冻结；1：解除冻结
                            if(cellvalue == "0"){
                                return "<button class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")'>冻结</button>";
                            }else{
                                return "<button class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\",\""+cellvalue+"\")'>解除冻结</button>";
                            }
                        }
                    },
                    {name:"phone",editable:true,editrules:{required:true},align:"center"},
                    {name:"createDate",align:"center"},
                    {name:"wechat",editable:true,align:"center"},
                    {name:"sex",editable:true,align:"center"},
                    {name:"area",editable:true,align:"center"}
                ]
            }).jqGrid("navGrid","#pager",{addtext:"添加",deltext:"删除",edittext:"修改",search:false},
                {
                    closeAfterEdit:true,
                    //在提交添加操作之后进行的操作
                    afterSubmit:function(response){
                        //进行文件上传以及图片路径修改
                        //response.responseText获取的是进行添加操作之后返回的添加的数据的id，用来进行修改图片路径的操作
                        $.ajaxFileUpload({
                            url:"${path}/user/upload",
                            // dataType:"JSON",
                            data:{id:response.responseText},
                            fileElementId:"headImg",//指定文件上传的input框name就是你添加操作的头像字段名
                            type:"post",
                            success:function(){
                                $("#myTable").trigger("reloadGrid");//刷新表单
                            }
                        });

                        return "hello";//必须有返回值才可以关闭添加框
                    }
                },
                {
                    closeAfterAdd:true,
                    //在提交添加操作之后进行的操作
                    afterSubmit:function(response){
                        //进行文件上传以及图片路径修改
                        //response.responseText获取的是进行添加操作之后返回的添加的数据的id，用来进行修改图片路径的操作
                        $.ajaxFileUpload({
                            url:"${path}/user/upload",
                           // dataType:"JSON",
                            data:{id:response.responseText},
                            fileElementId:"headImg",//指定文件上传的input框name就是你添加操作的头像字段名
                            type:"post",
                           success:function(){
                                $("#myTable").trigger("reloadGrid");//刷新表单
                            }
                        });

                       return "hello";//必须有返回值才可以关闭添加框
                    }
                });
            $("#basic-addon").click(function(){
                sendCode();
            })
            $("#exportBtn").click(function(){
                exportUser();
            })
        });
       //修改状态值的方法
        function updateStatus(id,status) {
           if(status=="1"){
               $.ajax({
                   url: "${path}/user/option",
                   data: {"id":id,"status":"0","oper":"edit"},
                   type:"post",
                   success:function(){
                       $("#myTable").trigger('reloadGrid');  //刷新表单
                   }
               })
           }else{
               $.ajax({
                   url: "${path}/user/option",
                   data: {"id":id,"status":"1","oper":"edit"},
                   type:"post",
                   success:function(){
                       $("#myTable").trigger('reloadGrid');  //刷新表单
                   }
               })
           }
        }

       function sendCode(){
            var phoneNumber = $("#phoneNumber").val();
            $.ajax({
                url:"${path}/user/send",
                data:{"phoneNumber":phoneNumber},
                type:"post",
                success:function(data){

                   $("#showMsg").html(data);
                   $("#sendMsg").show();
                   setTimeout(function(){
                       $("#sendMsg").hide();
                   },2000);
                }
            })
       }
       function exportUser(){
            $.ajax({
                url:"${path}/user/export",
                type:"post",
                success:function(data){
                    $("#showMsg").html(data);
                    $("#sendMsg").show();
                    setTimeout(function(){
                        $("#sendMsg").hide();
                    },2000);
                }
            })
       }
    </script>
<div class="panel panel-danger">
    <!-- 面板头部信息 -->
    <div class="panel-heading"><h2>用户信息</h2></div>
    <br>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">用户管理</a></li>
    </ul>
    <div class="alert alert-warning alert-dismissible" role="alert" id="sendMsg" style="width: 300px;display: none">
        <span id="showMsg"/>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-3">
            <ul class="nav nav-pills navbar-left">
                <li><button class="btn btn-success" id="exportBtn">导出用户信息</button></li>
                <li><button class="btn btn-info">导入用户</button></li>
            </ul>
        </div>
        <div class="col-sm-4 col-sm-offset-5">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="请输入手机号" id="phoneNumber" aria-describedby="basic-addon">
                <span class="input-group-addon" id="basic-addon">发送验证码</span>
            </div>
        </div>
    </div>
    <br>
    <!--展示用户信息表-->
    <table  id="myTable">
    </table>
    <div id="pager"></div>
</div>


