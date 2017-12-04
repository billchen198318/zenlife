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
	<jsp:include page="../head-sm.jsp">
		<jsp:param name="backUrl" value="./index-sm.do"/>
	</jsp:include>
	
<div class="container">
<q:if test=" null != courseList && courseList.size > 0 ">
	<c:forEach items="${courseList}" var="item" varStatus="myIndex">
		<div class="card mb-3 text-center">
		  <div class="card-block">
		    <h4 class="card-title">${item.course.title}</h4>
		    <p class="card-text">${item.course.description}</p>
		    <a href="./courseRead-sm.do?oid=${item.course.oid}" class="btn btn-primary">進入課程</a>
		  </div>
		  <div class="card-footer text-muted">
		    發佈時間:&nbsp;${item.course.cdate}
		  </div>
		</div>	
	</c:forEach>
</q:if>
<q:else>
	<div class="card mb-3 text-center">
	  <div class="card-header">
	    課程資訊
	  </div>
	  <div class="card-block">
	    <h4 class="card-title">無教學課程</h4>
	    <p class="card-text">由於尚未發佈教學課程，請返回首頁.</p>
	    <a href="./index-sm.do" class="btn btn-primary">首頁</a>
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

</body>
</html>