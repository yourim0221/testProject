<%@page import="com.sights.dto.SightsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
SightsDto dto = (SightsDto)request.getAttribute("dto");
String homepage = dto.getHomepage();
//System.out.println("homepage=" + homepage);

//이미지파일 경로설정
String serverPath = request.getContextPath();
//System.out.println("serverPath=" + serverPath);

//이미지 주소로 받을때랑 서버에서 받을때 두가지
String filename = (dto.getFilename().trim().substring(0,4).equals("http")
						?dto.getFilename():serverPath  + "/upload/" + dto.getFilename());
//System.out.println("filename=" + filename);

//리뷰평점평균
int count = (int) request.getAttribute("count");
//System.out.println(count);
%>
     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- global conf include -->
<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!-- 만든 CSS 파일 -->
<link rel="stylesheet" href="/jejuya/resources/css/sights/sightsdetail.css"/>

</head>
<body>
<!-- GNB include -->
<jsp:include page="/views/templates/header.jsp"></jsp:include>

<div align="center">
	<img class="mainImg" src='<%=filename%>'>
	
	<div class="containerD">	
		<ul class="tabsD">			    
		    <li id="basic" class="menu current">기본정보</li>
		    <li id="content" class="menu">상세정보</li>
		    <li id="reviewlist" class="menu">리뷰 및 평가등록</li>
		    <li id="findroad" class="menu">길찾기</li>		
	  	</ul> 	
	  	
	  	<div class="content">
			<table id="basicTb" class="tb2">
				<tr class="tr1">
					<td class="td1">관광지명</td>
					<td><%=dto.getTitle() %></td> 
				</tr>
				<tr class="tr1">
					<td class="td1">평점</td>
					<td><%=count %></td>
				</tr>	
				<tr class="tr1">
					<td class="td1">주소</td>
				    <td><%=dto.getAddress() %></td>
				</tr>
				<tr class="tr1">
					<td class="td1">연락처</td>
					<td><%=dto.getPhone() %></td>
				</tr>
				<% if(homepage != null){
					%>
					<tr class="tr1">
						<td class="td1">홈페이지</td>
						<td><a href='http://<%=homepage %>' target='_blank'><%=homepage %></a></td>
					</tr>										
					<%
					}
					%>																
			</table>
			<br>
			<div id="map" style="width: 500px; height: 400px"></div>
											  	
	  	</div>	  	 	
	</div>	
</div>

<script>
$(function () {
	
	// 메뉴 page넘기기
	$("#basic").mousedown(function () {
		location.href='/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle()%>';		
	});
	$("#content").mousedown(function () {
		location.href='/jejuya/SightsController?command=conBasic&title=<%=dto.getTitle()%>';		
	});
	$("#reviewlist").mousedown(function () {
		location.href='/jejuya/SightsController?command=reviewList&title=<%=dto.getTitle()%>';		
	});
	/* $("#findroad").mousedown(function () {
		location.href="";		
	}); */
	
});


//평점 숫자 별모양으로 바꾸기
$(function () {
	if('<%=count%>' == 5){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>★★★★★</font>");
	}
	else if('<%=count%>' == 4){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>★★★★☆</font>");
	}
	else if('<%=count%>' == 3){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>★★★☆☆</font>");
	}
	else if('<%=count%>' == 2){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>★★☆☆☆</font>");
	}
	else if('<%=count%>' == 1){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>★☆☆☆☆</font>");
	}
	else if('<%=count%>' == 0){
		$("#basicTb tr:eq(1) td:eq(1)").html("<font style='color:gold'>☆</font>");
	}

});


</script>

<jsp:include page="/views/sights/map.jsp"></jsp:include>

<!-- include footer -->
<jsp:include page="/views/templates/footer.jsp"></jsp:include>


</body>
</html>











