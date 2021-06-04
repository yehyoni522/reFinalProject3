<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();   
%>   

<style type="text/css">

	table {
	    width: 100%;
	    margin-top: 50px;

  	}
  	th, td {
  		text-align: center;
  		height: 50px;
  	}
  	td {
  		border-bottom: solid 1px black;
  	}
  	.tablesize1 {
  		width: 110px;
  	}
  	.tablesize {
  		width: 70px;
  	}

  	

</style>

<script>

</script>

<div style="margin: 0 50px 40px 50px; font-family: 'Noto Sans KR', sans-serif;">

	<h5>홈  - 출석</h5>
	<br>
	<div align="center"><h2>쪽지시험</h2></div>
	
	<div align="center">
		<table>
			<thead>
				<tr style="border-bottom: 2px solid black; border-top: 2px solid black;">
					<th class="testno">번호</th>
					<th>시험명</th>
					<th>교수</th>
					<th>시험현황</th>
					<th>시험일자</th>
				</tr>
				
			</thead>
			
			<tbody>
				<tr>
					<td class="tablesize">반복문 인덱스</td>
					<td class="tablesize1">시험명</td>
					<td class="tablesize">교수</td>
					<td class="tablesize">시험현황(학생정답테이블이 null이 아니면 응시완료를 주자.)</td>
					<td class="tablesize">시험날짜</td>
				</tr>
				<tr>
					<td class="tablesize">반복문 인덱스</td>
					<td class="tablesize1">시험명</td>
					<td class="tablesize">교수</td>
					<td class="tablesize">시험현황(학생정답테이블이 null이 아니면 응시완료를 주자.)</td>
					<td class="tablesize">시험날짜</td>
				</tr>
			</tbody>
		</table>
		
		<div align="center" style="margin-top: 7px;">페이지 이동링크</div>
		
		<div style="cursor: pointer;" align="right" onclick="javascript:location.href='<%=ctxPath%>/lesson/testadd.sam'">글쓰기</div>
		
	</div>




</div>