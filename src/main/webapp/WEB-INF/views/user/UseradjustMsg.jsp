<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    //Controller로부터 전달받은 데이터
    String msg = CmmUtil.nvl((String) request.getAttribute("msg"));
    String url = CmmUtil.nvl((String) request.getAttribute("url"));

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>Adjust UserInfo Result</title>
    <script type="text/javascript">
        alert("<%=msg%>");
        window.location= '<%=url%>';
    </script>
</head>
<body>
</body>
</html>