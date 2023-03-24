<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/board.css" type="text/css" />
</head>
<body>
${logInfo } <a href="Logout.do">[로그아웃]</a>
<p>
<table style="width:600px;">
	<caption>게시판 리스트</caption>
<tr>
	<th>글번호</th>
	<th>제목</th>
	<th>이름</th>
	<th>작성일</th>
	<th>조회수</th>
</tr>
<c:forEach items="${list }" var="dto">
<tr>
	<td>${dto.no }</td>
	<td><a href="BoardView.do?no=${dto.no }">${dto.title }</a></td>
	<td>${dto.memberDto.name }(${dto.memberDto.id })</td>
	<td>${dto.regdate }</td>
	<td align="right">${dto.readcount }</td>
</tr>
</c:forEach>
</table>
<a href="BoardInsert.do">[글쓰기]</a>
</body>
</html>