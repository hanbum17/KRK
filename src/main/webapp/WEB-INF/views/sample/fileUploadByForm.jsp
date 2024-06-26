<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- form을 이용한 다중 파일 업로드 -->
<!-- form의 enctype 속성은 반드시 'multipart/form-data' 값으로 지정 -->
<!-- form의 method 속성은 반드시 'post' 값으로 지정 -->
<!-- 하나의 파일 input으로 여러 개의 파일을 전송려는 경우, multiple 속성(기본값: false) 명시 -->

<form method="post" enctype="multipart/form-data" action="${contextPath}/doFileUploadByForm">
    <input type="text" name="btitle" placeholder="제목입력하세요..."><br><br>
    <input type="file" id="fileInput" name="uploadFiles" multiple="multiple"><br><br>
    <button>Submit</button>
</form>

</body>
</html>
