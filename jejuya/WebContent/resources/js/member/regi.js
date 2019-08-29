/**
 * 
 */

$(function(){
	$("#btnSignupInSignupModal").prop("disabled",true);
	$("#chkAllOk").hide();
	var idOk = false;
	var pw1Ok = false;
	var pw2Ok = false;
	var nameOk = false;
	var emailOk = false;
	var birthOk = true;
	var isOk = false;
	
	//정규식 활용 id 체크(영소문자+숫자 5자 이상)
	$("#inputIdInSignup").keyup(function(){
		var inputTxt = $("#inputIdInSignup").val()+'';
		
		var chkOnlyEngNum = /^[a-zA-Z0-9_-]{5,15}$/;

		if( chkOnlyEngNum.test( inputTxt ) || inputTxt.length > 4){
			//정규식 조건을 통과한 경우
			$("#signupWarn").text( '　' );	
			//Ajax 활용 ID 중복체크, 중복이 아니면? true = 사용가능 ID			
			var varIsNotDupId = false;
			
			$.ajax({
				type: "GET"
				, url: "/jejuya/member?command=getOneId"
				, datatype: "json"
				, data: { "inputId" : inputTxt }
				, success: function( data ){
					//alert('succeed : ' + data + ', 들어온값길이: ' + data.length );

					if( data === 'true' ){
						//컨트롤러에서 넘어온 결과가 true인 경우 사용 가능한 아이디로 판단
						//alert('ok data 트루라고 했다');
						varIsNotDupId = true;
						idOk = true;
						$("#signupWarn").text( '　' );			
						isOk = allOk( idOk, pw1Ok, pw2Ok, nameOk, emailOk, birthOk );
						if( isOk == true ){
							$("#btnSignupInSignupModal").prop("disabled",false);
						}
						$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
					}else{
						//중복된 ID인 경우
						idOk = false;
						$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
						$("#signupWarn").text( '중복된 ID입니다. 다른 ID를 입력해 주세요.' );
						$("#btnSignupInSignupModal").prop("disabled",true);
					}
					
				}, error: function( error ){
					alert('error : ' + error);
				}
			});					
		}else{
			//조건에 맞지 않는 경우
			idOk = false;
			$("#signupWarn").text( 'ID형식 : 5~15자 사이의 영문 소문자, 영문 대문자, 숫자의 조합' );
			$("#btnSignupInSignupModal").prop("disabled",true);
		}
	});
	
	//PW 길이 체크
	$("#inputPw1InSignup").keyup(function(){
		if( $("#inputPw1InSignup").val().length < 5 ){
			pw1Ok = false;
			$("#signupWarn").text( '비밀번호 형식 : 5자 이상' );
			$("#btnSignupInSignupModal").prop("disabled",true);
		}else{
			pw1Ok = true;
			$("#signupWarn").text( '　' );			
			isOk = allOk( idOk, pw1Ok, pw2Ok, nameOk, emailOk, birthOk );
			if( isOk == true ){
				$("#btnSignupInSignupModal").prop("disabled",false);
			}
			$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
		}
	});
	
	//PW 확인 체크
	$("#inputPw2InSignup").keyup(function(){
		var pw1 = $("#inputPw1InSignup").val();
		var pw2 = $("#inputPw2InSignup").val();
		if( pw1 == pw2 ){
			pw2Ok = true;			
			$("#signupWarn").text( '　' );			
			isOk = allOk( idOk, pw1Ok, pw2Ok, nameOk, emailOk, birthOk );
			if( isOk == true ){
				$("#btnSignupInSignupModal").prop("disabled",false);
			}
			$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
		}else{
			pw2Ok = false;
			$("#signupWarn").text( '두 비밀번호 불일치' );
			$("#btnSignupInSignupModal").prop("disabled",true);
		}
	});
	
	
	//E-mail 체크
	$("#inputEmailInSignup").keyup(function(){
		var inputTxt = $("#inputEmailInSignup").val();
		var chkEmail = /^[a-zA-Z0-9]+@([a-zA-Z0-9]{1,10})\.([a-zA-Z]{1,10})+$/;
		if( chkEmail.test(inputTxt) ){
			emailOk = true;
			$("#signupWarn").text( '　' );
			isOk = allOk( idOk, pw1Ok, pw2Ok, nameOk, emailOk, birthOk );
			if( isOk == true ){
				$("#btnSignupInSignupModal").prop("disabled",false);
			}
			$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
		}else{
			emailOk = false;
			$("#signupWarn").text( '이메일 형식 : aaa@aaa.aaa ' );
			$("#btnSignupInSignupModal").prop("disabled",true);
		}
	});
	
	//이름 길이 체크. 2자~20자까지
	$("#inputName").keyup(function(){
		var inputLen = $("#inputName").val().length;
		
		if(inputLen < 2 || inputLen > 20){
			nameOk = false;
			$("#signupWarn").text( '이름은 2자 이상 20자 이하로 입력해 주세요.' );
			$("#btnSignupInSignupModal").prop("disabled",true);
		}else{
			nameOk = true;
			$("#signupWarn").text( '　' );
			isOk = allOk( idOk, pw1Ok, pw2Ok, nameOk, emailOk, birthOk );
			if( isOk == true ){
				$("#btnSignupInSignupModal").prop("disabled",false);
			}
			$("#chkAllOk").text(idOk + ', ' + pw1Ok + ', ' + pw2Ok + ', ' + nameOk + ', ' + emailOk + ', ' + birthOk);
		}
	});
	
});


function allOk( ok1, ok2, ok3, ok4, ok5, ok6 ){
	if( ok1 && ok2 && ok3 && ok4 && ok5 && ok6 ){
		return true;
	}else{
		return false;
	}
}

/*
//ID를 입력받아서 ID중복여부를 컨트롤러에 Ajax로요청
function aisNotDupId( inputId ){
	
	$.ajax({
		type: "GET"
		, url: "/jejuya/member?command=getOneId"
		, datatype: "json"
		, data: { "inputId" : inputId }
		, success: function( data ){
			alert('succeed : ' + data + ', 들어온값길이: ' + data.length );

			if( data === true ){
				//컨트롤러에서 넘어온 결과가 true인 경우 사용 가능한 아이디로 판단
				return true;
			}else{
				return false;
			}
		}, error: function( error ){
			alert('error : ' + error);
		}
	});
}
*/