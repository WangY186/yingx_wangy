<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

        $(function(){
            $("#videoTable").jqGrid({
                url:"${path}/video/showAll",
                editurl:"${path}/video/option",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                datatype:"json",
                pager:"#videoPage",
                rowNum:3,
                rowList:[3,5,10],
                viewrecords:true,
                multiselect:true,
                colNames:["ID","标题","视频","描述","上传时间","状态","所属类别","类别id","用户id"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",align:"center",editable:true},
                    {name:"path",editable:true,align:"center",edittype:'file',
                        formatter:function(cellvalue, options, rowObject){
                           return "<video src='"+cellvalue+"' controls poster='"+rowObject.cover+"' width='200px' height='100px'/>";
                        }
                    },
                    {name:"brief",editable:true,align:"center"},
                    {name:"publishDate",align:"center"},
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
                    {name:"category.cateName",align:"center"},
                    {name:"categoryId",align:"center",editable:true,edittype:'select',editoptions:{value:getCategory()}},
                    {name:"userId",editable:true,align:"center"}
                ]
            }).jqGrid("navGrid","#videoPage",{addtext:"添加",deltext:"删除",edittext:"修改",search:true},
                {
                    closeAfterEdit:true,
                    //在提交添加操作之后进行的操作
                    beforeShowForm :function(obj){
                        obj.find("#path").attr("disabled",true);//禁用input
                    }
                },
                {
                    closeAfterAdd:true,
                    //在提交添加操作之后进行的操作
                    afterSubmit:function(response){
                        //进行文件上传以及图片路径修改
                        //response.responseText获取的是进行添加操作之后返回的添加的数据的id，用来进行修改图片路径的操作
                        $.ajaxFileUpload({
                            url:"${path}/video/upload",
                            data:{id:response.responseText},
                            fileElementId:"path",//指定文件上传的input框name就是你添加操作的头像字段名
                            type:"post",
                           success:function(){
                                $("#videoTable").trigger("reloadGrid");//刷新表单
                            }
                        });

                       return "hello";//必须有返回值才可以关闭添加框
                    }
                },{
                    closeAfterDel:true,
                    //提交之後在頁面顯示
                    afterSubmit:function(data){
                        //將錯誤信息填入span標籤
                        $("#showMsg").html(data.responseJSON.message);
                        //顯示警告框
                       $("#delMsg").show();
                       //设置3秒关闭警告框
                        setTimeout(function(){
                            $("#delMsg").hide();
                        },3000)
                        return "ok";
                    }
                }
                );
        });
       //修改状态值的方法
        function updateStatus(id,status) {
           if(status=="1"){
               $.ajax({
                   url: "${path}/video/option",
                   data: {"id":id,"status":"0","oper":"edit"},
                   type:"post",
                   success:function(){
                       $("#videoTable").trigger('reloadGrid');  //刷新表单
                   }
               })
           }else{
               $.ajax({
                   url: "${path}/video/option",
                   data: {"id":id,"status":"1","oper":"edit"},
                   type:"post",
                   success:function(){
                       $("#videoTable").trigger('reloadGrid');  //刷新表单
                   }
               })
           }
        }
        function getCategory(){
            //动态生成select内容
            var str="";

            $.ajax({

                type:"post",

                async:false,

                url:"${path}/category/queryTwo",

                success:function(data){

                    if (data != null) {

                        var jsonobj=eval(data);

                        var length=jsonobj.length;

                        for(var i=0;i<length;i++){

                            if(i!=length-1){

                                str+=jsonobj[i].id+":"+jsonobj[i].cateName+";";

                            }else{

                                str+=jsonobj[i].id+":"+jsonobj[i].cateName;// 这里是option里面的 value:label

                            }

                        }

                    }
                }

            });

            return str;
        }
    </script>
<div class="panel panel-danger">
    <!-- 面板头部信息 -->
    <div class="panel-heading"><h2>视频信息</h2></div>
    <br>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">视频信息</a></li>
    </ul>
    <div class="alert alert-warning alert-dismissible" role="alert" id="delMsg" style="width: 300px;display: none">
        <span id="showMsg"/>
    </div>
    <!--展示信息表-->
    <table  id="videoTable">
    </table>
    <div id="videoPage"></div>
</div>


