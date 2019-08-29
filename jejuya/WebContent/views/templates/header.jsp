<%@page import="java.util.Enumeration"%>
<%@page import="com.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	//세션 계정검사 스크립트릿 구역	

	MemberDto currUser = null;
	String loginIdentifierInHeader = "로그인";
	String accountLinkUrl = "member";
	
	//currUser attr가 세션에 있는 경우, GNB 우측 상단 메뉴 이름과 기능을 로그아웃으로 설정
	if( request.getSession().getAttribute("currUser") != null){
		currUser = (MemberDto)request.getSession().getAttribute("currUser");
		loginIdentifierInHeader = "로그아웃";
		accountLinkUrl = "member?command=dologout";
	}
	request.setCharacterEncoding("UTF-8");

	String contextPath = request.getAttribute("javax.servlet.forward.context_path") + "";
	String servletPath = request.getAttribute("javax.servlet.forward.servlet_path") + "";
	String uniCode = java.net.URLDecoder.decode( (request.getAttribute("javax.servlet.forward.query_string") + "" ) );
	//System.out.println("최종 URL : " + contextPath + servletPath + uniCode );
	String latestURI = contextPath + servletPath + "?" + uniCode;
	request.getSession().setAttribute("latestURI", latestURI);
	/* 
	System.out.println("Servlet path : [" + request.getAttribute("javax.servlet.forward.request_uri") + "] !!!!!!!!!!!");
	Enumeration enum_req = request.getAttributeNames();
	System.out.print("enum : ");
	while(enum_req.hasMoreElements() ){
		String attr = enum_req.nextElement() + "";
		System.out.print( " [" + attr + ":" + request.getAttribute(attr) + "]");
	}
	System.out.println(); 
	*/
%>

<script type="text/javascript" src="/jejuya/resources/js/templates/header.js"></script>
  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand js-scroll-trigger" href="/jejuya/main">JEJUYA</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/jejuya/SightsListController?command=sortByConditionInSightlist&category=0">관광지</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/jejuya/SightsListController?command=sortByConditionInSightlist&category=1">음식점</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/jejuya/SightsListController?command=sortByConditionInSightlist&category=2">숙박</a>
          </li>
           <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="/jejuya/ScheduleController?command=addlist&pajing=1">나의여행</a>
          </li>
          <li class="nav-item" id="headerMemberBtn">
          	<!-- 디폴트 값 : 로그인, 세션에 로그인한 아이디 정보가 있으면 로그아웃 -->
          	<a class="nav-link js-scroll-trigger" href="/jejuya/<%=accountLinkUrl %>">
          		<%=loginIdentifierInHeader %>
          	</a>
          </li>
          <%-- <li class="nav-item" id="headerMemberBtn">
          	<!-- 디폴트 값 : 로그인, 세션에 로그인한 아이디 정보가 있으면 로그아웃 -->
          	<a class="nav-link js-scroll-trigger" id="headerMemberLbl"><%=loginIdentifierInHeader %> </a>
          	<form action="/jejuya/<%=accountLinkUrl%>" method="post" id="headerMemberFrm">
          		<input type="hidden" id="currUrl" name="currUrl" value="<%= request.getRequestURL().substring(28) %>">
          		<input type="hidden" id="destUrl" name="destUrl" value="/jajuya/<%=accountLinkUrl%>">
          	</form>
          </li> --%>
          <!-- <li class="nav-item" id="headerLoginBtn">
          	<a class="nav-link js-scroll-trigger" href="/jejuya/member">로그인</a>
          </li>
          <li class="nav-item" id="headerLogoutBtn">
         	<a class="nav-link js-scroll-trigger" href="/jejuya/">로그아웃</a>
          </li>   -->      
        </ul>
      </div>
    </div>
  </nav>


</html>