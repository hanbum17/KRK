<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ include file="../include/header.jsp" %>



<style>
	th{text-align: center;}
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
                        <div class="panel-heading"><h4>게시물 수정</h4></div><!-- /.panel-heading -->
                        <div class="panel-body">
                            
                            <form id="frmModify" role="form" method="post" action="${contextPath }/myboard/modify">
	                            <div class="form-group">
	                                <label>글제목</label>
	                                <input class="form-control" id="btitle" name="btitle" value='<c:out value="${board.btitle }"/>'>
	                            </div>
	                            <div class="form-group">
	                                <label>글내용</label>
	                                <textarea class="form-control" rows="3" id="bcontent" name="bcontent"><c:out value="${board.bcontent }"/></textarea>
	                            </div>
	                            <div class="form-group">
	                                <label>작성자</label>
	                                <input class="form-control" id="bwriter" name="bwriter" value='<c:out value="${board.bwriter }"/>' readonly="readonly">
	                            </div>
	                            <input type="hidden" value='<c:out value="${board.bno }"/>' name="bno">
	                            <button type="button" class="btn btn-primary" onclick="checkValues(); checkValueModify();">수정</button>
	                            <button type="button" class="btn btn-danger" onclick="checkValues(); checkValueDelete();">삭제</button>
	                            <button type="button" class="btn btn-warning" onclick="location.href='${contextPath}/myboard/list';">목록</button>
                            </form>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

            <!-- /.row -->

            <!-- /.row -->
            
                <!-- /.col-lg-6 -->
                
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
<script>

	function checkValues() {
		var btitle = document.getElementById("btitle").value;
		var bcontent = document.getElementById("bcontent").value;
		var bwriter = document.getElementById("bwriter").value;
		
		//정규표현식: 빈칸만 입력된 패턴 금지
		var regExp = /^\s+$/;
		var result = regExp.test(btitle);
		
		if(!btitle || !bcontent || !bwriter || regExp.test(bcontent)){
			alert("글제목, 글내용, 작성자를 모두 유효한 값으로 입력하세요.");
			return false;
		}
		return true;
	}
	function checkValueModify() {
		var frmModify = document.getElementById("frmModify");
		
		frmModify.setAttribute("action", "${contextPath}/myboard/modify");
		frmModify.submit();
		//return true;
	}
	
	function checkValueDelete() {
		var frmModify = document.getElementById("frmModify");
		
		frmModify.setAttribute("action", "${contextPath}/myboard/delete?bno=${board.bno}");
		frmModify.submit();
		//return true;
	}
	
</script>

<!-- [수정] 버튼을 누르면 수정된 내용이 컨트롤러로 보내지고 detail로 이동 -->
<!-- [삭제] 버튼을 누르면 해당 게시물의 bdelFlag값을 1로 변경하고 list로 이동 -->

<%@ include file="../include/footer.jsp" %>
