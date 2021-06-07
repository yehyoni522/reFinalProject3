<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<style type="text/css">
    
div#adminhome {
	min-height: 700px;
	padding-top: 20px;
	font-family: 'Noto Sans KR', sans-serif;
}

div#adminside {
	border-right:1px solid #b0b0b5;
	float: left;
	width: 20%;
	padding-left: 50px;
	min-height: 600px;
}
    
div#admincontent {
	float: left;
	padding: 0 50px 0 50px;
	width: 80%;
}    

.admsubtitle {
	border-left:solid 5px black; 
 	clear: both;
 	font-size: 18pt;
 	font-weight:bold;	
 	padding-left: 5px;
 
}

h3 {
 font-weight: bold;
}

th {
padding: 15px 10px 10px 0;
}
</style>
<script type="text/javascript">
	$(function() {
		
		$("button#sub").click(function() {
			
			var subname = $("input#subname").val();
			var majseq= $("input#majseq").val();
			var profnum = $("select#profnum").val();
			var credit = $("input#credit").val();
			var semeter = $("input#semeter").val();
			var week = $("select#week").val();
			var time1 = $("input#time1").val();
			var time2 = $("input#time2").val();
			
			$.ajax({
				url:"<%=ctxPath%>/admin/addSubjectEnd.sam",
				type:"post",
				data:{"subname": subname,"profnum": profnum, "majseq": majseq, "credit": credit, "semeter": semeter, "week": week,"time1":time1, "time2":time2},
				dataType:"json",
			   	success:function(json) {
			   		if(json.n == "1") {
			   			alert("수업 갱신 완료");
			   		} else {
			   			alert("수업 갱신 실패");
			   		}
			   		location.reload();
			   	}, error: function(request, status, error){
				      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		$("select#profnum").change(function() {
			console.log($(this).val());
		});
		
	});
	
	
	function searchProfessor() {
		var html="";
		
		$.ajax({
			url:"<%=ctxPath%>/admin/searchProfessor.sam",
			type:"get",
			data:{"majseq" : $("input#majseq").val()},
			dataType:"json",
		   	success:function(json) {
		   		$.each(json, function(index, item){
		   			html += "<option value='"+item.perno+"'>"+item.name+"</option>";
		   		});
		   		$("select#profnum").html(html);
		   	}, error: function(request, status, error){
			      alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		
	}
</script>
<div id="adminhome">
	
	<div id="admincontent">
	
	<div class="admsubtitle">
		<span >수업 갱신</span>
	</div>
	
	<div align="center" class="form-group">
		<form style="padding-top:60px;" name="subFrm">
			<table>
				<tr >
					<th>수업명</th>
					<td><input id="subname"type="text" class="form-control"></td>
				</tr>
				<tr>
					<th>학과번호</th>
					<td><input id="majseq" type="text" class="form-control"></td>
					<td><input type="button" class="btn btn-secondary" style="margin-left:15px;" onclick="searchProfessor();" value="검색"></td>
				</tr>
				<tr>
					<th>담당교수</th>
					<td>
						<select id="profnum" class="form-control">
						</select>
					</td>
				</tr>
				<tr>
					<th>학점</th>
					<td><input  id="credit" type="number" class="form-control" min="1" max="3"></td>
				</tr>
				<tr>
					<th>학기</th>
					<td><input id="semeter" type="number" class="form-control" min="1" max="2"></td>
				</tr>
				<tr>
					<th>날짜</th>
					<td>
					<select id="week" class="form-control">
						<option>월</option>
						<option>화</option>
						<option>수</option>
						<option>목</option>
						<option>금</option>
					</select>
					<input id="time1" type="time" class="form-group">&nbsp;&nbsp;~&nbsp;&nbsp;<input  id="time2" type="time" class="form-group"></td>
				</tr>
			</table>
			<div style="padding-top:20px;">
				<button id="sub" class="btn btn-secondary">등록</button>
				<button type="reset" class="btn btn-secondary">취소</button>
			</div>
			
		</form>
		
	</div>
	
	
	
		

</div>
</div>