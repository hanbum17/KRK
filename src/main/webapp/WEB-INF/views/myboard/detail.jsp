<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <!-- Spring Security 태그 라이브러리 추가 -->

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../include/header.jsp"%>

<style>
    th {
        text-align: center;
    }
    .fileUploadResult ul li {
        list-style: none;
    }
    .commentUL li {
        list-style: none;
    }
    .bigImageWrapper {
        position: absolute;
        display: none;
        justify-content: center;
        align-items: center;
        top: 0%;
        width: 100%;
        height: 100%;
        background-color: lightgray;
        z-index: 100;
    }
    .bigImage {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .bigImage img {
        height: 100%;
        max-width: 100%;
        width: auto;
        overflow: hidden.
    }
    .txtBoxCmt, .txtBoxComment {
        overflow: hidden;
        resize: vertical;
        min-height: 100px;
        color: black.
    }
    .btnSaveCmt, .btnCancelEditCmt {
        display: none;
        margin-left: 5px;
    }
    .commentLi {
        margin-left: 0;
    }
    .replyLi {
        margin-left: 40px; /* 답글 들여쓰기 */
    }
</style>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal.username" var="username"/>
</security:authorize>

<script>
    var username = '<c:out value="${username}" />';
</script>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header" style="white-space: nowrap;">
                Board - Detail:
                <c:out value="${myboard.bno}" />
                번 게시물
            </h3>
        </div>
    </div>
    <%-- 게시물 상세 표시 시작 --%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top: 11px;">
                            <strong style="font-size: 18px;">${myboard.bwriter}님 작성</strong>
                        </div>
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top: 16px;">
                            <span class="text-primary" style="font-size: smaller; height: 45px; padding-top: 19px;">
                                <span> <span>등록일:&nbsp;</span> <strong><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myboard.bregDate}" /></strong>
                                    <span>&nbsp;&nbsp;</span>
                            </span> <span>조회수:&nbsp;<strong><c:out value="${myboard.bviewsCnt}" /></strong></span>
                            </span>
                        </div>
                        <div class="col-md-6" style="height: 45px; padding-top: 6px;">
                            <div class="button-group pull-right">
                                <security:authorize access="isAuthenticated()">
                                    <c:if test="${username == myboard.bwriter }">
                                        <button type="button" id="btnToModify" data-oper="modify" class="btn btn-primary">
                                            <span>수정</span>
                                        </button>
                                    </c:if>
                                </security:authorize>
                                <button type="button" id="btnToList" data-oper="list" class="btn btn-info">
                                    <span>목록</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <%-- /.panel-heading --%>

                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">글제목</label>
                        <div class="col-sm-10">
                            <input class="form-control" name="btitle" value='<c:out value="${myboard.btitle}"/>' readonly="readonly" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">글내용</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" name="bcontent" style="resize: none;" readonly="readonly"><c:out value="${myboard.bcontent}" /></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="white-space: nowrap;">최종수정일</label>
                        <div class="col-sm-10">
                            <input class="form-control" name="bmodDate" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myboard.bmodDate}"/>' readonly="readonly" />
                        </div>
                    </div>
                </div>
                <%-- /.panel-body --%>
            </div>
            <%-- /.panel --%>
        </div>
        <%-- /.col-lg-12 --%>
    </div>
    <%-- /.row --%>

    <%-- 댓글 화면 표시 시작 --%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <p style="margin-bottom: 0px; font-size: 16px;">
                        <strong style="padding-top: 2px;"> <span>댓글&nbsp;<c:out value="${myboard.breplyCnt}" />개
                        </span> <span>&nbsp;</span>
                        <security:authorize access="isAuthenticated()">
                            <button type="button" id="btnChgCmtReg" class="btn btn-info btn-sm">댓글 작성</button>
                            <button type="button" id="btnRegCmt" class="btn btn-warning btn-sm" style="display: none">댓글 등록</button>
                            <button type="button" id="btnCancelRegCmt" class="btn btn-warning btn-sm" style="margin-left: 5px; display: none;">취소</button>
                        </security:authorize>
                        </strong>
                    </p>
                </div>
                <%-- /.panel-heading --%>

                <div class="panel-body">
                    <%-- 댓글 입력창 div 시작 --%>
                    <div class="form-group" style="margin-bottom: 5px;">
                        <textarea class="form-control txtBoxCmt" name="rcontent" placeholder="댓글작성을 원하시면,&#10;댓글 작성 버튼을 클릭해주세요." readonly="readonly"></textarea>
                    </div>
                    <hr style="margin-top: 10px; margin-bottom: 10px;">
                    <ul class="commentUL">
                        <%-- 댓글 목록 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
                    </ul>
                </div>
                <%-- 댓글 입력창 div 끝 --%>
                <div class="panel-footer text-center" id="showCmtPagingNums"></div>
            </div>
        </div>
    </div>
    <%-- /.row --%>
    
    <%-- 첨부파일 표시 끝 --%>

    <form id="frmSendValue">
        <%-- 폼을 추가 --%>
        <input type='hidden' name='bno' id="bno" value='<c:out value="${myboard.bno}"/>'> 
        <input type='hidden' name='pageNum' value='${myBoardPaging.pageNum}'>
        <input type='hidden' name='rowAmountPerPage' value='${myBoardPaging.rowAmountPerPage}'> 
        <input type='hidden' name='scope' value='${myBoardPaging.scope}'> 
        <input type='hidden' name='keyword' value='${myBoardPaging.keyword}'>
    </form>
</div>

<%-- 첨부파일 원본 이미지 표시 모달 --%>
<div class="modal fade" id="attachModal" tabindex="-1" role="dialog" aria-labelledby="attachModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" id="attachModal-body">처리가 완료되었습니다.</div>
        </div>
    </div>
</div>

<script>
	var csrfHeaderName = "${_csrf.headerName}";  
	var csrfTokenValue = "${_csrf.token}";
	
	$(document).ajaxSend(function(e,xhr, options){
		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
	});
</script>

<script>
    var frmSendValue = $("#frmSendValue");
    

    // 게시물 수정 페이지로 이동: 폼의 값을 전송해서 이동하는 형태로 변경
    $("#btnToModify").on("click", function() {
        frmSendValue.attr("action", "${contextPath}/myboard/modify");
        frmSendValue.attr("method", "get");
        frmSendValue.submit();
    });

    // 게시물 목록 페이지로 이동: 폼의 값을 전송해서 이동하는 형태로 변경
    $("#btnToList").on("click", function() {
        frmSendValue.find("#bno").remove(); // 목록화면 이동 시, bno 값 삭제
        frmSendValue.attr("action", "${contextPath}/myboard/list");
        frmSendValue.attr("method", "get");
        frmSendValue.submit();
    });

    var result = '<c:out value="${result}"/>';

    function checkModifyOperation(result) {
        if (result === '' || history.state) {
            return;
        } else if (result === 'successModify') {
            var myMsg = "글이 수정되었습니다";
        }

        alert(myMsg);
        myMsg = '';
    }

    checkModifyOperation(result);

    $(".attachLi").on("click", function() {
        var attachLi = $(this);
        var fileName = attachLi.data("repopath") + "/" + attachLi.data("uploadpath") + "/" + attachLi.data("uuid") + "_" + attachLi.data("file-name");
        var fileType = attachLi.data("file-type");

        if (fileType == "I") {
            $("#attachModal-body").html('<img src="${contextPath}/doFileDownloadAjax?fileName=' + encodeURI(fileName) + '" style="width: 100%"/>');
            $("#attachModal").modal("show");
        } else {
            location.href = "${contextPath}/doFileDownloadAjax?fileName=" + encodeURI(fileName);
        }
    });
</script>

<!--  댓글 처리 시작  -->
<script src="${contextPath}/resources/js/myreply.js"></script>
<script>
var commentUL = $(".commentUL");
var frmCmtPaingValue = $("#frmCmtPaingValue");
var bno = '<c:out value="${myboard.bno}"/>';
var pageNum = '<c:out value="${paging.pageNum}"/>' ;
var bwriter = '${myboard.bwriter}';
var username = '<c:out value="${username}"/>';

// 댓글 목록 및 페이징 번호 표시 함수
function showCmtPagingNums(rowTotal, pageNum, rowAmountPerPage) {
    var pagingNumCnt = 3; // 한 번에 표시할 페이징 번호의 수
    var endPagingNum = Math.ceil(pageNum / pagingNumCnt) * pagingNumCnt;
    var startPagingNum = endPagingNum - (pagingNumCnt - 1);
    var lastPagingNum = Math.ceil(rowTotal / rowAmountPerPage);

    if (lastPagingNum < endPagingNum) {
        endPagingNum = lastPagingNum;
    }

    var prev = startPagingNum > 1;
    var next = endPagingNum < lastPagingNum;

    var pagingShowHTML
        = '<div style="text-align:center;">'
        + '<ul class="pagination pagination-sm">';

    if (prev) {
        pagingShowHTML
            += '<li class="paginate_button previous" tabindex="0">'
            + '<a href="1"><span aria-hidden="true">&laquo;</span></a>'
            + '</li>'
            + '<li class="paginate_button previous" tabindex="0">'
            + '<a href="' + (startPagingNum - 1) + '">이전</a>'
            + '</li>';
    }

    for (var i = startPagingNum; i <= endPagingNum; i++) {
        var active = (pageNum == i) ? "active" : "";
        pagingShowHTML
            += '<li class="paginate_button ' + active + '" tabindex="0">'
            + '<a href="' + i + '">' + i + '</a>'
            + '</li>';
    }

    if (next) {
        pagingShowHTML
            += '<li class="paginate_button next" tabindex="0">'
            + '<a href="' + (endPagingNum + 1) + '">다음</a>'
            + '</li>'
            + '<li class="paginate_button next" tabindex="0">'
            + '<a href="' + lastPagingNum + '"><span aria-hidden="true">&raquo;</span></a>'
            + '</li>';
    }

    pagingShowHTML
        += '</ul>'
        + '</div>';

    $("#showCmtPagingNums").html(pagingShowHTML);
}

// 선택된 페이징 번호 클릭 시, 댓글목록 가져오는 함수: 이벤트 전파 이용
$("#showCmtPagingNums").on("click", "div ul li a", function (e) {
    e.preventDefault();
    var pageNum = $(this).attr("href");
    showCmtList(pageNum);
});

//댓글 목록 및 페이징 번호 표시 함수
function showCmtList(pageNum) {
    myCommentModule.getReplyList(
        { bno: bno, pageNum: pageNum },
        function (myReplyPagingCreator) {
            var resultHTML = "";
            for (var myReply of myReplyPagingCreator.myReplyList) {
                var indentClass = myReply.prno ? "replyLi" : "commentLi"; // 들여쓰기 클래스 적용
                resultHTML
                    += '<li class="left clearfix ' + indentClass + '" data-bno="' + myReply.bno + '" data-rno="' + myReply.rno + '" data-prno="' + (myReply.prno || '') + '">' // 수정
                    + '<div>'
                    + '<div>'
                    + '<span class="header info-rwriter">'
                    + '<strong class="primary-font">' + myReply.rwriter + '</strong>'
                    + '<span>&nbsp;</span>'
                    + '<small class="text-muted">' + myReply.regDate + '</small>'
                    + '</span>'
                    + '<p>' + myReply.rcontent + '</p>'
                    + '</div>'
                    + '</div>'
                    + '<div class="btnsComment" style="margin-bottom:10px">'
                    + '<security:authorize access="isAuthenticated()">'
                    + '<button type="button" class="btn btn-primary btn-xs btnChgReg">답글 작성</button>'
                    + '<button type="button" class="btn btn-warning btn-xs btnRegCmt" style="display:none">답글 등록</button>'
                    + '<button type="button" class="btn btn-warning btn-sm btnCancelRegCmt" style="display: none">취소</button>';
                    + '</security:authorize>'

                if (username === myReply.rwriter) {
                    resultHTML += '<button type="button" class="btn btn-danger btn-xs btnDelCmt" style="margin-left:5px;">삭제</button>'
                        + '<button type="button" class="btn btn-secondary btn-xs btnBlindCmt" style="margin-left:5px;">블라인드</button>'
                        + '<button type="button" class="btn btn-info btn-xs btnEditCmt" style="margin-left:5px;">수정</button>'
                        + '<button type="button" class="btn btn-success btn-xs btnSaveCmt" style="display:none; margin-left:5px;">수정 완료</button>'
                        + '<button type="button" class="btn btn-warning btn-xs btnCancelEditCmt" style="display:none; margin-left:5px;">취소</button>';
                }

                resultHTML += '<hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px">'
                    + '<textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-top:10px; display:none" placeholder="댓글 작성을 원하시면,&#10;댓글 작성 버튼을 클릭해주세요."></textarea>'
                    + '</div>'
                    + '</li>';
            }

            commentUL.html(resultHTML);
            showCmtPagingNums(myReplyPagingCreator.rowTotal,
                myReplyPagingCreator.myReplyPaging.pageNum,
                myReplyPagingCreator.myReplyPaging.rowAmountPerPage);
        },
        function (err) {
            console.log("err: \n" + err);
        }
    );
}


$("#btnChgCmtReg").on("click", function () {
    $(this).attr("style", "display: none;");
    $("#btnRegCmt").attr("style", "display:in-block; margin-right: 2px");
    $("#btnCancelRegCmt").attr("style", "display:in-block;");
    $(".txtBoxCmt").attr("placeholder", "").attr("readonly", false);
});

$("#btnCancelRegCmt").on("click", function () {
    $("#btnChgCmtReg").attr("style", "display:in-block;");
    $("#btnRegCmt").attr("style", "display:none");
    $("#btnCancelRegCmt").attr("style", "display:none;");
    $(".txtBoxCmt").val("")
        .attr("readonly", true)
        .attr("placeholder", "댓글 작성을 원하시면, \n댓글 작성 버튼을 클릭해주세요.");
});

//댓글 작성 버튼 클릭 이벤트
$("#btnRegCmt").on("click", function () {
    if (username === bwriter) {
        alert("자신의 글에는 댓글을 달 수 없습니다.");
        return;
    }
    var rcontent = $(".txtBoxCmt").val();
    var comment = { bno: bno, rcontent: rcontent, rwriter: username };
    myCommentModule.registerComment(comment, function (result) {
        if (result != null) {
            alert('댓글이 등록되었습니다.');
            showCmtList(1);
        } else {
            alert('댓글 등록이 취소되었습니다.');
        }
    });
});

$("#showCmtPagingNums").on("click", "div ul li a", function (e) {
    e.preventDefault();
    var pageNum = $(this).attr("href");
    showCmtList(pageNum);
});

$(document).ready(function () {
    showCmtList(1);

    // 답글 작성 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnChgReg", function () {
        $(this).hide();
        $(this).siblings(".btnRegCmt").show();
        $(this).siblings(".btnCancelRegCmt").show();
        $(this).siblings(".txtBoxCmtMod").show().focus();
    });

    // 답글 작성 취소 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnCancelRegCmt", function () {
        $(this).hide();
        $(this).siblings(".btnRegCmt").hide();
        $(this).siblings(".btnChgReg").show();
        $(this).siblings(".txtBoxCmtMod").hide();
    });

    // 답글 등록 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnRegCmt", function () {
        var rcontent = $(this).siblings(".txtBoxCmtMod").val();
        var rwriter = username; // 로그인한 사용자 정보로 대체
        var bno = $(this).closest("li").data("bno");
        var prno = $(this).closest("li").data("rno");
        var reply = { bno: bno, rcontent: rcontent, rwriter: rwriter, prno: prno };

        console.log("Register Reply: bno = " + bno + ", prno = " + prno);

        myCommentModule.registerReply(reply, function (result) {
            if (result === 'success') {
                alert('답글이 등록되었습니다.');
                showCmtList(1);
            }
        }, function (err) {
            alert('답글 등록에 실패했습니다.');
            console.log(err);
        });
    });

    // 댓글/답글 수정 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnEditCmt", function () {
    	$(this).hide();
        var $textarea = $(this).siblings(".txtBoxCmtMod");
        var currentContent = $(this).closest("li").find("p").text();
        $textarea.val(currentContent).show().focus();
        $(this).siblings(".btnSaveCmt").show();
        $(this).siblings(".btnCancelEditCmt").show();
    });

    // 수정 저장 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnSaveCmt", function () {
        var rcontent = $(this).siblings(".txtBoxCmtMod").val();
        var bno = $(this).closest("li").data("bno");
        var rno = $(this).closest("li").data("rno");
        var comment = { bno: bno, rno: rno, rcontent: rcontent };

        myCommentModule.updateComment(comment, function (result) {
            if (result === 'success') {
                showCmtList(1);
            }
        }, function (err) {
            alert('댓글 수정에 실패했습니다.');
            console.log(err);
        });
    });

    // 수정 취소 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnCancelEditCmt", function () {
    	$(this).siblings(".btnEditCmt").show();
    	$(this).siblings(".txtBoxCmtMod").hide();
    	$(this).siblings(".btnSaveCmt").hide();
    	$(this).hide();
    });
    

 // 댓글 삭제 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnDelCmt", function () {
        var bno = $(this).closest("li").data("bno");
        var rno = $(this).closest("li").data("rno");
        if (confirm("댓글을 삭제하시겠습니까?")) {
            myCommentModule.deleteComment(bno, rno, function (result) {
                if (result === 'success') {
                    alert('댓글이 삭제되었습니다.');
                    showCmtList(1);
                }
            }, function (err) {
                alert('댓글 삭제에 실패했습니다.');
                console.log(err);
            });
        }
    });

    // 댓글 블라인드 처리 버튼 클릭 이벤트
    $(".commentUL").on("click", ".btnBlindCmt", function () {
        var bno = $(this).closest("li").data("bno");
        var rno = $(this).closest("li").data("rno");
        if (confirm("댓글을 블라인드 처리하시겠습니까?")) {
            myCommentModule.blindComment(bno, rno, function (result) {
                if (result === 'success') {
                    alert('댓글이 블라인드 처리되었습니다.');
                    showCmtList(1);
                }
            }, function (err) {
                alert('댓글 블라인드 처리에 실패했습니다.');
                console.log(err);
            });
        }
    });
});

$(document).ready(function () {
    showCmtList(1);
});

</script>



<%@ include file="../include/footer.jsp"%>
