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

var msgFields = new Object();
msgFields['pwd0'] 		= 'pwd0';
msgFields['pwd1'] 		= 'pwd1';
msgFields['pwd2'] 		= 'pwd2';

var formGroups = new Object();
formGroups['pwd0'] 		= 'form-group1';
formGroups['pwd1'] 		= 'form-group1';
formGroups['pdw2'] 		= 'form-group2'

function updateSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	bootbox.alert({ 
		  size: "small",
		  title: "個人密碼更新完成",
		  message: "返回首頁", 
		  callback: function(){ window.location = "./index.do"; }
	});	
}

function clearUpdate() {
	$("#profileForm").trigger('reset');
	clearWarningMessageField(formGroups, msgFields);
}

function cancelUpdate() {
	window.location = './index.do';
}

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./index.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">

<form name="profileForm" id="profileForm" action="" method="post">

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		    <label for="pwd0">原密碼</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd0" name="pwd0" placeholder="請輸入原密碼" maxlength="12">			
		    <div class="form-control-feedback" id="pwd0-feedback"></div>			
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		    <label for="pwd1">新密碼</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd1" name="pwd1" placeholder="請輸入密碼" maxlength="12">			
		    <div class="form-control-feedback" id="pwd1-feedback"></div>			
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		    <label for="pwd2">新密碼(驗證)</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd2" name="pwd2" placeholder="請輸入密碼(驗證)" maxlength="12">
		    <div class="form-control-feedback" id="pwd2-feedback"></div>			
		</div>
	</div>	
</div>

</form>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="確認"
			xhrUrl="./personPwUpdateJson.do"
			xhrParameter="
			{
				'pwd0'			:	$('#pwd0').val(),
				'pwd1'			:	$('#pwd1').val(),
				'pwd2'			:	$('#pwd2').val()
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="clearUpdate();">
		</q:button>
		<q:button id="btnClear" label="清除" onclick="clearUpdate();" cssClass="btn btn-info"></q:button>
		<q:button id="btnCancel" label="取消" onclick="cancelUpdate();" cssClass="btn btn-secondary"></q:button>
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