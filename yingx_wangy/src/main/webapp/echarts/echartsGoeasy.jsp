<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${path}/js/goeasy-1.0.5.js"></script>
<script src="${path}/js/echarts.js"></script>
<script type="text/javascript">
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-81f2dddc6c054171b2d7168db3c647d5", //替换为您的应用appkey
    });
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


        goEasy.subscribe({
            channel: "user_channel", //替换为您自己的channel
            onMessage: function (message) {
                var datas = message.content;
                var data = JSON.parse(datas);
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
            }
        });
    })
</script>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div align="center">
        <div id="main" style="width: 600px;height:400px;"></div>
    </div>