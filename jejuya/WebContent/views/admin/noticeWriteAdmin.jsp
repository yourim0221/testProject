<%@page import="com.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//컨텍스트 패스 저장
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규 공지 등록</title>
<!-- jquery cdn -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- naver smart editor resources -->
<script type="text/javascript" src="<%=contextPath %>/resources/texteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<!-- custom resources -->
</head>
<body>



<div class="noticeWriterFormWrap" align="center">
	<form action="<%=contextPath %>/notice" id="noticeWriteForm" method="post">
		<input type="hidden" name="command" value="insertNewNotice">
		<table id="noticeWriteTbl">
			<colgroup>
				<col width="100px;">
				<col width="650px;">
			</colgroup>
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" id="author" name="author" value="<%=((MemberDto)request.getSession().getAttribute("currUser")).getId() %>" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" id="inputTitle" name="inputTitle">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea id="inputContent" name="inputContent" rows="15" cols="50"></textarea>
					<input type="hidden" id="filePointer" value="1">
					<input type="hidden" id="filename1" name="filename1" value="">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="저장" id="noticeBtnSave">
					<input type="button" value="초기화" id="noticeBtnInit">
					<input type="button" value="돌아가기" id="noticeBtnCancel"> 
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- apply naver smart editor to textarea -->
<script type="text/javascript" src="<%=contextPath %>/resources/js/admin/noticeWriteAdmin.js"></script>

</body>
</html>