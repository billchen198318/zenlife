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

<style type="text/css">


</style>


<script type="text/javascript">

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 		field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Id", 		field: "id"							},
		{ name: "Name", 	field: "name"						},
		{ name: "Phone", 	field: "phone"						},
		{ name: "TEL", 		field: "tel"						},
		{ name: "Mail", 	field: "mail"						},
		{ name: "Valid", 	field: "validFlag"					}
	];
}

function queryClear() {
	$("#id").val('');
	$("#name").val('');
	
	clearQueryGridTable();
	
}  

function editPage(oid) {
	parent.addTab('ZENLIFE_PROG001D0001E', parent.getProgUrlForOid('ZENLIFE_PROG001D0001E', oid) );
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"變更這位用戶使用註記?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./zenlife.personUpdateCancelFlagJson.do', 
						{ 'oid' : oid }, 
						function(data) {
							if ( _qifu_success_flag != data.success ) {
								parent.toastrWarning( data.message );
							}
							if ( _qifu_success_flag == data.success ) {
								parent.toastrInfo( data.message );
							}
							queryGrid();
						}, 
						function() {
							
						},
						_qifu_defaultSelfPleaseWaitShow
				);
			}
	);	
}

</script>

</head>

<body>

<q:toolBar 
	id="ZENLIFE_PROG001D0001Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('ZENLIFE_PROG001D0001Q');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('ZENLIFE_PROG001D0001Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

      <div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="id" value="" id="id" label="帳戶Id" placeholder="輸入帳戶Id" maxlength="24"></q:textbox>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="name" value="" id="name" label="名稱" placeholder="輸入名稱" maxlength="100"></q:textbox>
       </div>
      </div>
      
<br>
      
<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
<button type="button" class="btn btn-primary" id="btnClear" onclick="queryClear();">Clear</button>

<br>
<br>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[id]'		: $('#id').val(),
		'parameter[name]'	: $('#name').val(),
		'select'			: getQueryGridSelect(),
		'showRow'			: getQueryGridShowRow()	
	}
	"
	xhrUrl="./zenlife.personQueryGridJson.do" 
	id="ZENLIFE_PROG001D0001Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>