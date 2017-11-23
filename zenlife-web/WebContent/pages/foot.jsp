<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String c = (String)request.getParameter("c");

%>

<nav class="navbar fixed-bottom navbar-light bg-faded bg-dark">
  <a class="navbar-brand" href="./index.do?c=1">
  	<table class="rounded">
  		<tr>
  			<td align="center" <% if ("1".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-top"><img src="./images/computer.png" border="0"></td>
  		</tr>
  		<tr>
  			<td align="center" <% if ("1".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-bottom"><font color="#f2f2f2">首頁</font></td>
  		</tr>  		
  	</table>
  </a>
  <a class="navbar-brand" href="./course.do?c=2">
  	<table class="rounded">
  		<tr>
  			<td align="center" <% if ("2".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-top"><img src="./images/accessories-dictionary.png" border="0"></td>
  		</tr>
  		<tr>
  			<td align="center" <% if ("2".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-bottom"><font color="#f2f2f2">課程</font></td>
  		</tr>  		
  	</table>
  </a>
  <a class="navbar-brand" href="./bloodPressure.do?c=3">
  	<table class="rounded">
  		<tr>
  			<td align="center" <% if ("3".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-top"><img src="./images/applications-office.png" border="0"></td>
  		</tr>
  		<tr>
  			<td align="center" <% if ("3".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-bottom"><font color="#f2f2f2">血壓</font></td>
  		</tr>  		
  	</table>
  </a>  
  <a class="navbar-brand" href="./personProfileView.do?c=4">
  	<table class="rounded">
  		<tr>
  			<td align="center" <% if ("4".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-top"><img src="./images/contact-new.png" border="0"></td>
  		</tr>
  		<tr>
  			<td align="center" <% if ("4".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-bottom"><font color="#f2f2f2">履歷</font></td>
  		</tr>  		
  	</table>
  </a>  
  <a class="navbar-brand" href="tel:0800956956">
  	<table class="rounded">
  		<tr>
  			<td align="center" <% if ("5".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-top"><img src="./images/call-start.png" border="0"></td>
  		</tr>
  		<tr>
  			<td align="center" <% if ("5".equals(c)) { %> bgcolor="#717880" <% } %> class="rounded-bottom"><font color="#f2f2f2">聯絡</font></td>
  		</tr>  		
  	</table>
  </a>   
</nav>

<script>
var testConnFail = 0;
var testConnFunc = function() {
	$.ajax({
		type : 'GET',
	    url : './doTestJson.do',
	    timeout: _qifu_jqXhrTimeout,
	    dataType : 'json',
	    data : {},
	    cache: false,
	    async: true,
	    success : function(data, textStatus) {
			if (data==null || (typeof data=='undefined') ) {
				alert('Unexpected error!');
				window.location = './index.do';
				return;
			}    			
			if ( _qifu_success_flag != data.login ) {
				alert("Please try login again!");
				window.location = './index.do';
				return;
			}       
			if ( _qifu_success_flag != data.isAuthorize ) {
				alert("No permission!");
				window.location = './index.do';
				return;        				
			}
	    },
	    error : function(jqXHR, textStatus, errorThrown) {
	    	testConnFail++;
	    	if (testConnFail>=3) {
		    	alert('網路不穩定: '+textStatus);
		    	window.location = './index.do';	    		
	    	}
	    }
	});	
};
setInterval(testConnFunc, (1000*60*3));
</script>

