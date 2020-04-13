<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
    <script src="${path}/js/echarts.js"></script>
    <script src="${path}/js/china.js"></script>
    <script type="text/javascript">

        $(function(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            $.post("${path}/echarts/mapEcharts",function(data){
                var series=[];
                $.each(data,function(index,returnEntity){
                       series.push({
                           name: returnEntity.title,
                           type: 'map',
                           mapType: 'china',
                           roam: false,
                           label: {
                               normal: {
                                   show: false
                               },
                               emphasis: {
                                   show: true
                               }
                           },
                           data:returnEntity.citys
                       });

                });
                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text: '每月用户注册分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:["小男孩",'小姑娘']
                    },
                    visualMap: {
                        min: 0,
                        max: 10,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : series
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },"json");

        });

    </script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div align="center">
    <div id="main" style="width: 600px;height:400px;"></div>
</div>