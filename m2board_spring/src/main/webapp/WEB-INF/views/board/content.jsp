<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/board.css" type="text/css" />
<script type="text/javascript">
var confirm_delete = function(){
	if(!confirm('정말로 삭제하시겠습니까?')){
		return;		
	}
	
	location.href = 'BoardDeleteAction.do?no=${boardDto.no }';
}
</script>
</head>
<body>
<table width="600">
	<caption>글 상세보기</caption>
<tr>
	<th>글번호</th>
	<td>${boardDto.no }</td>
</tr>
<tr>
	<th>제목</th>
	<td>${boardDto.title }</td>
</tr>
<tr>
	<th>이름</th>
	<td>${boardDto.memberDto.name }(${boardDto.memberDto.id })</td>
</tr>
<tr>
	<th>조회수</th>
	<td>${boardDto.readcount }</td>
</tr>
<tr>
	<th>작성시간</th>
	<td>${boardDto.regdate }</td>
</tr>
<tr>
	<th>내용</th>
	<td>${boardDto.content }</td>
</tr>
<c:if test="${boardDto.fileList.size() != 0 }"> <!-- 파일이 있을 때만 출력 -->
<tr>
	<th>파일</th>
	<td>
		<c:forEach items="${boardDto.fileList }" var="fileDto" varStatus="st">
		file #${st.count} : <a href="Download.do?fno=${fileDto.fno }">${fileDto.targetName }</a> (${fileDto.fileSize }bytes)<br/>
		</c:forEach>
	</td>
</tr>
</c:if>
</table>
<a href="BoardList.do">[리스트]</a>
<c:if test="${boardDto.memberDto.id == sessionScope.userInfo.id }"> <!-- DB에서 가져온 해당 글 작성자 id와 세션 id가 일치 -->
	<a href="BoardUpdate.do?no=${boardDto.no }">[수정]</a>
	<a href="javascript:;" onclick="confirm_delete();">[삭제]</a>
</c:if>
</body>
</html>