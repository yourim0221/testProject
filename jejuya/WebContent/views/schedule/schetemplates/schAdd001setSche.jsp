<%@page import="com.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>	
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<%
	//접근권한 검사 부분
	String currIdInAdd001 = "guest";
	if( request.getSession().getAttribute("currUser") != null ) {
		Object obj = request.getSession().getAttribute("currUser");
		currIdInAdd001 = ( (MemberDto)obj ).getId();
	}
	if( currIdInAdd001.equals("guest") ){
		out.println("<script>alert('로그인 정보가 없습니다. 로그인 후 이용해 주세요.'); location.href='/jejuya/member'</script>");
	}
%>
<h3>나의 여행 일정</h3>
<form id="frm" method="get" >
<font id="addtextname" size="2"> 작성자 : <%=currIdInAdd001 %> </font>

<table id="addtable" border="1"rules="none" >
	<col width="100">
	<col width="150">
	<tr>
		<th><font size="1" id="addtext1">새 여행 일정등록</font></th>
		<th><font size="1" id="addtext2">나의 여행 일정</font></th>
	</tr>
	<tr>
		<th><font size="1" id="titletable">제목</font></th>
		<td><input type="text" placeholder="예)우리들의 4박 5일 제주" id="title" name="title"></td>
	</tr>

	<tr >
		<th><font size="1" id="titletime">기간</font>	</th>
		<td> <p id="tripdate"  ></p>
		<input type="hidden" id="tripdate_hidden" name="tripdate" >
		</td>
		
	</tr>

	<tr>
		<th colspan="2" >
		<div align="center">
			<input type="text" id="firstday" name="firstday" >
			<input type="text" id="lastday" name="lastday">
		</div>
		</th>
	</tr>
	<tr>
		<th><font size="1" id="member">인원</font> 
			<select id="memberselec" name="memberselec">
				<%for(int i = 0 ; i < 11 ; i++ ){
					out.println("<option value=\"" + i + "\">" + i + "명</option>");	
				}%>				
			</select>
		</th>
		<th><font size="1" id="with">일행</font> <select id="withselec" name="withselec">
				<option value="0">선택</option>	<option value="부모">부모</option>
				<option value="아이">아이</option>		<option value="친구">친구</option>
				<option value="커플">커플</option>		<option value="혼자">혼자</option>
		</select></th>
	</tr>

	<tr>
		<th><font size="1" id="section">구분</font></th>
		<th>
			<select id="sectionselec" name="sectionselec">
				<option value="0">여행 구분을 선택하세요</option>
				<option value="휴식">휴식과 치유여행</option>
				<option value="레저">레저와체험</option>
				<option value="트레킹">천천히 걷기</option>
				<option value="식도락">식도락 여행</option>
				<option value="문화체험">제주의 문화유산</option>
				<option value="전시">전시와 행사</option>
				<option value="쇼핑">쇼핑</option>
			</select>
		</th>
	</tr>
	<tr>
		<th colspan="2">
			<input  name="open" id=open type="radio" value="1">	<font size="1" id="open">공개</font> 
			<input  name="open"	 id=open type="radio" value="0"><font size="1" id="nonopen">비공개</font>
		</th>
	</tr>
	<tr>
		<th colspan="2">
			<div class="addschedule" align="center">
			<!-- 	<button type="button" id="maketrip" >일정 만들기</button>
				<button type="button" id="updatetrip" >일정 수정</button> -->
			    <button type="button" class="btn btn-outline-warning" id="maketrip">일정만들기</button>
			    <!-- <button type="button" class="btn btn-outline-warning" id="updatetrip">일정수정</button> -->
			    <input type="hidden" value="0" id="addtripchange">		    
			</div> 
		</th>
	</tr>
</table>
</form>
<script type="text/javascript">
var totalTripDate = 1;


//일정 수정 버튼을 눌렀을 때 발생하는 이벤트 입니다
$("#updatetrip").click(function () {
	
	  $(".setScheduleOption input").attr("readonly",false);
	     $("input[name='open']").attr('disabled',false);
	     $("#sectionselec").removeAttr("disabled");
	     $("#withselec").removeAttr("disabled");
	     $("#memberselec").removeAttr("disabled");
	     $("#firstday").datepicker('option','disabled',false);
	     $("#lastday").datepicker('option','disabled',false); 
	    // alert("Dd");
});
//일정만들기 버튼을 눌렀을 때 발생하는 이벤트입니다
$("#maketrip").click(function () {
	 var open = $(':input[name=open]:radio:checked').val();
    if($("#title").val().trim()==""){
    	alert("여행 제목을 입력 해 주세요");
    	$("#title").focus();
    }else if($("#firstday").val().trim()==""){
    	alert("여행 시작 날짜를 입력 해 주세요");
    	$("#firstday").focus();
    }else if($("#lastday").val().trim()==""){
    	alert("여행 마지막 날짜를 입력 해 주세요");
    	$("#lastday").focus();
    }else if($("#memberselec").val()==0){
    	alert("여행 인원을 선택 해 주세요 ");
    	$("#memberselec").focus();
    }else if($("#withselec").val()==0){
    	alert(" 일행을 선택 해 주세요 ");
    	$("#withselec").focus();
    }else if($("#sectionselec").val()==0){
    	alert(" 구분을 선택 해 주세요 ");
    	$("#sectionselec").focus();
    }else if(open==null) {
       alert("공개여부를 선택 하세요");
       return false;
   }else if( open  ){
	   //수정!!!!!!!!!!!!!!!!!!!!!!!1
	    $("#addtripchange").val('1');  
      return true;
  }
});



//startday와 lastday 즉 여행기간을 알아내기 위하여 여행시작일과 여행마지막날을
//구하기 위해 calendar를 넣는 작업입니다
$(function () {
	var week=['일','월','화','수','목','금','토'];
	var arr1;
	var startday='';
	var lastday='';
	//여행시작일
	$("#firstday").datepicker({
		dataFormat:"yy/dd/mm",
		dataNamesMin:week,
		monthNames:["1월","2월","3월","4월","5월","6월","7월","8월",
			   "9월","10월","11월","12월"],
	    minDate:0,
	    onSelect:function(d){
	    	$("#lastday").datepicker("option","minDate",d);
	    }

	});
	
	//마지막날 달력
	$("#lastday").datepicker({
		dataFormat:"yy/dd/mm",
		dataNamesMin:week,
		monthNames:["1월","2월","3월","4월","5월","6월","7월","8월",
			   "9월","10월","11월","12월"],
		minDate:0,
	    onSelect:function(d){
	    	 $("#firstday").datepicker("option","maxDate",d);
	    },
	    onClose: function(){
	    	calBetweenTwoDays();
	    }
	});
});

//마지막 날짜를 입력하고 닫힐 때 발생하는 함수
//마지막날 달력 onClose이벤트리스너에 추가
function calBetweenTwoDays(){
	//alert($("#firstday").val());
	//alert($("#lastday").val());
	
	var start = $("#firstday").val();
	var end = $("#lastday").val();
	
	//startarr 변수 선언부 없음
	/* startarr = start.split('/'); */		
	var startarr = start.split('/');
	//alert("startarr[0]"+startarr[0]+"startarr[1]"+startarr[1]+"startarr[2])"+startarr[2]);
	
	//startarr 변수 선언부 없음
	var endarr=end.split('/');
	//alert("endarrarr[0]"+endarr[0]+"endarrarr[1]"+endarr[1]+"endarrarr[2])"+endarr[2]);
			
	var startday = new Date( startarr[2], startarr[0], startarr[1]);
	var endday = new Date( endarr[2], endarr[0], endarr[1]);
		
	//시작날과 끝날을 입력받아 두 날짜를 뺄셈하여 여행일정이 몇박며칠인지 알아내기 위해 뺄셈
	var betweenday = Math.ceil( endday.getTime() - startday.getTime() ) / ( 60*1000*60*24 );
	var betweendayy = betweenday+1;
		
	//여행 일수를 구해서 p 태그에 넣어줌
	document.getElementById("tripdate").innerHTML=betweenday+"박"+betweendayy+"일";
	$("#tripdate_hidden").val(betweendayy);
	
	//총 여행일수가 x박 몇일인지 저장
	totalTripDate = betweendayy;
	$("#triptime").val(totalTripDate);

}
</script>

</html>