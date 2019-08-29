<%@page import="com.sights.dto.SightsDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<SightsDto> list = (List<SightsDto>) request.getAttribute("bbsupdate");

	int allBbspage = (Integer) request.getAttribute("allpage");
	System.out.println("allpage : " + allBbspage);

	int bbsPage = allBbspage / 10;
	if (allBbspage % 10 > 0) {
		bbsPage = bbsPage + 1;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<style type="text/css">
table.modal_table{
	font-size: 15px;
	font-family: "NanumSquare", "Nanum Gothic", "나눔고딕", "맑은 고딕";
	border-collapse: collapse;
	border: 1px solid #323b59;
}

select{
	width: 7.5em;
	padding: .55em .1em;
    border: 1px solid #999;
}

div.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}
</style>
<link type="text/css" rel="stylesheet"
	href="resources/css/listadmin.css?after">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
</head>
<body>

<form action="/jejuya/adminControl?" id="category_select" >
<!-- 	<input type="hidden" name="command" value="bbsupdate"> -->
</form>
	
	<div class="list_main">
		<form action="/jejuya/adminControl" method="get">
			<input type="hidden" name="command" value="bbsupdate">
			<table class="bbs_list_table">
				<col width="70"><col width="200"><col width="150"><col width="150"><col width="150"><col width="150">	
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="6"></td>
				</tr>
				<tr class="th_class">
					<th><input type="checkbox" id="alldel" name="alldel"></th>
					<th>Title</th>
					<th>Category</th>
					<th>Theme</th>
					<th>Add Schedule</th>
					<th>Readcount</th>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="6"></td>
				</tr>
				<%
					if (list == null || list.size() == 0) { // 데이터가 없을 때
				%>
				<tr bgcolor="#f6f6f6">
					<td colspan="6" align="center">게시글이 존재하지 않습니다</td>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="6"></td>
				</tr>
				<%
					} else { // 데이터가 있을 때
						for (SightsDto bbs : list) {
				%>
				<tr class="listclick" findseq=<%=bbs.getSeq() %> id="list_click" bgcolor="f6f6f6">
					<td align="center" class="check_box"><input type="checkbox" name="bbs_delck" value="<%=bbs.getTitle()%>" ></td>
					<td class="list" ><%=bbs.getTitle()%></td>
					<%
						if (bbs.getCategory() == 0) {
					%>
					<td class="list">관광지</td>
					<%
						} else if (bbs.getCategory() == 1) {
					%>
					<td class="list">음식점</td>
					<%
						} else if (bbs.getCategory() == 2) {
					%>
					<td class="list">숙소</td>
					<%
						}
					%>
					<td class="list"><%=bbs.getTheme()%></td>
					<td class="list"><%=bbs.getAddSchedule()%></td>
					<td class="list"><%=bbs.getReadcount()%></td>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="6"></td>
				</tr>
				<%
					}
				}
				%>
				<tr align="center">
					<td colspan="6">
						<%
						String sel =(String)request.getAttribute("searchsel");
						String searchW =(String)request.getAttribute("searchWord");
						
						System.out.println("list searchW .JSP : " + searchW);
						
							for (int i = 1; i <= bbsPage; i++) {
								%> 
								 <a href="/jejuya/adminControl?command=bbsupdate&page=<%=i%>&searchWord=<%=searchW%>&bbs_choice=<%=sel %>" style="font-size: 15pt; color: #5d5d5d; ">[<%=i%>]</a>
								 <%
 							}
 							%>
					</td>
				</tr>
<!-- 				<tr class="update_bbs" >
					<td colspan="6" class="tablesubmit">
					<input type="submit" value="게시글 수정" class="submit_update" style="width: 8em; height: 2.5em;">
					</td>
				</tr> -->
			</table>
		</form>
	</div>
	
	
	<!-- 수정페이지  -->
	
	<div id="myModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h3 align="center">여행지 수정</h3>

			<div align="center">
<!-- 			<form action="/jejuya/adminControl?command=bbs_update" method="post" enctype="multipart/form-data"> -->
				<form id="updateform"  enctype="multipart/form-data" method="post">		
					<input type="hidden" name="currDetailSeq" id="currDetailSeq" value="1">
					<table style="width: 700" class="modal_table" border="1">
						<col width="100"><col width="600">
							<tr class="modal_update" >	
							<th>여행지</th>
							<td>
								<select id="category" name="category" onchange="categorySel(this, 'theme')" >
								<option value="default">여행지종류</option>
									<option value="0">관광지</option>
									<option value="1">음식점</option>
									<option value="2">숙소</option>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;
								<select name="theme" id="theme"  style="width: 10em; height: 2.5em;">
									<option value="default">여행지테마</option>
								</select>
							</td>	
						</tr>	
						<tr class="modal_update">
							<th>여행지명</th>
							<td>	
								<input type="text" id="title" name="title" style="width: 20em; height: 2em;">
								<br>
								<span id="titlecheck" class="msg"></span>
							</td>
						</tr>	
						<tr class="modal_update">	
							<th>주소</th>
							<td>
								<jsp:include page="postNum.jsp" ></jsp:include>
							</td>
						</tr>
						<tr class="modal_update">
							<th>전화번호</th>
							<td>
								<input type="text" id="phone" name="phone" style="width: 20em; height: 2em;" >	
							</td>	
						</tr>
						<tr class="modal_update">
							<th>홈페이지</th>
							<td>
								<input type="text" id="homepage" name="homepage" style="width: 30em; height: 2em;">	
							</td>	
						</tr>
						<tr class="modal_update">
							<th>상세정보</th>
							<td>
								<textarea rows="20" cols="75" id="content" name="content" style="resize: none"></textarea>
							</td>	
						</tr>
						<tr class="modal_update">
							<th>기존 파일</th>
							<td>
								<input type="text" name="oldfile" id="oldfile" size="20">
							</td>	
						</tr>
						<tr class="modal_update">
							<th>대표사진</th>
							<td>
								<input type="file" id="filename" name="fileload" style="width: 400px">
								<div><span id="holder"></span></div>
							</td>	
						</tr>
						</table>
						<br>
						<input type="submit" id="modifyBtn" value="수정">

				</form>
			</div>
		</div>
	</div>
	
	
	
<form action="/jejuya/adminControl" method="get">
	<input type="hidden" name="command" value="bbsupdate">
	<table>
		<tr>
			<td><select name="bbs_choice">
					<option value="TITLE" >TITLE</option>
				</select></td>
			<td><input type="text" id="search" name="searchWord" style="width: 10em; height: 2em;" placeholder="Title을 입력하세요">
			</td>
			<td><input type="submit" value="search" style="width: 5em; height: 2.5em;"></td>
		</tr>
	</table>
</form>

<script type="text/javascript">

function categorySel(category, targetId) {
	var val = category.options[category.selectedIndex].value;	// 선택된 카테고리의 번호 	광광지0. 음식점1, 숙소2
	var targetE = document.getElementById(targetId);
// 	alert(val);
	removeAll(targetE);
	
	if(val == '0'){
		addOption('자연', targetE);
		addOption('문화관광', targetE);
		addOption('레저/체험', targetE);
		addOption('걷기', targetE);
		addOption('포토스팟', targetE);
	}
	
	else if(val == '1'){
		addOption('전통음식', targetE);
		addOption('한식', targetE);
		addOption('양식', targetE);
		addOption('중식', targetE);
		addOption('카페', targetE);
	}
	
	else if(val == '2'){
		addOption('호텔', targetE);
		addOption('리조트', targetE);
		addOption('펜션', targetE);
		addOption('민박', targetE);
		addOption('게스트하우스', targetE);
	}
	
}

function addOption(value, e) {
	var o = new Option(value);
	try{
		e.add(o);
	}catch(ee){
		e.add(o, null);
	}
}

function removeAll(e) {
	for(var i = 0, limit = e.options.length; i < limit - 1; i++){
		e.remove(1);
	}
}


$(".listclick .list").click(function() {
	
		var title;
		var category;
		var theme;
		var phone;
		var homepage;
		var content;
		var filename;
		
	$(".modal").css("display", "block");
// 		alert($(this).parent().attr("findseq"));
		
		var findseq = $(this).parent().attr("findseq");
// 		alert(findseq);
		$("#currDetailSeq").val(findseq);
// 		console.log($(this).parent()[0].cells[1].innerText);
// 		$("#title").val($(this).parent()[0].cells[1].innerText);

		$.ajax({
			url: "/jejuya/adminControl?command=bbsdetail",
			data: {findseq:findseq},
			type: "get",
			datatype: "json",
			success: function ( obj ) {
// 				alert("suc");
				var jsonData = JSON.parse(obj);
				
				title = jsonData.title;
				$("#title").val(title);
				
				category = jsonData.category;
				
				if(category == 0 ){
					$("#category option:eq(1)").attr("selected", "selected");
				}else if(category == 1){
					$("#category option:eq(2)").attr("selected", "selected");
				}else if(category == 2){
					$("#category option:eq(3)").attr("selected", "selected");
				}
				
				theme = jsonData.theme;
				console.log(theme);
				
				phone = jsonData.phone;
				console.log(phone);
				$("#phone").val(phone);
				
				homepage = jsonData.homepage;
				console.log(homepage);
				$("#homepage").val(homepage);
				
				content = jsonData.content;
				console.log(content);
				$("#content").val(content);
				
				filename = jsonData.filename;
				console.log(filename);
				$("#oldfile").val(filename);
								
			},
			error: function () {
				alert("error");
			}
		})
// 		$("#title").text(title);
		
			

});
$(".close").on("click", function() {
	$(".modal").css("display", "none");
});


$("#modifyBtn").click(function name() {	/*수정 버튼*/
// 	data: {"id":$("#id").val(), ""}

	var updateForm =$("#updateform").serialize();
	
	var seq = $("#currDetailSeq").val();
	alert(seq);
// 	alert($("#currDetailSeq").val());
	$.ajax({
		url: "/jejuya/adminControl?command=bbs_update&currDetailSeq=" + seq,
		enctype: 'multipart/form-data',
		type: "post",
		data: updateForm,
		datatype: "text",
	    processData: false,
        contentType: false,
		success: function () {
			alert("success");
// 			location.href = "/jejuya/adminControl?command=bbslist";
		},
		error: function () {
			alert("error");
		}
		
	});
});


</script>



</body>
</html>