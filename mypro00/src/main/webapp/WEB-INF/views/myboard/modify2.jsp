<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

<style>
	th {text-align: center; }
</style>


        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Board - Modify</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4>게시물 수정</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">

<form role="form" id="frmModify" method="post" action="${contextPath }/myboard/modify">
    <div class="form-group">
        <label>글제목</label>
        <input class="form-control" id="btitle" name="btitle"  value='<c:out value="${board.btitle }"/>'>
    </div>
    <div class="form-group">
        <label>글내용</label>
        <textarea class="form-control" id="bcontent" name="bcontent" rows="3" ><c:out value="${board.bcontent }"/></textarea>
    </div>
    <div class="form-group">
        <label>작성자</label>
        <input class="form-control" id="bwriter" name="bwriter" value='<c:out value="${board.bwriter }"/>' readonly="readonly">
    </div>
        <input type="hidden" value="${board.bno }" name="bno"/>
    <button type="button" class="btn btn-primary" onclick="checkValueModify();">수정</button>
    <button type="button" class="btn btn-danger" onclick="checkValueDelete();">삭제</button>
    <button type="button" class="btn btn-warning" 
            onclick="location.href='${contextPath}/myboard/list'">목록</button>
</form>



                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
        </div><%-- /#page-wrapper --%>

<script>
function checkValueModify(){
    var btitle = document.getElementById("btitle").value ;
    var bcontent = document.getElementById("bcontent").value ;
    var bwriter = document.getElementById("bwriter").value ;
	
    var regExp = /^\s+$/ ;
    
    var frmModify = document.getElementById("frmModify") ;
    
    if( !btitle || !bcontent || !bwriter || regExp.test(btitle) || regExp.test(bcontent) ){
    	alert("글제목/글내용/작성자를 모두 유효한 값으로 입력해야 합니다.");
    	return false ;
    } else {
    	frmModify.setAttribute("action", "${contextPath}/myboard/modify")
    	frmModify.submit() ;
        //return true ;
    }
}


function checkValueDelete(){
	var frmModify = document.getElementById("frmModify") ;
	
	frmModify.setAttribute("action", "${contextPath}/myboard/delete?bno=${board.bno}")
    frmModify.submit() ;
    
    
}


</script>







<script>
/* 
window.addEventListener('pageshow', (event) => {
	  if (event.persisted) {
	    console.log('This page was restored from the bfcache.');
	  } else {
	    console.log('This page was loaded normally.');
	  }
}); */

</script>	
<%@ include file="../myinclude/myfooter.jsp" %>