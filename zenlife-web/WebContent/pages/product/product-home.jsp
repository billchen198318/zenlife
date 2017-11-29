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
<div class="container">
<q:if test=" null != productList && productList.size > 0 ">
	<c:forEach items="${productList}" var="item" varStatus="myIndex">
		<div class="card mb-3 text-center">
		  <div class="card-header">
		    ${item.name}
		  </div>
		  <div class="card-block">
		    <h4 class="card-title">${item.name}</h4>
		    <p class="card-text">${item.description}</p>
		    <a href="./productNotice.do?oid=${item.oid}" class="btn btn-primary">我有興趣了解</a>
		  </div>
		</div>	
	</c:forEach>
</q:if>
<q:else>
無產品資料
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