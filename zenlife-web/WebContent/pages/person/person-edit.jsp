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

var formGroups = new Object();

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
		  title: "個人資料更新完成",
		  message: "返回首頁", 
		  callback: function(){ window.location = "./index.do"; }
	});	
}

function clearSave() {
	$("#profileForm").trigger('reset');
}

function cancelSave() {
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
<div class="row">
	<div class="col">
		<h5><span class="badge badge-primary" onclick=""><img alt="換密碼" src="./images/accessories-text-editor.png" border="0">&nbsp;更換密碼</span></h5>
	</div>
</div>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="name" value="person.name" id="name" label="名稱" requiredFlag="Y" maxlength="100" placeholder="請輸入名稱"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="phone" value="person.phone" id="phone" label="個人手機號碼" requiredFlag="Y" maxlength="10" placeholder="請輸入個人手機號碼"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="tel" value="person.tel" id="tel" label="聯絡(家中)電話" requiredFlag="N" maxlength="15" placeholder="請輸入聯絡(家中)電話"></q:textbox>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<label for="logDate"> 生日</label>
			<input  class="form-control" type="date" value="" id="birthday" name="birthday"/>
			<div class="form-control-feedback" id="birthday-feedback"></div>			
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		<br>
		性別 &nbsp;&nbsp;
		
<label class="custom-control custom-radio">
  <input id="genderRadioBoy" name="genderRadio" type="radio" class="custom-control-input" checked="checked">
  <span class="custom-control-indicator"></span>
  <span class="custom-control-description">男</span>
</label>
<label class="custom-control custom-radio">
  <input id="genderRadioGirl" name="genderRadio" type="radio" class="custom-control-input">
  <span class="custom-control-indicator"></span>
  <span class="custom-control-description">女</span>
</label>		
			
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="height" value="" id="height" label="身高" requiredFlag="N" maxlength="3" placeholder="請輸入身高"></q:textbox>
		</div>
	</div>			
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="weight" value="" id="weight" label="體重" requiredFlag="N" maxlength="3" placeholder="請輸入體重"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="address" value="" id="address" label="地址" requiredFlag="N" maxlength="500" placeholder="請輸入地址"></q:textbox>
		</div>
	</div>		
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		慢性病<br>
		
			<q:if test=" null != chronicList && chronicList.size > 0 ">
			<c:forEach items="${chronicList}" var="item" varStatus="myIndex">
			<q:checkbox name="chronic_${item.id}" id="_${item.id}" label="${item.name}" checkedTest=" @com.zenlife.util.ChronicUtils@isCheck('${item.id}', personChronicList) "></q:checkbox>
			</c:forEach>
			</q:if>
			
		</div>
	</div>	
</div>
</form>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="確認"
			xhrUrl="./personProfileSaveJson.do"
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