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

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"刪除血壓紀錄?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./bloodPressureDeleteJson.do', 
						{ 'oid' : oid }, 
						function(data) {
							if ( _qifu_success_flag != data.success ) {
								parent.toastrWarning( data.message );
							}
							if ( _qifu_success_flag == data.success ) {
								parent.toastrInfo( data.message );
							}
							window.location = "./bloodPressureList.do";
						}, 
						function() {
							
						},
						_qifu_defaultSelfPleaseWaitShow
				);
			}
	);	
}

function cancelSave() {
	window.location = "./bloodPressure.do";
}

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./bloodPressure.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">

<q:if test=" null != bloodPressureList && bloodPressureList.size>0 ">
<!-- table show -->
	<table class="table">
	  <thead class="thead-inverse">
	    <tr>
		  <th>#</th>
	      <th>日期</th>
	      <th>SBP</th>
	      <th>DBP</th>
	      <th>PULSE</th>
	    </tr>
	  </thead>
	  <tbody>
	  	<c:forEach items="${bloodPressureList}" var="item" varStatus="myIndex">
	    <tr>
	      <td><img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord('${item.oid}');" border="0"></img></td>
	      <td>${item.logDate.substring(0,4)}/${item.logDate.substring(4,6)}/${item.logDate.substring(6,8)} (${item.timePeriod})</td>
	      <td>${item.sbp}</td>
	      <td>${item.dbp}</td>
	      <td>${item.pulse}</td>
	    </tr>	  	
	  	</c:forEach>
	  </tbody>
	</table>
</q:if>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
	<q:button id="btnCancel" label="返回" onclick="cancelSave();" cssClass="btn btn-secondary"></q:button>
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
	
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
</html>