<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<meta charset="utf-8" />
<title>Moyeo</title>
<style type="text/css">
/* 중복아이디 존재하지 않는경우 */
.id_input_re_1{
	color : green;
	display : none;
}
/* 중복아이디 존재하는 경우 */
.id_input_re_2{
	color : red;
	display : none;
}
</style>
</head>
<body>
   <section>
      <h1>회원가입 페이지</h1>
   </section>

   <section>
      <form name="login" action="join" method="post">
         아이디<br> 
         <input type="text" class="id_input" name="id" placeholder="아이디 입력"><br>
		 <span class="id_input_re_1">사용 가능한 아이디입니다.</span>
		 <span class="id_input_re_2">아이디가 이미 존재합니다.</span>
       	 <br>
         비밀번호<br> 
         <input type="password" name="pw" id="pw"><br>
         비밀번호 재확인<br>
         <input type="password" id="pwConfirm" onkeyup="passConfirm()">
         <span id="confirmMsg"></span> <br>
         이름<br> 
         <input type="text" name="name" value=""> <br>
         생년월일<br> 
         <input type="text" name="birth" placeholder="2000-01-01"><br> <br> 
         
         우편번호<br>
         <input type="text" id="sample4_postcode" placeholder="우편번호">
		 <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
		 <input type="text" id="sample4_roadAddress" placeholder="도로명주소" size="60" ><br>
		 <input type="hidden" id="sample4_jibunAddress" placeholder="지번주소"  size="60">
		 <span id="guide" style="color:#999;display:none"></span>
		 <input type="text" id="sample4_detailAddress" placeholder="상세주소"  size="60"><br>
		 <input type="hidden" id="sample4_extraAddress" placeholder="참고항목"  size="60">
		 <input type="hidden" id="sample4_engAddress" placeholder="영문주소"  size="60" ><br>
         
         
         성별<br> 
         <label for="man">남자</label> 
         <input type="radio" name="gender" value="m" id="man"> 
         <label for="woman">여자</label> 
         <input type="radio" name="gender" value="f" id="woman"> <br> <br>
         
         <h3>이메일</h3>
         <input type="text" id="user_email" required>
         <span id="middle">@</span>
         <input type="text" id="email_address" list="user_email_address">
         <datalist id="user_email_address">
            <option value="naver.com"></option>
            <option value="daum.com"></option>
            <option value="google.com"></option>
            <option value="직접입력"></option>
         </datalist>
         <input type="hidden" id="totalemail" name="email" value=""> <br>
		 <div class="input-group-addon">
               <button type="button" class="btn btn-primary" id="mail-Check-Btn">본인인증</button>
            </div>
               <div class="mail-check-box">
            <input class="mail-check-input" placeholder="인증번호 6자리를 입력해주세요!" disabled="disabled" maxlength="6">
            </div>
            <div>
            	<span id="mail-check-warn"></span>
            </div>  
         휴대전화<br> 
         <input type="text" name="phone" placeholder="010-****-****"><br> <br> <br> <br>
         <input type="submit" value="제출">
      </form>
   </section>

   <script>
   	   /* 아이디 중복검사 */
   	   
   	   //아이디 중복검사
		$('.id_input').on("propertychange change keyup paste input", function(){
		
			//console.log("keyup 테스트");	
			var id = $('.id_input').val();			// .id_input에 입력되는 값
			var data = {id : id}				// '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'
	
			$.ajax({
				type : "post",
				url : "/testmoyeo/user/memberIdChk", 
				data : data,
				success : function(result){
					//console.log("성공 여부" + result);
					if(result != 'fail'){
						$('.id_input_re_1').css("display","inline-block");
						$('.id_input_re_2').css("display", "none");				
					} else {
						$('.id_input_re_2').css("display","inline-block");
						$('.id_input_re_1').css("display", "none");				
					}
					
				}//success 종료
			}); // ajax 종료	
		});// function 종료
   	   
		
		/* 비밀번호 중복 검사 */
		function passConfirm() {
	        var pw = document.getElementById('pw'); //비밀번호 
	        var pwConfirm = document.getElementById('pwConfirm'); //비밀번호 확인 값
	        var confirmMsg = document.getElementById('confirmMsg'); //확인 메세지
	        var correctColor = "green"; //맞았을 때 출력되는 색깔.
	        var wrongColor = "red"; //틀렸을 때 출력되는 색깔
	         
	        if(pw.value == pwConfirm.value){//password 변수의 값과 passwordConfirm 변수의 값과 동일하다.
		    	confirmMsg.style.color = correctColor;/* span 태그의 ID(confirmMsg) 사용  */
		        confirmMsg.innerHTML ="비밀번호 일치";/* innerHTML : HTML 내부에 추가적인 내용을 넣을 때 사용하는 것. */
	        } else {
		        confirmMsg.style.color = wrongColor;
		        confirmMsg.innerHTML ="비밀번호 불일치";
	        }
    	}
   	   
       /* 이메일 */
       
	   // user_email blur event
	   $("#user_email").blur(email);
	
	   // email_address blur event
	   $("#email_address").blur(email);
	   
	   /* 인증번호 이메일 전송 */
	   $('#mail-Check-Btn').click(function() {
	      const email = $('#totalemail').val(); // 이메일 주소값 얻어오기!
	      const checkInput = $('.mail-check-input'); // 인증번호 입력하는곳 
	
	      $.ajax({
	    	    type: "GET",
	    	    url: "mailCheck?email=" + email,
	    	    success : function (data) {
					console.log("data : " +  data);
					checkInput.attr('disabled',false);
					code =data;
					alert('인증번호가 전송되었습니다.')
				}		
	    	});
	   });
	
	   // 인증번호 비교 
	   $('.mail-check-input').blur(function() {
	      const inputCode = $(this).val();
	      const $resultMsg = $('#mail-check-warn');
	
	      if (inputCode == code) {
	         $resultMsg.html('인증번호가 일치합니다.');
	         $resultMsg.css('color', 'green');
	         $('#mail-Check-Btn').attr('disabled', true);
	         $('#totalemail').attr('readonly', true);
	      } else {
	         $resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
	         $resultMsg.css('color', 'red');
	      }
	   });

      // 이메일 주소 합성 및 설정 함수
      function email() {
         const userEmail = $("#user_email").val();
         const address = $("#email_address").val();
         if (userEmail != "" && address != "") {
            const fullEmail = userEmail + "@" + address;
            $("#totalemail").val(fullEmail);
         }
      };
      
      
    /*주소 API*/
      
     //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
         
                document.getElementById("sample4_engAddress").value = data.addressEnglish;
                       
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
   </script>
</body>
</html>