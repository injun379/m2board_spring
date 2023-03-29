<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="resources/js/ajax.js"></script>
<!-- 다음 주소 찾기 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
var user_id_count = 0; //1:사용할 수 없는 아이디, 0:사용할 수 있는 아이디, -1:시스템 오류
var user_id = '';

function openZipcode(){
	new daum.Postcode({
        oncomplete: function(data) { //data : 사용자가 선택한 주소 정보를 담고있는 객체
        	//alert('우편번호 : ' + data.zonecode + '\n도로명주소 : ' + data.roadAddress);
			var f = document.regist; //<form>태그의 name으로 접근
			f.zipcode.value = data.zonecode;
			f.address1.value = data.roadAddress;
        }
    }).open();
}

function validate(){
	var f = document.regist; //f는 <form>태그
	
	if(f.user_id.value.length < 3 || f.user_id.value.length > 12){
		alert('아이디는 3글자 이상 12글자 이하로 작성하세요.');
		f.user_id.focus();
		return;
	}
	
	if(f.user_name.value.length < 2 || f.user_name.value.length > 6){
		alert('이름은 2글자 이상 6글자 이하로 작성하세요.');
		f.user_name.focus();
		return;
	}
	
	if(f.user_pw.value.length < 4 || f.user_pw.value.length > 20){
		alert('비밀번호는 4글자 이상 20글자 이하로 작성하세요.');
		f.user_pw.focus();
		return;
	}
	
	if(f.user_pw.value != f.user_pw2.value){
		alert('비밀번호/비밀번호 확인이 일치하지 않습니다.');
		f.user_pw.focus();
		return;
	}
	
	if(f.user_birth.value == ''){
		alert('생년월일을 입력하세요.');
		f.user_birth.focus();
		return;
	}
	
	if(f.user_phone.value == ''){
		alert('연락처를 입력하세요.');
		f.user_phone.focus();
		return;
	}
	
	if(f.zipcode.value == ''){
		alert('우편번호를 입력하세요.');
		f.zipcode.focus();
		return;
	}
	
	if(f.address1.value == ''){
		alert('주소를 입력하세요.');
		f.address1.focus();
		return;
	}
	
	if(f.address2.value == ''){
		alert('상세주소를 입력하세요.');
		f.address2.focus();
		return;
	}
	
	if(user_id_count != 0){
		alert('사용할 수 있는 아이디가 아닙니다.');
		f.user_id.focus();
		return;
	}
	
	f.method = 'POST';
	f.action = 'MemberRegisterAction.do';
	f.submit();
	
}


function change_user_id(){ //아이디 중복체크(ajax 이용)
	//console.log(document.regist.user_id.value);
	
	var f = document.regist;
	user_id = f.user_id.value;
	
	if(user_id.length < 3 || user_id.length > 12){
		document.getElementById('check_id_result').innerHTML = '아이디는 3글자 이상 12글자 이하로 작성하세요.';
		return;
	}
	
	new Request('CheckID.do', 'user_id='+user_id, callback, 'POST');
	
}


function callback(xhr){
	if(xhr.readyState == 4){
		if(xhr.status == 200){
			user_id_count = parseInt(xhr.responseText.trim());
			//alert('user_id_count : ' + user_id_count);
			if(user_id_count == 1){
				document.getElementById('check_id_result').innerHTML = '<font style="color:red;">' + user_id + '는 이미 사용중인 아이디입니다.</form>';
				return;
			}else if(user_id_count == 0){
				document.getElementById('check_id_result').innerHTML = '<font style="color:green;">' + user_id + '는 사용 가능한 아이디입니다.</form>';
				return;
			}else{
				alert('시스템 오류입니다...')
			}
		}
	}
}

</script>
</head>
<body>
<div class="container mt-3" style="width:500px;">
	<div class="media border p-3" align="center">
		<h2>회원 가입</h2>
		<form name="regist" method="post">
			<div  align="right"><label for="user_id"id="check_id_result">영문자+숫자 3~12문자로 작성하세요</label></div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">아이디</span>
				<input type="text" class="form-control" name="user_id" id="user_id" onkeyup="change_user_id()"/>
			</div>
			<div  align="right">
				<label for="user_name">2~6문자</label>
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">이름</span>
				<input type="text" class="form-control"name="user_name" id="user_name" />
			</div>
			<div align="right">
				<label for="user_pw">4~20문자</label>
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">비밀번호</span>
				<input type="password" class="form-control" name="user_pw" id="user_pw" />
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">비밀번호 확인</span>
				<input type="password" class="form-control" name="user_pw2" id="user_pw2" />
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">생일</span>
				<input type="date" class="form-control" name="user_birth" id="user_birth" />
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">전화</span>
				<input type="text" class="form-control" name="user_phone" id="user_phone" />
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">우편번호</span>
				<input type="text" class="form-control" name="zipcode" readonly="readonly" onclick="alert('검색버튼을 이용하여 입력하세요');" style="width:55px;"/>
				<input type="button" value="검색" name="btnZipcode" onclick="openZipcode()" />
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">주소</span>
				<input type="text" class="form-control" name="address1" style="width:250px;"/><br/>
			</div>
			<div class="input-group mb-3 input-group-sm">
				<span class="input-group-text" style="width:105px;">상세 주소</span>
				<input type="text" class="form-control" name="address2" /><br/>
			</div>
			<div class="row">
				<div class="col-sm-6" align="left">
					<a class="btn btn-secondary" role="button" href="Login.do" >로그인 화면으로</a>
				</div>
				<div class="col-sm-6" align="right">
					<input type="button" class="btn btn-primary" value="완료" onclick="validate()" />
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>