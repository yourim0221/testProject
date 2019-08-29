<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.sights.dto.SightsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

//	System.out.println("tourlist까지 옴");
	
	// 전달받은 카테고리값으로 해당 카테고리 리스트
	List<SightsDto> list = (List<SightsDto>)request.getAttribute("sightslist");
	
	// 전달받은 카테고리값으로 해당 카테고리의 dto
	SightsDto categorydto = (SightsDto)request.getAttribute("categorydto");
	
	// 전달받은 카테고리값으로 해당 테마 리스트
	List<String> themeList = (List<String>)request.getAttribute("themelist");
	
	// 페이지넘버카운트
	int pagecount = (Integer)request.getAttribute("pagecount");
	
	
	String currTheme = "all";
	if( request.getParameter("theme") != null ){
		currTheme = request.getParameter("theme");
	} 
	
	int pageNum = 1;
	if( request.getParameter("pageNum") != null ){
		pageNum = Integer.parseInt(request.getParameter("pageNum"));
	}
	
	String sortsel = "all";
	if ( request.getParameter("_sort_sel") != null){
		sortsel = request.getParameter("_sort_sel");
	}
	
	String searchStr = "all";
	if ( request.getParameter("searchStr") != null){
		searchStr = request.getParameter("searchStr");
	}
	
	HashMap reviewcount = (HashMap)request.getAttribute("reviewcount");
		
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SightsList</title>

<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- 재덕이가 만든 CSS 파일 -->
<link rel="stylesheet" href="/jejuya/resources/css/sights/sightlist.css"/>

<!-- 재덕이가 만든 js 파일 -->
<!-- <script type="text/javascript" src="/jejuya/resources/js/sights/sightlist.js"></script> -->

</head>
<body>

<!-- GNB include -->
<jsp:include page="/views/templates/header.jsp"></jsp:include>

<div id="content">

<div class="top_content">
<%
	int categoryNum = categorydto.getCategory();
	if( categoryNum == 0 ){ %>
	<p class="top_content_tit">
	제주도 모든 여행지를 한 눈에…
<pre style="text-align: center;">
내가 가본 제주는 어디까지일까? 
수많은 제주의 아름다운 여행지를 취향에 맞게 선택해보자. 
368개의 크고 작은 오름을 비롯하여 눈 돌리면 어디에서나 마주치는 한라산 그리고 푸른 바다….
 제주의 보석 같은 여행지가 여러분의 선택을 기다린다.
</pre>
	</p>
	
		
<%
	}else if( categoryNum == 1 ){ %>
	<p class="top_content_tit">
	제주의 전통음식부터 다양한 음식의 천국 제주도
<pre style="text-align: center;">
여행의 즐거움 중 빼놓을 수 없는 것은 단연 음식이다. 
제주도는 흑돼지, 말요리 그리고 해산물 등 다양한 음식문화를 접할 수 있다. 
제주도의 전통 음식부터 줄을 서서 기다리는 최고 인기의 음식까지 모두 즐겨보자.
</pre>
	</p>
	
<%		
	}else if( categoryNum == 2){ %>
	<p class="top_content_tit">
	호텔부터 민박까지 다양한 종류의 숙소
<pre style="text-align: center;">
제주도 숙박시설에는 호텔 ,리조트 ,펜션 ,풀빌라 ,독채펜션 ,게스트 하우스 ,민박 ,모텔까지 다양한 종류의 숙소가 있다. 
여행을 함께 하는 구성원 그리고 여행의 목적, 체류기간에 맞춰 숙소를 결정하면 된다. 
가족, 연인을 위한 아늑하고 낭만적인 호텔이나 친구와 여행하기에 좀더 편한 펜션이 있고 
두 가족 이상의 대가족은 독립적인 공간의 독채 펜션​ 혹은 리조트가 좋을 것이다. 
부모님을 모시고 가거나 아이와 함께 간다면 수영장이 있는 제주도 호텔이나 풀빌라를 선택하는 것이 좋다.
</pre>
	</p>
	
<%		
	}
%>
</div>

<hr>

<!-- 테마텝: 테마이름을 활용해서 리스트를 보여줌 -->
<div class="recommend_tour">
	<input type="hidden" value="<%=categorydto.getCategory() %>" id="selectedCategoryInRecommend_tour">
	<input type="hidden" value="<%=currTheme %>" id="selectedThemeInRecommend_tour">
	<input type="hidden" value="<%=pageNum %>" id="pageNum">
	<input type="hidden" value="<%=sortsel %>" id="sortsel"> 
	<input type="hidden" value="<%=searchStr %>" id="searchStr">
	<ul class="theme_list">
	
	
		<%	// 테마를 선택 안하고 전체일경우
		if(currTheme.equals("all")){
		%>
		<li style="height: 146px; width: 200px; background-color: #ef6d00;">
		<a href="/jejuya/SightsListController?command=sortByConditionInSightlist
				&category=<%=categorydto.getCategory() %>">전체</a></li>
		
		<%
		}else {
		%>
			<li style="height: 146px; width: 200px; ">
		<a href="/jejuya/SightsListController?command=sortByConditionInSightlist
				&category=<%=categorydto.getCategory() %>">전체</a></li>
		<%
		}
		%>
			
				
 		<% // 카테고리별 테마들을 뿌려준다. 
		for(int i=0; i < themeList.size(); i++){
			if(themeList.get(i).equals(currTheme)){
		%>
			<li style="background-color: #ef6d00;">
			<a href="/jejuya/SightsListController?command=sortByConditionInSightlist
			&category=<%=categorydto.getCategory() %>
			&theme=<%=themeList.get(i) %>"><%=themeList.get(i) %></a>
			</li>
		<%
			}else {
		%>
			<li>
			<a href="/jejuya/SightsListController?command=sortByConditionInSightlist
			&category=<%=categorydto.getCategory() %>
			&theme=<%=themeList.get(i) %>"><%=themeList.get(i) %></a>
			</li>
		<%
			}
		} 
		%> 
		
		<% // 칸 맞추기 위한 빈칸 리스트 
		for(int i = 0; i < 6-themeList.size(); i++){
		%>
			<li></li>
		<%
		}
		%>
		
	</ul>
</div>

<hr>

<!-- 카테고리값과 조회 or 일정등록 정보로 리스트로 보여줌  -->
<div class="util_area">
<div class="category_name">
			<%
				String category1 = "";
				if (categorydto.getCategory() == 0) {	// 카테고리마다 s_day에 이름변경
						category1 = "관광지";
					} else if (categorydto.getCategory() == 1) {
						category1 = "음식점";
					} else if (categorydto.getCategory() == 2) {
						category1 = "숙소";
					}
			%>
	<p style="font-weight: 700; font-size: 30px;"><%=category1 %></p>
</div>
<div id="select_type">
	<span class="search_window">
		<input type="text" class="search_text" id="_search_text">
	</span>
	<button type="button" class="search_btn" id="_search_btn">검색</button>
	
	<select id="_sort_sel" class="sort_sel">
		<%-- <option value="<%=categorydto.getCategory() %>" id="_all_sel">전체보기</option> --%>
		<option value="all" id="_all_sel">정렬선택</option>
		<option value="addschedule">일정많은순</option>
		<option value="readcount">조회순</option>
		<option value="starcount">별점순</option>
	</select>
</div>
</div>


<div class="listcls">
	<%
		// 카테고리로 결정된 관 음 숙 중 의 하나의 리턴받은 list를 사진, 이름, 테마, 정보를 보여줌
		for (int i = 0; i < list.size(); i++) {
			SightsDto dto = list.get(i);
			String category = "";
			
	%>

<div class="item_list">
	<dl class="item_section">
		<dt class="item_top">
			<%
				if (dto.getCategory() == 0) {	// 카테고리마다 s_day에 이름변경
						category = "관광지";
					} else if (dto.getCategory() == 1) {
						category = "음식점";
					} else if (dto.getCategory() == 2) {
						category = "숙소";
					}
			
			
			String serverPath = request.getContextPath();
			
			//이미지 주소로 받을때랑 서버에서 받을때 두가지
			String filename = (dto.getFilename().trim().substring(0,4).equals("http")
									?dto.getFilename():serverPath + "/upload/" + dto.getFilename());
			System.out.println("filename: " + filename);
			
			%>
			<span class="s_day"><%=category%></span> 
			
			<!-- 받아온 DB정보에서 seq번호로 디테일화면으로 넘어가기 -->
			<div class="imgsightlistcls">
			<a href="/jejuya/SightsController?command=detailBasic&title=<%=dto.getTitle() %>">
				<img height="100%" width="100%" alt="<%=filename%>"
				class="item_img" src="<%=filename%>">
			</div>
				<div class="starcount">
					<%
						double star = Math.round( (dto.getScore()*10) )/10;
						
					%>
					<p class="starcount2" >
						<% if(star == 5){
							%><font style='color:gold'>★★★★★</font>
							<% 
						}
						else if(star == 4){
							%><font style='color:gold'>★★★★☆</font>
							  
							<%
						}
						else if(star == 3){
							%><font style='color:gold'>★★★☆☆</font>
							  
							<%
						}
						else if(star == 2){
							%><font style='color:gold'>★★☆☆☆</font>
							  
							<%
						}
						else if(star == 1){
							%><font style='color:gold'>★☆☆☆☆</font>
							  
							<%
						}else if(star == 0 ){
							%><font style='color:gold'>☆☆☆☆☆</font>
							<%
						}%>
					</p>
				</div>
				</a>
			
			
		<p class="s_tit" style="font-size: 21px;"><%=dto.getTitle()%></p>

			
		<dt style=" color: #ef6d00; font-size: 18px;" >
		<%
		if(dto.getCategory() == 0){
		%>
			<i class="fa fa-map-signs " style="padding-bottom: 10px;" ></i>
		<%
		}else if(dto.getCategory() == 1){
		%>	
			<i class="fa fa-coffee " style="padding-bottom: 10px;" ></i>
			
		<%	
		}else if(dto.getCategory() == 2){
		%>	
			<i class="fa fa-home " style="padding-bottom: 10px;"></i>
		<%	
		}
		%>
		
		테마 :<%=dto.getTheme()%>
		
		</dt>
		<dt style="float: left; width: 33%; padding: 25px 0 0 0; color: #a7a7a7; font-size: 15px;" >
		<i class="fa fa-camera-retro " style="padding-bottom: 10px;"></i><br>
		 리뷰 :<%=reviewcount.get(dto.getTitle())  %><br> 
		<!-- 리뷰 :<a class="item_reviewCount"></a><br> -->
		</dt>
		
		<dt style="float: left; width: 33%; padding: 25px 0 0 0; color: #a7a7a7; font-size: 15px;" >
		<i class="fa fa-user-plus " style="padding-bottom: 10px;"></i><br>
		일정등록수 :<%=dto.getAddSchedule() %><br>
		</dt>
		
		<dt style="float: left; width: 33%; padding: 25px 0 0 0; color: #a7a7a7; font-size: 15px;" >
		<i class="fa fa-eye " style="padding-bottom: 10px;"></i><br>
		조회수 : <%=dto.getReadcount() %>
		</dt>
		
	</dl>
</div>		
	
	<%
		}
	%>
</div>


<div class="pagetext" style="text-align: center;">
<div class="paging" >
<%
int listpage = pagecount / 6;
	if(pagecount % 6 > 0){
		listpage = listpage + 1;
	}
%>

<%
	for(int i = 1; i <= listpage; i++){
		if(pageNum == i){
%>			
<span class="nowpage">
		<%=i %>	<!-- 현재 페이지 -->
	</span>&nbsp;


<%	}else{%>

	<a class="gopage" href="/jejuya/SightsListController?command=sortByConditionInSightlist
			&category=<%=categorydto.getCategory() %>&theme=<%=currTheme %>
			&_sort_sel=<%=sortsel %>&pageNum=<%=i %>&searchStr=<%=searchStr %>"><%=i %></a>
<%
	}
}
%> 


</div>

</div>
</div>





<script type="text/javascript">
$(function () {
	var currCategory = $("#selectedCategoryInRecommend_tour").val();
	var firstsel = $("#_all_sel").val();
	var pageNum = $("#pageNum").val();
	var currTheme = $("#selectedThemeInRecommend_tour").val();
	var sortsel = $("#sortsel").val();
	var searchStr = $("#searchStr").val();
	
	// 정렬기준 변경시 실행
	$("#_sort_sel").change(function () {
		var a = $("#_sort_sel option:selected").val();
	
		var seltext = $("#_sort_sel option:selected").text();
	
		var currTheme = $("#selectedThemeInRecommend_tour").val();
		

		//컨트롤러로 설정값을 보냅니다. category(관.음.숙) theme(all, food, cafe) sort_sel(전체, 일정등록순, 조회순)
		location.href = "/jejuya/SightsListController?command=sortByConditionInSightlist"
					+ "&category=" + currCategory + "&theme=" + currTheme 
					+ "&_sort_sel=" + a + "&pageNum=" + pageNum + "&searchStr=" + searchStr;
		});
	
	// 검색 버튼 누를시 실행 
	$("#_search_btn").click(function () {
		var searchText = $("#_search_text").val();

		location.href = "/jejuya/SightsListController?command=sortByConditionInSightlist"
					+ "&category=" + currCategory + "&theme=" + currTheme 
					+ "&_sort_sel=" + sortsel  + "&searchStr=" + searchText; 
	});
	
	// 검색창에 엔터키 눌렀을때
	$("#_search_text").keypress(function (e) {
		if(e.which == 13){
			var searchText = $("#_search_text").val();

			location.href = "/jejuya/SightsListController?command=sortByConditionInSightlist"
						+ "&category=" + currCategory + "&theme=" + currTheme 
						+ "&_sort_sel=" + sortsel  + "&searchStr=" + searchText; 
		}
	});

	

});
	

	

</script>


<br><br>
<!-- include footer -->
 <jsp:include page="/views/templates/footer.jsp"></jsp:include>

</body>
</html>