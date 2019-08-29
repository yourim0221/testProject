<%@page import="com.sights.dto.SightsDto"%>
<%@page import="com.schedule.dto.ScheduleDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>







</head>



<body>

	<div id="chart_div"></div>
    <input type="hidden" id="data">
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
<html>
<head>
<!--Load the AJAX API-->
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
	google.load('visualization', '1', {
		packages : [ 'corechart' ],
		'language' : 'ko'
	});

	function drawChart(d) {
		//alert( 'd : ' + d.trim() );
				
		var inputStrArr = d.trim().split('____');
		var dataArr = new Array();
		//arr = d;
		
		dataArr[0] = [ inputStrArr[0].split('___')[0], inputStrArr[0].split('___')[1]  ];
		//ajax통신으로 가져온 문자열을 배열로 변환
		for( i = 1 ; i < inputStrArr.length - 1 ; i++){
			dataArr[i] = [ inputStrArr[i].split('___')[0], parseInt(inputStrArr[i].split('___')[1])  ];
			console.log('data' + dataArr[i]);
		}
		
		for( j = 0 ; j < dataArr.length ; j++ ){
			console.log(dataArr[j]);
		}
		//alert('배열' + dataArr);
		
		var data = google.visualization.arrayToDataTable( dataArr );
		//데이터를 array 형태로 구글 visualization에 넣어줘야 함.
		//var data = google.visualization.arrayToDataTable(d);
		/* ([ [ '이름', '인기순' ],
				[ '김녕해수욕장', 45 ], [ '이름', 42 ], [ '이름', 40 ], ['이름', 10 ],
				['이름', 8 ], [ '이름', 8 ], [ '이름', 7 ], [ '이름', 6 ],
				[ '이름', 5 ], [ '이름', 4 ] ]); */

			var title="";	
		if($("#hidden").val()==0){	
			title= '관광지 추천 TOP 5'
		}
		else if($("#hidden").val()==1){
			title = '음식점 추천  TOP 5'
		}else{
			title='숙소 추천 TOP 5'
		}
				
		var options = {
		    title:title,
			hAxis : {
				title : '관광지',
				minValue : 0,
				maxValue : 15
			},
			vAxis : {
				title : '추천순',
				minValue : 0,
				maxValue : 15
			},
			legend : 'none'
		};

		var chart = new google.visualization.ScatterChart(document
				.getElementById('chart_div'));

		chart.draw(data, options);
	}
	var arr = new Array(); 
	
	function rank(e) {
		
		var d=$("#hidden").val();
	
		var arr = new Array();
		$.ajax({
			url : "/jejuya/ScheduleController?command=rank",
			type : "get",
			data : {"category":d},
	
			success : function(data, status, xhr) {
				//alert("통신 성공!!!");
				
				//alert(data);
				
			  
				drawChart(data);

			},
			error : function(xhrInner, statusInner, errorInner) {
				alert("통신실패");
			},
			complete : function(xhrInner, statusInner) {
				//alert("통신 종료!");
			}

		});

	}
	google.setOnLoadCallback(rank);
</script>
</body>




</html>