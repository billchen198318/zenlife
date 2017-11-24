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
	
	$("#phone").ForceNumericOnly();
	$("#tel").ForceNumericOnly();	
	
});

var msgFields = new Object();
msgFields['id'] 			= 'id';
msgFields['password'] 		= 'pwd1';
msgFields['pwd2'] 			= 'pwd2';
msgFields['vcode']			= 'vcode';
msgFields['mail']			= 'mail';
msgFields['name']			= 'name';
msgFields['phone']			= 'phone';

var formGroups = new Object();
formGroups['id'] 			= 'form-group1';
formGroups['pwd1'] 			= 'form-group1';
formGroups['pwd2'] 			= 'form-group1';
formGroups['vcode'] 		= 'form-group1';
formGroups['mail'] 			= 'form-group2';
formGroups['name'] 			= 'form-group3';
formGroups['phone'] 		= 'form-group3';

function saveSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	bootbox.alert({ 
		  size: "small",
		  title: "會員建立完成",
		  message: "請至您的電子信箱: " + data.value.mail + " 收取驗證信連結<br>如無收到確認信連結，請連絡鐘先生: 0800-956-956", 
		  callback: function(){ window.location = "./index.do"; }
	});	
}

function refreshVcodeUrl() {
	$("#vcode-image").attr('src', './vCode.do?n='+guid());
}

function clearSave() {
	window.location = "./register.do";
}

function cancelSave() {
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
		    <label for="pwd1">密碼</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd1" name="pwd1" placeholder="請輸入密碼" maxlength="12">			
		    <div class="form-control-feedback" id="pwd1-feedback"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		    <label for="pwd2">密碼(驗證)</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd2" name="pwd2" placeholder="請輸入密碼(驗證)" maxlength="12">
		    <div class="form-control-feedback" id="pwd2-feedback"></div>					
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
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="mail" value="" id="mail" label="電子信箱email" requiredFlag="Y" maxlength="200" placeholder="請輸入電子信箱email"></q:textbox>
		</div>
	</div>
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="name" value="" id="name" label="名稱" requiredFlag="Y" maxlength="100" placeholder="請輸入名稱"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="phone" value="" id="phone" label="個人手機號碼" requiredFlag="Y" maxlength="10" placeholder="請輸入個人手機號碼"></q:textbox>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="tel" value="" id="tel" label="聯絡(家中)電話" requiredFlag="N" maxlength="15" placeholder="請輸入聯絡(家中)電話"></q:textbox>
		</div>
	</div>		
</div>    
   


</form>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="確認"
			xhrUrl="./registerSaveJson.do"
			xhrParameter="
			{
				'id'			:	$('#id').val(),
				'password'		:	$('#pwd1').val(),
				'pwd2'			:	$('#pwd2').val(),
				'vcode'			:	$('#vcode').val(),
				'mail'			:	$('#mail').val(),
				'name'			:	$('#name').val(),
				'phone'			:	$('#phone').val(),
				'tel'			:	$('#tel').val()	
			}
			"
			onclick="btnSave();"
			loadFunction="saveSuccess(data);"
			errorFunction="clearSave();">
		</q:button>
		<q:button id="btnClear" label="清除" onclick="clearSave();" cssClass="btn btn-info"></q:button>
		<q:button id="btnCancel" label="取消" onclick="cancelSave();" cssClass="btn btn-secondary"></q:button>
	</div>
</div>

</div>

    <br/>
    <label>ZenLife 0.1 version</label>
    
</body>
</html>
