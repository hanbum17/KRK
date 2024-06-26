<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ include file="../include/header.jsp" %>
<style>
	th {text-align: center;}
    .fileUploadResult ul li { list-style: none; }
    .bigImageWrapper {
        position: absolute;
        display: none;
        justify-content: center;
        align-items: center;
        top:0%;
        width: 100%;
        height: 100%;
        background-color: lightgray;
        z-index: 100;
    }
    .bigImage { position: relative; display: flex; justify-content: center; align-items: center; }
    .bigImage img { height: 100%; max-width: 100%; width: auto; overflow: hidden }
</style>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">Board - Detail</h3>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 style="display: inline-block;">게시물 상세정보</h4>
					<p class="pull-right">
						조회수:
						<c:out value="${myboard.bviewsCnt }" />
					</p>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="form-group">
						<label>글번호</label> <input class="form-control" id="bno" name="bno"
							readonly="readonly" value="<c:out value='${myboard.bno }'/>">
					</div>
					<div class="form-group">
						<label>글제목</label> <input class="form-control" id="btitle"
							name="btitle" readonly="readonly"
							value="<c:out value='${myboard.btitle }'/>">
					</div>
					<div class="form-group">
						<label>글내용</label>
						<textarea class="form-control" rows="3" id="bcontent"
							name="bcontent" readonly="readonly"><c:out
								value="${myboard.bcontent }" /></textarea>
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" id="bwriter"
							name="bwriter" readonly="readonly"
							value="<c:out value='${myboard.btitle }'/>">
					</div>
					<div class="form-group">
						<label>게시물 등록일</label> <input class="form-control" id="bregDate"
							name="bregDate" readonly="readonly"
							value="<fmt:formatDate value='${myboard.bregDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
					</div>
					<div class="form-group">
						<label>최종수정일</label> <input class="form-control" id="bmodDate"
							name="bmodDate" readonly="readonly"
							value="<fmt:formatDate value='${myboard.bmodDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
					</div>
					<button type="button" class="btn btn-primary" id="btnToModify">수정</button>
					<button type="button" class="btn btn-warning" id="btnToList">목록</button>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>첨부 파일</h4>
				</div><!-- /.panel-heading -->
				<div class="panel-body">
					<div class="form-group fileUploadResult">
						<ul>
<c:choose>
	<c:when test="${empty myboard.attachFileList }">
		<li style="font-size: 12pt;">첨부파일이 없습니다.</li>
	</c:when>
	<c:otherwise>
		<c:forEach var="attachFile" items="${myboard.attachFileList }">
			<c:choose>
				<c:when test="${attachFile.fileType =='F' }">
					<li class="attachLi"
						data-repopath="${attachFile.repoPath}"
						data-uploadpath="${attachFile.uploadPath}"
			            data-uuid="${attachFile.uuid}"
			            data-file-name="${attachFile.fileName}"
			            data-file-type="${attachFile.fileType}">
			            	<img src='${contextPath}/resources/icons/icon-attach.png' style='width:50px;'/>
			                &emsp; ${attachFile.fileName}
			        </li>
		        </c:when>
		        <c:when test="${attachFile.fileType =='I' }">
		        	<c:set var="thumbnail" value="${attachFile.repoPath}/${attachFile.uploadPath}/s_${attachFile.uuid}_${attachFile.fileName}"/>
					<li class="attachLi"
						data-repopath="${attachFile.repoPath}"
						data-uploadpath="${attachFile.uploadPath}"
			            data-uuid="${attachFile.uuid}"
			            data-file-name="${attachFile.fileName}"
			            data-file-type="${attachFile.fileType}">
			            	<img src="${contextPath}/displayThumbnail?fileName=${thumbnail}" style='width:50px;'/>
			                &emsp; ${attachFile.fileName}
			        </li>
		        </c:when>
	        </c:choose>
		</c:forEach>	
	</c:otherwise>

</c:choose>
						</ul>
					</div>
					
					
				</div><!-- /.panel-body -->
			</div><!-- /.panel -->
		</div><!-- /.col-lg-12 -->
	</div><!-- /.row -->
	
	<!-- 첨부파일 원본 이미지 표시모달 -->
	<div class="modal fade" id="attachModal" tabindex="-1" role="dialog" aria-labelledby="attachModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body" id="attachModal-body">처리가 완료되었습니다.</div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

	<!-- Modal:게시물 수정 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">게시물 수정</h4><!-- 수정 -->
                </div>
                <div class="modal-body">처리가 완료되었습니다.</div><!-- 수정 -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">확인</button><!-- 수정 -->
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div> <!-- /#page-wrapper -->

<!-- 페이지 정보 유지 폼 -->
<form id="frmSendValue">
    <input type='hidden' name='bno' id="bno" value='<c:out value="${myboard.bno}"/>'>
	<input type="hidden" id="pageNum" name="pageNum" value="${myBoardPaging.pageNum }">
    <input type="hidden" id="rowAmountPerPage" name="rowAmountPerPage" value="${myBoardPaging.rowAmountPerPage }">
    <input type="hidden" id="scope" name="scope" value="${myBoardPaging.scope }">
    <input type="hidden" id="keyword" name="keyword" value="${myBoardPaging.keyword }">

</form>

<script>

var frmSendValue = $("#frmSendValue");
// 게시물 수정 페이지로 이동: 폼의 페이징 값을 전송하도록 구현
$("#btnToModify").on("click", function(){
  frmSendValue.attr("action", "${contextPath}/myboard/modify");
  frmSendValue.attr("method", "get");
  frmSendValue.submit();
});

// 게시물 목록 페이지로 이동: 폼의 페이징 값을 전송하도록 구현
$("#btnToList").on("click", function(){
  frmSendValue.find("#bno").remove(); // 목록화면 이동 시, bno 값 삭제
  frmSendValue.attr("action", "${contextPath}/myboard/list");
  frmSendValue.attr("method", "get");
  frmSendValue.submit();
});



var result = '<c:out value="${result}"/>'; // 컨트롤러가 전달한 result 값을 변수에 저장

function checkModal(result) {
    if (result == '' || history.state) {
        return;
    } else if(result == 'successModify'){
        var myMsg = "글이 수정되었습니다";
    }

    $(".modal-body").html(myMsg);
    $("#myModal").modal("show");
    myMsg = '';
}

$(".attachLi").on("click", function() {
    var attachLi = $(this);
    var fileName = attachLi.data("repopath") + "/"
                 + attachLi.data("uploadpath") + "/"
                 + attachLi.data("uuid") + "_"
                 + attachLi.data("file-name");
    var fileType = attachLi.data("file-type");
    
    if (fileType == "I") {
        $("#attachModal-body").html('<img src="${contextPath}/doFileDownloadByAjax?fileName=' + fileName + '" style="width: 100%"/>');
        $("#attachModal").modal("show");
    } else {
        location.href = "${contextPath}/doFileDownloadByAjax?fileName=" + fileName;
    }
});

$(document).ready(function(){
    checkModal(result);
});
</script>





<%@ include file="../include/footer.jsp" %>
