<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.del {
   position: absolute;
    top: 10px;
    right: 10px;
    width: 2000px;
    height: 2000px;
    background:url('/jejuya/resources/image/main/btn_del.png') no-repeat 0 0;
    text-indent: -9999px;
}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

</head>
<body>
<button type="button" class="del"></button>
	<table class="timelinetable">
		<colgroup>
			<col width="60">
			<col width="420">

		</colgroup>
		<tbody>
			<tr>
			
				<th colspan="2"><h3>TIMELINE</h3></th>
			</tr>
			<tr>




			</tr>
			<tr>
				<th colspan="2"><a href="#" onclick="addtrip(1)"><img
						src="/jejuya/resources/image/main/left.gif"></a>DAY<a
					id="timelineday">3</a> <a href="#" onclick="addtrip(2)"><img
						src="/jejuya/resources/image/main/last.gif"></a> <input
					type="hidden" id="triptime" value="3"></th>
			</tr>



			<tr>
				<th colspan="2">
					<button type="button" id="addtrip" onclick="add()">일정 추가</button>
				</th>
			</tr>
			<tr class="01" style="display: none;">
				<th>05:00</th>
				<td id="01:05:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>06:00</th>
				<td id="01:06:00">성산일출봉<input type="checkbox" id="delck[]"
					value="성산일출봉"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>07:00</th>
				<td id="01:07:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>08:00</th>
				<td id="01:08:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>09:00</th>
				<td id="01:09:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>10:00</th>
				<td id="01:10:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>11:00</th>
				<td id="01:11:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>12:00</th>
				<td id="01:12:00">함덕해수욕장<input type="checkbox" id="delck[]"
					value="함덕해수욕장"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>13:00</th>
				<td id="01:13:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>14:00</th>
				<td id="01:14:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>15:00</th>
				<td id="01:15:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>16:00</th>
				<td id="01:16:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>17:00</th>
				<td id="01:17:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>18:00</th>
				<td id="01:18:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>19:00</th>
				<td id="01:19:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>20:00</th>
				<td id="01:20:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>21:00</th>
				<td id="01:21:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>22:00</th>
				<td id="01:22:00"></td>
			</tr>
			<tr class="01" style="display: none;">
				<th>23:00</th>
				<td id="01:23:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>05:00</th>
				<td id="02:05:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>06:00</th>
				<td id="02:06:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>07:00</th>
				<td id="02:07:00">노리매공원<input type="checkbox" id="delck[]"
					value="노리매공원"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>08:00</th>
				<td id="02:08:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>09:00</th>
				<td id="02:09:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>10:00</th>
				<td id="02:10:00">한라산식당<input type="checkbox" id="delck[]"
					value="한라산식당"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>11:00</th>
				<td id="02:11:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>12:00</th>
				<td id="02:12:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>13:00</th>
				<td id="02:13:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>14:00</th>
				<td id="02:14:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>15:00</th>
				<td id="02:15:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>16:00</th>
				<td id="02:16:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>17:00</th>
				<td id="02:17:00">새별오름<input type="checkbox" id="delck[]"
					value="새별오름"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>18:00</th>
				<td id="02:18:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>19:00</th>
				<td id="02:19:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>20:00</th>
				<td id="02:20:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>21:00</th>
				<td id="02:21:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>22:00</th>
				<td id="02:22:00"></td>
			</tr>
			<tr class="02" style="display: none;">
				<th>23:00</th>
				<td id="02:23:00"></td>
			</tr>
			<tr class="03" style="">
				<th>05:00</th>
				<td id="03:05:00"></td>
			</tr>
			<tr class="03" style="">
				<th>06:00</th>
				<td id="03:06:00"></td>
			</tr>
			<tr class="03" style="">
				<th>07:00</th>
				<td id="03:07:00"></td>
			</tr>
			<tr class="03" style="">
				<th>08:00</th>
				<td id="03:08:00"></td>
			</tr>
			<tr class="03" style="">
				<th>09:00</th>
				<td id="03:09:00"></td>
			</tr>
			<tr class="03" style="">
				<th>10:00</th>
				<td id="03:10:00"></td>
			</tr>
			<tr class="03" style="">
				<th>11:00</th>
				<td id="03:11:00">한라산호텔<input type="checkbox" id="delck[]"
					value="한라산호텔"></td>
			</tr>
			<tr class="03" style="">
				<th>12:00</th>
				<td id="03:12:00"></td>
			</tr>
			<tr class="03" style="">
				<th>13:00</th>
				<td id="03:13:00">천지연호텔<input type="checkbox" id="delck[]"
					value="천지연호텔"></td>
			</tr>
			<tr class="03" style="">
				<th>14:00</th>
				<td id="03:14:00"></td>
			</tr>
			<tr class="03" style="">
				<th>15:00</th>
				<td id="03:15:00"></td>
			</tr>
			<tr class="03" style="">
				<th>16:00</th>
				<td id="03:16:00"></td>
			</tr>
			<tr class="03" style="">
				<th>17:00</th>
				<td id="03:17:00"></td>
			</tr>
			<tr class="03" style="">
				<th>18:00</th>
				<td id="03:18:00"></td>
			</tr>
			<tr class="03" style="">
				<th>19:00</th>
				<td id="03:19:00"></td>
			</tr>
			<tr class="03" style="">
				<th>20:00</th>
				<td id="03:20:00"></td>
			</tr>
			<tr class="03" style="">
				<th>21:00</th>
				<td id="03:21:00"></td>
			</tr>
			<tr class="03" style="">
				<th>22:00</th>
				<td id="03:22:00"></td>
			</tr>
			<tr class="03" style="">
				<th>23:00</th>
				<td id="03:23:00"></td>
			</tr>
		</tbody>
	</table>
	 <div id="chart_div" style="width: 660px; height: 200px;"></div>
<script type="text/javascript">

   $.ajax({
    	    	url:"/jejuya/ScheduleController?command=rank",
    	    	type:"get",
    	    	data:{"category":1},
    	    	success:function(data,status,xhr){
    	    	    alert("통신 성공!!!");
    	    	data_=data;
    	    	alert(data_);
    	    	
    	 	    Highcharts.chart('container', {
    	    	  	  chart: {
    	    	  	    plotBackgroundColor: null,
    	    	  	    plotBorderWidth: null,
    	    	  	    plotShadow: false,
    	    	  	    type: 'pie'
    	    	  	  },
    	    	  	  title: {
    	    	  	    text: '관광지 별 추천 일정'
    	    	  	  },
    	    	  	  tooltip: {
    	    	  	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    	    	  	  },
    	    	  	  plotOptions: {
    	    	  	    pie: {
    	    	  	      allowPointSelect: true,
    	    	  	      cursor: 'pointer',
    	    	  	      dataLabels: {
    	    	  	        enabled: true,
    	    	  	        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
    	    	  	        style: {
    	    	  	          color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
    	    	  	        }
    	    	  	      }
    	    	  	    }
    	    	  	  },
    	    	  	  series: [{
    	    	  	    name: 'Brands',
    	    	  	    colorByPoint: true,
    	    	  	    data:<%=request.getAttribute("data")%>
    	    	  	  }]
    	    	  	});

    	    	},error:function(xhrInner,statusInner,errorInner){
    		    	alert("통신실패");
    		    },
    		    complete:function(xhrInner,statusInner){
    				//alert("통신 종료!");
    		    }
    	    	
    	    });
</script>
</body>
</html>