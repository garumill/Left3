<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="utf-8" />
<title>Moyeo</title>
</head>
<body>
   <section>
      <h1>회원가입 페이지</h1>
   </section>

   <section>
      <form name="login" action="join" method="post">
         아이디<br> 
         <input type="text" name="id" placeholder="아이디 입력"><br>

         비밀번호<br> 
         <input type="password" name="pw"><br> <br>
         이름<br> 
         <input type="text" name="name" value=""> <br>
         생년월일<br> 
         <input type="text" name="birth" placeholder="2000-01-01"><br> <br> 
         성별<br> 
         <label for="man">남자</label> 
         <input type="radio" name="gender" value="m" id="man"> 
         <label for="woman">여자</label> 
         <input type="radio" name="gender" value="m" id="woman"> <br> <br>
         
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
   </script>
</body>
</html>