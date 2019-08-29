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
	font-size: 15px;
	font-family: "NanumSquare", "Nanum Gothic", "나눔고딕", "맑은 고딕";
	border-collapse: collapse;
	border: 1px solid #323b59;
}

th{
	background-color: #7a87b4;
	color: #fff;
}

tr, td, th{
	height: 35px;	
}

input.btn {
    border-radius: 5px;
    border : 0;
    width: 100px;
    height: 35px;
    font-size: 14px;
    color: #fff;
    background: #323b59;
}

select{
	width: 7.5em;
	padding: .55em .1em;
    border: 1px solid #999;
}


</style>

</head>
<body>
<div align="center">
<form action="/jejuya/adminControl?command=addsights" method="post" enctype="multipart/form-data">
<!-- 	<input type="hidden" name="command" value="bbswrite"> -->
	
<h2>여행지 등록</h2>


<table border="1">
<col width="100"><col width="600">
<tr>	
	<th>여행지</th>
	<td>
		<select id="category" name="category" onchange="categorySel(this, 'theme')">
		<option value="default">여행지종류</option>
			<option value="0">관광지</option>
			<option value="1">음식점</option>
			<option value="2">숙소</option>
		</select>&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="theme" id="theme"  style="width: 10em; height: 2.5em;">
			<option value="default">여행지테마</option>
		</select>
	</td>	
</tr>	
<tr>
	<th>여행지명</th>
	<td>	
		<input type="text" id="title" name="title" style="width: 20em; height: 2em;">
		<br>
		<span id="titlecheck" class="msg"></span>
	</td>
	
</tr>	
<tr>	
	<th>주소</th>
	<td>
		<jsp:include page="postNum.jsp" ></jsp:include>
	</td>
</tr>
<tr>
	<th>전화번호</th>
	<td>
		<input type="text" id="phone" name="phone" style="width: 20em; height: 2em;" >	
	</td>	
</tr>
<tr>
	<th>홈페이지</th>
	<td>
		<input type="text" id="homepage" name="homepage" style="width: 30em; height: 2em;">	
	</td>	
</tr>
<tr>
	<th>상세정보</th>
	<td>
		<textarea rows="20" cols="75" id="content" name="content" style="resize: none"></textarea>
	</td>	
</tr>
<tr>
	<th>대표사진</th>
	<td>
		<input type="file" id="fileload" name="fileload" style="width: 400px">
		<div><span id="holder"></span></div>
	</td>	
</tr>
</table>
<br>
<input type="submit" class="btn" value="등록" onClick="checkForm()">


</form>
</div>


<!-- 업로드한 이미지 출력 -->
<%-- <img src="<%=request.getContextPath()%>\upload\udo.jpg"> --%>

<script type="text/javascript">
//
// $(function () {
// 	$("#_theme0").show(); $("#_theme1").hide(); $("#_theme2").hide();
// });


function categorySel(category, targetId) {
	var val = category.options[category.selectedIndex].value;	// 선택된 카테고리의 번호 	광광지0. 음식점1, 숙소2
	var targetE = document.getElementById(targetId);
// 	alert(val);
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


// 업로드 이미지 미리보기
function readInputFile(input) {
    if(input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#holder').html("<img src="+ e.target.result +" style='width: 250px; height: 200'>");
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$("#fileload").on('change', function(){
    readInputFile(this);
});
 
 // 여행지 등록 시 공백을 방지
$(".btn").on("click", function(e) {
    if ($('#category').val()=="" ||
    	$('#theme').val()=="" ||
    	$('#title').val()=="" ||
        $('#phone').val()=="" ||
        $('#homepage').val()=="" ||
        $('#content').val()==""){
        alert('모든 항목을 입력해주세요');
//         e.preventDefault();                        
    }else{
    	parent.document.location.reload();
    }
});


</script>



</body>
</html>









