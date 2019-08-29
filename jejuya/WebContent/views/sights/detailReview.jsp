<%@page import="com.member.dto.MemberDto"%>
<%@page import="com.sights.dto.Paging"%>
<%@page import="com.sights.dto.SightsDto"%>
<%@page import="com.sights.dto.SightReviewDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
   
<%
//로그인한 멤버
String currUserId = "guest";
if( request.getSession().getAttribute("currUser") != null ){
	currUserId = ( (MemberDto)request.getSession().getAttribute("currUser") ).getId();
}
System.out.println("currUserId=" + currUserId);

// 리뷰평점변수
int score = 0;
// 날짜 시간 제외할 변수
String writeDay = null;

//리뷰 리스트
List<SightReviewDto> list = (List<SightReviewDto>)request.getAttribute("list");
System.out.println("list.size()=" +list.size());
//for(int i = 0 ; i < list.size(); i++){
//	System.out.println(list.get(i).toString());
//}

//title뽑을용
SightsDto dto = (SightsDto)request.getAttribute("dto");
String title = dto.getTitle();
System.out.println("detailReview.jsp title:" + title);

//리뷰 총 갯수
int count = (Integer)request.getAttribute("count");

// 정렬용
String sorting = (String)request.getAttribute("sorting");
System.out.println("detailReview.jsp sorting:" + sorting);

//페이징
Paging paging = (Paging)request.getAttribute("paging");

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

<!-- global conf include -->
<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<!-- 만든 CSS 파일 -->
<link rel="stylesheet" href="/jejuya/resources/css/sights/sightsdetail.css"/>


</head>
<body>
<!-- 현재 유저의 정보를 갖는 hidden 태그, 접속정보가 세션에 없으면 value는 guest -->
<input type="hidden" value="guest" id="<%=currUserId %>">

<!-- GNB include -->
<jsp:include page="/views/templates/header.jsp"></jsp:include>

<div align="center">
	
	<img class="mainImg" src="<%=filename%>">

	<div class="containerD">	
		<ul class="tabsD">			    
		    <li id="basic" class="menu">기본정보</li>
		    <li id="content" class="menu">상세정보</li>
		    <li id="reviewlist" class="menu current">리뷰 및 평가등록</li>
		    <li id="findroad" class="menu">길찾기</li>		 
	  	</ul> 	
	  		  	
			<font style="font-size: 20px">여행가의 리뷰<font style="color: #ef6d00">(<%=count %>)</font></font><br>
			<font>
				<a href="/jejuya/SightsController?command=reviewList&title=<%=dto.getTitle()%>">최신순</a> | 
			    <a href="/jejuya/SightsController?command=reviewList&title=<%=dto.getTitle()%>&sorting=score">평가순</a>
			</font>
			
			<div class="content">
			<%
			if(list.size() == 0){
				%>
				작성된 글이 없습니다	
				<%
			}else{
				for(int i = 0; i < list.size(); i ++){
					SightReviewDto review = list.get(i);
					score = review.getScore();
					writeDay = review.getWdate();
					String wday = writeDay.substring(0, 10);
					
					String writeId = review.getId();
					System.out.println("writeId=" + writeId);				
				%>
			<table class="tb1">
			<col width="150"><col width="380"><col width="15">
				<tr>
					<td id="tdId" class="td1" align="center">
					<%=writeId %><br>
					<%=wday %><br>
					<% if(score == 5){
							%><font style='color:gold'>★★★★★</font><% 
						}
						else if(score == 4){
							%><font style='color:gold'>★★★★</font><%
						}
						else if(score == 3){
							%><font style='color:gold'>★★★</font><%
						}
						else if(score == 2){
							%><font style='color:gold'>★★</font><%
						}
						else if(score == 1){
							%><font style='color:gold'>★</font><%
						}%>		
					</td>
					<td class="td1" colspan="3">
						<%=review.getContent() %><br>												
					</td>					
						<%
						if(currUserId.equals(writeId)){
						%>
						<td class="td1">	
							<input type="button" id="delBtn<%=i %>" class="delBtn" value="삭제" seq=<%=review.getSeq() %> >
						</td>	
						<%
						}
						%>																					
				</tr>
				<tr>
					<td colspan="3" align="center" class="td1">
						<%	
						if(review.getFilename() != null){
							String str = review.getFilename();
				
							String[] reviewfile = str.split("/");
				//			for(String file : filename){
				//				System.out.println(file);
				//			} 
				
							for(String file : reviewfile){
								%>							
								<img class="reviewImg" src="<%=request.getContextPath()%>/upload/review/<%=file %>" style="width: 130px; height: 80px">
														
								<div id="myModal" class="modal">									
									<div class="modal-content">
									<!-- 	<button type="button" class="closeModal" data-dismiss="modal">&times;</button><br> -->
										<img class="modalImg" src="" style="width: 500px; height: 300px">				
									</div>	
								</div>							
								<%
							}						
						}
						%>
					</td>
				</tr>								
			</table>
				<%	
				}
			}
			%> 
			<hr style="border: 0.5px solid #ef6d00">
			
			<c:url var="action" value="/SightsController?command=reviewList"/>
			<c:if test="${paging.prev}">
			 <a href="${action}&page=${paging.beginPage-1}&title=<%=title%>&sorting=<%=sorting%>">prev</a>
			</c:if>
			<c:forEach begin="${paging.beginPage}" end="${paging.endPage}" step="1" var="index">
				<c:choose>
					<c:when test="${paging.nowpage==index}">
						[${index}]
					</c:when>
					<c:otherwise>
						<a href="${action}&page=${index}&title=<%=title%>&sorting=<%=sorting%>">[${index}]</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${paging.next}">
				<a href="${action}&page=${paging.endPage+1}&title=<%=title%>&sorting=<%=sorting%>">next</a>
			</c:if>
			<br><br>	
								
			<form id="frm" method="post" enctype="multipart/form-data">
					 <input type="hidden" name="id" value="<%=currUserId%>">
					 <input type="hidden" name="title" value=<%=title %>>
				<!-- <input type="hidden" name="command" value="addReview"> -->
				
				<table class="tb2">
					<tr>
						<td colspan="3">
							<input type="radio" id="score" name="score" value="5" checked="checked">매우 좋아요
							<input type="radio" id="score" name="score" value="4">좋아요
							<input type="radio" id="score" name="score" value="3">보통
							<input type="radio" id="score" name="score" value="2">싫어요
							<input type="radio" id="score" name="score" value="1">매우 싫어요
						</td>
					</tr>
					<tr>
						<%
						if(currUserId.equals("guest")){
						%>
						<td colspan="3">
							<textarea style="width: 800px" rows="5" name="content" placeholder="로그인한 사람만 쓸 수 있습니다" readonly="readonly"></textarea>
						</td>		
						<%
						 }else{							 
						%>
						<td colspan="3">
							<textarea style="width: 800px" rows="5" name="content" placeholder="리뷰를 입력해주세요"></textarea>
						</td>		
						<%	 
						 }
						%>
												
					</tr>
					<tr>
						<td><input type="file" class="fileTd" id="fileload" name="fileload1"></td>
						<td><input type="file" class="fileTd" id="fileload" name="fileload2"></td>
						<td><input type="file" class="fileTd" id="fileload" name="fileload3"></td>
					</tr>	
					<tr>
						<td colspan="3" align="center" style="padding: 10px">
							<input type="button" value="등록" id="btnD" class="btnD">
						</td>
					</tr>
				</table>
			</form>		
										  	
	  	</div>	  	 	
	</div>	
</div>

<div id="map" style="width: 100px; height: 100px"></div>

<script type="text/javascript">
var modal = document.getElementById("myModal");

// 길찾기용 지도 감추기
$(function () {
	$("#map").hide();
})

//리뷰 사진 누르면 모달open
$(function () {		
	$(".reviewImg").click(function () {	
		var src = $(this).attr("src");
//		alert(src);
		
		$(".modalImg").attr("src", src);				
		$(".modal").css("display","block");											
	});

	$(".modalImg").click(function () {
		$(".modal").css("display", "none");
	});
	
	// 리뷰테이블 회색으로 변하기	
	$(".tb1").hover(function () {
		$(this).css("background", "#f4f4f4");
	}, function () {
		$(this).css("background", "#ffffff");
	});	
	
});

$(function () {
	$("#btnD").click(function () {
		if(<%=currUserId.equals("guest")%>){
			alert("로그인 해주세요");
			return;			
		}else{
			$("#frm").attr("action", "/jejuya/SightsController?command=addReview").submit();
		}								
	});	
});


/* //삭제버튼 */
$(function () {		
  	$(".delBtn").click(function () {
		var id = $(this).attr("id");
//		alert(id);		
		var seq = $("#" + id).attr("seq");
//		alert(seq);	
		
		location.href="/jejuya/SightsController?command=delReview&seq="+seq+"&title=<%=title%>";  	
	});
	   	
});

// 메인메뉴
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
	});  */

	
});




</script>

<jsp:include page="/views/sights/map.jsp"></jsp:include>

<!-- include footer -->
<jsp:include page="/views/templates/footer.jsp"></jsp:include>

</body>
</html>











