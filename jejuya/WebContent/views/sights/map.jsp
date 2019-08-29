<%@page import="com.sights.dto.SightsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>
<body>

<%
SightsDto dto = (SightsDto)request.getAttribute("dto");
String homepage = dto.getHomepage();
System.out.println("homepage=" + homepage);

%>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=76a79f0ac1e4d339eaf1a7c26d8e87c2&libraries=services"></script>
<script>

//지도 불러오기
//var coordXY   = document.getElementById("coordXY");
var x = "" ,y = "";

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
level: 4 // 지도의 확대 레벨
};  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//주소로 좌표를 검색합니다
geocoder.addressSearch('<%=dto.getAddress()%>', function(result, status) {

//정상적으로 검색이 완료됐으면 
if (status === kakao.maps.services.Status.OK) {

var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
		y = result[0].x;
	 	x = result[0].y;

// 결과값으로 받은 위치를 마커로 표시합니다
var marker = new kakao.maps.Marker({
    map: map,
    position: coords
});

// 인포윈도우로 장소에 대한 설명을 표시합니다
var infowindow = new kakao.maps.InfoWindow({
    content: '<div style="width:150px;text-align:center;padding:6px 0;"><%=dto.getTitle()%></div>'
});
infowindow.open(map, marker);

// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
map.setCenter(coords);

$("#findroad").click(function() {
		<%-- alert(x + " , " + y);
		alert('<%=dto.getTitle()%>'); --%>
	
		window.open('https://map.kakao.com/link/to/' + '<%=dto.getTitle()%>' + ',' + x + ',' + y, '_blank');
	});
        
} 
});

</script>









</body>
</html>