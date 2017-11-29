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


<div class="bd-example">
  <address>
  
    <strong>${person.name}</strong><br />
生日:&nbsp;${profile.birthdayYear}/${profile.birthdayMonth}/${profile.birthdayDay}<br />
<abbr title="Phone">電話(P):</abbr> ${person.phone}<br />
<abbr title="Phone">電話(T):</abbr> ${person.tel}<br />
生高/體重:&nbsp;${profile.height}&nbsp;/&nbsp;${profile.weight}<br />
性別:&nbsp;<q:if test=" \"1\" == profile.gender ">男</q:if><q:elseif test=" \"2\" == profile.gender ">女</q:elseif><q:else>&nbsp;</q:else><br />
${profile.address}
  </address>

</div>

<br/>
<span class="badge badge-info">最近一筆血壓記錄</span>
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
<q:else>
<div class="alert alert-warning" role="alert">
  <strong>無記錄!</strong> 請至血作業維護您的資料.
</div>
</q:else>

<br/>
<span class="badge badge-danger">慢性病記錄</span>
<q:if test=" null != chronicList && chronicList.size > 0 && personChronicList != null && personChronicList.size > 0 ">
	<table class="table">
	  <thead class="thead-inverse">
	    <tr>
	      <th>名稱</th>
	      <th>說明</th>
	    </tr>
	  </thead>
<c:forEach items="${chronicList}" var="item" varStatus="myIndex">
	<q:if test=" @com.zenlife.util.ChronicUtils@isCheck('${item.id}', personChronicList) ">
	    <tr>
	      <td>${item.name}</td>
	      <td>${item.description}</td>
	    </tr>		
	</q:if>
</c:forEach>
	  </tbody>
	</table>
</q:if>
<q:else>
<div class="alert alert-success" role="alert">
  <strong>無記錄!</strong>
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