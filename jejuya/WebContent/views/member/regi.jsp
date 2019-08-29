<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<title>회원가입</title>

<jsp:include page="/views/templates/staticresources.jsp"></jsp:include>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!--Bootsrap 4 CDN-->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
   
<!--Fontawesome CDN-->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

<!-- custom resources -->
<link href="/jejuya/resources/css/member/regi.css" rel="stylesheet"></link>
<script type="text/javascript" src="/jejuya/resources/js/member/regi.js"></script>
</head>

<body>

<jsp:include page="/views/templates/header.jsp"></jsp:include>

<div class="wrapContent">

<br><br><br><br><br><br><br>

<div class="wrapInRegistPage" align="center">
	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div align="center" class="cardSignup">
			
				<div class="cardSignup-header">
					<h2>회원가입</h2>
				</div>
			
				<div class="cardSignup-body">
					<form action="/jejuya/member" method="POST">
					<input type="hidden" name="command" value="doregi">
					<table>
					<tr> <!-- user id row -->
						<td colspan="2">
							<!-- <input type="text" name="id" size="20" id="inputIdInSignup"> -->
							<div class="input-group form-group">
								<div class="input-group-prepend">
									<span class="input-group-text"><i class="fas fa-user"></i></span>
								</div>
								<input name="id" id="inputIdInSignup" type="text" class="form-control" placeholder="user ID"/>
							</div>		
						</td>
					</tr>
					
					<tr> <!-- user pw row -->
						<td colspan="2">
							<!-- <input type="password" name="pwd" size="20" id="inputPw1InSignup"> -->
							<div class="input-group form-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i class="fas fa-key"></i></span>
									</div>
									<input name="pwd" id="inputPw1InSignup" type="password" class="form-control" placeholder="user password">
							</div>
						</td>
					</tr>
					
					<tr> <!-- user pw2 row -->
						<td colspan="2">
							<!-- <input type="password" name="pwd2" size="20" id="inputPw2InSignup"> -->
							<div class="input-group form-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i class="fas fa-key"></i></span>
									</div>
									<input name="pwd2" id="inputPw2InSignup" type="password" class="form-control" placeholder="password again">
							</div>
						</td>
					</tr>
					
					<tr> <!-- user name row -->
						<td colspan="2">
							<!-- <input type="text" name="name" size="20" id="inputName"> -->
							<div class="input-group form-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i class="fas fa-kiss-wink-heart"></i></span>
									</div>
									<input name="name" id="inputName" type="text" class="form-control" placeholder="your name">
							</div>
						</td>
					</tr>
					
					<tr> <!-- user email row -->
						<td colspan="2">
							<!-- <input type="text" name="email" size="20" id="inputEmailInSignup"> -->
							<div class="input-group form-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i class="fas fa-envelope"></i></span>
									</div>
									<input name="email" id="inputEmailInSignup" type="text" class="form-control" placeholder="your email">
							</div>
						</td>
					</tr>
					
					
					<tr> <!-- user birth row -->
						<td>
							<!-- <input type="text" name="birth" size="20"> -->
							<div class="input-group form-group">
									<div class="input-group-prepend">
										<span class="input-group-text"><i class="fas fa-kiss-wink-heart"></i></span>
									</div>
									<input name="birth" id="inputBirthInSignup" type="text" class="form-control" placeholder="your birthday">
							</div>
						</td>
					</tr>
					
					<tr align="center">
						<td colspan="2">
							<div class="form-group" align="center" style="text-align: center;">
								<input type="submit" value="회원가입" id="btnSignupInSignupModal"  class="btn signup_btn"/>
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							
						</td>
					</tr>
					
					</table>
					</form>
					<p id="signupWarn"></p>
					<p id="chkAllOk"></p>
				</div>
		
			</div>
		</div>
	</div>
</div>



<br><br><br><br><br><br><br>
</div>
<jsp:include page="/views/templates/footer.jsp"></jsp:include>



</body>
</html>