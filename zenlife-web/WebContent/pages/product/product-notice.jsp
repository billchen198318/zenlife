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
msgFields['phone'] 			= 'phone';

var formGroups = new Object();
formGroups['phone'] 		= 'form-group1';

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
		  title: "資料輸入完成",
		  message: "返回查詢產品", 
		  callback: function(){ window.location = "./product.do"; }
	});	
}

function clearSave() {
	$("#productNoticeForm").trigger('reset');
	clearWarningMessageField(formGroups, msgFields);
}

function cancelSave() {
	window.location = './product.do';
}


</script>


</head>


<body>
	<jsp:include page="../head.jsp">
		<jsp:param name="backUrl" value="./product.do"/>
	</jsp:include>
	
<!-- ############################################################################################################### -->
<div class="container">
<q:if test=" null != product ">
		<div class="card mb-3 text-center">
		  <div class="card-block">
		    <h4 class="card-title">${product.name}</h4>
		    <p class="card-text">${product.content}</p>
		  </div>
		</div>	

<br>		
<span class="badge badge-success">留下您的聯絡方式，我們將會為您服務.</span>
		
<form name="productNoticeForm" id="productNoticeForm" action="" method="post">
	<div class="form-group" id="form-group1">
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textbox name="phone" value="person.phone" id="phone" label="電話" requiredFlag="Y" maxlength="10" placeholder="請輸入聯絡電話"></q:textbox>
			</div>		
		</div>
	</div>	
	<div class="form-group" id="form-group2">
		<div class="row">
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textbox name="address" value="profile.address" id="address" label="地址" requiredFlag="N" maxlength="500" placeholder="請輸入聯絡地址"></q:textbox>
			</div>		
			<div class="col-xs-6 col-md-6 col-lg-6">
				<q:textarea name="message" id="message" rows="3" value="我對這個商品感到興趣，請盡快的聯絡我!" label="留下您的訊息"></q:textarea>
			</div>		
		</div>
	</div>			
</form>	
<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="確認"
			xhrUrl="./productNoticeSaveJson.do"
			xhrParameter="
			{
				'prodOid'		: 	'${product.oid}',			
				'phone'			: 	$('#phone').val(),
				'address'		: 	$('#address').val(),
				'message'		: 	$('#message').val()
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
		
		
</q:if>
<q:else>
	<div class="card mb-3 text-center">
	  <div class="card-header">
	    課程內容
	  </div>
	  <div class="card-block">
	    <h4 class="card-title">無內容</h4>
	    <p class="card-text">無課程內容，請回首頁.</p>
	    <a href="./index.do" class="btn btn-primary">首頁</a>
	  </div>
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