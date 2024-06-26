<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

<style>
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
                    <h3 class="page-header">Board - Detail <small>조회수: <c:out value="${myBoard.bviewsCnt }" /></small></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4>게시물 상세</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">

    <div class="form-group">
        <label>글번호</label>
        <input class="form-control" id="bno" name="bno" readonly="readonly" value='<c:out value="${myBoard.bno }"/>' >
    </div>
    <div class="form-group">
        <label>글제목</label>
        <input class="form-control" id="btitle" name="btitle" readonly="readonly" value='<c:out value="${myBoard.btitle }"/>' >
    </div>
    <div class="form-group">
        <label>글내용</label>
        <textarea class="form-control" id="bcontent" name="bcontent" rows="3" readonly="readonly" 
                   ><c:out value="${myBoard.bcontent }"/></textarea>
    </div>
    <div class="form-group">
        <label>작성자</label>
        <input class="form-control" id="bwriter" name="bwriter" readonly="readonly" value='<c:out value="${myBoard.bwriter }"/>'  >
    </div>
    <div class="form-group">
        <label>게시물 등록일</label>
        <input class="form-control" id="bregDate" name="bregDate" readonly="readonly" 
               value='<fmt:formatDate value="${ myBoard.bregDate }" pattern="yyyy/MM/dd HH:mm:ss"/>'  >
    </div>
    <div class="form-group">
        <label>게시물 수정일</label>
        <input class="form-control" id="bmodDate" name="bmodDate" readonly="readonly" 
               value='<fmt:formatDate value="${ myBoard.bmodDate }" pattern="yyyy/MM/dd HH:mm:ss"/>'  >
    </div><%-- 
    <button type="button" class="btn btn-primary" 
            onclick="location.href='${contextPath}/myboard/modify?bno=${board.bno }'">수정</button>
    <button type="button" class="btn btn-warning" 
            onclick="location.href='${contextPath}/myboard/list'">목록</button> --%>

    <button type="button" class="btn btn-primary" id="btnToModify">수정</button>
    <button type="button" class="btn btn-warning" id="btnToList">목록</button>

<%-- Modal: 게시물 수정 후, 수정 결과 표시 모달 --%>
<div class="modal fade" id="yourModal" tabindex="-1" role="dialog" aria-labelledby="yourModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="yourModalLabel">게시물 수정 결과</h4>
            </div>
            <div class="modal-body" id="yourModal-body"></div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal --%>

<form id="frmSendValue">
    <input type="hidden" id="bno" name="bno" value='<c:out value="${myBoard.bno }"/>' >
    <input type="hidden" id="pageNum" name="pageNum" value="${myBoardPaging.pageNum }">
    <input type="hidden" id="rowAmountPerPage" name="rowAmountPerPage" value="${myBoardPaging.rowAmountPerPage }">
    <input type="hidden" id="scope" name="scope" value="${myBoardPaging.scope }">
    <input type="hidden" id="keyword" name="keyword" value="${myBoardPaging.keyword }">
    <input type="hidden" id="beginDate" name="beginDate" value="${myBoardPaging.beginDate }">
    <input type="hidden" id="endDate" name="endDate" value="${myBoardPaging.endDate }">
</form>
                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
<%-- 첨부 파일 표시 영역 --%>
             <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4>첨부 파일</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">
                            <div class="form-group fileUploadResult">
                                <ul>
<c:choose>
<c:when test="${empty myBoard.attachFileList }">
    <li style="font-size: 12pt;">첨부파일이 없습니다.</li>
</c:when>
<c:otherwise>
    <c:forEach var="attachFile" items="${myBoard.attachFileList }">
        <c:choose>
        <c:when test="${attachFile.fileType == 'F' }">
           <li class="attachLi" 
                data-repopath="${attachFile.repoPath }"
	            data-uploadpath="${attachFile.uploadPath }" 
			    data-uuid="${attachFile.uuid }" 
			    data-filename="${attachFile.fileName }"
			    data-filetype="${attachFile.fileType }">
				        <img src='${contextPath}/resources/icons/icon-attach.png' style='width:50px;'/>
				        &emsp;${attachFile.fileName}
			</li>
		</c:when>
        <c:when test="${attachFile.fileType == 'I' }">
           <c:set var="thumbnail" value="${attachFile.repoPath}/${attachFile.uploadPath}/s_${attachFile.uuid}_${attachFile.fileName}"/>
           <li class="attachLi" 
                data-repopath="${attachFile.repoPath }"
	            data-uploadpath="${attachFile.uploadPath }" 
			    data-uuid="${attachFile.uuid }" 
			    data-filename="${attachFile.fileName }"
			    data-filetype="${attachFile.fileType }">
				        <img src="${contextPath}/displayThumbnail?thumbnail=${thumbnail}" style='width:50px;'/>
				        &emsp;${attachFile.fileName}
			</li>
		</c:when>
        </c:choose>
    </c:forEach>
</c:otherwise>
</c:choose>
                                </ul>
                            </div>


                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
<%-- Modal: 첨부파일 원본 이미지 표시모달 --%>
<div class="modal fade" id="attachModal" tabindex="-1" role="dialog" aria-labelledby="attachModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" id="attachModal-body">메시지</div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal --%>


        </div><%-- /#page-wrapper --%>

<script>

var frmSendValue = $("#frmSendValue") ;

//게시물 수정 페이지로 이동
$("#btnToModify").on("click", function(){
	//location.href="${contextPath}/myboard/modify?bno=${board.bno }" ;
	frmSendValue.attr("action", "${contextPath}/myboard/modify") ;
	frmSendValue.attr("method", "get");
	
	frmSendValue.submit() ;
	
});

//게시물 목록 페이지로 이동
$("#btnToList").on("click", function(){
	//location.href="${contextPath}/myboard/list" ;
	frmSendValue.find("#bno").remove() ;
	frmSendValue.attr("action", "${contextPath}/myboard/list") ;
	frmSendValue.attr("method", "get");
	
	frmSendValue.submit() ;
});


function runModal(result) {
	if (result.length == 0) {
		return ;
	
	} else if ( result == "successModify" ) {
		var myMsg =  "게시글이 수정되었습니다. " ;
		
	}  

	$("#yourModal-body").html(myMsg) ;
	
	$("#yourModal").modal("show") ;
	
	myMsg = "" ;
}

$(".attachLi").on("click", function(){
	var attachLi = $(this) ;
	var fileName = attachLi.data("repopath") + "/"
	             + attachLi.data("uploadpath") + "/"
	             + attachLi.data("uuid") + "_" + attachLi.data("filename") ;
	var fileType = attachLi.data("filetype") ;
	
	if(fileType == "I") {
		$("#attachModal-body").html('<img src="${contextPath }/doFileDownloadByAjax?fileName=' + encodeURI(fileName) + '" style="width:100%"/>');
		
		$("#attachModal").modal("show") ;
		
		
	} else {
		location.href = "${contextPath}/doFileDownloadByAjax?fileName=" + encodeURI(fileName)
	}
});


$("#attachModal").on("click", function(){
	$("#attachModal").modal("hide") ;
});


$(document).ready(function(){
	//runModal(result) ;

});
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