<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
function goUserRegist(){  //회원가입
	location.href='MemberRegister.do';
}
</script>
</head>
<body>
<div class="container mt-3">
 <div class="media border p-3" style="width:max-content;">
  <h2>로그인</h2>
  <form name="loginform" action="LoginAction.do" method="post">
    <div class="form-floating mb-3 mt-3">
      <input type="text" class="form-control"  name="id" autofocus="autofocus" placeholder="Enter ID" required="required" value="${cookie.id.value }">
      <label for="id">ID</label>
 	</div>
    <div class="form-floating mb-3 mt-3">
      <input type="password" class="form-control" placeholder="Enter Password" name="password" required="required">
      <label for="password">Password</label>
    </div>
    <div class="form-check form-switch">
     <input class="form-check-input" type="checkbox" name="setid" value="1" <c:if test="${id != null }">checked</c:if>>
     <label class="form-check-label" for="setid">ID 저장</label>
    </div>
    <div>
     <button type="submit" class="btn btn-primary" >확인</button>
     <button type="button" class="btn btn-primary" onclick="javascript:goUserRegist();">회원가입</button>
   </div>
  </form>
 </div>
</div>

</body>
</html>











