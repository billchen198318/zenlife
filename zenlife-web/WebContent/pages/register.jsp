<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #ffffff;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}

</style>

<script type="text/javascript">


</script>

</head>

<body>

<div class="container">   
<form class="form-signin" name="loginForm" id="loginForm" action="./login.do" method="post">

    <div><img src="./images/logo2.png" width="48" height="48" border="0"/>&nbsp;&nbsp;&nbsp;<b><font color="#041431">ZenLife - 禪生活保健</font></b></div>
   
    <br/>
   
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   
    <br/>
    
    <label>ZenLife 0.1 version</label>

</form>
</div>

</body>
</html>
