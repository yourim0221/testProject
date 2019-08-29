<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<title>Login</title>
<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!--Bootsrap 4 CDN-->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
   
<!--Fontawesome CDN-->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

<!-- Custom resources -->
<script type="text/javascript" src="/jejuya/resources/js/member/login.js"></script>
<link href="/jejuya/resources/css/member/login.css" rel="stylesheet"></link>
</head>
<body>

<jsp:include page="/views/templates/header.jsp"></jsp:include>

<br><br><br><br><br><br><br>

<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div align="center" class="cardSignIn">
	
			<div class="cardSignIn-header">
				<h2>로그인</h2>
			</div>
	
			<div class="cardSignIn-body">						
			
			<form action="/jejuya/member" method="post">
			<input type="hidden" name="command" value="dologin">
			
			<table class="tblInLoginPage">
			
			<tr>
			<td colspan="2">
				<!-- <input type="text" name="id" size="20"> -->
				<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input name="id" id="inputIdInSignin" type="text" class="form-control" placeholder="username">
				</div>		
			</td>
			</tr>
			
			<tr>
			<td colspan="2">
				<!-- <input type="password" name="pwd" size="20"> -->
				<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input name="pwd" type="password" class="form-control" placeholder="password">
				</div>
			</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="row align-items-center remember">
						<input type="checkbox" name="rememberIdChk" id="_rememberIdChk">Remember Me
					</div>
				</td>
			</tr>
			<!-- 행 간격 설정 --><tr><td height="10"></td></tr>
			<tr>
			<td colspan="2">
				<div class="form-group">
					<input type="submit" value="Login" id="_cardSignInSubmit" class="btn float-right login_btn"/>
				</div>
				<!-- <input type="submit" value="      로그인        "> -->
				<br>			
				<!-- 회원가입 페이지로 이동 -->
				<!-- <button type="button" onclick="location.href='/jejuya/member?command=goregi'"> 회원가입 </button> -->
				<!-- ID/PW 찾기 페이지로 이동 -->
				<!-- <button type="button" onclick="location.href='/jejuya/member?command=gofind'">ID/PW찾기</button> -->
			</td>
			</tr>
			<!-- 행 간격 설정 --><tr><td height="10"></td></tr>
			<tr align="center">
				<td colspan="2">
					계정이 없으신가요? &nbsp;&nbsp;&nbsp;
					<a href='/jejuya/member?command=goregi'>회원가입</a>
				</td>
			</tr>
			<!-- 행 간격 설정 --><tr><td height="10"></td></tr>
			<tr align="center">
				<td colspan="2">					
					<a href='/jejuya/member?command=gofind'>계정 또는 비밀번호를 잊어버리셨나요?</a>
				</td>
			</tr>
			</table>
			</form>	
			
			<!-- cardSignIn body end -->	
			</div>
			
		</div>
	</div>
</div>

<br><br><br><br><br><br><br>


<jsp:include page="/views/templates/footer.jsp"></jsp:include>

</body>
</html>