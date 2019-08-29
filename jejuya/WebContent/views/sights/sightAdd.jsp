<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<style type="text/css">
table{
	font-size: 13px;
	font-family: "NanumSquare", "Nanum Gothic", "나눔고딕", "맑은 고딕";
	border-collapse: collapse;
	border: 1px solid #ef6d00;
}

.btn {
    border-radius: 5px;
    border : 0;
    width: 100px;
    height: 35px;
    font-size: 14px;
    color: #fff;
    background: #ef6d00;
}

</style>

</head>
<body>

<div align="center">

<form action="/jejuya/SightsController?command=addsights" method="post" enctype="multipart/form-data">
	<!-- <input type="hidden" name="command" value="addsights"> -->
	
<h3>여행지 등록</h3>

<table border="1">
<tr>	
	<td>여행지</td>
	<td>
		<select id="category" name="category" onchange="categorySel(this, 'theme')">
			<option value="default">여행지종류</option>
			<option value="0">관광지</option>
			<option value="1">음식점</option>
			<option value="2">숙소</option>
		</select>
		<select name="theme" id="theme">
			<option value="default">여행지테마</option>
		</select>		
	</td>	
</tr>	
<tr>
	<td>여행지명</td>
	<td>
		<input type="text" id="title" name="title" onblur="titlecheck()">
		<br>
		<span id="titlecheck" class="msg"></span>	
	</td>
</tr>
<tr>	
	<td>주소</td>
	<td>
		<jsp:include page="postNum.jsp" ></jsp:include>
	</td>
</tr>
<tr>
	<td>전화번호</td>
	<td>
		<input type="text" name="phone">	
	</td>	
</tr>
<tr>
	<td>홈페이지</td>
	<td>
		<input type="text" name="homepage">	
	</td>	
</tr>
<tr>
	<td>상세정보</td>
	<td>
		<textarea rows="25" cols="55" name="content"></textarea>
	</td>	
</tr>
<tr>
	<td>대표사진</td>
	<td>
		<input type="file" id="fileload" name="fileload" style="width: 400px">
        <div id="holder"><span></span></div>
	</td>	
</tr>
</table>

<input type="submit" class="btn" value="등록">
<input type="button" id="btnUpdate" class="btn" value="수정">
<input type="button" id="btnDel" class="btn" value="삭제">

</form>
</div>


<!-- 업로드한 이미지 출력 -->
<%-- <img src="<%=request.getContextPath()%>\upload\udo.jpg"> --%>

<script type="text/javascript">
$(function () {
	$("#_theme0").show(); $("#_theme1").hide(); $("#_theme2").hide();	
})


function categorySel(category, targetId) {
	var val = category.options[category.selectedIndex].value;
	var targetE = document.getElementById(targetId);
//	alert(val);
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

/* function categorySel() {
	// 카테고리 선택이 바뀌면 그 값을 가져온다
	var num = document.getElementById("category");
	valNum = num.options[num.selectedIndex].value;
//	alert(valNum);

	switch(valNum){
	case '0': $("#_theme0").show(); $("#_theme1").hide(); $("#_theme2").hide(); break;			  
	case '1': $("#_theme0").hide(); $("#_theme1").show(); $("#_theme2").hide(); break;
	case '2': $("#_theme0").hide(); $("#_theme1").hide(); $("#_theme2").show(); break;
	default : break;
	}			
}

 function theme() {
	var theme = document.getElementsByName("theme");
	var themeName = theme.options[theme.selectedIndex].value;
	alert(themeName);		
			
}  */


// 업로드 이미지 미리보기
var upload = document.getElementById("fileload"),
    holder = document.getElementById("holder")
    
upload.onchange = function (e) {
e.preventDefault();

var file = upload.files[0],
    reader = new FileReader();
    
reader.onload = function (event) {
  var img = new Image();
  img.src = event.target.result;
  // note: no onload required since we've got the dataurl...I think! :)
  if (img.width > 250) { // holder width
    img.width = 250;
  }
  holder.innerHTML = '';
  holder.appendChild(img);
};
reader.readAsDataURL(file);

return false;
};



</script>



</body>
</html>









