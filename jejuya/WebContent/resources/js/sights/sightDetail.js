/**
 * 
 */
$(function(){
	//alert('dd');
	
	//(String)페이지에 접근했을 때, 처음 접근인지 리뷰를 작성한 이후의 접근인지 파악하기위한 변수
	var isAfterReviewAdded = $("#_isAfterReviewAdded").val();
	
	if( isAfterReviewAdded === 'true' ){	//
		
		//탭버튼 클래스 설정
		var menuLength = $(".tabs").children().length;
		for( i = 0 ; i < menuLength ; i++){
			$(".tabs").children().eq(i).removeClass('current');
		}
		$(".tabs").children().eq(2).addClass('current');
		
		//탭 내용 클래스 설정
		var contentLength = $(".sightDetailContent").children().length;
		for( i = 0 ; i < contentLength ; i++){
			$(".sightDetailContent").children().eq(i).removeClass('current');
		}
		$(".sightDetailContent").children().eq(2).addClass('current');
	}
});