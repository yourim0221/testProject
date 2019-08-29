/** url-pattern : '/jejuya/member'
 * 	path :  
 */
$(function(){
	
	var key = getCookie("key");
	$("#inputIdInSignin").val(key);
	
	//id입력칸이 빈칸이 아닌 경우 ID저장하기를 체크상태로 설정
	if( $("#inputIdInSignin").val() != "" ){
		$("#_rememberIdChk").attr("checked",true);
	}
	
	$("#_rememberIdChk").change(function(){
		if( $("#_rememberIdChk").is(":checked") ){
			//체크이벤트 발생 시 7일 동안 쿠키 보관
			setCookie("key", $("#inputIdInSignin").val(), 7);
			//alert('chk!');
		}else{
			//체크해제 이벤트 발생 시 쿠키 삭제
			deleteCookie("key");
		}
	});
	
	// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("#inputIdInSignin").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#_rememberIdChk").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            setCookie("key", $("#inputIdInSignin").val(), 7); // 7일 동안 쿠키 보관
        }
    });
});

//쿠키설정 함수
function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}
 
//쿠키제거 함수
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}
 
//쿠키취득 함수
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
