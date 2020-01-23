<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<style>
    .insert-label {
      display: inline-block;
      width: 80px;
      line-height: 40px
    }
</style>
</head>
<body>
	<div class="container">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/nav.jsp"%>

		<div class="container">

			<h3>게시글 등록</h3>
			<hr>
			<form action="<%=request.getContextPath()%>/board/insert" method="post" 
		
				  enctype="multipart/form-data" role="form" onsubmit="return validate();">
				<!--enctype="multipart/form-data" : 파일 업로드 시 꼮 필요 -->

				<div class="mb-2">
					<label class="input-group-addon mr-3 insert-label">카테고리</label> 
					<select	class="custom-select" id="category" name="category" style="width: 150px;">
						<option value="10">운동</option>
						<option value="20">영화</option>
						<option value="30">음악</option>
						<option value="40">요리</option>
						<option value="50">게임</option>
						<option value="60">기타</option>
					</select>
				</div>
				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">제목</label> 
					<input type="text" class="form-control" id="title" name="title" size="70">
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">작성자</label>
					<h5 class="my-0" id="writer"><%= loginMember.getMemberId() %></h5>
				</div>


				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">작성일</label>
					<h5 class="my-0" id="today"></h5>
				</div>

				<hr>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">썸네일</label>
					<div class="boardImg" id="titleImgArea">
						<img id="titleImg" width="200" height="200">
								<!-- 이미지 크기 다름, 다르게 사용하려고 아래 이미지와 다른 id 사용함. -->
					</div>
				</div>

				<div class="form-inline mb-2">
					<label class="input-group-addon mr-3 insert-label">업로드<br>이미지</label>
					<div class="mr-2 boardImg" id="contentImgArea1">
						<img id="contentImg1" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea2">
						<img id="contentImg2" width="150" height="150">
					</div>

					<div class="mr-2 boardImg" id="contentImgArea3">
						<img id="contentImg3" width="150" height="150">
					</div>
				</div>


				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
				<!-- multiple 속성
						- input 요소 하나에 둘 이상의 값을 입력할 수 있음을 명시
				 -->

				
					<input type="file" id="img1" 
						name="img1" onchange="LoadImg(this,1)"> 
						
					<input type="file" id="img2" 
						name="img2" onchange="LoadImg(this,2)">
						
					<input type="file" id="img3" 
						name="img3" onchange="LoadImg(this,3)">
						
					<input type="file" id="img4" 
						name="img4" onchange="LoadImg(this,4)">
						
				</div>

				<div class="form-group">
					<div>
						<label for="content">내용</label>
					</div>
					<textarea class="form-control" id="content" name="content"
						rows="10" style="resize: none;"></textarea>
				</div>


				<hr class="mb-4">

				<div class="text-center">
					<button type="submit" class="btn btn-primary">등록</button>
					<button type="button" class="btn btn-primary">목록으로</button>
				</div>

			</form>
		</div>

		<%@ include file="../common/footer.jsp"%>
	</div>

	<script>
		// 오늘 날짜 출력 
		var today = new Date();

		var str = today.getFullYear() + "-" + today.getMonth() + 1 + "-"
				+ today.getDate();
		$("#today").html(str);

		// 유효성 검사
		function validate() {
			if ($("#title").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#title").focus();
				return false;
			}

			if ($("#content").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#content").focus();
				return false;
			}
		}
		
		// 이미지 공간을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
	    $(function () {
	    	// 파일 선택 버튼이 있는 영역을 보이지 않게 함
			$("#fileArea").hide();
	    	
	    	// 이미지 영역 클릭 시 파일 첨부창 띄우기
	    	$("#titleImgArea").click(function(){
	    		$("#img1").click();
	    		// 클릭해라~ 라는 뜻
	    	});
	    	
	    	$("#contentImgArea1").click(function(){
	    		$("#img2").click();
	    		// 클릭해라~ 라는 뜻
	    	});
	    	
	    	$("#contentImgArea2").click(function(){
	    		$("#img3").click();
	    		// 클릭해라~ 라는 뜻
	    	});
	    	
	    	$("#contentImgArea3").click(function(){
	    		$("#img4").click();
	    		// 클릭해라~ 라는 뜻
	    	});
	    	
	    	
	    });

	    // 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
	    function LoadImg(value, num) {
	    	
	    	// 파일 업로드 시 업로드 된 파일의 경로는 
	    	// 해당 요소에 files라는 '배열'이 생성되면 저장됨
	    	if(value.files && value.files[0])
	    		// 파일이 선택이 된 경우!!   <-- 경로
	    		var reader = new FileReader();
	    						// 자바에서 제공하는 함수, 경로의 파일을 읽어옴!
	    		reader.onload = function(e){
	    			switch(num){
	    			
	    			case 1: $("#titleImg").prop("src", e.target.result); break // reader.onload 에 result 결과 값이 있음
	    			case 2: $("#contentImg1").prop("src", e.target.result); break // reader.onload 에 result 결과 값이 있음
	    			case 3: $("#contentImg2").prop("src", e.target.result); break // reader.onload 에 result 결과 값이 있음
	    			case 4: $("#contentImg3").prop("src", e.target.result); break // reader.onload 에 result 결과 값이 있음
	    			
	    			}
	    		}	
	    		// file에서 내용(Content)을 읽어옴
	    		// + base64 인코딩 된 경로를 반환 
	    		reader.readAsDataURL(value.files[0]);				
	    }
		
	</script>
</body>
</html>
