<%@page import="java.util.ArrayList"%>
<%@page import="com.admin.dto.NoticePagingDto"%>
<%@page import="com.admin.dto.NoticeDto"%>
<%@page import="com.member.dto.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	List<NoticeDto> list = new ArrayList<>();
	NoticePagingDto noticePagingDto = null;
	
	if( request.getSession().getAttribute("noticeList") != null){
		if( request.getSession().getAttribute("noticeListPaging") != null ){
			list = (List<NoticeDto>)request.getSession().getAttribute("noticeList");
			noticePagingDto = (NoticePagingDto)request.getSession().getAttribute("noticeListPaging");
		}else{
			out.println("<script>alert('공지사항을 불러오던 중 오류가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
		}
	}else{
		out.println("<script>alert('공지사항을 불러오던 중 오류가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
	}	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
tr.listclick:hover{
	border-collapse: collapse;
	background-color: #abd;
}
select{ /* 190820 css수정 */
	width: 7.5em;
	padding: .55em .1em;
    border: 1px solid #999;
}

</style>
<link type="text/css" rel="stylesheet" href="/jejuya/resources/css/admin/listadmin.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>

	<h2>공지</h2>


	<div class="list_main">
		<form action="/jejuya/notice" method="post">
			<input type="hidden" name="command" value="notice_muldel">
			<table class="user_list_table">
				<col width="90">
				<col width="90">
				<col width="450">
				<col width="300">
				<col width="200">
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="5"></td>
				</tr>
				<tr class="th_class">
					<th><input type="checkbox" name="alldel"
						onclick="deletechecks(this.checked)"></th>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="5"></td>
				</tr>
				<%
					if (list == null || list.size() == 0) { // 데이터가 없을 때
				%>
				<tr bgcolor="#f6f6f6">
					<td colspan="5" align="center">공지가 존재하지 않습니다</td>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="5"></td>
				</tr>
				<%
					} else { // 데이터가 있을 때
						for (NoticeDto d : list) {
// 							System.out.println("mem list: "  + mem.toString());
				%>
				<tr class="listclick" id="list_click" bgcolor="f6f6f6">
					<td align="center"><input type="checkbox" name="delck"
						value="<%=d.getSeq()%>"></td>
					<td class="list"><%=d.getSeq()%></td>
					
					<td class="list"><%=d.getTitle()%></td>					
					<td class="list"><%=d.getAuthor()%></td>
					<td class="list"><%=d.getWdate()%></td>
					<%-- <td class="list"><%=mem.getBirth()%></td> --%>
				</tr>
				<tr>
					<td class="table_border" height="1" bgcolor="#000" colspan="5"></td>
				</tr>
				<%
					}
				}
				%>
				<tr align="center">
				<td colspan="5">
					<%
												
// 						System.out.println("list sel : " + sel);
// 						System.out.println("list searchW : " + searchW);
						/* 	 190820 페이징 CSS 수정  */
						for (int i = 1; i <= noticePagingDto.getPageSize(); i++) {
								%>
								 <a href="/jejuya/notice?command=showlistAdmin&pageNum=<%=i %>" style="font-size: 15pt; color: #5d5d5d; ">[<%=i%>]</a>
								<%	
// 							}else {

							}
					 %>
				</td>
				</tr>
				<tr class="delete_member">
				<td class="tablesubmit" colspan="4"><input type="button" id="writeNewNoticeBtn" name="writeNewNoticeBtn" value="새 공지사항 작성" style="width: 9em; height: 2.5em;" onclick="location.href='/jejuya/notice?command=writeNewNotice'"></td>
					<td class="tablesubmit" colspan="5"><input type="submit" class="submit_del" name="submit_del" value="공지 삭제" style="width: 9em; height: 2.5em;"></td>
				</tr>
			</table>
		</form>
	</div>


	<br>
	<br>
	<form action="#" method="get">
		<input type="hidden" name="command" value="userlist">
		<table>
			<tr>
				<td><select name="choice">
						<option value="TITLE" selected="selected">TITLE</option>
				</select></td>
				<td><input type="text" id="search" name="searchWord" style="width: 10em; height: 2em;">
				</td>
				<td><input type="submit" value="search" style="width: 5em; height: 2.5em;"></td>
			</tr>
		</table>
	</form>


	<!-- 상세 페이지 -->


	<div id="myModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h3 align="center">회원정보</h3>

			<div align="center">
				<form action="/jejuya/adminControl" method="post">
					<input type="hidden" name="command" value="update">
					<table style="width: 700">
						<col width="200">
						<col width="600">
						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
						</tr>
						<tr class="modal_detail" bgcolor="f6f6f6">
							<th class="modal_table_header">ID</th>
							<td id="id"></td>
						</tr>
						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
						</tr>
						<tr class="modal_detail" bgcolor="f6f6f6">
							<th class="modal_table_header">PWD</th>
							<td id="pw"></td>
						</tr>
						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
						</tr>
						<tr class="modal_detail" bgcolor="f6f6f6">
							<th class="modal_table_header">NAME</th>
							<td id="name"></td>
						</tr>
						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
						</tr>
						<tr class="modal_detail" bgcolor="f6f6f6">
							<th class="modal_table_header">EMAIL</th>
							<td id="email"></td>
						</tr>
						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
						</tr>
						<tr class="modal_detail" bgcolor="f6f6f6">
							<th class="modal_table_header">BIRTH</th>
							<td id="birth"></td>
						</tr>

						<tr>
							<td height="1" bgcolor="#000" colspan="3"></td>
					</table>
				</form>
			</div>
		</div>
	</div>


<script type="text/javascript">
//190820 체크박스 눌렀을 때 글로 들어가는 현상 수정
$(".listclick .list").click(function() {
	var seq = $(this).parent().children().eq(0).children().eq(0).val();
	//alert(seq);

	location.href="/jejuya/notice?command=noticeDetail&seq=" + seq;	

});
/* $(".close").on("click", function() {
	$(".modal").css("display", "none");
}); */

//$('td.list:nth-child(3)').hide(); // td의 세번째 열 데이터를 숨김

function deletechecks(e) {
	var arr = document.getElementsByName("delck");

	for (i = 0; i < arr.length; i++) {
		arr[i].checked = e;
	}
}

//190820 선택을 안한채로 삭제됐을 때 오류 방지
$(".submit_del").click(function() {
	// 체크 된 체크박스의 개수
	var checkLen = $("input:checkbox[name='delck']:checked").length;

	// 체크개수가 1 미만이면  submit(삭제) 실행X
	if(checkLen < 1){
		alert('삭제할 공지를 1개 이상 선택하세요');
		return false;
	}
}); 
</script>

</body>
</html>