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

<jsp:include page="./common-f-inc.jsp"></jsp:include>


<style type="text/css">

</style>


<script type="text/javascript">

</script>


</head>


<body>
	<jsp:include page="./head-sm.jsp"></jsp:include>
	
<div class="container">	
<div class="card-deck">
  <div class="card mb-3 text-center" onclick="window.location='./course-sm.do';">
    <img class="card-img-top" src="./images/001.jpg" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">生活課堂</h4>
      <p class="card-text">提供您生活保健知識，與說醫門生活課程內容.</p>
    </div>
  </div>
</div>
</div>

</body>

</html>