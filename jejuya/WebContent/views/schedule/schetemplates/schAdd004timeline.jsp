<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<form class="timeline_box"  target="_blank">
<input type="hidden" name="command" value="addtimelinebox">
	<table class="timelinetable">
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
				<input type="hidden"  id="triptime" value="0">
			</th>
		</tr>

        
<%--         
        <tbody class="timelinetable">
        <%
        for(int i=5;i<24;i++){
        	%>
        	<tr >
			<th><%=((i<10)?"0"+i:i) %>:00</th>
			<td id= <%=((i<10)?"0"+i:i) %>:00></td>
		    </tr>        	
        	<%
        }   
        
         %>
             
      
      
      
      
         </tbody> --%>
        <tr>
        <th colspan="2">
          <button type="button" id="addtrip" class="btn btn-outline-warning"  >일정 추가</button>
        </th>
        </tr>
	</table>
	<input type="hidden" value="1" id="parentseq" name="parentseq">
</form>
<script type="text/javascript">
//수정!!!!!!!!!!!!!!!!!!!!!!!!!!!!
var returnofcontroller = "1";
var checktimeline=1;
//일정 추가 버튼을 눌렀을 때 발생하는 함수 입니다
$("#addtrip").click(function () {
	
	//#frm폼의 submit 작업이 완료되어 DB에 저장을 했다면, contoller로 부터 여행 총 일정의 seq번호를 받아올 수 있다.
	//seq번호를 외래키로 갖게끔 timeline_box의 세부일정들을 DB에 저장(contoller로 submit) 하면 끝.
	//보낼 데이터
	//<td id="01:12:00">함덕해수욕장<input type="checkbox" id="delck[]" value="함덕해수욕장"></td>
	//seq	patent_seq	schedate	stime	dest 
	//얘는nextval, seq, 01, 12, 함덕해수욕장

	//alert(totaldata);  //우리가 보낼 데이터 !
	var totaldata = "";
	var len=$(".timelinetable td").length;
	//alert("len:"+len);

	var td="";
	var timetitle="";
	for(i=0; i < len ; i++){
		
		//모든 td를 한 번씩 선택해줘야함
		if( $(".timelinetable td").eq(i).text().length >1 ){
			td=$(".timelinetable td").text().trim();
			//alert(td.length);
			//alert(td);
			var tempVar = $(".timelinetable td").eq(i).attr("id").trim();//id가 저장됨
			totaldata += tempVar.substring(0, 6) + $(".timelinetable td").eq(i).text() + "_";  //01:12:함덕해수욕장_ 까지 저장됨
			//01:05:우도_01:07:일출봉_02:12:함덕해수욕장_01:12:함덕해수욕장_01:12:함덕해수욕장_01:12:함덕해수욕장_
			//언더바(_)로 먼저 자르고 콜롱(:)으로 자르면 DB에 바로 저장 가능 schedate, stime, dest
			//01:07:한라산_01:14:월정리해변_
			
			timetitle += $(".timelinetable td").eq(i).text() + "_";
			// 타이틀값만 받아오기 위해서 -> 한라산_월정리해변_
		}
	}
	//여행 총 일정에 대한 정보 submit -> controller와 ajax통신을 통해서 리턴이 true면 timeline submit
	//ajax 통신을 이용하지 않는다면 timeline_box폼의 데이터가 날아감.
	//$("#frm").submit();
	var frm=$("form[id='frm']").serialize();
	$.ajax({
		url:"/jejuya/ScheduleController?command=addtrip",
		type:"get",
		data:frm,
		success:function(data,status,xhr){
			
			
			//alert(data);
			returnofcontroller = data.trim();
			//alert("returnofcontroller:"+returnofcontroller + " data:" + data.trim());
			//ajax통신을 이용하여 부모시퀀스 값을 구해옴
			//구해온 부모시퀀스 값을 hidden vlaue값에 넣어줬음
			$("#parentseq").val(data);
			//alert("부모시퀀스:"+$("#parentseq").val());
			
			
				//$(".<form class="timeline_box").submit(); 
				var timelinebox=$(".timeline_box").serialize(); 
				$.ajax({
					url:"/jejuya/ScheduleController?command=addtimelinebox",
					type: "get",
					data:{"timelinebox":timelinebox,"returnofcontroller":returnofcontroller,"totaldata":totaldata,"timetitle":timetitle},
			         success:function(dataInner,statusInner,xhrInner){	    	
				    	
				    	//alert("통신성공");
				    	if(dataInner.trim() == 'true'){
				    		alert("여행일정 저장이 완료 되었습니다");
				    		location.href="/jejuya/ScheduleController?command=addlist";
				    	}else{
				    		alert("값을 못가져옴");
				    	}
				    },
				    error:function(xhrInner,statusInner,errorInner){
				    	//alert("통신실패");
				    },
				    complete:function(xhrInner,statusInner){
						//alert("통신 종료!");
				    }
				});
			
		},
		error:function(xhr,status,error){
			//alert("통신실패"+error);
		}, complete:function(xhr,status){
			//alert("통신 종료!");
	    }
	});
	
	
    $(".setScheduleOption input").attr("readonly","readonly");
    $("input[name='open']").attr('disabled',true);
    $("#sectionselec").attr("disabled",'true');
    $("#withselec").attr("disabled",'true');
    $("#memberselec").attr("disabled",'true');
    $("#firstday").datepicker('option','disabled',true);
    $("#lastday").datepicker('option','disabled',true);
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

	$("#maketrip").click(function() {

		//수정!!!!!!!!!!!!!!!!!!!!
		if( checktimeline==1){
				//alert($("#triptime").val());	

				//몇개의 day일정인지 구해줌
				var days = $("#triptime").val();

				//alert(days);
				//그 day 수 만큼 테이블 생성
				for (i = 1; i <= days; i++) {
					for (j = 5; j < 24; j++) {
						var day = ((i < 10) ? "0" + i : i);
						var time = ((j < 10) ? "0" + j : j);
						$(".timelinetable").append(
								"<tr class="+day+"><th>" + time + ":00"
										+ "</th>" + "<td id="+day+":"+time+":00></td></tr>");
						$("." + day).hide();//모두 일단 숨김
					}					
					$(".01").show();
					//day1빼고 모두 숨김					
				}
		}

	});
</script>
</html>