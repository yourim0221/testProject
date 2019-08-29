/**
 * 
 */
$(function(){
	//context path
	var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	$(".borderSiteMapItem").hide();

	/*메인 공지사항 페이징 관련*/
	
	//전체 페이지 수
	var totalNavItemSize = parseInt( $("#totalNavItemSize").val().trim() );
	//네비게이션 바 사이즈
	var navSize = 5;
	//prev 클릭 시 공지사항 nav 바 변경, 현재 페이지가 1페이지면 얼럿
	$(".page-prev").click(function(){		
		var currPage = parseInt( $("#noticePagingNav").children().eq(1).text() );
		var lastNavNum = parseInt( $("#noticePagingNav").children().eq( $("#noticePagingNav").children().length-2 ).text() ); 
		if( currPage === 1 ){
			alert('첫 번째 페이지입니다.');
		}else{
			if( currPage < (navSize + 1) ){
				//현재 페이지가 6보다 작은 수인 경우 1페이지를 보여줌
				for(i = 1 ; i < 6 ; i++){
					$("#noticePagingNav").children().eq(eqNum++).children().eq(0).text(i);
				}
			}else{
				//현재 페이지가 6 이상이면 이전 다섯개를 보여줌
				var prevFirstNavNum = currPage - navSize;
				//prev네비게이션의 끝번호는 prev시작번호 +4. 만약 prev끝번호가 총 페이지 수 보다 크면 끝번호는 총 페이지 수
				var prevLastNavNum = ( (prevFirstNavNum + 4) > totalNavItemSize )? totalNavItemSize : (prevFirstNavNum + 4) ;
				var eqNum = 1;
				
				for(i = prevFirstNavNum ; i < prevLastNavNum + 1 ; i++ ){
					$("#noticePagingNav").children().eq(eqNum++).children().eq(0).text(i);
				}
			}
		}
	});
	
	//next 클릭 시 공지사항 nav 바 변경, 현재 페이지가 마지막 페이지면 얼럿
	$(".page-next").click(function(){		
		var currPage = parseInt( $("#noticePagingNav").children().eq(1).text() );
		var lastNavNum = parseInt( $("#noticePagingNav").children().eq( $("#noticePagingNav").children().length-2 ).text() );
		//alert(lastNavNum);
		if( currPage > (totalNavItemSize - navSize) ){
			//마지막페이지
			alert('마지막 페이지입니다.');
		}else{
			//다음 네비게이션 메뉴 보여주기
			var nextFirstNavNum = currPage + navSize;
			//다음 네비게이션의 끝번호는 현재 끝번호 + 네비게이션 사이즈. 만약 다음 끝번호가 총 nav크기보다 크면 다음 끝번호는 총nav크기
			var nextLastNavNum = ( (lastNavNum + navSize) > totalNavItemSize )? totalNavItemSize : (lastNavNum + navSize) ;
			var eqNum = 1;
			
			//alert( 'lastNavNum:' + lastNavNum + ',  navSize:' + navSize + ',  totalNavItemSize:' + totalNavItemSize +',    다음시작 : ' + nextFirstNavNum + ' , 다음끝:' + nextLastNavNum);
			
			for(i = nextFirstNavNum ; i < nextLastNavNum + 1 ; i++ ){
				$("#noticePagingNav").children().eq(eqNum++).children().eq(0).text(i);
			}
		}
	});
	

	
	// 공지 paging nav 숫자를 클릭하면 해당 페이지를 보여주도록
	$(".page-nav-item").click( function() {
		//alert( $(this).text() );
		var selectedIndex = $(this).text().trim();
		$.ajax({
			type : "POST",
			url : ctx + "/notice?command=showlistajax",
			data : {
				"selectedIndex" : selectedIndex
			},
			datatype : "json",
			success : function(data) {
				//alert(data);
				var result = data.json;

				// alert(result);
				// alert(data.noticeList)
				var str = data.split('____');
				for (i = 1; i < str.length; i++) {
					var temp = str[i].split('___');
					// var seq = temp[0].split('=');
					//$("#asdasd").html(	$("#asdasd").html() + temp[0].split('=')[1]	+ '<br/> ');
					// alert(str[i].indexOf('wdate='));
				}
				
				// 공지 테이블 초기화
				var tblLength = $("#mainNoticeTbl tr").length;
				for (i = 1; i < tblLength - 1; i++) {
					$("#mainNoticeTbl tr").eq(1).remove();
				}
				
				//json으로 받아온 값을 테이블에 입력
				for( i = 0 ; i < str.length - 1 ; i++ ){
					var $tempTr = $('<tr>');
					$tempTr.prop('class', 'mainNoticeTblRow');
					$tempTr.prop('data-target', '#noticeDetailModal');
					var tempArr = [0, 2, 8];
					for( j = 0 ; j < 3 ; j ++){
						var $tempTd = $('<td>');
						$tempTd.text( str[i].split('___')[j].split('__')[1] );
						//$tempTd.text( str[i].split(',')[tempArr[j]].split('=')[1].replace(')','').replace('-','. ').replace('-', '. ') );
						$tempTd.appendTo($tempTr);
					}										
					//$tempTr.insertAfter($(".mainNoticeTblHeader"));
					$tempTr.insertBefore($(".mainNoticeTblContentBottom"));
				}
				
				//클릭 이벤트 리스너 추가. 해당글의 seq로 DB에서 검색한 내용을 모달에 보여줌
				$(".mainNoticeTblRow").click(function(){
					var seq = $(this).children().eq(0).text().trim();
					//alert(seq);
					$.ajax({
						type : "POST",
						url : ctx + "/notice?command=noticeDetailAjax",
						data : { "seq" : seq },
						datatype : "json",
						success : function(data) {
							var str = data.trim().split('noticeContent:');
							$("#noticeModalBodyTitle").text(str[0].split('oticeTitle:')[1]);
							$("#noticeModalBodyContent").html('');
							$("#noticeModalBodyContent").html(str[1]);
						},
						error : function(error) {
							alert('error : ' + error);
						}
					});
					
					$("#noticeDetailModal").modal();
				});
			},
			error : function(error) {
				alert('error : ' + error);
			}
		});
	});
	
	$(".mainNoticeTblRow").click(function(){
		var str = '';
		var seq = $(this).children().eq(0).text().trim();
		for(i = 0 ; i < $(this).children().length ; i++){
			str += $(this).children().eq(i).text();
		}		
		$.ajax({
			type : "POST",
			url : ctx + "/notice?command=noticeDetailAjax",
			data : { "seq" : seq },
			datatype : "json",
			success : function(data) {
				var str = data.trim().split('noticeContent:');
				$("#noticeModalBodyTitle").text(str[0].split('oticeTitle:')[1]);
				$("#noticeModalBodyContent").html('');
				$("#noticeModalBodyContent").html(str[1]);
			},
			error : function(error) {
				alert('error : ' + error);
			}
		});
		//alert( str );
		$("#noticeDetailModal").modal();
	});
	
	/*메인 관음숙 마우스오버 이벤트*/
	$(".cmp1InnerSiteMapItem h3").hover(function() {
	    $(".cmp1BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp1BorderSiteMapItem").fadeOut();
	});
	
	$(".cmp2InnerSiteMapItem h3").hover(function() {
	    $(".cmp2BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp2BorderSiteMapItem").fadeOut();
	});
	
	$(".cmp3InnerSiteMapItem h3").hover(function() {
	    $(".cmp3BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp3BorderSiteMapItem").fadeOut();
	});
	
	$(".cmp4InnerSiteMapItem h3").hover(function() {
	    $(".cmp4BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp4BorderSiteMapItem").fadeOut();
	});
	
	$(".cmp5InnerSiteMapItem h3").hover(function() {
	    $(".cmp5BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp5BorderSiteMapItem").fadeOut();
	});
	
	$(".cmp6InnerSiteMapItem h3").hover(function() {
	    $(".cmp6BorderSiteMapItem").fadeIn()},
	    							function() {
	    $(".cmp6BorderSiteMapItem").fadeOut();
	});

	//교통정보 아이콘 링크 설정
	var trafficIndex = 0;
	//일반 버스
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=three&menuId=DOM_000002000000000033#');
	//관광지 순환 버스
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=three&subTap=two&menuId=DOM_000002000000000033#');
	//택시
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=four&menuId=DOM_000002000000000034#');
	//렌터카
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=five&menuId=DOM_000001703002009000#');
	//공항 리무진
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=three&subTap=three&menuId=DOM_000002000000000033');
	//전세버스
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=three&subTap=four&menuId=DOM_000002000000000033#');
	//항공
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=one&menuId=DOM_000002000000000031#');
	//여객선, 도항
	$(".trafficIcons").children().eq(trafficIndex++).children().eq(0).prop('href','https://www.visitjeju.net/kr/tourInfo/traffic?tap=two&menuId=DOM_000002000000000032#');

	//공지테이블 2열 좌측정렬
	var noticeTblLen = $("#mainNoticeTbl").children().eq(0).children().length;
	//alert('notice len : ' + noticeTblLen);
	for( i = 1 ; i < noticeTblLen - 1 ; i++ ){
		//$("#mainNoticeTbl").children().eq(0).children().eq(i).children().eq(1).attr('style','text-align:left; padding-left: 150px;');
	}
	//$(".mainNoticeTblRow").children().eq(1).attr('style','text-align:left; padding-left: 50px;');
	//$(".mainNoticeTblRow").children().eq(1).text('style');
});