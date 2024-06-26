<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload By Form</title>
</head>
<body>

<!-- form을 이용한 다중 파일 업로드 -->
<!-- form의 enctype 속성은 반드시 'multipart/form-data' 값으로 지정 -->
<!-- form의 method 속성은 반드시 'post' 값으로 지정 -->
<!-- 하나의 파일 input으로 여러 개의 파일을 전송려는 경우, multiple 속성(기본값: false) 명시 -->

<form method="post" enctype="multipart/form-data" action="${contextPath }/doFileUploadByForm"><!-- 
	<input type="text" name="btitle" placeholder="제목입력하세요..."><br><br>
	<input type="text" name="bcontent" placeholder="간단히 내용을 입력하세요..."><br><br> -->
	<input type="text" name="msg" placeholder="간단한 메시지를 입력하세요...">
	<input type="file" id="fileInput"  name="uploadFiles" multiple="multiple">
	<button>Submit</button>
</form>

</body>
</html>