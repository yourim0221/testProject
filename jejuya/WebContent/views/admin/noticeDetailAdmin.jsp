<%@page import="com.admin.dto.NoticeDto"%>
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
<title>공지 디테일</title>
<!-- jquery cdn -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- naver smart editor resources -->
<script type="text/javascript" src="<%=contextPath %>/resources/texteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<!-- custom resources -->
<body>

<%
	NoticeDto dto = null;
	if( request.getSession().getAttribute("currNoticeDto") != null ){
		dto = (NoticeDto)request.getSession().getAttribute("currNoticeDto");	
	}else{
		out.println("<script>alert('데이터 로드 중 문제가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
	}
	
	String content = dto.getContent1()
			+ ( ( dto.getContent2() == null )?"":dto.getContent2() ) 
			+ ( ( dto.getContent3() == null )?"":dto.getContent3() );
%>
<div class="noticeDetailFormWrap" align="center">
	<form action="<%=contextPath %>/notice" id="noticeDetailForm" method="post">
		<input type="hidden" id="command" name="command" value="modNotice">
		<table id="noticeDetailTbl">
			<colgroup>
				<col width="100px;">
				<col width="650px;">
			</colgroup>
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" id="author" name="author" value="<%=dto.getAuthor() %>" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" id="inputTitle" name="inputTitle" value="<%=dto.getTitle() %>">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea id="inputContent" name="inputContent" rows="15" cols="50"><%=content %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="저장" id="noticeBtnSave">
					<input type="button" value="삭제" id="noticeBtnDelete">
					<input type="button" value="돌아가기" id="noticeBtnCancel"> 
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- apply naver smart editor to textarea -->
<script type="text/javascript" src="<%=contextPath %>/resources/js/admin/noticeDetailAdmin.js"></script>


</body>
</html>