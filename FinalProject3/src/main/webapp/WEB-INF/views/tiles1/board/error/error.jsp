<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에러메시지</title>
</head>
<body>

<div style="color:red;">
	${requestScope.errMsg}
</div>
</body>
</html>