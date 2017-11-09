// ====================================================================
// https://stackoverflow.com/questions/995183/how-to-allow-only-numeric-0-9-in-html-inputbox-using-jquery
// Numeric only control handler
jQuery.fn.ForceNumericOnly =
function()
{
    return this.each(function()
    {
        $(this).keydown(function(e)
        {
            var key = e.charCode || e.keyCode || 0;
            // allow backspace, tab, delete, enter, arrows, numbers and keypad numbers ONLY
            // home, end, period, and numpad decimal
            return (
                key == 8 || 
                key == 9 ||
                key == 13 ||
                key == 46 ||
                key == 110 ||
                key == 190 ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
    });
};

function xhrSendParameter(xhrUrl, jsonParam, successFn, errorFn, selfPleaseWaitShow) {
	if (null == selfPleaseWaitShow || _qifu_success_flag != selfPleaseWaitShow) {
		parent.showPleaseWait();
	} else {
		showPleaseWait();
	}
	$.ajax({
		type : _qifu_jqXhrType,
	    url : xhrUrl,
	    timeout: _qifu_jqXhrTimeout,
	    dataType : 'json',
	    data : jsonParam,
	    cache: _qifu_jqXhrCache,
	    async: _qifu_jqXhrAsync,
	    success : function(data, textStatus) {
	    	hidePleaseWait();
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				return;
			}    			
			if ( _qifu_success_flag != data.login ) {
				alert("Please try login again!");
				return;
			}       
			if ( _qifu_success_flag != data.isAuthorize ) {
				alert("No permission!");
				return;        				
			}        						
			if ( 'E' == data.success ) { // xhr load success, but has Exception or Error
				parent.toastrError( data.message );
				return;
			}			
			successFn(data, textStatus);
	    },
	    error : function(jqXHR, textStatus, errorThrown) {
	    	alert(textStatus);
	    	hidePleaseWait();
	        errorFn(jqXHR, textStatus, errorThrown);
	    }
	});
}

function xhrSendForm(xhrUrl, formId, successFn, errorFn, selfPleaseWaitShow) {
	if (null == selfPleaseWaitShow || _qifu_success_flag != selfPleaseWaitShow) {
		parent.showPleaseWait();
	} else {
		showPleaseWait();
	}
	$.ajax({
		type : _qifu_jqXhrType,
	    url : xhrUrl,
	    timeout: _qifu_jqXhrTimeout,
	    dataType : 'json',
	    data : $("#"+formId).serialize(),
	    cache: _qifu_jqXhrCache,
	    async: _qifu_jqXhrAsync,
	    success : function(data, textStatus) {
	    	hidePleaseWait();
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				return;
			}    			
			if ( _qifu_success_flag != data.login ) {
				alert("Please try login again!");
				return;
			}       
			if ( _qifu_success_flag != data.isAuthorize ) {
				alert("No permission!");
				return;        				
			}        			
			if ( 'E' == data.success ) { // xhr load success, but has Exception or Error
				parent.toastrError( data.message );
				return;
			}			
			successFn(data, textStatus);
	    },
	    error : function(jqXHR, textStatus, errorThrown) {
	    	alert(textStatus);
	    	hidePleaseWait();
	        errorFn(jqXHR, textStatus, errorThrown);
	    }
	});
}

function xhrSendParameterNoPleaseWait(xhrUrl, jsonParam, successFn, errorFn) {
	$.ajax({
		type : _qifu_jqXhrType,
	    url : xhrUrl,
	    timeout: _qifu_jqXhrTimeout,
	    dataType : 'json',
	    data : jsonParam,
	    cache: _qifu_jqXhrCache,
	    async: false, // _qifu_jqXhrAsync
	    success : function(data, textStatus) { 	    	
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				return;
			}    			
			if ( _qifu_success_flag != data.login ) {
				alert("Please try login again!");
				return;
			}       
			if ( _qifu_success_flag != data.isAuthorize ) {
				alert("No permission!");
				return;        				
			}        					
			if ( 'E' == data.success ) { // xhr load success, but has Exception or Error
				parent.toastrError( data.message );
				return;
			}			
			successFn(data, textStatus);
	    },
	    error : function(jqXHR, textStatus, errorThrown) {    	
	        alert(textStatus);
	        errorFn(jqXHR, textStatus, errorThrown);
	    }
	});
}

function xhrSendFormNoPleaseWait(xhrUrl, formId, successFn, errorFn) {
	$.ajax({
		type : _qifu_jqXhrType,
	    url : xhrUrl,
	    timeout: _qifu_jqXhrTimeout,
	    dataType : 'json',
	    data : $("#"+formId).serialize(),
	    cache: _qifu_jqXhrCache,
	    async: false, // _qifu_jqXhrAsync
	    success : function(data, textStatus) {   	
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				return;
			}    			
			if ( _qifu_success_flag != data.login ) {
				alert("Please try login again!");
				return;
			}       
			if ( _qifu_success_flag != data.isAuthorize ) {
				alert("No permission!");
				return;        				
			}        			
			if ( 'E' == data.success ) { // xhr load success, but has Exception or Error
				parent.toastrError( data.message );
				return;
			}			
			successFn(data, textStatus);
	    },
	    error : function(jqXHR, textStatus, errorThrown) {	    	
	        alert(textStatus);
	        errorFn(jqXHR, textStatus, errorThrown);
	    }
	});
}

function setWarningMessageField(formGroups, fields, checkFields) {
	if (null == fields) {
		return;
	}
	for (var k in fields) {
		var idKey = '';
		var msgContent = '';
		var formGroupId = '';
		for (var d in checkFields) {			
			if ( k == d ) {
				idKey = fields[k];
				msgContent = checkFields[d];
			}
		}
		for (var g in formGroups) {
			if ( fields[k] == g ) {
				formGroupId = formGroups[g]; 
			}
		}
		if (null == idKey || idKey == '') {
			continue;
		}
		$("#"+idKey+"-feedback").html( msgContent );
		$("#"+idKey).addClass( "form-control-warning" );
		if (null != formGroupId && formGroupId != '') {
			$("#"+formGroupId).addClass( "has-warning" );
		}
	}
}
function clearWarningMessageField(formGroups, fields) {
	for (var k in fields) {
		var idKey = fields[k];
		$("#"+idKey+"-feedback").html( "" );
		$("#"+idKey).removeClass( "form-control-warning" );		
	}
	for (var g in formGroups) {
		$("#"+formGroups[g]).removeClass( "has-warning" );
	}
}
