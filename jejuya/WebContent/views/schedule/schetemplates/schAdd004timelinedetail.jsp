<%@page import="java.util.List"%>
<%@page import="com.schedule.dto.ScheduledetailDto"%>
<%@page import="com.schedule.dto.ScheduleDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
ScheduleDto dto=(ScheduleDto)request.getAttribute("dto");
List<ScheduledetailDto> list=(List)request.getAttribute("list");
String seq=request.getParameter("seq");
%>
<div id="timelineDetailVeil">
</div>
<form class="timeline_box"  target="_blank">
<input type="hidden" name="command" value="addtimelinebox">
	<table class="timelinetable" >
		<col width="60">
		<col width="420">

		<tr>
			<th colspan="2"><h3>TIMELINE</h3></th>
		<tr>

			<%
				int day = 3;
				String p = String.format("<a href=# onclick='addtrip(1)'>" + "<img src='/jejuya/resources/image/main/left.gif'></a>",
						"http://localhost:8090/jejuya/views/schedule/scheduleadd.jsp", day - 1);

				String n = String.format("<a href=# onclick='addtrip(2)'>" + "<img src='/jejuya/resources/image/main/last.gif'></a>",
						"http://localhost:8090/jejuya/views/schedule/scheduleadd.jsp", day + 1);
			%>

		
		<tr>			
			<th colspan="2"><%=p%>DAY<a id="timelineday">1</a> <%=n%>
				<input type="hidden"  id="triptime" value="<%=dto.getTotaldays()%>">
			</th>
		</tr>
                <input type="hidden" id="listsize" value="<%=list.size()%>">
        <tr>
        <th colspan="2">
          <button type="button"  class="btn btn-outline-warning" id="addtrip" onclick="add()" >일정 추가</button>
        </th>
        </tr>
	</table>
	<input type="hidden" value="1" id="parentseq" name="parentseq">
	<input type="hidden" value="<%=seq%>" id="seq" name ="seq">
	<div id="listdest">
<%
for(int i=0;i<list.size();i++){
	ScheduledetailDto detail=list.get(i);%>	
	<input type="hidden" id="dest<%=i%>" value="<%=detail.getDest() %>">
  <%}%>
</div>  
	<div id="listschedate">
<%for(int i=0;i<list.size();i++){
	ScheduledetailDto detail=list.get(i);
	%>	<input type="hidden" id=schedate<%=i%>" value="<%=detail.getSchedate() %>">
   <%}
%>
</div>
	<div id="listtime">
	<%for(int i=0;i<list.size();i++){
	ScheduledetailDto detail=list.get(i);%>	
	<input type="hidden" id="time<%=i%>" value="<%=detail.getStime() %>">   <%
}
%>
</div>
</form>
<script type="text/javascript">
var seq=$("#seq").val();
//alert($("#triptime").val());	
//alert($("#listsize").val());
////var listsize = $("#listsize").val();
var listsize = parseInt( $("#listsize").val() );
var dest = new Array();
var schedate = new Array();
var listtime = new Array();
//alert(listsize);

//목적지
for( i = 0 ; i < listsize ; i++){
	dest[i]=$("#listdest").children().eq(i).val().trim();
	//console.log('목적지' + i + dest[i]);
} 
//해당날
for( i = 0 ; i < listsize ; i++){
	schedate[i]=$("#listschedate").children().eq(i).val().trim();
	//console.log('날짜' + i + schedate[i]);
	//alert( 'index:' + i + ', schedate : ' + schedate[i] + ',  int : ' + parseInt(schedate[i]));
} 
//시간
for( i = 0 ; i < listsize ; i++){
	listtime[i] =$("#listtime").children().eq(i).val().trim();
	//console.log('시간' + i + listtime[i]);
} 

//몇개의 day일정인지 구해줌
////var days = $("#triptime").val();
var days = parseInt( $("#triptime").val() );

//dest, schedate, listtime 포인터 변수 ppp
var ppp = 0;
//alert(days);
//그 day 수 만큼 테이블 생성
for( i = 1 ; i <= days ; i++){
	for( j = 5 ; j < 24 ; j++ ){
		//문자열 비교를 위해 +''
		var day = ((i < 10) ? "0" + i : i + '');
		var time = ((j < 10) ? "0" + j : j + '');
		
		//console.log('day(js):[' + day + '] , time(js):[' + time + ']  , 날짜:[' + schedate[ppp] + '], 시간:[' + listtime[ppp] + ']');
		
		$(".timelinetable").append(
				"<tr class="+day+"><th>" + time + ":00"
						+ "</th>" + "<td id="+day+":"+time+":00></td></tr>");
		console.log('append done');
		$("." + day).hide();
	}
}

//controller에서 가져온 관광지, schedate, listtime을 timeline에 추가 
for( i = 0 ; i < listsize ; i++ ){
	
	var eleId = schedate[i] + ":" + listtime[i] + ":00";
	
	//console.log('포인터 : ' + ppp + ',  eleId : ' + eleId);	
	var td = document.getElementById(day+":"+time+":00");
	var checkbox = document.createElement("input");

	checkbox.setAttribute('type', 'checkbox');
	checkbox.setAttribute('class', 'timelinechkbox');
	checkbox.setAttribute('name', 'chkBoxDel');
	checkbox.setAttribute( 'value', (schedate[i] + ':' + listtime[i] + ':00') );
	
	//td에 관광지 타이틀 추가
	document.getElementById( eleId ).append( dest[i] );
	
	//td에 체크박스 추가
	document.getElementById( eleId ).append( checkbox );
	
	checkbox.onclick=function(){
	    text=document.createTextNode("");
	    var id=$(this).val();
	    //alert(id);
	    var result = confirm('일정을 삭제하시겠습니까?');
	    if( result ){
	    	document.getElementById(id).innerHTML="";	
	    }			      
	}
}
	
//1일차 show
$(".01").show();
//checkbox show
$(".timelinechkbox").show();

/* for (i = 1; i <= days; i++) {
	for (j = 5; j < 24; j++) {
		var day = ((i < 10) ? "0" + i : i);
		var time = ((j < 10) ? "0" + j : j);
	
		$(".timelinetable").append(
				"<tr class="+day+"><th>" + time + ":00"
						+ "</th>" + "<td id="+day+":"+time+":00></td></tr>");
	     $("." + day).hide();
	      for(q = i ; q < (listsize+1) ; q++ ){
	    		console.log('q입니다 : ' + q + '  , listsize' + listsize);
	    	    if(day==schedate[q]&&time==listtime[q]){
	    			document.getElementById(day+":"+time+":00").innerHTML=dest[q];
	    		
	    			var td=document.getElementById(day+":"+time+":00");
	    			var checkbox=document.createElement("input");
	    			checkbox.type="checkbox";

                    checkbox.id="del[]";
	    			checkbox.name="del[]";
	    			checkbox.value=day+":"+time+":00";
	    			td.appendChild(checkbox);
	    			
	    			checkbox.onclick=function(){
	    			    text=document.createTextNode("");
	    			    var id=$(this).val();
	    			    alert(id);
	    			    document.getElementById(id).innerHTML="";
	    			    
	    			}
	    	     }
			}  
	
	}
	$(".01").show();
} */




var returnofcontroller = "1";
//일정 추가 버튼을 눌렀을 때 발생하는 함수 입니다
$("#addtrip").click(function ()  {


	//alert(totaldata);  //우리가 보낼 데이터 !
	var totaldata = "";
	var len=$(".timelinetable td").length;
//

	var td="";
	for(i=0; i < len ; i++){
		
		//모든 td를 한 번씩 선택해줘야함
		if( $(".timelinetable td").eq(i).text().length >1 ){
			td=$(".timelinetable td").text().trim();
		
			var tempVar = $(".timelinetable td").eq(i).attr("id").trim();//id가 저장됨
			totaldata += tempVar.substring(0, 6) + $(".timelinetable td").eq(i).text() + "_";  //01:12:함덕해수욕장_ 까지 저장됨
			
		}
	}
	
	
	//여행 총 일정에 대한 정보 submit -> controller와 ajax통신을 통해서 리턴이 true면 timeline submit
	//ajax 통신을 이용하지 않는다면 timeline_box폼의 데이터가 날아감.
	//$("#frm").submit();
	var frm=$("form[id='frm']").serialize();
	
	alert(seq);
	$.ajax({
		url:"/jejuya/ScheduleController?command=update",
		type:"get",
		//data:{ "frm":frm ,"seq":seq},
		data: frm,
		success:function(data,status,xhr){
			returnofcontroller = data.trim();
			
			$("#parentseq").val(data);
			//alert("부모시퀀스:"+$("#parentseq").val());
			
				//$(".<form class="timeline_box").submit(); 
				var timelinebox=$(".timeline_box").serialize(); 
				$.ajax({
					url:"/jejuya/ScheduleController?command=updatedetail",
					type: "get",
					data:{"timelinebox":timelinebox,"seq":seq,"totaldata":totaldata},
			         success:function(dataInner,statusInner,xhrInner){	    	
				    	
				    	//alert("통신성공");
				    	if(dataInner.trim() == 'true'){
				    		alert("여행일정 수정이 완료 되었습니다");
				    		location.href="/jejuya/ScheduleController?command=addlist";
				    	}else{
				    		alert("값을 못가져옴");
				    	}
				    }
				});
			
		},
		error:function(xhr,status,error){
			//alert("통신실패"+error);
		}, complete:function(xhr,status){
			//alert("통신 종료!");
	    }	
});

});


function addtrip(e) {
	//이전으로 돌아가는 버튼을 눌렀을 때
	days=$("#triptime").val();
	if(e==1){
		var currday=parseInt($("#timelineday").html().trim());
		if(currday!=1){
			$("#timelineday").text(currday-1);	
			if(currday>10){
				 $(".0"+currday).hide();
			     $(".0"+$("#timelineday").html().trim()).show();
			}else{
				 $(".0"+currday).hide();
				$(".0"+$("#timelineday").html().trim()).show();
			   
			}			
		}		
		//alert(currday-1);		
	//그다음 day로 가는 버튼을 눌렀을 때
	}else{		
		//InparseInt($("#timelineday").html().trim());
		
		currday=parseInt($("#timelineday").html().trim());
			//일정 수 만큼만 옆으로 넘기게 할 수 있게 제어
			
			if (currday < days) {
				$("#timelineday").text(currday + 1);
				if (currday > 10) {
					$("."+$("#timelineday").html().trim()).show();
				    $("."+currday).hide();
			
				} else {
					$(".0"+$("#timelineday").html().trim()).show();
				    $(".0"+currday).hide();
				}
			}
		}
}



			
</script>
</html>