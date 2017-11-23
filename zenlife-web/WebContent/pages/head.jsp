<%@page import="org.qifu.util.SimpleUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String backUrl = (String) request.getParameter("backUrl");
%>

    <nav class="navbar navbar-expand-md navbar-inverse fixed-top bg-dark">
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
		
		 
        <img alt="menu list" src="./images/view.png" border="0">
        <!--
        <span class="navbar-toggler-icon"></span>
        -->
      </button>
      <%
      if ( null == backUrl || "".equals(SimpleUtils.getStr(backUrl).trim()) ) {
      %>
	  <a class="navbar-brand" href="./index.do"><img alt="ZenLife - 禪生活保健" src="./images/logo3.png" border="0"></a>
      <%
      } else {
      %>
      <a class="navbar-brand" href="<%=backUrl%>"><img alt="上一頁" src="./images/back.png" border="0"></a>
      <%
      }
      %>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="./index.do" onclick=""><b>首頁</b></a>
          </li>              
          <li class="nav-item">
            <a class="nav-link" href="./course.do" onclick=""><b>生活課堂</b></a>
          </li>      
          <li class="nav-item">
            <a class="nav-link" href="./bloodPressure.do" onclick=""><b>血壓</b></a>
          </li>                  
          <!--              
          <li class="nav-item">
            <a class="nav-link" href="#" onclick=""><b>血糖</b></a>
          </li> 
          -->                   
          <li class="nav-item">
            <a class="nav-link" href="#" onclick=""><b>保健商品</b></a>
          </li>      
          <li class="nav-item">
            <a class="nav-link" href="./personProfileEdit.do" onclick=""><b>個人資料</b></a>
          </li>                        
          <li class="nav-item">
            <a class="nav-link" href="./changePwEdit.do" onclick=""><b>變更密碼</b></a>
          </li>               
          <li class="nav-item">
            <a class="nav-link" href="./about.do" onclick=""><b>關於</b></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" onclick="logoutEvent();"><b>登出</b></a>
          </li>		  
        </ul>
      </div>
    </nav>
    
<br>
<br>
<br>
    