<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세보기</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
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
<div class="container mt-3 border p-3">
	<h2>${boardDto.no }. ${boardDto.title }</h2>
	<p>${boardDto.memberDto.name }(${boardDto.memberDto.id }) <small><i>${boardDto.regdate }</i></small></p>
	<p>조회수 : ${boardDto.readcount }</p>
	<div class="media border p-3">
		<div class="media-body">
			
			${boardDto.content }   
     		 <c:forEach items="${boardDto.fileList }" var="fileDto" varStatus="st">
     		 	<img src="resources/upload/${fileDto.folder }/${fileDto.targetName }" width="100%" />
				file #${st.count} : <a href="Download.do?fno=${fileDto.fno }">${fileDto.targetName }</a> (${fileDto.fileSize }bytes)<br/>
			</c:forEach>
    	</div>
  	</div>
	<br/>
	<div class="row">
		<div class="col-sm-6" align="left">
   			<a class="btn btn-secondary" role="button" href="BoardList.do" >목록으로</a>
  		</div>
 		<div class="col-sm-6" align="right">
			<div class="btn-group">
				<c:if test="${boardDto.memberDto.id == sessionScope.userInfo.id }"> <!-- DB에서 가져온 해당 글 작성자 id와 세션 id가 일치 -->
					<a class="btn btn-primary" role="button" href="BoardUpdate.do?no=${boardDto.no }">수정</a>
					<a class="btn btn-danger" role="button" href="javascript:;" onclick="confirm_delete();">삭제</a>
				</c:if>
			</div>
		</div>
	</div>
</div>
</body>
</html>