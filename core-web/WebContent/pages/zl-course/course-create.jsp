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
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>

<!-- Include Editor style. -->
<link href="./froala_editor/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
<link href="./froala_editor/css/froala_style.min.css" rel="stylesheet" type="text/css" />
<!-- Include Editor JS files. -->
<script type="text/javascript" src="./froala_editor/js/froala_editor.pkgd.min.js"></script>

<style type="text/css">


</style>


<script type="text/javascript">

$( document ).ready(function() {
	
	$('#content').froalaEditor({
		height: 250,
		width: 650
	});
	
	$('#description').froalaEditor({
		height: 150,
		width: 650
	});	
	
});

var msgFields = new Object();
msgFields['id'] 			= 'id';
msgFields['title'] 			= 'title';
msgFields['content'] 		= 'content';
msgFields['description'] 	= 'description';

var formGroups = new Object();
formGroups['id'] 			= 'form-group1';
formGroups['title'] 		= 'form-group1';
formGroups['content'] 		= 'form-group2';
formGroups['description'] 	= 'form-group2';

function saveSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	clearSave();
}

function clearSave() {
	clearWarningMessageField(formGroups, msgFields);
	$("#id").val( '' );
	$("#title").val( '' );
	$('#content').froalaEditor('html.set', '');
	$('#description').froalaEditor('html.set', '');
	$("#showFlag").prop('checked', true);
}

</script>

</head>

<body>

<q:toolBar 
	id="ZENLIFE_PROG001D0002A_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('ZENLIFE_PROG001D0002A');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnSave();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('ZENLIFE_PROG001D0002A');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="id" value="" id="id" label="Id" requiredFlag="Y" maxlength="20" placeholder="請輸入Id"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="title" value="" id="title" label="標題" requiredFlag="Y" maxlength="100" placeholder="請輸入標題"></q:textbox>
		</div>
	</div>
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="content" id="content" value="" label="內容" requiredFlag="Y" escapeHtml="N"></q:textarea>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" id="description" value="" label="描述" requiredFlag="Y" escapeHtml="N"></q:textarea>
		</div>
	</div>
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="showFlag" id="showFlag" label="在前台顯示" checked="Y"></q:checkbox>
		</div>
	</div>
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="Save"
			xhrUrl="./zenlife.courseSaveJson.do"
			xhrParameter="
			{
				'id'			:	$('#id').val(),
				'title'			:	$('#title').val(),
				'content'		:	$('#content').froalaEditor('html.get'),
				'description'	:	$('#description').froalaEditor('html.get'),
				'showFlag'		:	( $('#showFlag').is(':checked') ? 'Y' : 'N' )
			}
			"
			onclick="btnSave();"
			loadFunction="saveSuccess(data);"
			errorFunction="clearSave();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="clearSave();"></q:button>
	</div>
</div>

</body>
</html>