<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

        $(function(){
           $("#selBtn").click(function(){
               searchVideo();
           })
        });

        function searchVideo(){
           var content = $("#content").val();
            $("#searchTable").empty();
           $.ajax({
               url:"${path}/video/querySearch",
               data:{"content":content},
               dataType:"json",
               type:"post",
               success:function(data){
                   $.each(data,function(index,video){
                       $("#searchTable").append("<tr>" +
                           "<td>"+video.id+"</td>" +
                           "<td>"+video.title+"</td>" +
                           "<td>"+video.brief+"</td>" +
                           "<td><img src='"+video.cover+"' width='200px' height='100px'></td>" +
                           "<td>"+video.publishDate+"</td>" +
                           "</tr>")
                   })
               }
           })
        }
</script>
    <div align="center">
        <div class="input-group" style="width: 500px">
            <input type="text" class="form-control" placeholder="输入要查询的字段" id="content">
            <span class="input-group-btn">
               <button class="btn btn-default" type="button" id="selBtn">视频搜索</button>
           </span>
        </div>
    </div>
<hr>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">搜索结果</div>

    <!-- Table -->
    <table class="table">
        <tr>
            <td>ID</td>
            <td>标题</td>
            <td>简介</td>
            <td>封面</td>
            <td>注册时间</td>
        </tr>
        <tbody id="searchTable">

        </tbody>
    </table>
</div>



