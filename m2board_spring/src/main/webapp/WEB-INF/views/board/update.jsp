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
<div class="container mt-3">
	<div class="media border p-3" >
 		<h2>글 수정</h2>
		<form action="BoardUpdateAction.do" method="post" name="myform">
			<div class="input-group mb-3 input-group-sm">
				<input type="hidden" class="form-control"  name="no" required="required" value="${boardDto.no }">
			</div>
				<div class="form-floating mb-3 mt-3">
				<input type="text" class="form-control" name="title" autofocus="autofocus" placeholder="Enter Title" required="required" value="${boardDto.title }"/>
				<label for="title">제목</label>
			</div>
			<div>
				<textarea rows="5" name="content" id="content" required="required" style="width:100%;">${boardDto.content }</textarea>
				<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
					 oAppRef: oEditors,
					 elPlaceHolder: "content",
					 sSkinURI: "resources/se2/SmartEditor2Skin.html",
					 fCreator: "createSEditor2"
					});
				</script>
			</div>
			<div class="row">
				<div class="col-sm-6" align="left">
					<a href="BoardView.do?no=${boardDto.no }" role="button" class="btn btn-secondary">돌아가기</a>
				</div>
				<div class="col-sm-6" align="right">
					<input type="button" class="btn btn-primary" value="완료" onclick="goUpdate();" />
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>