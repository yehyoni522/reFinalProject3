<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
     
%>    

<style type="text/css">

	.colorbox {
	margin-right: 15px;
	}
	table {
    width: 100%;
    border: 1px solid #444444;
  	}
	th, td {
    border: 1px solid #444444;
    width: 50px;
    height: 100px;
  	}

</style>

<script>

</script>

<div style="margin: 0 50px 40px 50px; font-family: 'Noto Sans KR', sans-serif;">

	<h5>홈  - 출석</h5>
	<div align="center"><h2>출석</h2></div>
	
	<div align="center"><button type="button">출석하기</button></div>
	
	<div align="right" style="margin-bottom: 15px;">
	<span class="colorbox">
		<span  style="background-color: green;">&nbsp;&nbsp;&nbsp;</span>출석
	</span>
	<span class="colorbox">
		<span style="background-color:orange;">&nbsp;&nbsp;&nbsp;</span>지각
	</span>
	<span class="colorbox">
		<span style="background-color: red;">&nbsp;&nbsp;&nbsp;</span>결석
	</span>
	</div>
	
	<div align="center">
		<table>
			<tbody>
				<tr>
					<td></td>
					<td>.</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>.</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>.</td>
					<td></td>
				</tr>			
				<tr>
					<td></td>
					<td>.</td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>

</div>
