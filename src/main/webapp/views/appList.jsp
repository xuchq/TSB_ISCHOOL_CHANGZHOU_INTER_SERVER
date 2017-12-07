<%--
  Created by IntelliJ IDEA.
  User: xuchq
  Date: 2017/11/23
  Time: 下午14：00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>应用列表</title>
    <style>
        /** {
            padding: 0px;
            margin: 0px;
            list-style-type: none;
        }

        .appListContent {
            width: 970px;
            display: flex;
            flex-flow: row wrap;
        }

        .appListContent li {
            width: 320px;
            padding-top: 20px;
        }

        .appListContent li img {
            width: 75px;
            height: 75px;
            overflow: hidden;
            border-radius: 25px;
            background: red;
            float: left;
            margin-right: 10px;
        }

        .appListContent li h3 {
            font-size: 16px;
            color: #333;
            line-height: 100%;
            padding: 5px 0px 12px;
        }

        .appListContent li span {
            color: #ccc;
            font-size: 14px;
            display: inline-block;
        }*/

        *{ padding: 0px; margin: 0px;  list-style-type: none;}
        .appListContent{ width:1080px;}
        .appListContent li{ width: 360px; padding-top: 20px;overflow: hidden;float: left;}
        .appListContent li img{ width: 75px; height: 75px; overflow: hidden; border-radius: 25px; background: red; float: left; margin-right: 10px; }
        .appListContent li h3{ font-size: 16px; color: #333; line-height: 100%; padding: 5px 0px 12px;}
        .appListContent li span{ color: #ccc; font-size: 14px; display: inline-block; }
    </style>
</head>
<body>
<ul class="appListContent">
    <c:forEach var="app" items="${appList}" varStatus="status">
        <%--appId:${app.appId},appName:${app.appName},appUrl:${app.appUrl},personScope:${app.personScope}
        ,appLvl:${app.appLvl},appDesc:${app.appDesc},appImage:<img src="${app.appImage}">,
        appIcon1:<img src="${app.appIcon1}">,appIcon2:<img src="${app.appIcon2}">,
        intoSpace:${app.intoSpace},eduType:${app.eduType},appClassification:${app.appClassification},
        providerName:${app.providerName},providerId:${app.providerId}<br>--%>
        <li>
            <a href="../user/toThird.tsb?appUrl=${app.appUrl}" target="_blank"><img src="${app.appImage}" alt="appImage"></a>
            <h3>${app.appName}</h3>
            <span>${app.providerName}</span>
        </li>
    </c:forEach>
</ul>
</body>
</html>
