/* 
 * http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript 
 */
function guid() {
	
	  function s4() {
	    return Math.floor((1 + Math.random()) * 0x10000)
	      .toString(16)
	      .substring(1);
	  }
	  
	  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
	    s4() + '-' + s4() + s4() + s4();
	  
}


function logoutEvent() {
	bootbox.confirm(
			"您確定登出嗎?", 
			function(result){ 
				if (!result) {
					return;
				}
				window.location = "./logout.do";
			}
	);	
}


function showPleaseWait() {
	//$('#myPleaseWait').modal('show');
	bootbox.dialog({ message: '<div class="text-center"><i class="fa fa-spin fa-spinner"></i> Loading...</div>', closeButton: false });
}
function hidePleaseWait() {
	//$('#myPleaseWait').modal('hide');
	bootbox.hideAll();
}

function toastrInfo(message) {
	toastr.options = { onclick: function () { alert(message); } }
	toastr.info( message.replace(/\n/gi, "<br>").replace("/\r\n", "<br>") );
}
function toastrWarning(message) {
	toastr.options = { onclick: function () { alert(message); } }
	toastr.warning( message.replace(/\n/gi, "<br>").replace("/\r\n", "<br>") );
}
function toastrError(message) {
	toastr.options = { onclick: function () { alert(message); } }
	toastr.error( message.replace(/\n/gi, "<br>").replace("/\r\n", "<br>") );
}
