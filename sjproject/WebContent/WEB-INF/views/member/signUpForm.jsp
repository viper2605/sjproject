<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
    <style>
    	/* number 태그 화살표 제거 */
	    input[type="number"]::-webkit-outer-spin-button,
	    input[type="number"]::-webkit-inner-spin-button {
	        -webkit-appearance: none;
	        margin: 0;
	    }
	</style>
</head>
<body>
	<div class="container">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/nav.jsp"%>

		<div class="py-5 text-center">
            <h2>회원 가입</h2>
        </div>

        <div class="row">
            <div class="col-md-6 offset-md-3">

                <form method="POST" action="signUp.do" class="needs-validation" name="signUpForm" onsubmit="return validate();">
				<%-- action="<%=request.getContextPath() %>/member/signUp.do" --%>
				
                    <!-- 아이디 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="id">* 아이디</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" name="id" id="id" maxlength="12" placeholder="아이디를 입력하세요" autocomplete="off" required>
                            <!-- required : 필수 입력 항목으로 지정 -->
                            <!-- autocomplete="off" : input 태그 자동완성 기능을 끔 -->
                        </div>
                        
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkId">&nbsp;</span>
                        </div>
                    </div>


                    <!-- 비밀번호 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="pwd1">* 비밀번호</label>
                        </div>
                        <div class="col-md-6">
                            <input type="password" class="form-control" id="pwd1" name="pwd1" maxlength="12" placeholder="비밀번호를 입력하세요" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkPwd1">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 비밀번호 확인 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="pwd2">* 비밀번호 확인</label>
                        </div>
                        <div class="col-md-6">
                            <input type="password" class="form-control" id="pwd2" maxlength="12" placeholder="비밀번호 확인" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkPwd2">&nbsp;</span>
                        </div>
                    </div>
                    <br>

                    <!-- 이름 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="name">* 이름</label>
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkName">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 전화번호 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="phone1">* 전화번호</label>
                        </div>
                        <!-- 전화번호1 -->
                        <div class="col-md-3">
                            <select class="custom-select" id="phone1" name="phone1" required>
                                <option>010</option>
                                <option>011</option>
                                <option>016</option>
                                <option>017</option>
                                <option>019</option>
                            </select>
                        </div>
                        
                        <!-- 전화번호2 -->
                        <div class="col-md-3">
                            <input type="number" class="form-control phone" id="phone2"  maxlength="4" name="phone2" required>
                        </div>
                        
                        <!-- 전화번호3 -->
                        <div class="col-md-3">
                            <input type="number" class="form-control phone" id="phone3"  maxlength="4" name="phone3" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkPhone">&nbsp;</span>
                        </div>
                    </div>

                    <!-- 이메일 -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="email">* Email</label>
                        </div>
                        <div class="col-md-6">
                            <input type="email" class="form-control" id="email" name="email" autocomplete="off" required>
                        </div>
                        
                        <div class="col-md-6 offset-md-3">
                            <span id="checkEmail">&nbsp;</span>
                        </div>
                    </div>
                    <br>

                    <!-- 주소 -->
                    <!-- 오픈소스 도로명 주소 API -->
                    <!-- https://www.poesis.org/postcodify/ -->
                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="postcodify_search_button">우편번호</label>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="post" class="form-control postcodify_postcode5">
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-primary" id="postcodify_search_button">검색</button>
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address1">도로명 주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_address" name="address1" id="address1">
                        </div>
                    </div>

                    <div class="row mb-3 form-row">
                        <div class="col-md-3">
                            <label for="address2">상세주소</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" class="form-control postcodify_details" name="address2" id="address2">
                        </div>
                    </div>

                    <!-- jQuery와 postcodify 를 로딩한다. -->
                    <script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
                    <script>
                        // 검색 단추를 누르면 팝업 레이어가 열리도록 설정한다.
                        $(function(){
                            $("#postcodify_search_button").postcodifyPopUp();
                        });
                    </script>

                    <!-- 관심분야 -->
                    <hr class="mb-4">
                    <div class="row">
                        <div class="col-md-3">
                            <label>관심 분야</label>
                        </div>
                        <div class="col-md-9 custom-control custom-checkbox">
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="sports" value="운동"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="sports">운동</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="movie" value="영화"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="movie">영화</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="music" value="음악"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="music">음악</label>
                            </div>
                            <br>
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="cooking" value="요리"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="cooking">요리</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="game" value="게임"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="game">게임</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input type="checkbox" name="memberInterest" id="etc" value="기타"
                                class="form-check-input custom-control-input">
                                <label class="form-check-label custom-control-label" for="etc">기타</label>
                            </div>
                        </div>
                    </div>

                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block" type="submit">가입하기</button>
                </form>
            </div>
        </div>
        <br><br>
        
        <script>
        // 각 유효성 검사 결과를 저장할 객체
        var signUpCheck = { 
        		"id":false,
        		"idDup":false,
				"pwd1":false,
				"pwd2":false,
				"name":false,
				"phone":false,
				"email":false
				};
        
   	 	// 실시간 입력 형식 검사
		// 정규표현식
		$(document).ready(function(){
			
			// jQuery 변수 : 변수에 직접적으로 jQuery메소드를 사용할 수 있음.
			var $id = $("#id");
			var $pwd1 = $("#pwd1");
			var $pwd2 = $("#pwd2");
			var $name = $("#name");
			var $phone2 = $("#phone2");
			var $phone3 = $("#phone3");
			var $email = $("#email");
			
			
			// 아이디  유효성 검사
			// 첫글자는 영어 소문자, 나머지 글자는 영어 대,소문자 + 숫자, 총 6~12글자
			
			// 1. 실시간 유효성 검사
			
			$id.on("input",function(){
				var regExp = /^[a-z][a-zA-z\d]{5,11}$/;
				if( !regExp.test($id.val())){
					$("#checkId").text("아이디의 형식이 유효하지 않습니다.")
								.css("color","red");
					signUpCheck.id=false;
					
					
				}else{
					
					signUpCheck.id=true;
					// 유효 X , 사용X , 사용O
					// aJax를 이용한 실시간 아이디 중복검사
					$.ajax({
						url : "idDupCheck.do",
						data : {id : $id.val()},
								    // 아이디 값 저장 장소
						type : "GET",
						success : function(result){
							// 서블릿 완성 후 여기 다시와서 작성
							
							if(result == "yes"){
								$("#checkId").text("사용 가능한 아이디 입니다.")
								.css("color","green");
								
								signUpCheck.idDup = true;
								// 사인업 체크 영역에 아이디 중복검사.
							}else{
								$("#checkId").text("사용할 수 없는 아이디 입니다.")
								.css("color","red");
							}
							
							
						},		    
						error : function(e){
							console.log("아이디 중복검사 ajax 실패");
							console.log(e);
							
						}	
						
						
						
					});
					
				}
			});
			
			
			
			// 아이디 중복 체크창 오픈
		//	$("#idDupCheck").click(function(){
			//	window.open("idDupForm.do","idDupForm", "width=300 , height=200" );
				// 			팝업창 주소			팝업창의 이름
		//	});		
			
			// 비밀번호  유효성 검사
			//영어 대,소문자 + 숫자, 총 6~12글자
			
			  $pwd1.on("input",function(){
				var regExp = /^[A-Za-z0-9]{6,12}$/;
				if( !regExp.test($pwd1.val())){
					$("#checkPwd1").text("비밀번호의 형식이 유효하지 않습니다")
								.css("color","red");
					signUpCheck.pwd1=false;
					
				}else{
					$("#checkPwd1").text("유효한 비밀번호 입니다.")
								.css("color","green");
					signUpCheck.pwd1=true;
				} 
			}); 
			
			
			
			// 비밀번호 일치 여부
		  	 $pwd2.on("input",function(){
				
				if($pwd1.val().trim() != $pwd2.val().trim()){
					$("#checkPwd2").text("비밀번호가 일치하지 않습니다.")
					.css("color","red");
						signUpCheck.pwd2=false;
				}else{
					
						$("#checkPwd2").text("비밀번호가 일치합니다.")
						.css("color","green");
						signUpCheck.pwd2=true;
				
				} 
			}); 
			
			
			// 이름 유효성 검사
			// 한글 두 글자 이상
				
			 $name.on("input",function(){
				var regExp = /^[가-힣]{2,}$/;
				if( !regExp.test($name.val())){
					signUpCheck.name=false;
				}else{
					signUpCheck.name=true;
				}
				
			}); 
			
		
			
			// 전화번호 관련
		 	$(".phone").on("input",function(){
		 		
				// 전화번호 input 태그에 4글자 이상 입력하지 못하게 하는 이벤트
                if ($(this).val().length > $(this).prop("maxLength")){
                    $(this).val($(this).val().slice(0, $(this).prop("maxLength")));
                }
                
				// 전화번호 유효성 검사
                var regExp2 =  /^\d{3,4}$/; // 숫자 3~4 글자
                var regExp3 =  /^\d{4,4}$/; // 숫자 4 글자
                
                // 단순하게 하면 안됨
				if(!regExp2.test( $phone2.val()) || !regExp3.test( $phone3.val()) ){
					$("#checkPhone").text("전화번호가 유효하지 않습니다.")
									.css("color","red");
					signUpCheck.phone=false;
				}else{
					$("#checkPhone").text("유효한 전화번호 형식입니다")
					.css("color","green");
					signUpCheck.phone=true;
				}
            });
			
			
		 	// 이메일 유효성 검사
			$email.on("input",function(){
				var regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/; 
				if(!regExp.test($email.val())){
					$("#checkEmail").text("이메일이 유효하지 않습니다.")
					.css("color","red");
					signUpCheck.email=false;
				}else{
					$("#checkEmail").text("유효한 이메일입니다.")
					.css("color","green");
					signUpCheck.email=true;
					}  
				});
				
			});
			
   	 	
		// submit 동작
		function validate(){
			
			// 아이디 중복 검사 결과
			// if( $("#idDup").val() == "true")	signUpCheck.idDup = true;
			// else				  				signUpCheck.idDup = false;
			
			for(var key in signUpCheck){
				// singUpCheck 객체중 key 값을 가진 
				
				if(!signUpCheck[key]){
					alert("일부 입력값이 잘못되었습니다.");
					var id = "#"+key;
							// id 선택자가 됨
					$(id).focus();
					return false;
				}
			}
		}
		
        
		
        </script>
		
		<%@ include file="../common/footer.jsp"%>
	</div>
</body>
</html>
