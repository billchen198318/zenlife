<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
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


<style type="text/css">


</style>


<script type="text/javascript">

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./course.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">
<q:if test=" null != course ">
		<div class="card mb-3 text-center">
		  <div class="card-block">
		    <h4 class="card-title">${course.title}</h4>
		    <p class="card-text">${course.content}</p>
		  </div>
		</div>	
</q:if>
<q:else>
	<div class="card mb-3 text-center">
	  <div class="card-header">
	    課程內容
	  </div>
	  <div class="card-block">
	    <h4 class="card-title">無內容</h4>
	    <p class="card-text">無課程內容，請回首頁.</p>
	    <a href="./index.do" class="btn btn-primary">首頁</a>
	  </div>
	</div>
</q:else>
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