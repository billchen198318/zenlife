<%@page import="org.qifu.util.SimpleUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String uuidStr = SimpleUtils.getRandomUUIDStr();

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

$( document ).ready(function() {
	
	$('#id').keyup(function(){
	    this.value = this.value.toUpperCase();
	});
	
});

var msgFields = new Object();
msgFields['id'] 			= 'id';
msgFields['vcode']			= 'vcode';

var formGroups = new Object();
formGroups['id'] 			= 'form-group1';
formGroups['vcode'] 		= 'form-group1';

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
		  title: "新密碼產生完成",
		  message: "請至您的電子信箱收取新密碼信", 
		  callback: function(){ window.location = "./index.do"; }
	});	
}

function refreshVcodeUrl() {
	$("#vcode-image").attr('src', './vCode.do?n='+guid());
}

function clearUpdate() {
	window.location = "./renewpwd.do";
}

function cancelUpdate() {
	window.location = "./login.do";
}

</script>

</head>

<body>

<div class="container">
<form name="registerForm" id="registerForm" action="" method="post">
<div><img src="./images/logo2.png" width="48" height="48" border="0"/>&nbsp;&nbsp;&nbsp;<b><font color="#041431">ZenLife - 禪生活保健</font></b></div>
<br/>
<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="id" value="" id="id" label="身份證字號 或 自然人號" requiredFlag="Y" maxlength="24" placeholder="請輸入 身份證字號 或 自然人號"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		
		    <label for="vcode">驗證碼</label>&nbsp;<font color="RED">*</font> &nbsp;&nbsp; <img id="vcode-image" alt="vcode-image" src="./vCode.do?n=<%=uuidStr%>">
		    <span class="badge badge-info" onclick="refreshVcodeUrl()">更換驗證碼</span> 
		    
		    <input type="text" class="form-control" id="vcode" name="vcode" placeholder="請輸入 驗證碼">
		    <div class="form-control-feedback" id="vcode-feedback"></div>			
			
		</div>
	</div>		
</div>   
</form>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="確認"
			xhrUrl="./renewpwdUpdateJson.do"
			xhrParameter="
			{
				'id'			:	$('#id').val(),
				'vcode'			:	$('#vcode').val()
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
    <label>ZenLife 0.1 version</label>
    
</body>
</html>
