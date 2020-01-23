<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
</head>
<style>
	#content-main{
	height: 830px;}
</style>
<body>
	<div class="container" id="content-main">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/nav.jsp"%>

		<div class="row">
			<%@ include file="sideMenu.jsp"%>

			<div class="col-sm-8">
				<h3>비밀번호 변경</h3>
				<hr>
				<div class="bg-white rounded shadow-sm container p-3">
					<form class="form-horizontal" role="form"
					method="POST" action="updatePwd.do" onsubmit="return validate();">
						<!-- 아이디 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>아이디</h6>
							</div>
							<div class="col-md-6">
								<h5 id="id"><%= loginMember.getMemberId() %></h5>
							</div>
						</div>

						<hr>
						<!-- 현재 비밀번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>현재 비밀번호</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="currentPwd"
									name="currentPwd">
							</div>
						</div>

						<!-- 새 비밀번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>새 비밀번호</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="newPwd1"
									name="newPwd1">
							</div>
						</div>

						<!-- 새 비밀번호 확인-->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>새 비밀번호 확인</h6>
							</div>
							<div class="col-md-6">
								<input type="password" class="form-control" id="newPwd2"
									name="newPwd2">
							</div>
						</div>
						
						<hr class="mb-4">
						<button class="btn btn-primary btn-lg btn-block" type="submit">변경하기</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../common/footer.jsp"%>

	

	<script>
		
	// submit 동작
		function validate() {
			// 비밀번호 유효성 검사
			// 영어 대,소문자+숫자, 총 6~12글자
			
			var regExp = /^[A_Za-z\d]{6,12}$/;
			
			if(!regExp.test($("#newPwd1").val() )){
				alert("유효하지 않은 비밀번호 입니다.");
				$("#newPwd1").val("");
				$("#newPwd1").focus();
				
				return false;
			}
			// 새 비밀번호 일치여부 확인
			if($("#newPwd1").val() != $("#newPwd2").val()){
				alert("새 비밀번호가 일치하지 않습니다.")
				$("#newPwd2").val("");
				$("#newPwd2").focus();
			
				return false;
			}
			
		}
		
	</script>
	
</body>
</html>
