/**
 * 
 */
//context path
var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

var oEditors = [];
nhn.husky.EZCreator.createInIFrame({ 
	oAppRef: oEditors, 
	elPlaceHolder: 'inputContent', 
	sSkinURI: ctx + '/resources/texteditor/SmartEditor2Skin.html', 
	fCreator: 'createSEditor2' 
}); 

$("#noticeBtnSave").click(function(){
	oEditors.getById["inputContent"].exec("UPDATE_CONTENTS_FIELD", []); 
	$("#noticeDetailForm").submit();
});

$("#noticeBtnDelete").click(function(){
	$("#command").val("delNotice");
	$("#noticeDetailForm").submit();
});

$("#noticeBtnCancel").click(function(){
	location.href= ctx + '/notice?command=showlistAdmin';
});

$(function(){
	var originTitle = $("#inputTitle").val();
	var originContent = $("#inputContent").text();

});

function isChanged( ok1, ok2 ){
	if( ok1 && ok2 ){
		return true;
	}else{
		return false;
	}
}