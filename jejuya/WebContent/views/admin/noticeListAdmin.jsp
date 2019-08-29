<%@page import="java.util.ArrayList"%>
<%@page import="com.admin.dto.NoticePagingDto"%>
<%@page import="com.admin.dto.NoticeDto"%>
<%@page import="java.util.List"%>
<%@page import="com.admin.dao.impl.NoticeDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jquery cdn -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- bootstrap cdn -->
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"></link>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<!-- custom resources -->
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/noticeListAdmin.js"></script>
<link href="<%=request.getContextPath() %>/resources/css/admin/noticeListAdmin.css" rel="stylesheet">
</head>
<body>

<%
	List<NoticeDto> lst = new ArrayList<>();
	NoticePagingDto noticePagingDto = null;
	
	if( request.getSession().getAttribute("noticeList") != null){
		if( request.getSession().getAttribute("noticeListPaging") != null ){
			lst = (List<NoticeDto>)request.getSession().getAttribute("noticeList");
			noticePagingDto = (NoticePagingDto)request.getSession().getAttribute("noticeListPaging");
		}else{
			out.println("<script>alert('공지사항을 불러오던 중 오류가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
		}
	}else{
		out.println("<script>alert('공지사항을 불러오던 중 오류가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
	}	
%>

<table id="noticeListAdminTbl">
	<colgroup>
		<col width="90">
		<col width="350">
		<col width="200">
		<col width="70">
		<col width="200">
	</colgroup>
	<tr><td class="table_border" height="1" bgcolor="#000" colspan="5"></td></tr>
	<tr class="th_class">
		<th>글 번호</th> <th>제목</th> <th>작성자</th> <th>파일</th> <th>작성일</th>		
	</tr>
	<tr><td class="table_border" height="1" bgcolor="#000" colspan="5"></td></tr>
	<%for(NoticeDto d : lst){ %>
	<tr class="listclick">
		<td><%=d.getSeq() %></td>
		<td><%=d.getTitle() %></td>
		<td><%=d.getAuthor() %></td>
		<td>i</td>
		<td><%=d.getWdate().toLocaleString().substring(0,12) %></td>			
	</tr>	
	<%} %>
	<tr><td class="table_border" height="1" bgcolor="#000" colspan="5"></td></tr>
	
<%-- 	<tr>
		<td colspan="5">
			<%for(int i = 1 ; i <= noticePagingDto.getPageSize() ; i++) {%>
				<a href="<%=request.getContextPath()%>/notice?command=showlist&pageNum=<%=i%>">[<%=i %>]</a>
			<%} %>
		</td>
	</tr> --%>
</table>
<div class="mainNoticeTblFooter" align="center">
	<table>
	<tr class="mainNoticeTblNav">
		<td colspan="3">
			<input type="hidden" id="totalNavItemSize" value="<%=noticePagingDto.getPageSize() %>">
			<ul class="pagination" id="noticePagingNav">
				<li class="page-item page-prev"><a class="page-link"><i class="fas fa-angle-left"></i></a></li>
				<%
					for(int i = noticePagingDto.getNavStartNum() ; i < noticePagingDto.getNavStartNum()+5 ; i++ ){
						%>
							<li class="page-item page-nav-item"><a class="page-link"><%=i %></a></li>
						<%												
					}
				%>
				<li class="page-item page-next"><a class="page-link"><i class="fas fa-angle-right"></i></a></li>
			</ul>
		</td>								
	</tr>
	</table>
</div>
<div class="functionalBtn" align="center">
	<table>
		<tr>
			<td colspan="5">
				<input type="button" value="새 공지 작성" id="writeNewBtn">
				<input type="button" value="글 삭제">
				<input type="button" value="돌아가기" id="goToMainBtn">
			</td>
		</tr>
	</table>
</div>

<!-- Modal -->
<div class="modal" id="noticeDetailModal" role="dialog">
  <div class="modal-dialog">  
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body" id="noticeModalBody">
        <div id="noticeModalBodyTitle">제목</div>
        <hr>
        <div id="noticeModalBodyContent"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>    
  </div>
</div>

</body>
</html>