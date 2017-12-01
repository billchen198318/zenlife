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
	<jsp:include page="./head.jsp">
		<jsp:param name="backUrl" value="./index.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">

<div class="bd-example">
  <address>
  
    <strong>關於說醫門</strong><br />
    
    <!-- 
    <img alt="about" src="./images/about_bk1.jpg" border="0">
    <br/>    
     -->
    
	以禪修方式，啟發生活智慧，協助國人身心健康，增進家庭幸福美滿，推動社會祥和進步為宗旨。
	<br/>
	<br/>
	歲次壬申    季夏  
	
  </address>

</div>

<!--  
<br/>
電話: 0800-956-956<br/>
位置: <span class="badge badge-info">桃園市新屋區中山西路777號</span><br/>
<div class="embed-responsive embed-responsive-4by3">
	<iframe width="800" height="600" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" 
		src=http://maps.google.com.tw/maps?f=q&hl=zh-TW&geocode=&q=桃園市新屋區中山西路777號&z=16&output=embed&t=></iframe>
</div>
-->

</div>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
	
<!-- ############################################################################################################### -->
	
	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>