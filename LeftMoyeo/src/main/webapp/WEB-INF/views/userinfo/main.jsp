<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   성공
    <br>

    <br>
 <input type="button" value="로그아웃"
  onclick="location.href='/testmoyeo/user/logout';">
  
   <input type="button" value="마지막 로그인"
  onclick="lastlogin();">
  
  <script>
   function lastlogin() {
       // user.jsp로 이동
       location.href = '/testmoyeo/user/user';
}
</script>
</body>
</html>