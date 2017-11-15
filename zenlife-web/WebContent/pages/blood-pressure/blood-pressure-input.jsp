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
msgFields['sbp'] 			= 'sbp';
msgFields['dbp'] 			= 'dbp';
msgFields['pulse'] 			= 'pulse';
msgFields['logDate']		= 'logDate';

var formGroups = new Object();
formGroups['sbp'] 			= 'form-group1';
formGroups['dbp'] 			= 'form-group1';
formGroups['pulse'] 		= 'form-group1';
formGroups['logDate'] 		= 'form-group2';

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
		  title: "血壓資料輸入完成",
		  message: "返回查詢血壓資料記錄", 
		  callback: function(){ window.location = "./bloodPressure.do"; }
	});	
}

function clearSave() {
	$("#bloodPressureForm").trigger('reset');
}

function cancelSave() {
	window.location = './bloodPressure.do';
}

</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./bloodPressure.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">
<form name="bloodPressureForm" id="bloodPressureForm" action="" method="post">
	<div class="form-group" id="form-group1">
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textbox name="sbp" value="" id="sbp" label="收縮壓(SBP)" requiredFlag="Y" maxlength="3" placeholder="請輸入收縮壓(SBP)"></q:textbox>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textbox name="dbp" value="" id="dbp" label="舒張壓(DBP)" requiredFlag="Y" maxlength="3" placeholder="請輸入舒張壓(DBP)"></q:textbox>
			</div>
		</div>	
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textbox name="pulse" value="" id="pulse" label="脈搏(PULSE)" requiredFlag="Y" maxlength="3" placeholder="請輸入脈搏(PULSE)"></q:textbox>
			</div>
		</div>
	</div>
	<div class="form-group" id="form-group2">
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<label for="logDate"> 日期 &nbsp;<font color="RED">*</font></label>
				<input  class="form-control" type="date" value="" id="logDate" name="logDate"/>
				<div class="form-control-feedback" id="logDate-feedback"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
			    <select class="form-control" id="timePeriod" name="timePeriod">
			      <option value="1">1 - 凌晨(00 ~ 06)</option>
			      <option value="2">2 - 上午(06 ~ 12)</option>
			      <option value="3">3 - 下午(12 ~ 18)</option>
			      <option value="4">4 - 晚間(18 ~ 24)</option>
			    </select>			
			</div>
		</div>		
	</div>	
	<div class="form-group" id="form-group3">
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textarea name="description" value="" id="description" label="備註欄" rows="1" placeholder="備註"></q:textarea>
			</div>
		</div>	
	</div>
</form>	

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="確認"
			xhrUrl="./bloodPressureSaveJson.do"
			xhrParameter="
			{
				'sbpStr'		: 	$('#sbp').val(),
				'dbpStr'		: 	$('#dbp').val(),
				'pulseStr'		: 	$('#pulse').val(),
				'logDate'		: 	$('#logDate').val(),
				'timePeriod'	: 	$('#timePeriod').val(),
				'description'	:	$('#description').val()
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
<br/>
<br/>
<br/>
<br/>
<br/>
	
<!-- ############################################################################################################### -->
	
	<jsp:include page="../foot.jsp"></jsp:include>
</body>
