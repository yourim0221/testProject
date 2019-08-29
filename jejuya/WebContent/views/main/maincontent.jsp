<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sights.dto.SightsDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html>
<html> -->

<script type="text/javascript" src="/jejuya/resources/js/main/maincontent.js"></script>
<link href="/jejuya/resources/css/main/maincontent.css" rel="stylesheet">

<br><br><br><br><br><br>


<%
	List<SightsDto> mainListAllCate = new ArrayList<>();

	if( request.getAttribute("mainListAllCate") != null){
		mainListAllCate = (List<SightsDto>)request.getAttribute("mainListAllCate");
	}	
%>
<div class="container text-center my-3">
	
	    <h2 class="carouselDescription">제주도의 아름다운 관광지</h2>
	    <a class="carouselDescriptionLink" href="/jejuya/SightsListController?command=sortByConditionInSightlist&category=0">더보기 ></a>
    
    <div id="recipeCarousel" class="carousel slide w-100" data-ride="carousel">
        <div class="carousel-inner w-100" role="listbox">
        	<div class="carousel-item row no-gutters active">
        <%
        	
        	for(int i = 0 ; i < 4 ; i++){
        		SightsDto dto = mainListAllCate.get(i);
        		//첫 번째 carousel-item에만 active 클래스 부여, 한 화면에는 요소 4개씩만 표현
         		%>
         			<!-- 메인화면에 뿌려줄 관광지 정보 -->
          		 	<div class="col-3 float-left" align="center">
               			<dl class="item_section">
							<dt class="item_top">
								<span class="mainItemTitle"><%=dto.getTitle() %></span> 
								<!-- 받아온 DB정보에서 seq번호로 디테일화면으로 넘어가기 -->
								<a href="/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle() %> ">
									<img height="130px" width="100%" alt="dto.getFilename()"
									class="img-fluid" src="/jejuya/upload/<%=dto.getFilename() %>">
								</a>	
								<table class="itemDescriptionTbl">
									<tr><td><a class="mainItemReadcnt">조회수: <%=dto.getReadcount() %> </a></td></tr>
									<tr><td><a class="mainItemAddsche">리뷰등록수: <%=dto.getAddSchedule() %> </a></td></tr>
									<tr><td><a class="mainItemTheme">테마: <%=dto.getTheme() %> </a></td></tr>
									<tr><td><a class="mainItemCont"></a>	</td></tr>
								</table>				
																
							</dt>
						</dl>
                	</div>
        		<%     			
        	}
        
        %>
        	 </div>	
        	 <div class="carousel-item row no-gutters">
        	 	<%
        	
		        	for(int i = 4 ; i < 8 ; i++){
		        		SightsDto dto = mainListAllCate.get(i);
		        		//첫 번째 carousel-item에만 active 클래스 부여, 한 화면에는 요소 4개씩만 표현
		         		%>
		         			<!-- 메인화면에 뿌려줄 관광지 정보 -->
		          		 	<div class="col-3 float-left" align="center">
		               			<dl class="item_section">
									<dt class="item_top">
										<span class="mainItemTitle"><%=dto.getTitle() %></span> 
										<!-- 받아온 DB정보에서 seq번호로 디테일화면으로 넘어가기 -->
										<a href="/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle() %> ">
											<img height="130px" width="100%" alt="dto.getFilename()"
											class="img-fluid" src="/jejuya/upload/<%=dto.getFilename() %>">
										</a>					
										<table class="itemDescriptionTbl">
											<tr><td><a class="mainItemReadcnt">조회수: <%=dto.getReadcount() %> </a></td></tr>
											<tr><td><a class="mainItemAddsche">리뷰등록수: <%=dto.getAddSchedule() %> </a></td></tr>
											<tr><td><a class="mainItemTheme">테마: <%=dto.getTheme() %> </a></td></tr>
											<tr><td><a class="mainItemCont"></a>	</td></tr>
										</table>							
									</dt>
								</dl>
		                	</div>
		        		<%     			
		        	}
		        
		        %>
        	 </div>
        </div>
        <a class="carousel-control-prev" href="#recipeCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#recipeCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>    
</div>



<div class="mainContentBgImg">
</div>

<br><br><br><br><br><br><br><br><br><br><br>


<!-- </html> -->