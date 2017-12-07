<%--
  Created by IntelliJ IDEA.
  User: xuchq
  Date: 2017/11/21
  Time: 上午11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--<meta charset="UTF-8">--%>
    <title>青果报错de页面</title>
    <link rel="stylesheet" href="http://www.iqingguo.cn/Public/style/global.css" rel="stylesheet">
    <style type="text/css">
        body{background: #ededed;}
        .errordiv{width: 1080px; margin: 0px auto; min-height: 226px;}
        .errorp1{text-align: center;margin: 200px auto 10px;}
        .errorp2{margin: 20px auto; font-size: 24px; color: #607b69; text-align: center;}
        .errorp3{margin: 20px auto; width: 197px;}
        .errorp3 a{background: #6bb258; width: 197px; height: 44px; border-radius: 20px; display: block; text-align: center; line-height: 44px; font-size: 24px; color: #fff; text-decoration: none;}
    </style>
</head>
<body>
<div class="errordiv">
    <p class="errorp1"><img src="imgs/error-bg.png"> </p>
    <p class="errorp2">${errorMessage}</p>
    <p class="errorp3"><a href="javascript:window.location.reload()">重试</a> </p>
</div>
</body>
</html>
