<%@page import="com.sights.dto.SightsDto"%>
<%@page import="com.schedule.dto.ScheduleDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
       
    
    var category=$("#hidden").val();  //카테고리의 검색 값 받아옴 
    
    $.ajax({
    	url:"/jejuya/ScheduleController?command=rank",
    	type:"get",
    	data:{"category":category},
    	success:function(data,status,xhr){
    	    alert("통신 성공!!!");
    	    alert(data);
    	    

    	},error:function(xhrInner,statusInner,errorInner){
	    	alert("통신실패");
	    },
	    complete:function(xhrInner,statusInner){
			//alert("통신 종료!");
	    }
    	
    });
    
    
    
    
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['관광지', '추천순'],
          [ 8,      12],
          [ 4,      5.5],
          [ 11,     14],
          [ 4,      5],
          [ 3,      3.5],
          [ 6.5,    7]
        ]);

        var options = {
          title: '추천 일정',
          hAxis: {title: '관광지', minValue: 0, maxValue: 15},
          vAxis: {title: '추천순', minValue: 0, maxValue: 15},
          legend: 'none'
        };

        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

        chart.draw(data, options);
      }
    </script>

</head>



<body>

 <div id="chart_div" style="width: 660px; height: 200px;"></div>
 

</body>




</html>