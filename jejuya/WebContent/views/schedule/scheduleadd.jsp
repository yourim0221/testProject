<%@page import="com.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 작성</title>
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
<%
	//접근권한 검사 부분
	String currId = "guest";
	if( request.getSession().getAttribute("currUser") != null ) {
		currId = ( (MemberDto)request.getSession().getAttribute("currUser") ).getId();
	}
	if( currId.equals("guest") ){
		out.println("<script>alert('로그인 정보가 없습니다. 로그인 후 이용해 주세요.'); location.href='/jejuya/member'</script>");
	}
%>
	<div class="mainWrapScheduleAdd" align="center">
	
		<!-- 여행일정 시작일, 종료일 등 선택하는 부분 -->
		<div class="setScheduleOption">
			<jsp:include page="/views/schedule/schetemplates/schAdd001setSche.jsp"></jsp:include>
		</div>
		
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
			<jsp:include page="/views/schedule/schetemplates/schAdd005rank.jsp"></jsp:include>
		</div>
		
	</div>

</body>
</html>