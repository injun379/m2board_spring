<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/search-icon.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
$(document).ready(function(){

	var s = $('.search_icon'),
	f  = $('.search_form'),
	a = $('.after'),
	m = $('h4');
	
	$("#myInput").on("keyup", function(event) {
		
		if(event.keyCode == 27 || event.which == 27) {
			event.preventDefault();
			if( !f.hasClass('open') ) return;
			
			f.addClass('close');
			f.removeClass('open');
			s.val('');
			
			setTimeout(function(){
				f.removeClass('close');
				}, 1300);
		}

		var value = $(this).val();
		
		$("#myTable tr").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value.toLowerCase()) > -1)
		});
	});

	
	s.on('click', function(){
		if( f.hasClass('open') ) return;
		
		f.addClass('in');
		
		setTimeout(function(){
			f.addClass('open');
			f.removeClass('in');
			}, 1300);
		
		
	});
	
	a.on('click', function(e){
		e.preventDefault();
		if( !f.hasClass('open') ) return;
		f.addClass('close');
		f.removeClass('open');
		setTimeout(function(){
			f.removeClass('close');
			}, 1300);
		s.val('');
		var value = $(this).val().toLowerCase();
		 $("#myTable tr").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
	})
	
	
	
	f.submit(function(e){
		e.preventDefault();
		m.html('Thanks, high five!').addClass('show');
		f.addClass('explode');
		setTimeout(function(){
			f.removeClass('explode');
			m.removeClass('show');
			}, 1300);
	})

});
</script>
</head>
<body>

<div class="container mt-3">
	<p align="right">${logInfo } <a class="btn btn-light" role="button" href="Logout.do">로그아웃</a></p>
	<h2 align="center">게시판</h2>
	<br>
	<form class="search_form">
		<input class="search_icon" id="myInput" type="text" placeholder="빠른 검색" />
		<div class="after"></div>
		<input class="search_icon" type="submit" />
	</form>
	<br>
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
 		<tbody id="myTable">
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
	<a class="btn btn-primary" role="button" href="BoardInsert.do">글쓰기</a>
</div>
</body>
</html>