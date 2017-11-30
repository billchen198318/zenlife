<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>ZenLife - 禪生活保健</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>


<!-- Highcharts -->
<script src="<%=basePath%>/highcharts/js/highcharts.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-3d.js"></script>
<script src="<%=basePath%>/highcharts/js/highcharts-more.js"></script>
<script src="<%=basePath%>/highcharts/js/modules/heatmap.js"></script>
<script src="<%=basePath%>/highcharts/js/modules/exporting.js"></script>	
<script src="<%=basePath%>/highcharts/js/modules/solid-gauge.js"></script>


<style type="text/css">


</style>


<script type="text/javascript">

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./index.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->

<div class="container">

<div class="row">
	<div class="col">
		<h5><span class="badge badge-primary" onclick="window.location='./bloodPressureInput.do';"><img alt="自行輸入" src="./images/accessories-text-editor.png" border="0">&nbsp;輸入血壓資料</span></h5>
	</div>
	<div class="col">
		<q:if test=" null != bloodPressureList && bloodPressureList.size>0 ">
		<h5><span class="badge badge-primary" onclick="window.location='./bloodPressureList.do';"><img alt="血壓記錄" src="./images/document-properties.png" border="0">&nbsp;血壓記錄資料</span></h5>
		</q:if>				
	</div>				
</div>

<q:if test=" null == bloodPressureList || bloodPressureList.size<1 ">
	<div class="alert alert-info" role="alert">
	  <strong>注意</strong> 由於您未輸入血壓資料，所以無法呈現血壓資訊，您可以點選上方的 <strong>"輸入血壓資料"</strong> 進行血壓資訊輸入。
	</div>
	<br>
</q:if>

<q:if test=" null != bloodPressureList && bloodPressureList.size==1 ">
	<div class="alert alert-info" role="alert">
	  <strong>注意</strong> 由於血壓資料少於兩筆，所以無法為您提供圖表。
	</div>
	<br>
</q:if>

<q:if test=" null != bloodPressureList && bloodPressureList.size>=2 ">
<!-- line-chart -->
	<div id="bloodLineChart"></div>
	<script>
	$('#bloodLineChart').highcharts({
	    title: {
	        text: '血壓走勢',
	        x: -20 //center
	    },
	    subtitle: {
	        text: '最近${bloodPressureList.size()}筆血壓記錄',
	        x: -20
	    },
        xAxis: {
            categories: [
				<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
				'${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})'<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
				</c:forEach>
			]
        },	    
	    yAxis: {
	        title: {
	            text: '血壓 ( SBP/DBP/PULSE )'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        valueSuffix: ' 測量血壓質'
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle',
	        borderWidth: 0
	    },
	    series: [
			{
		        name: 'SBP',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.sbp}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>
				]
		    }, {
		        name: 'DBP',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.dbp}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>
				]
		    }, {
		        name: 'PULSE',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.pulse}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>	               
				]
		    }
	    ]
	});
	</script>
	
	
	<div id="bloodLineSBPChart"></div>
	<script>
	$('#bloodLineSBPChart').highcharts({
	    title: {
	        text: 'SBP走勢',
	        x: -20 //center
	    },
	    subtitle: {
	        text: '最近${bloodPressureList.size()}筆血壓SBP記錄',
	        x: -20
	    },
        xAxis: {
            categories: [
				<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
				'${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})'<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
				</c:forEach>
			]
        },	    
	    yAxis: {
	    	minRange: 119,	    	
			min:0,
	        title: {
	            text: '血壓 SBP'
	        },
	        plotLines: [{
	            value: 119,
	            width: 1,
	            color: '#ff3300',
	            dashStyle: 'Dash'
	        }]
	    },
	    tooltip: {
	        valueSuffix: ' 測量血壓質'
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle',
	        borderWidth: 0
	    },
	    series: [
			{
		        name: 'SBP',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.sbp}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>
				]
		    }
	    ]
	});
	</script>
	
	
	<div id="bloodLineDBPChart"></div>
	<script>
	$('#bloodLineDBPChart').highcharts({
	    title: {
	        text: 'DBP走勢',
	        x: -20 //center
	    },
	    subtitle: {
	        text: '最近${bloodPressureList.size()}筆血壓DBP記錄',
	        x: -20
	    },
        xAxis: {
            categories: [
				<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
				'${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})'<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
				</c:forEach>
			]
        },	    
	    yAxis: {
	    	minRange: 79,	    	
			min:0,	    	
	        title: {
	            text: '血壓 DBP'
	        },
	        plotLines: [{
	            value: 79,
	            width: 1,
	            color: '#ff3300',
	            dashStyle: 'Dash'
	        }]
	    },
	    tooltip: {
	        valueSuffix: ' 測量血壓質'
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle',
	        borderWidth: 0
	    },
	    series: [
			{
		        name: 'DBP',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.dbp}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>
				]
		    }
	    ]
	});
	</script>	
	
	
	<div id="bloodLinePULSEChart"></div>
	<script>
	$('#bloodLinePULSEChart').highcharts({
	    title: {
	        text: 'PULSE走勢',
	        x: -20 //center
	    },
	    subtitle: {
	        text: '最近${bloodPressureList.size()}筆血壓PULSE記錄',
	        x: -20
	    },
        xAxis: {
            categories: [
				<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
				'${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})'<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
				</c:forEach>
			]
        },	    
	    yAxis: {
	    	minRange: 72,	    	
			min:0,	    	
	        title: {
	            text: '血壓 PULSE'
	        },
	        plotLines: [{
	            value: 72,
	            width: 1,
	            color: '#ff3300',
	            dashStyle: 'Dash'
	        }]
	    },
	    tooltip: {
	        valueSuffix: ' 測量血壓質'
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle',
	        borderWidth: 0
	    },
	    series: [
			{
		        name: 'PULSE',
		        data: [
						<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
						${item.pulse}<q:if test=" ${myIndex.index+1} < bloodPressureList.size ">,</q:if>
						</c:forEach>	               
				]
		    }
	    ]
	});
	</script>
			
</q:if>

<q:if test=" null != bloodPressureList && bloodPressureList.size>0 ">
<!-- table show -->
	<table class="table">
	  <thead class="thead-inverse">
	    <tr>
	      <th>日期</th>
	      <th>SBP</th>
	      <th>DBP</th>
	      <th>PULSE</th>
	    </tr>
	  </thead>
	  <tbody>
	  	<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
	    <tr>
	      <td>${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})</td>
	      <td>${item.sbp}</td>
	      <td>${item.dbp}</td>
	      <td>${item.pulse}</td>
	    </tr>	  	
	  	</c:forEach>
	  </tbody>
	</table>
</q:if>

</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
	
<!-- ############################################################################################################### -->
	
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>