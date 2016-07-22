/**
 * 概况图表
 */

$(function() {
	// ---------------------------最近12个月销售情况概览-------------------------------
	var feedBackPieChart = echarts.init(document.getElementById('feed-back-pie-chart'));
	$.getJSON('getFeedBackPieChartData').done(function(data) {
		feedBackPieChart.setOption({
			 title : {
			        text: '用户反馈处理统计图',
			        subtext: '用户反馈处理统计图',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['已处理','未处理']
			    },
			    series : [
			        {
			            name: '处理数',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:data.series,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
		});
	});

});