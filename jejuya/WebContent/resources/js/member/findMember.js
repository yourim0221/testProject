/** ID/PW찾기 뷰에서 사용하는 자바스크립트.
 *  URL : /jejuya/resources/js/member/findMember.js
 */

$(function(){
	//alert('dddd');

	//아이디 찾기 버튼 클릭 이벤트 리스너 추가
	$("#_findIdSubmitBtn").click(function(){
		
		var inputName = $("#_findIdInputNameTxt").val();
		var inputEmail = $("#_findIdInputEmailTxt").val();
		
		//입력한 이름과 이메일이 빈칸이 아닐 때에만 ajax 통신 수행
		if( inputName.length > 0 && inputEmail.length > 0 ){
			$.ajax({
				type: "POST"
				, url: "/jejuya/member?command=doconfirmid"
				, datatype: "json"
				, contentType: "application/x-www-form-urlencoded; charset=UTF-8" 
				, data: { "inputName" : inputName, "inputEmail":inputEmail }
				, success: function( data ){
					//alert(data);
					var ajaxResult = data.trim();
					
					if(ajaxResult === 'true'){
						//DB에서 이름을 찾은 경우 컨트롤러에 이메일 발송 명령 전달
						$("#_findIdForm").submit();						
					}else{
						alert('입력하신 내용과 일치하는 회원 정보가 없습니다');
					}
				}, error: function( error ){
					alert('error : ' + error);
				}
			});
		}else{
			alert('이름과 이메일을 입력해주세요.');
		}
	});
	
	$("#_findPwSubmitBtn").click(function(){
		
		var inputId = $("#_findPwInputIdTxt").val();
		var inputEmail = $("#_findPwInputEmailTxt").val();
		
		//입력한 이름과 이메일이 빈칸이 아닐 때에만 ajax 통신 수행
		if( inputId.length > 0 && inputEmail.length > 0 ){
			$.ajax({
				type: "POST"
				, url: "/jejuya/member?command=doconfirmpw"
				, datatype: "json"
				, contentType: "application/x-www-form-urlencoded; charset=UTF-8" 
				, data: { "inputId" : inputId, "inputEmail":inputEmail }
				, success: function( data ){
					//alert(data);
					var ajaxResult = data.trim();
					
					if(ajaxResult === 'true'){
						//DB에서 이름을 찾은 경우 컨트롤러에 이메일 발송 명령 전달
						$("#_findPwForm").submit();						
					}else{
						alert('입력하신 내용과 일치하는 회원 정보가 없습니다');
					}
				}, error: function( error ){
					alert('error : ' + error);
				}
			});
		}else{
			alert('이름과 이메일을 입력해주세요.');
		}
	});
});