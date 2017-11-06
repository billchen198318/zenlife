<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="<%=basePath%>/tether/tether.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script type="text/javascript" src="<%=basePath%>/tether/tether.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="<%=basePath%>/popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="<%=basePath%>/jquery/jquery-3.1.1.min.js?ver=${jsVerBuild}"></script>
<link rel="stylesheet" href="<%=basePath%>/bootstrap-4/css/bootstrap.css?ver=${jsVerBuild}" crossorigin="anonymous">
<link href="<%=basePath%>/font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/bootstrap-4/js/bootstrap.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="<%=basePath%>/configJs.do?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="<%=basePath%>/js/f.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>
