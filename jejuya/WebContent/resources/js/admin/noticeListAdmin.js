/**
 * 
 */
$(function(){
	//context path
	var ctx = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));


	$("#writeNewBtn").click(function(){
		location.href= ctx + "/notice?command=writeNewNotice";
	});
	
	$("#goToMainBtn").click(function(){
		location.href= ctx + "/adminControl?command=main";
	});
	
	//테이블 행 클릭 시 해당 시퀀스에 맞는 디테일 페이지로 이동
	$(".listclick").click(function(){
		var seq = $(this).children().eq(0).text();
		location.href= ctx + "/notice?command=noticeDetail&seq=" + seq;
	});
		
	//$(".borderSiteMapItem").hide();

	/*메인 페이징 관련*/
	
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
			url : ctx + "/notice?command=showAdminListAjax",
			data : { "selectedIndex" : selectedIndex },
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
				var tblLength = $("#noticeListAdminTbl tr").length;
				for (i = 0; i < tblLength ; i++) {
					$("#noticeListAdminTbl tr").eq(1).remove();
				}
				
				var $tblLineTr = $('<tr>');
				var $tblLineTd = $('<td>');
				$tblLineTd.prop('class', 'table_border');
				$tblLineTd.prop('colspan', '5');
				$tblLineTd.appendTo($tblLineTr);
				
				$("#noticeListAdminTbl").append($tblLineTr);
				
				var $tblHeader = $('<tr>');
				$tblHeader.prop('class', 'th_class');
				var strArr = ['글 번호', '제목', '작성자', '파일', '작성일'];
				for( i = 0 ; i < 5 ; i++ ){
					var $tblHeaderTd = $('<td>');
					$tblHeaderTd.text( strArr[i] );
					$tblHeaderTd.appendTo($tblHeader);
				}				
				$("#noticeListAdminTbl").append($tblHeader);
				
				$tblLineTr = $('<tr>');
				$tblLineTd = $('<td>');
				$tblLineTd.prop('class', 'table_border');
				$tblLineTd.prop('colspan', '5');
				$tblLineTd.appendTo($tblLineTr);
				$("#noticeListAdminTbl").append($tblLineTr);
				//alert('append done');
				//json으로 받아온 값을 테이블에 입력
				for( i = 0 ; i < str.length - 1 ; i++ ){
					var $tempTr = $('<tr>');
					//$tempTr.prop('class', 'noticeListAdminTblRow');
					$tempTr.prop('class', 'listclick');
										
					for( j = 0 ; j < 5 ; j ++){
						var $tempTd = $('<td>');
						$tempTd.text( str[i].split('___')[j].split('__')[1] );
						//$tempTd.text( str[i].split(',')[tempArr[j]].split('=')[1].replace(')','').replace('-','. ').replace('-', '. ') );
						$tempTd.appendTo( $tempTr );
					}										
					//$tempTr.insertAfter($(".noticeListAdminTblContentBottom"));
					//$tempTr.insertBefore( $(".noticeListAdminTblContentBottom") );
					$("#noticeListAdminTbl").append($tempTr);
				}
				
				$("#noticeListAdminTbl").append($tblLineTr);
				
				var $tblFooter = $('<tr>');
				$tblFooter.prop('class', 'noticeTblFootRow');
				
				var $tblFooterTd = $('<td>');
				$tblFooterTd.text( strArr[i] );
				$tblFooterTd.prop('colspan', '5');
				$tblFooterTd.appendTo($tblFooter);
								
				$("#noticeListAdminTbl").append($tblFooter);
				
				//테이블 행 클릭 시 해당 시퀀스에 맞는 디테일 페이지로 이동
				$(".listclick").click(function(){
					var seq = $(this).children().eq(0).text();
					location.href= ctx + "/notice?command=noticeDetail&seq=" + seq;
				});
								
			},
			error : function(error) {
				alert('error : ' + error);
			}
		});
	});
	
	
});

