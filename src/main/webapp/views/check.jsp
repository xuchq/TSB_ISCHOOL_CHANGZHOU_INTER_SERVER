<%--
  Created by IntelliJ IDEA.
  User: xuchq
  Date: 2017/11/21
  Time: 上午10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>用户认证</title>
    <style>
        /*.checkContent {
            padding: 0px;
            margin: 0px;
        }

        .checkContent li {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            text-align: right;
            font-size: 14px;
            height: 40px;
        }

        .checkContent li span {
            width: 120px;
            padding-right: 10px;
        }

        .checkContent li span i {
            color: red;
        }

        .checkContent li input {
            border-radius: 5px;
            border: 1px solid #ccc;
            padding: 5px;
            margin-right: 10px;
        }

        .checkContent li input.h_upLoad {
            padding: 5px 15px;
            color: #fff;
            background: #009fff;
            font-size: 16px;
        }

        .checkContent li:last-child {
            padding-top: 20px;
        }*/
        li{list-style-type: none;}
        .checkContent{ padding: 0px; margin: 0px; }
        .checkContent li {align-items:center; font-size: 14px; height: 40px;}
        .checkContent li span{ width: 120px; padding-right: 10px; display: inline-block; text-align: right;}
        .checkContent li span i{ color: red; }
        .checkContent li input{ border-radius: 5px; border: 1px solid #ccc; padding: 0 5px; margin-right: 10px; height: 24px; line-height: 24px;}
        .checkContent li input.h_upLoad{ padding: 0 5px; height: 32px; color: #fff; background: #009fff; font-size: 16px; }
        .checkContent li:last-child{padding-top: 20px;}
    </style>
</head>
<body>
<form action="../user/userCheck.tsb" method="post" id="checkUser">
    <fieldset>
        <%--<legend>请先进行身份认证：</legend>--%>
        <h3>请先进行身份认证：</h3>
        <input type="text" id="sid" name="sid" value="${userInfo.sid}" style="display:none">
        <input type="text" id="lgnname" name="lgnname" value="${userInfo.lgnname}" style="display:none">
        <input type="text" id="occupation" name="occupation" value="${userInfo.occupation}" style="display:none">
        <%--以上字段不显示给用户--%>
        <ul class="checkContent">
            <li>
                <span><i>*</i>姓名:</span>
                <input type="text" id="name" name="name"
                       value="${userInfo.byname}${userInfo.forename}"><br>
            </li>
            <li>
                <span><i>*</i>身份证号:</span>
                <input type="text" id="certificateid" name="certificateid"
                       value="${userInfo.certificateid}"
                       style="width:200px;"
                       maxlength="18"><br>
            </li>
            <li>
                <span><i>*</i>身份:</span>
                <input type="text" value="${userInfo.juese}" disabled="disabled"><br>
            </li>
            <%--<select id="occupation">
                <option value="0">学生</option>
                <option value="1">老师</option>
                <option selected="selected" value="2">家长</option>
                <option value="3">学校工作人员</option>
                <option value="4">机构工作人员</option>
                <option value="5">其他</option>
            </select><br>--%>
            <li>
                <span><i>*</i>学校/机构:</span>
                <input type="text" id="schoolname" name="schoolname" value="${userInfo.schoolname}"
                       style="width:200px;"><br>
            </li>
            <li>
                <span><i>*</i>手机号:</span>
                <input type="text" id="mobile" name="mobile" value="${userInfo.mobile}"><br>
            </li>
            <li>
                <span><i>*</i>短信验证码:</span>
                <input type="text" id="verifyCode" name="verifyCode">
                <input type="button" value="获取短信验证码" id="getVerifyCode"><br>
            </li>
            <li>
                <span></span>
                <input type="button" value="提交认证" class="h_upLoad" id="userCheck">
                <%--<input type="button" class="h_upLoad" value="提交认证" id="userCheck">--%>
            </li>
        </ul>
    </fieldset>
</form>
<%--<script type="text/javascript" src="/TSB_ISCHOOL_CHANGZHOU_INTER_SERVER/WEB-INF/views/js/check.js"></script>--%>
<script type="text/javascript" src="/TSB_ISCHOOL_CHANGZHOU_INTER_SERVER/views/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/TSB_ISCHOOL_CHANGZHOU_INTER_SERVER/views/js/check.js"></script>
</body>
</html>
