<%@page import="com.member.dto.MemberDto"%>
<%@page import="common.util.pageDto"%>
<%@page import="com.schedule.dto.ScheduleDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- common <head> include -->
<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!-- 개발자 작성 리소스 -->
<link rel="stylesheet"
	href="/jejuya/resources/css/schedule/scheduleadd.css"></link>

<!-- 지도 관련 리소스 -->
<link rel="stylesheet"
	href="/jejuya/resources/css/schedule/scheduleadd_map.css"></link>

<!-- 타임라인 관련 리소스 -->
<link rel="stylesheet"
	href="/jejuya/resources/css/schedule/scheduleadd_timeline.css"></link>

<style type="text/css">
</style>
</head>
<body>

	<%!public pageDto page(int total, int pg) {
		int list = 7; //전체페이지에 10개를 출력 
		int startnum = 1;
		int lastnum = 1;
		int pagingnum = 0; //페이징 갯수

		if (total % list != 0) {
			pagingnum = total / list + 1;
		} else {
			pagingnum = total / list;

		}
		lastnum = pg * list;
		startnum = lastnum - (list - 1);

		pageDto dto = new pageDto(startnum, lastnum, pagingnum);

		System.out.println("daopage" + dto.toString());
		return dto;

	}%>

	<jsp:include page="/views/templates/header.jsp"></jsp:include>



	<!-- response.sendRedirect("/Projectjeju/ScheduleController?command=tourism&pg=1"); -->


	<%
		List<ScheduleDto> list = (List) request.getAttribute("list");

		String pajing = request.getParameter("page");

		System.out.println("paging" + pajing);
		if (pajing == null) {
			pajing = 1 + "";
		}
		pageDto pagedto = page(list.size(), Integer.parseInt(pajing));
	%>
	
<%
	//접근권한 검사 부분
	String currId = "guest";
	MemberDto currMem = new MemberDto("guest", "guest", "guest", "guest", "guest");
	if( request.getSession().getAttribute("currUser") != null ) {
		Object obj = request.getSession().getAttribute("currUser");
		currId = ( (MemberDto)obj ).getId();
		currMem = (MemberDto)obj;
	}
	if( currId.equals("guest") ){
		out.println("<script>alert('로그인 정보가 없습니다. 로그인 후 이용해 주세요.'); location.href='/jejuya/member'</script>");
	}
%>
	<div class="total">
		
		<div class="photo_zone">
			<input type="hidden" id="city" value="Jeju, KR"></input>
			<img id="weatherImg"></img>
			<div id="showWeatherForcast">
			</div>
		</div>
			
		<div class="profile_wrap clear">	
			<div class="weather_zone"></div>
			<div class="name_zone">
				<span class="user_name"><b><%=currMem.getName() %></b></span>
				<span class="text_tit">님의 제주여행</span>
			</div>
		</div>
		

		<div>
			<div class="wrap_tit_zone">
				<p class="wrap_tit">
					&nbsp;&nbsp;나의 여행일정 (<%=list.size()%>)
				</p>
			</div>
			<!-- <div class="util_area">
				<b>최신순/</b>
				<b>오래된순/</b>
				><b>여행전/</b>
				<b>여행완료</b>

			</div> -->
			<div class="util_area2">
				<div class="order_type">
					<select id="searchselecttitle">
						<option value="title">제목</option>

					</select> <input type="text" id="titlename">
					<button type="button" class="btn btn-warning" id="search">검색</button>
				</div>
			</div>
		</div>
		<br>


		<div class="mylist">

			<table id="addtablelist" align="center">

				<col width="70">
				<col width="100">
				<tr>
					<span class="add_day">일정등록</span>
					<td colspan="2"><dt class="dt">나의 일정만들기</dt></td>

				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2"><sapn> <a
							href="/jejuya/ScheduleController?command=tourism&pg=1"> <img
							src="/jejuya/resources/image/main/btn_add.png">
						</a></td>
				</tr>
			</table>
		</div>


		<%
			int lastnum = 0;
			if (list.size() > pagedto.getLastnum()) {
				lastnum = pagedto.getLastnum();
			} else {
				lastnum = list.size();
			}

			System.out.println("pagedto" + pagedto.toString());
			for (int i = pagedto.getStartnum() ; i <= lastnum; i++) {
				ScheduleDto dto = list.get(i - 1);
		%>
		<div class="mylist">
			<dl>
				<dd class="item_del" onclick="del(<%=dto.getSeq()%>)">
				
				</dd>
			</dl>
			<a href="/jejuya/ScheduleController?command=detail&seq=<%=dto.getSeq()%>">
				<table id="addtablelist">
					<col width="70">
					<col width="100">
					<tr>
						<td colspan="2" id="jejuimg"><img
							src="/jejuya/resources/image/main/jejuya!!.jpg"></td>
					</tr>
					<tr>
						<td colspan="2"><span class="s_day"><%=dto.getTotaldays() - 1%>박<%=dto.getTotaldays()%>일</span>

							<p class="s_tit"><%=dto.getTitle()%></p>
							<p class="s_theme"><%=dto.getSection()%>|<%=dto.getCompanion()%>|<%=dto.getMember()%>명
							</p> <%
 	String date[] = dto.getSdate().split("/");
 %> <span class="e_day"><%=date[2]%>년<%=date[0]%>월<%=date[1]%>일</span>
						</td>
					</tr>
				</table>
			</a>

		</div>



		<%
			}
		%>
		<div id="pagingbtn">
			<%
				for (int i = 1; i <= pagedto.getPagignum(); i++) {
			%>

			<a href="/jejuya/ScheduleController?command=addlist&pajing=<%=i%>"><b>|<%=i%>|
			</b></a>

			<%
				}
			%>
		</div>

	</div>

	<jsp:include page="/views/templates//footer.jsp"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){
        var img=document.getElementById('weatherImg');
		var atmos;

		var city = $("#city").val();//입력한 도시정보 가져오는듯 - 단일 모드

		var key = 'b525af562a68cd4a32cdb6fa717b05e4'; 

		$.ajax({/* ajax는 새로고침할 필요없이 버튼을 클릭하면 자동으로 새로 기능이 수행하게끔 해준다. */

		url: 'http://api.openweathermap.org/data/2.5/weather?lang={lkr}',//이미지

		dataType: 'json',

		type: 'GET',

		data: {q:city, appid:key, units:'metric'},//도시 API키 단위법:섭씨, 키, 기간

		success: function(data){//출력부

		var wf = '';

		var atmos = "";

		$.each(data.weather, function(index, val){

			atmos = val.main;//날씨를 변수로 지정
			
			
			if(atmos == "Clear"){
				atmos="맑음"
				img.src ="/jejuya/resources/image/main/sun.png";

				}else if(atmos == "Rain"){
					atmos="비"
				    img.src  ="/jejuya/resources/image/main/rain.png";
				
				}else if(atmos == "Clouds"){
					atmos="구름많음"
					img.src  ="/jejuya/resources/image/main/sun.png";

				}	
			
			
		wf += '<p><b>' +'제주도'+ ":"+

		data.main.temp + '°C ' + ' | ' + atmos ;

		



		});//선택한 요소의 속성src의 이미지 값을 weatherImg에 줌

		$("#showWeatherForcast").html(wf);

		}

		});

		});

	

function del(e) {
	
	//함수를 통해 들어온 시퀀스 넘버를 컨트롤러로 넘겨 해당 글을 삭제
	var seq=e;
	location.href="/jejuya/ScheduleController?command=del&seq="+seq;
}

$("#search").click(function () {

	if($("#titlename").val().trim()==""){
		alert("제목을 입력하세요");
	    $("#titlename").focus();
	  
	}else{
		
		var title=$("#titlename").val();
		alert(title);
		location.href="/jejuya/ScheduleController?command=search&title="+title;	
	}
	
});
</script>

</body>
</html>