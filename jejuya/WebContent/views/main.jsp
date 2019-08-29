<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- common <head> include -->
<jsp:include page="./templates/staticresources.jsp"></jsp:include>

<body id="page-top">


<!-- 한 세션에 로그인 한 유저 정보 : MemberDto request.getSession().getAttribute("currUser") -->
<!-- common header include -->
<jsp:include page="./templates/header.jsp"></jsp:include>

<!-- main content include -->
<%-- <jsp:include page="./templates/projects.jsp"></jsp:include> --%>
<jsp:include page="./main/mainsitemap.jsp"></jsp:include>
<jsp:include page="./main/maincontent.jsp"></jsp:include>


<!-- main signup content include -->
<%-- <jsp:include page="./templates/signup.jsp"></jsp:include> --%>

<!-- common footer include -->
<jsp:include page="./templates/footer.jsp"></jsp:include>

</body>
</html>