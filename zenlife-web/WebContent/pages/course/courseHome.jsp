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

.faq .section p.quetn a, .faq .section p.quetn a:hover {
    text-decoration:underline;
    font-weight:bold
}

</style>


<script type="text/javascript">

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./index.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->

<q:if test=" null != courseList && courseList.size > 0 ">
	<c:forEach items="${courseList}" var="item" varStatus="myIndex">
		<div class="card mb-3 text-center">
		  <div class="card-header">
		    <q:if test=" \"Y\" == item.read "><span class="badge badge-info">已讀</span></q:if><q:else><span class="badge badge-warning">未讀</span></q:else>
		  </div>
		  <div class="card-block">
		    <h4 class="card-title">${item.course.title}</h4>
		    <p class="card-text">${item.course.description}</p>
		    <a href="./courseRead.do?oid=${item.course.oid}" class="btn btn-primary">進入課程</a>
		  </div>
		  <div class="card-footer text-muted">
		    ${item.course.cdate}
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
	    <a href="./index.do" class="btn btn-primary">首頁</a>
	  </div>
	</div>
</q:else>
	


<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
	
<!-- ############################################################################################################### -->
	
	<jsp:include page="../foot.jsp"></jsp:include>
</body>