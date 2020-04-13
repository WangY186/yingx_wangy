<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script>
        $(function(){
            $("#list").click(function(){
                $('#divRight').load('${path}/main/showuser.jsp');
            })
            $("#list4").click(function(){
                $("#divRight").load("${path}/category/category.jsp");
            })
            $("#list6").click(function(){
                $("#divRight").load("${path}/feedback/feedback.jsp");
            })
            $("#videoShow").click(function(){
                $("#divRight").load("${path}/video/video.jsp");
            })
            $("#logBtn").click(function(){
                $("#divRight").load("${path}/log/log.jsp");
            })
            $("#list2").click(function(){
                $('#divRight').load('${path}/echarts/echartsGoeasy.jsp');
            })
            $("#list3").click(function(){
                $("#divRight").load("${path}/echarts/mapJson.jsp");
            })
            $("#searchBtn").click(function(){
                $("#divRight").load("${path}/video/searchVideo.jsp");
            })
        })
    </script>

</head>
<body>
    <!--顶部导航-->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#myCollapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand">应学视频App后台管理系统</a>
            </div>
            <div class="collapse navbar-collapse" id="myCollapse">

                <ul class="nav navbar-nav navbar-right">
                    <li class=""><a href="">欢迎：<span class="text text-primary"><strong>${admin.username}</strong></span></a></li>
                    <li><a href="${path}/admin/exit" class="navbar-link">退出<span class="glyphicon glyphicon-log-out" ></span></a></li>
                </ul>
            </div>
        </div>
    </nav>
    <br><br><br>
    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row">
            <!--左边手风琴部分-->
            <div class="col-sm-2"  align="center" >
                <div class="panel-group" id="accordion" role="tablist">
                    <div class="panel panel-danger">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel">

                            <div class="panel-body" >
                                <ul class="nav nav-pills nav-stacked">
                                    <li><button type="button" class="btn btn-danger" id="list">用户展示</button><br><br></li>
                                    <li><button type="button" class="btn btn-danger" id="list2">用户统计</button><br><br></li>
                                    <li><button type="button" class="btn btn-danger" id="list3">用户分布</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-success">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                    分类管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body" >
                                <ul class="nav nav-pills nav-stacked">
                                    <li><button type="button" class="btn btn-success" id="list4">分类展示</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-warning" >
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                    视频管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><button type="button" class="btn btn-warning" id="videoShow">视频展示</button></li>
                                    <li><button type="button" class="btn btn-warning" id="searchBtn">视频搜索</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-info">
                        <div class="panel-heading" role="tab" id="headingFour"  align="center">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                    日志管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><button type="button" class="btn btn-info" id="logBtn">日志展示</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-primary">
                        <div class="panel-heading" role="tab" id="headingFive"  align="center">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                    反馈管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><button type="button" class="btn btn-primary" id="list6">反馈展示</button></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-10" id="divRight">
                <!--巨幕开始-->
                <div class="jumbotron">
                    <h1>欢迎来到应学视频App后台管理系统</h1>
                </div>
                <!--右边轮播图部分-->
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" align="center">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox" >
                        <div class="item active">
                            <img src="${path}/bootstrap/img/pic1.jpg" alt="...">
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic2.jpg" alt="...">
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic3.jpg" alt="...">
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic4.jpg" alt="...">
                        </div>
                    </div>
                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

        </div>
      </div>
    <!--栅格系统-->
    <!--页脚-->
    <div class="panel panel-footer" align="center">
        <div>@百知教育 wangyan@zparkhr.com</div>
    </div>
</body>
</html>
