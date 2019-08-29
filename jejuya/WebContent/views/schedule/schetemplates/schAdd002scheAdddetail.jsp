<%@page import="com.sights.dto.SightsDto"%>
<%@page import="java.util.List"%>
<%@page import="common.util.pageDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%!
public pageDto page(int total, int pg) {
		int list = 10; //전체페이지에 10개를 출력 
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

}	
%>
<br> <font size="2" id="coment">하단의 관광지 중 목록에 담고싶다면 클릭하세요</font>	<br>
<input type="hidden" id="hidden" value="0">
<button type="button" class="btn btn-secondary" id="category" value="0" onclick="func(0);changehidden(0);rank(0)">관광지</button>
<button type="button" class="btn btn-secondary"  id="category" value="1" onclick="func(1);changehidden(1);rank(1)">음식점</button>
<button type="button" class="btn btn-secondary" id="category" value="2" onclick="func(2);changehidden(2);rank(2)">숙소   </button>

<br>

<!-- table태그는 위치를 잡기 위한 용도로 사용됨 -->
<table><col width="100"><col width="150"><tr><th colspan="2">
<select id="choicetime">
	<option value="0">시간 선택</option>
		<% for(int i = 5 ; i < 24 ; i++){
				out.println("<option value=\"");
				out.println( ( (i<10)? "0"+i : i ) + ":00\">" );
				out.println( ( (i<10)? "0"+i : i ) + ":00</option>");
		}%>
</select>
</th></tr></table>


<%
		//day는 schAdd004timeline.jsp에서 설정
		//int day = 3;

		List<SightsDto> list0 = (List) request.getAttribute("list0"); //관광지 리스트 받기
		List<SightsDto> list1 = (List) request.getAttribute("list1"); //음식점 리스트 받기
		List<SightsDto> list2 = (List) request.getAttribute("list2"); //숙소 리스트 받기

%>
<!-- 관광지 선택 테이블 시작 부분 -->
<table id="triplist">
	<col width="50">
	<col width="250">
	<%
		int paging = 1;
		//System.out.println(list0.size());
		pageDto pagedto = page(list0.size(), paging);
		//System.out.println(pagedto.toString());
		int lastnum = 10;
		for (int i = 0; i < pagedto.getLastnum(); i++) {
			SightsDto sight = list0.get(i);
			//System.out.println("sightdto"+sight.toString()); 값들어옴
	%>
	<tr>
		<th><input type="checkbox" name="addck[]" onclick="checkboxcheck(this)" value='<%=sight.getTitle()%>'></th>
		<th><font size="2" id="coment"><%=sight.getTitle()%></font></th>
	</tr>
	<%
		}
	%>
	<!-- 	
</table>
<table id="paging"  >
	<col width="50">
	<col width="250"> -->
	<tr>
		<th colspan="2">
					<%
						for (int i = 1; i <= pagedto.getPagignum(); i++) {
					%>
					<button type="button" class="btn btn-light" onclick="pagingBtnClick(<%=i%>)"><%=i%></button>
					<%
						}
					%>
		</th>
	</tr>

	<tr>
		<th colspan="2"><button type="button" onclick="add()"
			class="btn btn-outline-secondary"	id="addbtn">추가</button></th>
	</tr>

</table>

<script type="text/javascript">
function checkboxcheck(e){
	
	//수정!!!!!!!!!!!!!!!!!!!!!

	var obj = document.getElementsByName("addck[]");
    for(var i=0; i<obj.length; i++){
        if(obj[i] != e){
            obj[i].checked = false;
        }
    }

}
//관광 음식 호텔 의 버튼을 눌렀을 때 해당 카테고리의 리스트를 가져오는 함수 입니다 
//e에 각 카테고리 숫자를 넣어 함수를 호출하여 ajax를 통하여 새로고침을 하지 않으면서
//컨트롤으로 접근하여 데이터를 가져오는 함수 입니다
function func(e) {
	//alert(e);
	$("#triplist").html(''); //기존 테이블에 있던 정보를 일단 초기화
	$.ajax({
		url:"/jejuya/ScheduleController?command=tourismAjaxTblTriblist",
	    type:"get",
	    data:{"pg":1, "category":e},
	    success:function(data,status,xhr){	    	
	    	
	    //	alert(data);	
	    	
	    	$("#triplist").html(data);
	    },
	    error:function(xhr,status,error){
	    	//alert("통신실패");
	    },
	    complete:function(xhr,status){
			//alert("통신 종료!");
	    }
	});
	
}

//페이지 버튼을 눌렀을 때 ajax로 넘어가 데이터를 가져오는 함수입니다
function pagingBtnClick( pageNum ){
	//alert('clicked page number : ' + pageNum);
	
	$("#triplist").html('');
	var category=$("#hidden").val();
//	alert(category);
	$.ajax({
	    url:"/jejuya/ScheduleController?command=tourismAjaxTblTriblist",
	    type:"get",
	    data:{"pg":pageNum, "category":category},
	    success:function(data,status,xhr){	    	
	    	
	    	//alert(data);	
	    	
	    	$("#triplist").html(data);
	    },
	    error:function(xhr,status,error){
	    	//alert("통신실패");
	    },
	    complete:function(xhr,status){
			//alert("통신 종료!");
	    }
	});
}

//hidden의 값을 바꿔주어 현재 어떤 카테고리 즉 관광인지 음식인지 호텔인지 알려주는 함수입니다
function changehidden(e) {
//	alert(e);
	$("#hidden").val(e);
//	alert($("#hidden").val());
}

//타임라인에 선택한 값을 등록하는 메소드
function add() {
	//alert("클릭"); 
 var chk=document.getElementsByName("addck[]"); //체크박스의 객체를 담음
 var len=chk.length;  //체크박스의 전체 갯수 한 페이지당 10개 나옴
 var checkRow='';  //체크된 체크박스의 value를 담기 위한 변수
 var checkCnt=0;  //체크된 체크박스의 갯수
 var rowid='' ; //체크된 체크 박스의 모든 value값을 담는다
 var cnt=0;
 
 var choicetime=document.getElementById("choicetime").value.trim();
 //alert(choicetime);
	var currday=parseInt($("#timelineday").html().trim());

	if(currday<10){
		currday="0"+currday;
	}else{
		currday=currday;
	}
	
//	alert(currday);
	 
    for(var i=0;i<len;i++){
	 if(chk[i].checked==true){
		checkCnt++;   //체크된 체크박스의 갯수
		
		checkRow=chk[i].value;
		//alert(checkRow);
		//alert(currday+":"+choicetime+"");
		document.getElementById(currday+":"+choicetime+"").innerHTML = checkRow;
		
		var td =document.getElementById(currday+":"+choicetime+"");
		var checkbox=document.createElement("input");
		checkbox.type="checkbox";
		checkbox.id="delck[]";
		
		checkbox.value=checkRow;
	
		td.appendChild(checkbox);
		chk[i].checked=false;
		checkbox.onclick=function(){
		    text=document.createTextNode("");
		    td.innerHTML="";
		}
	 
 }
  
}

}

</script>
</html>