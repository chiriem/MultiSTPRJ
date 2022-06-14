<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    //Controller 에 저장된 세션으로 로그인 할 때 생성됨
    String SS_USER_ID = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
    String res = CmmUtil.nvl((String) request.getAttribute("res"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>로그아웃되었습니다.</title>
    <%
        String msg = "";
        if (res.equals("0")) {
            msg = SS_USER_ID + "로그아웃 완료!";
        } else if (res == null) {
            msg = "바랍니다" + res;
        } else {
            msg = "시스템에 문제가 발생하였습니다. 잠시 후 다시 시도하여 주시길 바랍니다" + res;
        }
    %>
</head>
<body>
<%=msg %>
<a href="/index">go</a>
</body>
</html>