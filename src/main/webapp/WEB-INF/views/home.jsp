<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!-- 수정 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><!-- 추가 -->  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><!-- 추가 -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" /><!-- 추가 -->
<%@ include file="./include/header.jsp" %><!-- 추가 -->
<div id="page-wrapper">
    <div class="row">
	    <div class="col-lg-12">
		    <h3 class="page-header">Welcome, My Home World!!!</h3>
	    </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">
                <!-- 로그인 하지 않은 경우 -principle: anonymousUser String 객체-->
                <!-- 로그인 한  경우       -principle: 로그인 사용자의 Authentication 객체  -->
            <sec:authentication property="principal" var="principal"/>
                <c:choose>
                    <c:when test="${principal eq 'anonymousUser' }">
                        <span>반갑습니다.</span>
                    </c:when>
                    <c:otherwise>
                        <span>${principal.username}님, 반갑습니다.</span> 
                        <hr>
                        <span>principal: ${principal}</span>
                        <hr>
                        <span>authentication: ${authentication}</span>
                        <hr>
                    </c:otherwise>
                </c:choose>
		    </h3>
	    </div>
    </div>
    <p> 현재 시간은, <strong>${serverTime}</strong>입니다람쥐 </p>

</div><%-- /.page-wrapper --%>

<%@ include file="./include/footer.jsp" %> 
