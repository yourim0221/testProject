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
	fCreator: 'createSEditor2' ,
    htParams : {
        // 툴바 사용 여부
        bUseToolbar : true,
        // 입력창 크기 조절바 사용 여부
        bUseVerticalResizer : true,
        // 모드 탭(Editor | HTML | TEXT) 사용 여부
        bUseModeChanger : true,
    }
}); 

$("#noticeBtnSave").click(function(){
	alert('save!');
	oEditors.getById["inputContent"].exec("UPDATE_CONTENTS_FIELD", []); 
	$("#noticeWriteForm").submit();
});

$("#noticeBtnCancel").click(function(){
	location.href= ctx + '/notice?command=showlist';
});

//클립보드 이미지 붙여넣기
$("#inputContent").on('paste', function(e){
	alert('content paste!');
});

$("#inputTitle").on('paste', function(e){
	alert('title paste!');
});