<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 개발자 작성 리소스 -->
<link rel="stylesheet" href="/jejuya/resources/css/schedule/scheduleadd.css"></link>

<!-- 지도 관련 리소스 -->
<link rel="stylesheet" href="/jejuya/resources/css/schedule/scheduleadd_map.css"></link>

<!-- 타임라인 관련 리소스 -->
<link rel="stylesheet" href="/jejuya/resources/css/schedule/scheduleadd_timeline.css"></link>

<!-- jquery cdn -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- jquery UI cdn -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- chart cdn -->
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

</head>
<body>

	<div class="mainWrapScheduleAdd" align="center">
	
		<!-- 여행일정 시작일, 종료일 등 선택하는 부분 -->
		<div class="setScheduleOption">
			<jsp:include page="/views/schedule/schetemplates/schAdd001setSche.jsp"></jsp:include>
		</div>
		
		<script type="text/javascript">
		/* $(function(){
			$(".setScheduleOption input").attr("readonly","readonly");
		}); */
		</script>
		
		<!-- 타임라인에 스케쥴을 추가하는 부분 -->
		<div class="scheAdder">
			<jsp:include page="/views/schedule/schetemplates/schAdd002scheAdd.jsp"></jsp:include>
		</div>
		
		<!-- 지도와 각 관음숙의 정보가 표시되는 부분 -->
		<div class="mapAboutEachSights">
			<jsp:include page="/views/schedule/schetemplates/schAdd003map.jsp"></jsp:include>
		</div>
		
		<!-- 유저가 선택한 타임라인을 보여주는 부분 -->
		<div class="addedScheTimeline">
			<jsp:include page="/views/schedule/schetemplates/schAdd004timeline.jsp"></jsp:include>
		</div>	
		
		<!-- 관음숙 랭킹을 보여주는 부분 -->
		<div class="sightsRankDiagram">
			관음숙 랭킹을 보여주는 부분
		</div>
		
	</div>

</body>
</html>