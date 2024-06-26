<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../myinclude/myheader.jsp" %>

<%-- 댓글 화면 표시 시작, 붙여넣기 후, 아래의 style 태그 내용은 <div id="page-wrapper"> 태그 위에 옮겨놓을 것 --%>
<style>
    .commentUL li { list-style: none; }
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
    .txtBoxCmt, .txtBoxComment {overflow: hidden; resize: vertical; min-height: 100px; color: black; }
</style>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header"
                style="white-space: nowrap;" >Board - Detail: <c:out value="${board.bno}"/>번 게시물</h3>
        </div>
    </div>
<%-- 게시물 상세 표시 시작 --%>    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <div class="row">
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:11px;">
                            <strong style="font-size:18px;">${board.bwriter}님 작성</strong>
                        </div>
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:16px;">
                            <span class="text-primary" style="font-size: smaller; height: 45px; padding-top: 19px;">
                                <span>
                                    <span>등록일:&nbsp;</span>
                                    <strong><fmt:formatDate 	pattern="yyyy-MM-dd HH:mm:ss" 
                                                              	value="${board.bregDate}"/></strong>
                                    <span>&nbsp;&nbsp;</span>
                                </span>
                                <span>조회수:&nbsp;<strong><c:out value="${board.bviewsCnt}"/></strong></span>
                            </span>
                        </div>
                        <div class="col-md-6" style="height: 45px; padding-top:6px;"><%-- vertical-align: middle; --%>
                            <div class="button-group pull-right">
                                <button type="button" id="btnToModify" data-oper="modify" 
                                         class="btn btn-primary"><span>수정</span></button>
                                <button type="button" id="btnToList" data-oper="list"
                                         class="btn btn-info"><span>목록</span></button>
                            </div>
                        </div>
                    </div>
                </div><%-- /.panel-heading --%>
                
                <div class="panel-body form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">글제목</label>
                        <div class="col-sm-10"> 
                            <input class="form-control" name="btitle" value='<c:out value="${board.btitle}"/>' 
                                    readonly="readonly"/>

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">글내용</label>
                        <%-- <textarea>와 </textarea>는 한 줄에 작성되어야 필요없는 공백이 포함되지 않음 --%>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" name="bcontent" style="resize: none;" 
                                       readonly="readonly"><c:out value="${board.bcontent}"/></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">최종수정일</label>
                        <div class="col-sm-10"> 
                            <input class="form-control" name="bmodDate"
                                    value='<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${board.bmodDate}"/>'
                                    readonly="readonly" />
                        </div>
                    </div>
                </div><%-- /.panel-body --%>
            </div><%-- /.panel --%>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>


<form id="frmSendValue">
    <input type="hidden" id="bno" name="bno" value='<c:out value="${myBoard.bno }"/>' >
    <input type="hidden" id="pageNum" name="pageNum" value="${myBoardPaging.pageNum }">
    <input type="hidden" id="rowAmountPerPage" name="rowAmountPerPage" value="${myBoardPaging.rowAmountPerPage }">
    <input type="hidden" id="scope" name="scope" value="${myBoardPaging.scope }">
    <input type="hidden" id="keyword" name="keyword" value="${myBoardPaging.keyword }">
    <input type="hidden" id="beginDate" name="beginDate" value="${myBoardPaging.beginDate }">
    <input type="hidden" id="endDate" name="endDate" value="${myBoardPaging.endDate }">
</form>

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


<%-- 댓글 화면 표시 시작 --%>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default" >
            <div class="panel-heading">
                <p style="margin-bottom: 0px; font-size: 16px;">
                    <strong style="padding-top: 2px;">
                        <span>댓글&nbsp;<c:out value="${board.breplyCnt}"/>개</span> 
                        <span>&nbsp;</span>
                        <button type="button" id="btnChgCmtReg" class="btn btn-info btn-sm">댓글 작성</button>
                        <button type="button" id="btnRegCmt" class="btn btn-warning btn-sm"
                                style="display:none">댓글 등록</button>
                        <button type="button" id="btnCancelRegCmt" class="btn btn-warning btn-sm"
                                style="display:none">취소</button>
                    </strong>
                </p>
            </div> <%-- /.panel-heading --%>

            <div class="panel-body">
<%-- 댓글 입력창 div 시작 --%>
                <div class="form-group" style="margin-bottom: 5px;">
                    <textarea class="form-control txtBoxCmt" name="rcontent"
                               placeholder="댓글작성을 원하시면,&#10;댓글 작성 버튼을 클릭해주세요."
                               readonly="readonly"
                    ></textarea>
                </div>
                <hr style="margin-top: 10px; margin-bottom: 10px;">
<%-- 댓글 입력창 div 끝 --%>
                <ul class="commentUL">
                <%-- 댓글 목록 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
    <li class="left clearfix commentLi" data-bno="123456"  data-rno="12">
        <div>
            <div>
                <span class="header info-rwriter">
                    <strong class="primary-font">user00</strong>
                    <span>&nbsp;</span>
                    <small class="text-muted">2018-01-01 13:13</small>
                </span>
                <p>앞으로 사용할 댓글 표시 기본 템플릿입니다.</p>
            </div>
            <div class="btnsComment" style="margin-bottom:10px">
                <button type="button" style="display:in-block"
                         class="btn btn-primary btn-xs btnChgReg">답글 작성</button>
                <button type="button" style="display:none" 
                         class="btn btn-warning btn-xs btnRegCmt">답글 등록</button>
                <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">
                <textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-bottom:10px"
                           placeholder="답글작성을 원하시면,&#10;답글 작성  버튼을 클릭해주세요."
                 ></textarea>
            </div>
        </div>
    </li>

                
                </ul><%-- /.chat --%>
            </div><%-- /.panel-body --%>

            <div class="panel-footer text-center" id="showCmtPagingNums">
                <%-- 댓글 목록의 페이징 번호 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
            </div>
        </div><%-- /.panel --%>
    </div><%-- .col-lg-12 --%>
</div><%-- .row : 댓글 화면 표시 끝 --%>
<%-- 댓글 페이징 데이터 저장 form --%>
<form id="frmCmtPagingValue">
    <input type='hidden' name='pageNum' value='' />
    <input type='hidden' name='rowAmountPerPage' value='' />
</form>

        


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

var result = '<c:out value="${result}"/>'   ;

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


</script>
<script src="${contextPath }/resources/js/myreply.js"></script>
<script>
var commentUL = $(".commentUL") ;
var frmCmtPagingValue = $("#frmCmtPagingValue") ;
var bno = '<c:out value="${myBoard.bno}"/>' ; 

<%-- 댓글 페이징 번호 표시 --%>
function showCmtPagingNums(rowTotal, pageNum, rowAmountPerPage) {
	
    var pagingNumCnt = 3 ;
    var endPagingNum = Math.ceil(pageNum/pagingNumCnt) * pagingNumCnt ;
    var startPagingNum = endPagingNum - (pagingNumCnt - 1) ;
    var lastPagingNum = Math.ceil(rowTotal/rowAmountPerPage) ;
    
    if(lastPagingNum < endPagingNum) {
        endPagingNum = lastPagingNum ;
    }
    
    var prev = startPagingNum > 1 ;
    var next = endPagingNum < lastPagingNum ;
    var pagingShowHTML
        = '<div style="text-align:center;">'
        + '    <ul class="pagination pagination-sm">' ;
    if(prev) {
        pagingShowHTML
        +='        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="1"><span aria-hidden="true">&laquo;</span></a>'
        + '        </li>'
        + '        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="' + (startPagingNum - 1) + '">이전</a>'
        + '        </li>' ;
    }
    for (var i = startPagingNum ; i <= endPagingNum ; i++) {
        var active = ((pageNum == i) ? "active" : "") ;
        pagingShowHTML
        +='        <li class="paginate_button ' + active + '" tabindex="0" >'
        + '            <a href="' + i + '">' + i + '</a>'
        + '        </li>' ;
    }
    if(next) {
        pagingShowHTML
        +='        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="' + (endPagingNum + 1) + '">다음</a>'
        + '        </li>'
        + '        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="' + lastPagingNum + '"><span aria-hidden="true">&raquo;</span></a>'
        + '        </li>' ;
    }
        pagingShowHTML
        +='    </ul>'
        + '</div>' ;
        
        $("#showCmtPagingNums").html(pagingShowHTML) ;
}

<%-- 선택된 페이징 번호 클릭 시, 댓글목록 가져오는 함수: 이벤트 전파 이용 --%>
<%-- #showCmtPagingNums > div > ul > li:nth-child(2) > a--%>
$("#showCmtPagingNums").on("click","div ul li a",function(e){
    e.preventDefault() ;
    var pageNum = $(this).attr("href") ;
    showCmtList(pageNum) ;
});
<%--댓글 목록표시 함수--%>
function showCmtList(pageNum){
    myCommentModule.getReplyList(
        {bno: bno, pageNum: pageNum } ,
        
        function(myReplyPagingCreator){
            //console.log("myReplyList: \n" + myReplyList) ;
            var resultHTML = "" ;
            for(var myReply of myReplyPagingCreator.myReplyList) {
       resultHTML
       +='<li class="left clearfix commentLi" data-bno="' + myReply.bno + '"  data-rno="' + myReply.rno +'">'
       + '    <div>'
       + '        <div>'
       + '            <span class="header info-rwriter">'
       + '                <strong class="primary-font">' + myReply.rwriter + '</strong>'
       + '                <span>&nbsp;</span>'
       + '                <small class="text-muted">' + myReply.rregDate + '</small>'
       + '            </span>'
       + '            <p style="white-space:pre-wrap;">' + myReply.rcontent + '</p>'
       + '        </div>'
       + '        <div class="btnsComment" style="margin-bottom:10px">'
       + '            <button type="button" style="display:in-block"'
       + '                    class="btn btn-primary btn-xs btnChgReg">답글 작성</button>'
       + '            <button type="button" style="display:none" '
       + '                    class="btn btn-warning btn-xs btnRegCmt">답글 등록</button>'
       + '            <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">'
       + '            <textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-bottom:10px"'
       + '                      placeholder="답글작성을 원하시면,&#10;답글 작성  버튼을 클릭해주세요."'
       + '                      ></textarea>'
       + '        </div>'
       + '    </div>'
       + '</li>' ;
            }<%--for-end--%>
            
            commentUL.html(resultHTML) ;
            
            showCmtPagingNums(myReplyPagingCreator.rowTotal,
                              myReplyPagingCreator.myReplyPaging.pageNum,
                              myReplyPagingCreator.myReplyPaging.rowAmountPerPage) ;
        } ,
        
        function(err) {
            console.log("오류: err: \n" + err) ;
        }
    );<%-- getReplyList()-end --%>
}

<%-- 댓글 작성 버튼 클릭 처리 --%>
$("#btnChgCmtReg").on("click", function(){
	
	$(this).attr("style", "display:none;") ;
	$("#btnRegCmt").attr("style", "display:in-block; margin-right:2px") ;
	$("#btnCancelRegCmt").attr("style", "display:in-block;") ;
	$(".txtBoxCmt").attr("placeholder", "").attr("readonly", false) ;
	
});

<%-- 댓글 등록 "취소" 버튼 클릭 처리 --%>
$("#btnCancelRegCmt").on("click", function(){
	$("#btnChgCmtReg").attr("style", "display:in-block;") ;
	$("#btnRegCmt").attr("style", "display:none") ;
	$("#btnCancelRegCmt").attr("style", "display:none;") ;
	$(".txtBoxCmt").val("")
				   .attr("readonly", true)
				   .attr("placeholder", "댓글작성을 원하시면,\n댓글 작성 버튼을 클릭해주세요.") ; 
});

<%-- "댓글등록" 버튼 클릭 처리 --%>
$("#btnRegCmt").on("click", function(){
	var rcontent = $(".txtBoxCmt").val() ;
	var rwriter = "user1"
	var comment = {bno: bno, rcontent: rcontent, rwriter: rwriter }
	
	myCommentModule.registerComment(
	    comment ,
	    function(result){
	    	if (result != null){
	    		alert(result + "번 댓글이 등록되었습니다.") ;
	    	} else {
	    		alert("서버 장애로 댓글 등록이 취소되었습니다.") ;
	    	}
	    	
	    	showCmtList(1) ;
	    }
	) ; //myCommentModule.registerComment()-end
});



</script>
<script>
$(document).ready(function(){
    runModal(result) ;
    showCmtList(1) ;
});
</script>

<%@ include file="../myinclude/myfooter.jsp" %>











<%--
<script>
/* 
window.addEventListener('pageshow', (event) => {
	  if (event.persisted) {
	    console.log('This page was restored from the bfcache.');
	  } else {
	    console.log('This page was loaded normally.');
	  }
}); */
</script> --%>