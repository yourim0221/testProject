<%@page import="com.sights.dto.SightsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
SightsDto dto = (SightsDto)request.getAttribute("dto");
//String filename = dto.getFilename();

// 세부내용 경로 설정
String titlecontent = "/views/sights/content/" + dto.getTitle() + ".jsp";
System.out.println("titlecontent=" + titlecontent);

//이미지파일 경로설정
String serverPath = request.getContextPath();
System.out.println("serverPath=" + serverPath);

//이미지 주소로 받을때랑 서버에서 받을때 두가지
String filename = (dto.getFilename().trim().substring(0,4).equals("http")
						?dto.getFilename():serverPath + "/upload/" + dto.getFilename());
%>

      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=76a79f0ac1e4d339eaf1a7c26d8e87c2&libraries=services"></script>

<!-- global conf include -->
<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<!-- 만든 CSS 파일 -->
<link rel="stylesheet" href="/jejuya/resources/css/sights/sightsdetail.css"/>

</head>
<body>
<!-- GNB include -->
<jsp:include page="/views/templates/header.jsp"></jsp:include>

<div align="center">
	<%-- <img class="mainImg" src="<%=request.getContextPath()%>\upload\<%=filename %>"> --%>
	<img class="mainImg" src="<%=filename%>">
	
	<div class="containerD">	
		<ul class="tabsD">			    
		    <li id="basic" class="menu">기본정보</li>
		    <li id="content" class="menu current">상세정보</li>
		    <li id="reviewlist" class="menu">리뷰 및 평가등록</li>
		    <li id="findroad" class="menu">길찾기</li>		
	  	</ul> 	
	  	
	  	<div class="content" align="left">
			<%
			String content = dto.getContent();
//			System.out.println("content=" + content);
			if(content == null){
			%>
				<!-- title.jsp로 저장된 세부내용 불러오기 -->
				<jsp:include page="<%=titlecontent %>" flush="false"/>
			<%
			}else{
			%>
				<%=content %>
			<%
			}
			%>									  	
	  	</div>	  	 	
	</div>	
</div>

<div id="map" style="width: 100px; height: 100px"></div>

<script type="text/javascript">

//길찾기용 지도 감추기
$(function () {
	$("#map").hide();
})

$(function () {
	
	$("#basic").mousedown(function () {
		location.href="/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle()%>";		
	});
	$("#content").mousedown(function () {
		location.href="/jejuya/SightsController?command=conBasic&title=<%=dto.getTitle()%>";		
	});
	$("#reviewlist").mousedown(function () {
		location.href="/jejuya/SightsController?command=reviewList&title=<%=dto.getTitle()%>";		
	});
	/* $("#findroad").mousedown(function () {
		location.href="";		
	}); */
			
});



</script>

<jsp:include page="/views/sights/map.jsp"></jsp:include>

<!-- include footer -->
<jsp:include page="/views/templates/footer.jsp"></jsp:include>


</body>
</html>











