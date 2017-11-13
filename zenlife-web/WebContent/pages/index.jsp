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

.faq .section p.quetn a, .faq .section p.quetn a:hover {
    text-decoration:underline;
    font-weight:bold
}

</style>


<script type="text/javascript">

</script>


</head>


<body>
	<jsp:include page="./head.jsp"></jsp:include>
	
<!-- ############################################################################################################### -->
	
<div class="card-deck">
  <div class="card mb-3 text-center">
    <img class="card-img-top" src="./images/001.jpg" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">生活課堂</h4>
      <p class="card-text">提供您生活保健知識，與說醫門生活課程內容.</p>
    </div>
  </div>
  <div class="card mb-3 text-center">
    <img class="card-img-top" src="./images/002.jpg" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">血壓</h4>
      <p class="card-text">輸入您寫壓測量資料，將會以圖表化方式為您呈現血壓走勢圖表.</p>
    </div>
  </div>
  <div class="card mb-3 text-center">
    <img class="card-img-top" src="./images/003.jpg" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">血糖</h4>
      <p class="card-text">輸入您個人血糖資訊，將會以圖表化方式為您呈現血堂走勢圖表.</p>
    </div>
  </div>
  <div class="card mb-3 text-center">
    <img class="card-img-top" src="./images/004.jpg" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">保健商品</h4>
      <p class="card-text">購買說醫門保健商品，留下您的通訊方式，我們將為您提供服務.</p>
    </div>
  </div>
  <div class="card mb-3 text-center">
    <img class="card-img-top" src="./images/005.png" alt="Card image cap">
    <div class="card-block">
      <h4 class="card-title">個人資料</h4>
      <p class="card-text">維護您的個人資料.</p>
    </div>
  </div>    
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