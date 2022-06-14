<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR" %>
<%
    String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="EUC-KR">
    <title>회원가입 화면</title>
    <script type="text/javascript">
        //회원가입 정보의 유효성 체크하기
        function doRegUserCheck(f) {

            if (f.user_id.value == "") {
                alert("아이디를 입력하세요.");
                f.user_id.focus();
                return false;
            }

            if (f.user_nm.value == "") {
                alert("이름을 입력하세요.");
                f.user_nm.focus();
                return false;
            }

            if (f.user_pw.value == "") {
                alert("비밀번호를 입력하세요.");
                f.user_pw.focus();
                return false;
            }

            if (f.user_pw2.value == "") {
                alert("비밀번호확인을 입력하세요.");
                f.user_pw2.focus();
                return false;
            }

            if (f.age.value == "") {
                alert("출생년도를 입력하세요.");
                f.age.focus();
                return false;
            }
        }
    </script>
</head>
<body>

<h1>회원가입 화면</h1>
<br/>
<br/>
<form name="f" method="GET" action="/user/updateUserInfo" onsubmit="return doRegUserCheck(this);">
    <table border="1">
        <col width="150px">
        <col width="150px">
        <col width="150px">
        <col width="150px">
        <tr>
            <td>아이디 확인</td>
            <td><input type="text" name="user_id" style="width:150px" value="<%= SS_USER_ID %>" readonly/></td>
            <td>이름</td>
            <td><input type="text" name="user_nm" style="width:150px"/></td>
        </tr>
        <tr>
            <td>비밀번호</td>
            <td><input type="password" name="user_pw" style="width:150px"/></td>
            <td>비밀번호 확인</td>
            <td><input type="password" name="user_pw2" style="width:150px"/></td>
        </tr>
        <tr>
            <td>출생년도</td>
            <td colspan="3"><input type="text" name="age" style="width:450px"/></td>
        </tr>
    </table>
    <input type="submit" value="회원가입"/>
</form>
</body>
</html>

