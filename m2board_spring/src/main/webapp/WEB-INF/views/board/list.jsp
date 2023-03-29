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
</head>
<body>

<div class="container">
	<h2>게시판</h2>
	<p>${logInfo } <a class="btn btn-light" role="button" href="Logout.do">로그아웃</a></p>

	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
 		<tbody>
			<c:forEach items="${list }" var="dto">
			<tr>
				<td>${dto.no }</td>
				<td><a href="BoardView.do?no=${dto.no }">${dto.title }</a></td>
				<td>${dto.memberDto.name }(${dto.memberDto.id })</td>
				<td>${dto.regdate }</td>
				<td align="right">${dto.readcount }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<a class="btn btn-secondary" role="button" href="BoardInsert.do">글쓰기</a>
</div>
</body>
</html>