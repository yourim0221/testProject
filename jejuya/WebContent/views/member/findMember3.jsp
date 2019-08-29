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

<style type="text/css">
p, h1, form, button{border:0; margin:0; padding:0;}
.spacer{clear:both; height:1px;}

.myform{
	margin:10px;
	width:400px;
	padding:14px;
}


.left-box {

  float: left;
  width: 50%;
}


.right-box {

  float: right;
  width: 50%;
}

#stylized{
	border:solid 2px #b7ddf2;
	background:#ebf4fb;
}
#stylized h1 {
	font-size:16px;
	font-weight:bold;
	margin-bottom:8px;
	font-family:nanumgothic,dotum;

}
#stylized p{
	font-size:11px;
	color:#666666;
	margin-bottom:20px;
	border-bottom:solid 1px #b7ddf2;
	padding-bottom:10px;
	font-family:dotum;
}
#stylized label{
	display:block;
	font-weight:bold;
	text-align:right;
	width:140px;
	float:left;
	font-family:tahoma;
}
#stylized .small{
	color:#666666;
	display:block;
	font-size:11px;
	font-weight:normal;
	text-align:right;
	width:140px;
	font-family:dotum;
	letter-spacing:-1px;
}
#stylized input{
float:left;
font-size:12px;
padding:4px 2px;
border:solid 1px #aacfe4;
width:200px;
margin:2px 0 20px 10px;
}

#stylized button{
clear:both;
margin-left:150px;
width:125px;
height:31px;
text-align:center;
line-height:31px;
background-color:#000;
color:#FFFFFF;
font-size:11px;
font-weight:bold;
font-family:tahoma;
}



</style>



<br><br><br><br><br><br><br>

<div class="container">  
<div class="d-flex justify-content-center h-100">
<div align="center" class="cardSignIn">
<div class="cardSignIn-header">

<!-- �ڽ� -->


<!-- <div class="findMemberWrapper" align="center">
	<div class="findIdWrapper">
		<form action="/jejuya/member?command=dofindid" class="findIdForm" id="_findIdForm" method="post"> -->

			
			
<div class="findMemberWrapper" align="center">		

<form action="/jejuya/member" method="post">
<input type="hidden" name="command" value="dofindid">
<table >

<tr>
<td colspan="2"><h2>ID 찾기</h2></td>
</tr>
<tr>
<td><span style="color:white">이름</span></td>
<td>
<input type="text" id="_findIdInputNameTxt" name="findIdInputNameTxt" placeholder="이름을 입력하세요.">
</td>
</tr>
<tr>
<td><span style="color:white">이메일</span> </td>
<td>
<input type="text" id="_findIdInputEmailTxt" name="findIdInputEmailTxt" placeholder="이메일을 입력하세요.">
</td>
</tr>
<tr>
<th colspan="2">
<input type="submit" value="ID 찾기" align="left" id="_cardSignInSubmit" class="btn float-right login_btn"/>
</th>
</tr>
</table>

</form>

	<form action="/jejuya/member" class="findPwForm"
		id="_findPwForm" method="post">
		<input type="hidden" name="command" value="dofindpw">
		<table>
			<tr>
				<td colspan="2"><h2>PW 찾기</h2></td>
			</tr>

			<tr>
				<td><span style="color: white">ID</span></td>
				<td><input type="text" id="_findPwInputIdTxt"
					name="findPwInputIdTxt" placeholder="이름을 입력하세요"></td>
			</tr>

			<tr>
				<td><span style="color: white">이메일</span></td>
				<td><input type="text" id="_findPwInputEmailTxt"
					name="findPwInputEmailTxt" placeholder="이메일을 입력하세요.">
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<div class="form-group">
						<input type="submit" value="PW 찾기" align="left"
							id="_cardSignInSubmit" class="btn float-right login_btn" />
					</div>
				</td>
			</tr>
		</table>
	</form>

	
	
<div class='right-box'>		
	<div class="findPwWrapper">
		
	
			</div>
		

</div>
</div>
</div>
</div>

</div>
</div>
<!-- Footer -->
<br><br><br><br><br><br><br>
<jsp:include page="/views/templates/footer.jsp"></jsp:include>

</body>
</html>