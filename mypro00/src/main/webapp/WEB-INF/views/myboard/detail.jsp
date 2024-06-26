<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../myinclude/myheader.jsp" %>

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
            <h3 class="page-header" style="white-space: nowrap;">Board - Detail</h3><%--수정--%>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
<div class="panel-heading">
    <h4 style="display:inline-block;white-space: nowrap;" >No. <c:out value="${myBoard.bno}"/>-글 상세</h4>
    <small class="pull-right" >조회수: <c:out value="${myBoard.bviewsCnt}"/></small><%--수정--%>
</div><%-- /.panel-heading --%>
<div class="panel-body">
    <%-- 기존 form 시작/종료 태그만 삭제 --%>
    <div class="form-group">
        <label>글제목</label>
        <input class="form-control" name="btitle" 
               value='<c:out value="${myBoard.btitle}"/>' readonly="readonly">
    </div><!-- class="col-sm-2 "  -->
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">글내용</label>
        <textarea class="form-control" rows="3" name="bcontent"
                  readonly="readonly"
                  ><c:out value="${myBoard.bcontent}"/></textarea>
    </div>
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">작성자</label>
        <input class="form-control" name="bwriter"
               value='<c:out value="${myBoard.bwriter}"/>' readonly="readonly"/>
    </div>
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">최종수정일</label> [등록일시: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${myBoard.bregDate}"/>]
        <input class="form-control" name="bmoddate" 
               value='<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${myBoard.bmodDate}"/>'
               readonly="readonly" />
    </div>
    
<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="username"/>
	<c:if test="${username == myBoard.bwriter }">
    	<button type="button" class="btn btn-default" id="btnToModify" >수정</button>
    </c:if>
</security:authorize> 
    
    <button type="button" class="btn btn-info" id="btnToList" >목록</button>

</div><!-- /.panel-body -->
            </div><!-- /.panel -->
        </div><!-- /.col-lg-12 -->
    </div><!-- /.row -->

<%-- Modal 게시물 수정 후, 수정 결과 표시 모달 --%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">처리 결과</h4>
            </div>
            <div class="modal-body">처리가 완료되었습니다.</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
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

<%-- 첨부파일 표시 --%>    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"><h4><strong>첨부 파일</strong></h4></div><%-- /.panel-heading --%>
                <div class="panel-body">
	                <div class="form-group fileUploadResult"><%--첨부파일 목록 표시 div--%>
	                    <ul>
<c:choose>
<c:when test="${empty myBoard.attachFileList }">
    <li style="font-size: 12pt;">첨부파일이 없습니다</li>
</c:when>
<c:otherwise>
    <c:forEach var="attachFile" items="${myBoard.attachFileList }">
        <c:choose>
        <c:when test="${attachFile.fileType == 'F' }">
            <li class="attachLi"
                data-repopath = "${attachFile.repoPath }"
                data-uploadpath = "${attachFile.uploadPath }"
                data-uuid = "${attachFile.uuid }"
                data-filename = "${attachFile.fileName }"
                data-filetype = "F" >
                    <img src='${contextPath}/resources/icons/icon-attach.png' style='width:50px;'>
                    &emsp;${attachFile.fileName}
            </li>
        </c:when>
        <c:otherwise>
            <c:set var="thumbnail" value="${attachFile.repoPath}/${attachFile.uploadPath}/s_${attachFile.uuid}_${attachFile.fileName}"/>
            <li class="attachLi"
                data-repopath = "${attachFile.repoPath }"
                data-uploadpath = "${attachFile.uploadPath }"
                data-uuid = "${attachFile.uuid }"
                data-filename = "${attachFile.fileName }"
                data-filetype = "I" >
                    <img src="${contextPath}/displayThumbnail?thumbnail=${thumbnail}" style='width:50px;'>
                    &emsp;&nbsp;${attachFile.fileName}
            </li>
        </c:otherwise>
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

<%-- Modal: 첨부파일 원본 이미지 표시 --%>
<div class="modal fade" id="attachModal" tabindex="-1" role="dialog" aria-labelledby="attachModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" id="attachModal-body">
            	<%--이미지표시 --%>
            </div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal --%>
<%-- 게시물 상세 표시 끝 --%>

<%-- 댓글 화면 표시 시작, 아래의 style 태그 내용은 <div id="page-wrapper"> 태그 위에 옮겨놓을 것 --%>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default" >
            <div class="panel-heading">
                <div style="margin-bottom: 0px; font-size: 16px;">
                    <strong style="padding-top: 2px;">
                        <span>댓글&nbsp;<c:out value="${myBoard.breplyCnt}"/>개</span>
                        <span id="replyTotal"></span> 
                        <span>&nbsp;</span>
                        <security:authorize access="isAuthenticated()">
		                    <button type="button" id="btnChgCmtReg" class="btn btn-info btn-sm">댓글 작성</button>
                        </security:authorize>
                        <button type="button" id="btnRegCmt" class="btn btn-warning btn-sm"
                                style="display:none">댓글 등록</button>
                        <button type="button" id="btnCancelRegCmt" class="btn btn-warning btn-sm"
                                style="display:none">취소</button>
                    </strong>
                </div>
            </div> <%-- /.panel-heading --%>

            <div class="panel-body">
<%-- 댓글 입력창 div 시작 --%>
				<security:authorize access="isAuthenticated()">
                <div class="form-group" style="margin-bottom: 5px;">
                    <textarea class="form-control txtBoxCmt" name="rcontent"
                               placeholder="댓글작성을 원하시면,&#10;댓글 작성 버튼을 클릭해주세요."
                               readonly="readonly"
                    ></textarea>
                </div>
                </security:authorize>
                <hr style="margin-top: 10px; margin-bottom: 10px;">
<%-- 댓글 입력창 div 끝 --%>
                <ul class="commentUL" style="padding-left:0; margin-left:0;">
                <%-- 댓글 목록 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
<li class="commentLi" data-bno="123456"  data-rno="12">
    <div>
        <div>
            <span class="header info-rwriter">
                <strong class="primary-font">user00</strong>
                <span>&nbsp;</span>
                <small class="text-muted">2018-01-01 13:13</small>
            </span>
            <p  style="white-space:pre-wrap;" >앞으로 사용할 댓글 표시 기본 템플릿입니다.</p>
        </div>
        <div class="btnsComment" style="margin-bottom:10px">
        <security:authorize access="isAuthenticated()">
        <security:authentication property="principal.username" var="username"/>
        <c:if test="${username != myReply.rwriter }">
            <button type="button" style="display:in-block"
                     class="btn btn-primary btn-xs btnChgReg">답글 작성</button>
        </c:if> 
        </security:authorize>
            <button type="button" style="display:none" 
                     class="btn btn-warning btn-xs btnRegReply">답글 등록</button>
            <button type="button" style="display:none; margin-left:4px;" 
                     class="btn btn-warning btn-xs btnCancelRegReply">취소</button>
            <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">
            <textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-bottom:10px"
                       placeholder="답글작성을 원하시면,&#10;답글 작성 버튼을 클릭해주세요."
             ></textarea>
        </div>
    </div>
</li>

                </ul><%-- /.commentUL --%>
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
var _csrfHeaderName = "${_csrf.headerName}" ;
var _csrfTokenValue = "${_csrf.token}" ;

$(document).ajaxSend(function(e, xhr, options){
	xhr.setRequestHeader(_csrfHeaderName, _csrfTokenValue) ;
}) ;
</script>


<script>

var frmSendValue = $("#frmSendValue") ;

//게시물 수정 페이지로 이동
$("#btnToModify").on("click", function(){
<%--
	//location.href='${contextPath}/myboard/modify?bno=<c:out value="${myboard.bno}"/>' ;
--%>

    var bno = '<c:out value="${myBoard.bno}"/>' ;
    var bwriter = '<c:out value="${myBoard.bwriter}"/>' ;
//    frmSendValue.append("<input type='hidden' name='bno' value='" + bno + "'/>") ;
//    frmSendValue.append("<input type='hidden' name='bwriter' value='" + bwriter + "'/>") ;
    frmSendValue.attr("action", "${contextPath}/myboard/modify").attr("method", "get") ;
    frmSendValue.submit() ;
});

//게시물 목록 페이지로 이동
$("#btnToList").on("click", function(){
<%--
    window.location.href="${contextPath}/myboard/list" ;
--%>
	frmSendValue.find("#bno").remove() ;
	frmSendValue.attr("action", "${contextPath}/myboard/list") ;
	frmSendValue.attr("method", "get");
	
	frmSendValue.submit() ;
});

var result = '<c:out value="${result}"/>'   ;

function runModal(result) {
//  if (result.length == 0|| history.state) {
	if (result.length == 0) {
		return ;
	
	} else if ( result == "successModify" ) {
		var myMsg =  "게시글이 수정되었습니다. " ;
		
	}  

	$("#yourModal-body").html(myMsg) ;
	
	$("#yourModal").modal("show") ;
	
	myMsg = "" ;
}

<%-- 첨부파일 이미지 표시 --%>
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
		self.location.href = "${contextPath}/doFileDownloadByAjax?fileName=" + encodeURI(fileName)
	}
});

<%-- 표시된 이미지 모달 감추기 --%>
$("#attachModal").on("click", function(){
	$("#attachModal").modal("hide") ;
});
</script>

<%-- 댓글/답글 자바스크립트 시작--%>
<script src="${contextPath }/resources/js/myreply.js"></script>
<script>
var bno = '<c:out value="${myBoard.bno}"/>' ;
var commentUL = $(".commentUL") ;
var frmCmtPagingValue = $("#frmCmtPagingValue") ;

var loginUser = '';

<security:authorize access="isAuthenticated()">
	loginUser='<security:authentication property="principal.username"/>';
</security:authorize>

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
<%-- 맨 앞으로 --%>        
        +='        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="1"><span aria-hidden="true">&laquo;</span></a>'
        + '        </li>'
<%-- 이전 페이징 번호 그룹 --%>
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
<%-- 다음 페이징 번호 그룹 --%>
        +='        <li class="paginate-button previous" tabindex="0" >'
        + '            <a href="' + (endPagingNum + 1) + '">다음</a>'
        + '        </li>'
<%-- 맨 뒤로 --%>
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
        	$("#replyTotal").html("--현재 댓글&nbsp;" + myReplyPagingCreator.rowTotal + "개") ;
        
        	frmCmtPagingValue.find("input[name='pageNum']").val(pageNum) ;
			frmCmtPagingValue.find("input[name='rowAmountPerPage']")
			                 .val(myReplyPagingCreator.myReplyPaging.rowAmountPerPage) ;	
        
            //console.log("myReplyList: \n" + myReplyList) ;
            var resultHTML = "" ;
            
            if(!myReplyPagingCreator.myReplyList == null || myReplyPagingCreator.myReplyList.length == 0){
                    resultHTML
                    +='<li class="commentLi" '
                    + '    style="text-align: center; background-color: lightCyan;'
                    + '    height: 35px;margin-bottom: 0px;padding-bottom:0px;'
                    + '    padding-top:6px;border: 1px dotted;">'
                    + '    <strong>등록된 댓글이 없습니다.</strong>'
                    + '</li>';
				
				commentUL.html(resultHTML) ;
				return ;
			}            
            for(var myReply of myReplyPagingCreator.myReplyList) {
                if(myReply.rdelFlag == 1) {	
                    resultHTML
                    +='<li class="commentLi">'
                    + '    <div style="background-color: Moccasin; text-align: center">'
                    + '        <em>작성자에 의해서 삭제글입니다.</em>'
                    + '    </div>'
                    + '</li>';
                } else {
                	resultHTML
                    +='<li class="commentLi" '
                    + '    data-bno="' + myReply.bno + '" '
                    + '    data-rno="' + myReply.rno + '" '
                    + '    data-rwriter="' + myReply.rwriter + '" '
                    + '    data-rdelflag="' + myReply.rdelFlag + '" >' 
                    + '    <div>'
<%-- 댓글 답글 들여쓰기 --%>
                    + '        <div style="padding-left:' + (myReply.lvl-1)*2*10 + 'px;">' 
                    ;
<%-- 답글의 레벨이 5이상이면 동일한 들여쓰기 --%><%-- 
                    if(myReply.lvl == 1){
                    resultHTML
                    +='        <div>' ;
                    } else if (myReply.lvl == 2){
                    resultHTML
                    +='        <div style="padding-left: 25px;">' ;
                    } else if (myReply.lvl == 3){
                    resultHTML
                    +='        <div style="padding-left: 50px;">' ;
                    } else if (myReply.lvl == 4){
                    resultHTML
                    +='        <div style="padding-left: 70px;">' ;
                    } else { 
                    resultHTML 
                    +='        <div style="padding-left: 100px;">' ;
                    }--%>
                    <%-- 답글에 대한 아이콘 표시  --%>
                    if(myReply.lvl > 1) {
                    resultHTML    
                    +='            <i class="fa fa-reply fa-fw"></i>&nbsp;';
                    }  	             
                    resultHTML     
                    +='            <span class="header info-rwriter">'
                    + '                <strong class="primary-font">' + myReply.rwriter + '</strong>'
                    + '                <span>&nbsp;</span>'
                    + '                <small class="text-muted">' + myCommentModule.myDateTimeFmt(myReply.rregDate) + '</small>'
                    + '            </span>'
                    + '            <p style="white-space:pre-wrap;">' + myReply.rcontent + '</p>'
                    + '        </div>'
                    + '        <div class="btnsReply " style="margin-bottom:10px">'
                    + ' 		<security:authorize access="isAuthenticated()">'
                    + '			<security:authentication property="principal.username" var="username"/>'
                    + '			<c:if test="${username != myReply.rwriter }">'
                    + '            <button type="button" style="display:in-block"'
                    + '                    class="btn btn-primary btn-xs btnChgReplyReg">답글 작성</button>' <%--
                    + '            <button type="button" style="display:none" '
                    + '                    class="btn btn-warning btn-xs btnRegReply">답글 등록</button>'
                    + '            <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">'
                    + '            <textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-bottom:10px"'
                    + '                      placeholder="답글작성을 원하시면,&#10;답글 작성  버튼을 클릭해주세요."'
                    + '                      ></textarea>' --%>
                    + '			 </c:if>  '
                    + '			</security:authorize> ' 
                    + '            <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">'
                    + '        </div>'
                    + '    </div>'
                    + '</li>' ;
                }
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


<%-- [댓글 작성] 버튼 클릭 처리 --%>
$("#btnChgCmtReg").on("click", function(){
    resetRelyRegElements() ;
    resetReplyModElements() ;	
    $(this).attr("style", "display:none;") ;
    $("#btnRegCmt").attr("style", "display:in-block; margin-right:2px") ;
    $("#btnCancelRegCmt").attr("style", "display:in-block;") ;
    $(".txtBoxCmt").attr("placeholder", "").attr("readonly", false) ;
});

<%-- [댓글등록] 버튼 클릭 처리 --%>
$("#btnRegCmt").on("click", function(){
    var rcontent = $(".txtBoxCmt").val() ;
    var rwriter = loginUser
    var prno = null ;
    var reply = {bno: bno, rcontent: rcontent, rwriter: rwriter, prno: prno }

    myCommentModule.registerReply(
        reply ,
        function(result){
            if (result != null){
                alert(result + "번 댓글이 등록되었습니다.") ;
            } else {
                alert("서버 장애로 댓글 등록이 취소되었습니다.") ;
            }
            resetCmtRegElements();
            showCmtList(1) ;
        }
    ) ;<%-- myCommentModule.registerComment()-end --%>
});

<%--댓글 작성/등록을 위한 요소 초기화 함수 --%>
function resetCmtRegElements(){
	$("#btnChgCmtReg").attr("style", "display:in-block;") ;
	$("#btnRegCmt").attr("style", "display:none") ;
	$("#btnCancelRegCmt").attr("style", "display:none;") ;
	$(".txtBoxCmt").val("")
				   .attr("readonly", true)
				   .attr("placeholder", "댓글작성을 원하시면,\n댓글 작성 버튼을 클릭해주세요.") ;
	
	//$("#spanLoginUser").attr("style", "display:none;") ;
}

<%-- 댓글 등록 [취소] 버튼 클릭 처리 --%>
$("#btnCancelRegCmt").on("click", function(){
    resetCmtRegElements() ;
});

<%-- [답글 작성] 버튼 클릭 처리: 함수 실행으로 표시된 버튼이므로 요소 선택을 위해 이벤트 전파를 이용해야 함 --%>
<%-- ul > li:nth-child(1) > div > div.btnsReply > button  --%>
$(".commentUL").on("click","li .btnChgReplyReg" , function(){
	resetCmtRegElements(); <%-- 먼저 진행 중인 댓글 등록 화면 요소 초기화 --%>
	resetRelyRegElements() ; <%-- 먼저 진행 중인 다른 답글 등록 화면 요소 초기화 --%>
	resetReplyModElements() ; <%-- 먼저 진행 중인 다른 답글 수정 화면 요소 초기화 --%>

    var strTxtBoxReply = "" ;
    strTxtBoxReply
    +='<textarea class="form-control txtBoxReply" name="rcontent" style="margin-bottom:10px;"'
    + '		 placeholder="답글작성을 원하시면, &#10;답글 작성 버튼을 클릭해주세요."'
    + '			></textarea>'
    + '<button type="button" class="btn btn-warning btn-xs btnRegReply">답글 등록</button>'
    + '<button type="button" class="btn btn-danger btn-xs btnCancelRegReply"'
    + ' 	   style="margin-left:4px;">취소</button>';
    
    $(this).after(strTxtBoxReply);
    $(this).attr("style", "display:none;")
});

<%-- 답글 등록 버튼 클릭 처리: 이벤트 전파 --%>
<%-- ul.commentUL > li:nth-child(1) > button.btn.btn-warning.btn-xs.btnRegReply  --%>
$(".commentUL").on("click", "li .btnRegReply", function(){
	if(!loginUser){
		return ;
	}

    var rcontent = $(this).prev().val() ;<%--
    var rwriter = "user1" ;--%>
    var prno = $(this).closest("li").data("rno") ;
    
    var reply = {bno: bno, rcontent: rcontent, rwriter: loginUser, prno:prno } ;
    myCommentModule.registerReply(
            reply,
            function(result){
                alert(result + "번 답글이 등록되었습니다.") ;
                var pageNum = frmCmtPagingValue.find('input[name="pageNum"]').val() ;
                showCmtList(pageNum) ;
            }
    );
});


<%--답글 작성/등록을 위한 요소 초기화 함수 --%>
function resetRelyRegElements() {
    $(".btnRegReply").remove() ;
    $(".btnCancelRegReply").remove() ;
    $(".txtBoxReply").remove() ;
    $(".btnChgReplyReg").attr("style", "display: in-block;") ;
	//$("#replyloginUserSpan").attr("style", "display: none;") ;
}


<%-- 답글 등록 [취소] 버튼 클릭 처리: 이벤트 전파 --%>
<%-- #chat > li:nth-child(1) > button.btn.btn-danger.btn-xs.btnCancelRegReply--%><%--
$(".btnCancelRegReply").on("click", function(){--%>
$(".commentUL").on("click", "li .btnCancelRegReply", function(){
    //$("#replyloginUserSpan").attr("style", "display: none;") ;
    resetRelyRegElements() ;
});



<%-- 댓글/답글 수정 요소 표시: 글내용(p요소) 클릭 시, 입력창, 수정, 삭제, 취소 버튼 화면 표시 --%>
<%-- ul > li:nth-child(3) > div > div:nth-child(1) > p --%>
$(".commentUL").on("click","li div div p", function(){ <%-- 이벤트전파 --%>
    resetCmtRegElements(); <%-- 먼저 진행 중인 댓글 등록 화면 요소 초기화 --%>
    resetRelyRegElements() ; <%-- 먼저 진행 중인 다른 답글 등록 화면 요소 초기화 --%>
    resetReplyModElements() ; <%-- 먼저 진행 중인 다른 답글 수정 화면 요소 초기화 --%>
	
<%--로그인 하지 않은 경우--%>/* 
	if(!loginUser) {
		//alert("로그인 후 수정이 가능합니다.") ;
		return ;
	} */
	
<%--로그인 계정과 작성자가 다른 경우--%>	/* 
	var rwriter = $(this).closest("li").data("rwriter") ;
	//alert("rwriter: " + rwriter) ;
	
	if (loginUser != rwriter) {
		//alert("작성자만 수정할 수 있습니다.") ;
		return ;
	} */

	
	$(this).parents("li").find(".btnChgReplyReg").attr("style", "display:none") ;
	var rcontent = $(this).text() ;
	var strTxtBoxReply =
		  "<textarea class='form-control txtBoxMod' name='rcontent' style='margin-bottom:10px;'"
		+ "         ></textarea>"
		+ "<button type='button' class='btn btn-warning btn-sm btnModCmt'>수정</button> "
		+ "<button type='button' class='btn btn-danger btn-sm btnDelCmt'>삭제</button>"
		+ "<button type='button' class='btn btn-info btn-sm btnCancelCmt' style='margin-left: 4px;'>취소</button>";
	
	$(this).after(strTxtBoxReply) ;
	$(".txtBoxMod").val(rcontent);
	$(this).attr("style", "display:none");
	
<%--예, div#id1 > div#id2 > ul > li > p : $("p").closest("div") : div#id2--%>
<%--예, div#id1 > div#id2 > ul > li > p : $("p").parents() : div#id1, div#id2, ul, li--%>
<%--예, div#id1 > div#id2 > ul > li > p : $("p").parents("div") : div#id1, div#id2--%>
<%--예, div#id1 > div#id2 > ul > li > p : $("p").parent() : li--%>
<%--	
//	잘못된 코드: $(this).closest(button): 선택된 p를 기준으로 p의 조상들(부모 포함) 중에 가장 가까운 button 을 찾음
//	$(this).closest("button").attr("style", "display:none") ; 
--%>
}) ;

<%-- 댓글-답글 수정, 클릭 이벤트 전파 이용 선택 --%>
<%-- ul.commentUL > li:nth-child(1) > div > div:nth-child(1) > button.btn.btn-warning.btn-sm.btnModCmt --%>
$(".commentUL").on("click", "li div div button.btnModCmt", function(){
	var rcontent = $(this).prev().val() ;
	var rno = $(this).closest("li").data("rno") ;
	var rwriter = $(this).closest("li").data("rwriter") ;
	
	var reply = {bno: bno, rno: rno, rcontent: rcontent, rwriter: rwriter} ;
	
	
	myCommentModule.modifyReply(
			reply,
			function(result){
				
				if (result == "modifySuccess") {
					alert("댓글(답글)이 수정되었습니다.") ;		
				} else {
					alert("서버 내부 오류로 댓글(답글) 수정이 취소되었습니다.") ;	
				}
						
				var _pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
				showCmtList(_pageNum) ;
			}
	
	);
});


<%-- 댓글-답글의 블라인드 처리, 클릭에 대한 이벤트 전파로 요소 선택 --%>
<%-- ul.commentUL > li:nth-child(1) > div > div:nth-child(1) > button.btn.btn-warning.btn-sm.btnModCmt --%>
$(".commentUL").on("click","li div div button.btnDelCmt", function(){
    if(!confirm("삭제하시겠습니까?")){
        return ;
    }
    var rno = $(this).closest("li.commentLi").data("rno") ;
    var reply ={bno: bno, rno: rno} ;
//    var rwriter = $(this).parents("li.commentLi").data("rwriter") ;
//    var reply ={bno: bno, rno: rno, rwriter: rwriter} ;

<%--
    myCommentModule.blindReply(--%><%--주석처리, 블라인드 처리 시 사용 됨 --%>
    myCommentModule.removeReply(<%-- 추가, 특정 댓글-답글 및 해당 자식 답글 모드 삭제 시 사용--%>
        reply,
        function(){
            alert("글이 삭제되었습니다.") ;
            var _pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
            showCmtList(_pageNum) ;
        }
    );
});


<%--댓글-답글의 수정-삭제를 위한 요소 초기화 함수 --%>
function resetReplyModElements() {
	$("p").attr("style", "display:in-block;white-space:pre;") ;
	$(".btnModCmt").remove() ;
	$(".btnDelCmt").remove() ;
	$(".btnCancelCmt").remove() ;
	$(".txtBoxMod").remove() ;
}

<%-- 댓글-답글 수정/삭제 취소 --%>
<%-- #chat > li:nth-child(1) > div > button.btn.btn-info.btn-sm.btnCancelCmt --%>
$(".commentUL").on("click","li div button.btnCancelCmt", function(){

	<%--서버로 요청이 전달됨--%><%--
	var _pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
	showCmtList(_pageNum) ; --%>
	
	<%--브라우저에서 처리(서버 요청 없음)--%>
	$(this).parents("li").find("button.btnChgReplyReg").attr("style","in-block" );
	$(this).parents("li").find("p").attr("style", "display:in-block;white-space:pre-wrap;") ;
	$(this).siblings(".btnModCmt").remove();
	$(this).siblings(".btnDelCmt").remove();
	$(this).siblings(".txtBoxMod").remove();
	$(this).remove() ;

});
	

</script>
<script>
$(document).ready(function(){
    runModal(result) ;
<%--    
    window.addEventListener("popstate", function (event) {
		history.pushState(null, null, location.href) ;
	}) ;
	
	history.pushState(null, null, location.href) ; --%>
	
    showCmtList(1) ;<%--댓글-답글 표시 --%>
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
<%-- mycomment.js 클로저 메서드 테스트 코드 --%>
<%--
//날짜형식 표시 함수 클로저 처리 테스트
console.log(myReplyClsr.myDateTimeFmt(1636701192000));

//게시물의 모든 댓글 삭제 클로저 처리 테스트
myReplyClsr.removeAllReply(
		229374 ,
		function (result){
			console.log(result) ;
		}
);

//댓글 삭제 클로저 처리 테스트
myReplyClsr.removeCmtReply(
	{bno: bnoValue, rno: 144} ,
	function(result) {
		console.log(result) ;
	}
);
//댓글 수정 클로저 처리 테스트
myReplyClsr.modifyCmtReply(
		{bno: bnoValue, rno: 1, rcontent:"JS클로저댓글답글수정---"} ,
		function(result) {
			console.log(result) ;
		}
);
//댓글 조회 클로저 처리 테스트
myReplyClsr.getCmtReply(
	{bno: bnoValue, rno: 1} ,
	function (reply) {
		console.log(reply) ;
	}
);
//답글 등록 클로저 처리 테스트
myReplyClsr.registerReply(
		{bno: bnoValue, rcontent: "JS댓글등록1", rwriter: "user7", prno: 1} ,
		function(result) {
			console.log("서버등록 결과: " + result) ;
		}
	);
//댓글 등록 클로저 처리 테스트
myReplyClsr.registerCmt(
	{bno: 229374, rcontent: "JS댓글등록2", rwriter: "user8"} ,
	function(result) {
		console.log("서버등록 결과: " + result) ;
	}
);

//댓글 목록 클로저 처리 테스트
myReplyClsr.getCmtList(
		{bno: bnoValue} ,
		function(myReplayPagingCreator){
			console.log(myReplayPagingCreator.replyTotCnt);
			for(var reply of myReplayPagingCreator.myreplyList) {
				console.log(reply) ;
				console.log(reply.rregDate);
			}
		}
);
--%>