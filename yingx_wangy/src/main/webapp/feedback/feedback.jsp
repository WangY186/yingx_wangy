<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

        $(function(){
            $("#feedTable").jqGrid({
                url:"${path}/feedback/showAll",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                datatype:"json",
                pager:"#pager",
                rowNum:3,
                rowList:[3,5,10],
                viewrecords:true,
                multiselect:true,
                colNames:["ID","标题","反馈内容","反馈用户","反馈时间"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",align:"center"},
                    {name:"content",align:"center"},
                    {name:"userId",align:"center"},
                    {name:"saveDate",align:"center"}
                ]
            }).jqGrid("navGrid","#pager",{add:false,del:false,edit:false,search:false});
        });

    </script>
<div class="panel panel-primary">
    <!-- 面板头部信息 -->
    <div class="panel-heading"><h2>反馈信息</h2></div>
    <br>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">反馈管理</a></li>
    </ul>
    <br>
    <!--展示信息表-->
    <table  id="feedTable">
    </table>
    <div id="pager"></div>
</div>


