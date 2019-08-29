/**
 * 
 */

$(function(){
	
	//context path
	var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	$.ajax({
		type : "POST",
		url : ctx + "/member?command=confirmCurrIdAjax",
		//data : { "seq" : seq },
		datatype : "json",
		success : function(data) {
			var currId = data.trim();
			if( currId === 'guest' ){
				$("#headerLoginBtn").show();
				$("#headerLogoutBtn").hide();
			}else{
				$("#headerLoginBtn").hide();
				$("#headerLogoutBtn").show();
			}
		},
		error : function(error) {
			alert('error : ' + error);
		}
	});
	
	$("#headerMemberBtn").click(function(){
		var currUrl = $("#currUrl").val().trim();
		var destUrl = $("#destUrl").val().trim();
		$("#headerMemberFrm").submit();
	});
});