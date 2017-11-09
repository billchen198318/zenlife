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
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="./tether/tether.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script type="text/javascript" src="./tether/tether.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./jquery/jquery-3.1.1.min.js?ver=${jsVerBuild}"></script>
<link rel="stylesheet" href="./bootstrap-4/css/bootstrap.css?ver=${jsVerBuild}" crossorigin="anonymous">
<link href="./font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}" rel="stylesheet" type="text/css" />
<script src="./bootstrap-4/js/bootstrap.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="./bootbox/bootbox.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<link rel="stylesheet" href="./toastr/toastr.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script src="./toastr/toastr.min.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>


<style type="text/css">


</style>

<script type="text/javascript">

function refreshVcodeUrl() {
	var nStr = Math.random().toString(36).substring(2);
	$("#vcode-image").attr('src', './vCode.do?n='+nStr);
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
		    <input type="password" class="form-control" id="pwd1" placeholder="請輸入密碼">			
		    <div class="form-control-feedback" id="pwd1-feedback"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		    <label for="pwd1">密碼(驗證)</label>&nbsp;<font color="RED">*</font>
		    <input type="password" class="form-control" id="pwd1" placeholder="請輸入密碼(驗證)">
		    <div class="form-control-feedback" id="pwd2-feedback"></div>					
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		
		    <label for="vcode">驗證碼</label>&nbsp;<font color="RED">*</font> &nbsp;&nbsp; <img id="vcode-image" alt="vcode-image" src="./vCode.do?n=<%=uuidStr%>"> 
		    <button type="button" class="btn btn-info btn-sm" onclick="refreshVcodeUrl()">更換驗證碼</button>
		    
		    <input type="text" class="form-control" id="vcode" placeholder="請輸入 驗證碼">
		    <div class="form-control-feedback" id="vcode-feedback"></div>			
			
		</div>
	</div>			
</div>
<br/>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="mail" value="" id="mail" label="電子信箱email" requiredFlag="Y" maxlength="24" placeholder="請輸入電子信箱email"></q:textbox>
		</div>
	</div>
</div>
<br/>
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


<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="確認"
			xhrUrl="./registrSave.do"
			xhrParameter="
			{
				
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

    <br/>
    <label>ZenLife 0.1 version</label>
    
</body>
</html>
