/**
 * 
 */
$(function(){		
	var isSearchedResult = $("#_isSearchedResult").val().trim();
	
	//만약 회원검색을 통해 컨트롤러 search를 거쳐서 이 뷰에 들어왔다면 회원관리 게시판 보여주기
	if( isSearchedResult === 'true' ){
		//메인메뉴 게시판 네비게이션 버튼의 수 만큼 반복해서 current 클래스 제거
		var bbsNavLength = $(".tabs").children().length;
		for( i = 0 ; i < bbsNavLength ; i++){
			$(".tabs").children().eq(i).removeClass("current");
		}
		//회원관리 버튼에 current 클래스 추가
		$(".tabs").children().eq(1).addClass("current");
		
		//메인메뉴 게시판 영역에 뿌려지는 게시판의 수 만큼 반복해서 current 클래스 제거
		var bbsMenuLength = $(".mainmenuBbs").children().length;		
		for( i = 0 ; i < bbsMenuLength ; i++ ){
			$(".mainmenuBbs").children().eq(i).removeClass("current");
		}
		//회원정보 게시판에 current 클래스 추가
		$("#tab-2").addClass("current");
		
		//alert('true!!!' + isSearchedResult);
		
		//다음 작업을 위해 hidden val 초기화
		$("#_isSearchedResult").val('false');
	}else{
		//alert('false!!!' + isSearchedResult);
	}
	
});