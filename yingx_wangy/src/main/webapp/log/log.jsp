<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

        $(function(){
            $("#logTable").jqGrid({
                url:"${path}/log/showAll",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                datatype:"json",
                pager:"#logPager",
                rowNum:3,
                rowList:[3,5,10],
                viewrecords:true,
                colNames:["ID","操作者","操作时间","操作","状态"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"adminOper",align:"center"},
                    {name:"time",align:"center"},
                    {name:"operation",align:"center"},
                    {name:"status",align:"center"}
                ]
            }).jqGrid("navGrid","#logPager",{add:false,del:false,edit:false,search:false});
        });
    </script>
<div class="panel panel-danger">
    <!-- 面板头部信息 -->
    <div class="panel-heading"><h2>日志信息</h2></div>
    <br>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">日志信息</a></li>
    </ul>
    <br>
    <!--展示用户信息表-->
    <table  id="logTable">
    </table>
    <div id="logPager"></div>
</div>


