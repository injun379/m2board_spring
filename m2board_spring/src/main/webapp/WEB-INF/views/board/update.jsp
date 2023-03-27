<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/board.css" type="text/css" />
<script type="text/javascript" src="resources/se2/js/service/HuskyEZCreator.js"></script>
<script type="text/javascript">
var goUpdate = function(){
	var f = document.myform;
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용된다.
	f.submit();
}
</script>
</head>
<body>
<form action="BoardUpdateAction.do" method="post" name="myform">
<table>
	<caption>글 수정</caption>
<tr>
	<th>번호</th>
	<td>${boardDto.no }<input type="hidden" name="no" autofocus="autofocus" required="required" value="${boardDto.no }" readonly="readonly"/></td>
</tr>
<tr>
	<th>제목</th>
	<td><input type="text" name="title" style="width:645px;" autofocus="autofocus" required="required" value="${boardDto.title }"/></td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="5" style="width:645px;"  name="content" id="content" required="required" >${boardDto.content }</textarea>
		<script type="text/javascript">
			var oEditors = [];
			nhn.husky.EZCreator.createInIFrame({
			 oAppRef: oEditors,
			 elPlaceHolder: "content",
			 sSkinURI: "resources/se2/SmartEditor2Skin.html",
			 fCreator: "createSEditor2"
			});
		</script>
	</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="submit" value="완료" />
	</td>
</tr>
</table>
</form>
</body>
</html>