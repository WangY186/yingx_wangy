<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/js/echarts.js"></script>
    <script type="text/javascript">
        $(function(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            $.post("${path}/echarts/barEcharts",function(data){
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: 'ECharts 入门示例'
                    },
                    tooltip: {},
                    legend: {
                        data:['男','女']
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},
                    series: [{
                        name: '男',
                        type: 'bar',
                        data: data.boys
                    },{
                        name: '女',
                        type: 'bar',
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },"json");
        })
    </script>
    <title>Document</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div align="center">
    <div id="main" style="width: 600px;height:400px;"></div>
</div>


</body>
</html>