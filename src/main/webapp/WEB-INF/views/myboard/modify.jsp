<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ include file="../include/header.jsp" %>



<style>
	
    .fileUploadResult ul li { list-style: none; }

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
	                                <input class="form-control" id="btitle" name="btitle" value='<c:out value="${myboard.btitle }"/>'>
	                            </div>
	                            <div class="form-group">
	                                <label>글내용</label>
	                                <textarea class="form-control" rows="3" id="bcontent" name="bcontent"><c:out value="${myboard.bcontent }"/></textarea>
	                            </div>
	                            <div class="form-group">
	                                <label>작성자</label>
	                                <input class="form-control" id="bwriter" name="bwriter" value='<c:out value="${myboard.bwriter }"/>' readonly="readonly">
	                            </div>

	                            
	                            
	                            <button type="button" class="btn btn-primary" id="btnModify" data-oper="modify">수정</button>
	                            <button type="button" class="btn btn-danger" id="btnDelete" data-oper="deleteFlag">삭제</button>
	                            <button type="button" class="btn btn-warning" id="btnToList" data-oper="list">목록</button>
	   
	                            <input type="hidden" value='<c:out value="${myboard.bno }"/>' name="bno">
								<input type='hidden' name='pageNum' value='${myBoardPaging.pageNum}'>
							    <input type='hidden' name='rowAmountPerPage' value='${myBoardPaging.rowAmountPerPage}'>
							    <input type="hidden" id="scope" name="scope" value="${myBoardPaging.scope }">
   	 							<input type="hidden" id="keyword" name="keyword" value="${myBoardPaging.keyword }">
   	 							<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}">
   	 							
	                            
                            </form><!-- /.table-responsive -->
                            
                        </div><!-- /.panel-body -->
                        
                    </div><!-- /.panel -->
                    
                </div><!-- /.col-lg-12 -->
                
            </div><!-- /.row -->




<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>첨부 파일</h4>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group uploadDiv">
                    <input type="file" class="btn btn-primary fileInput"
                        id="fileInput" name="fileInput" multiple>
                </div>
                <div class="fileUploadResult">
                    <ul class="existingFiles">
                        <c:choose>
                            <c:when test="${empty myboard.attachFileList }">
                                <li style="font-size: 12pt;">첨부파일이 없습니다.</li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="attachFile" items="${myboard.attachFileList }">
                                    <c:choose>
                                        <c:when test="${attachFile.fileType =='F' }">
                                            <li class="attachLi" data-repopath="${attachFile.repoPath }"
                                                data-uploadpath="${attachFile.uploadPath }"
                                                data-uuid="${attachFile.uuid }"
                                                data-filename="${attachFile.fileName }"
                                                data-filetype="${attachFile.fileType }"><img
                                                src='${contextPath}/resources/icons/icon-attach.png'
                                                style='width: 50px;' /> &emsp; ${attachFile.fileName}
                                                <span class='glyphicon glyphicon-remove-sign' data-filename='${attachFile.fileName}'
                                                data-filetype='F' style='color:red;'></span>
                                            </li>
                                        </c:when>
                                        <c:when test="${attachFile.fileType =='I' }">
                                            <c:set var="thumbnail"
                                                value="${attachFile.repoPath}/${attachFile.uploadPath }/s_${attachFile.uuid }_${attachFile.fileName }" />
                                            <li class="attachLi" data-repopath="${attachFile.repoPath }"
                                                data-uploadpath="${attachFile.uploadPath }"
                                                data-uuid="${attachFile.uuid }"
                                                data-filename="${attachFile.fileName }"
                                                data-filetype="${attachFile.fileType }"><img
                                                src="${contextPath}/displayThumbnail?fileName=${thumbnail}"
                                                style='width: 50px;' /> &emsp; ${attachFile.fileName}
                                                <span class='glyphicon glyphicon-remove-sign' data-filename='${attachFile.fileName}'
                                                data-filetype='I' style='color:red;'></span>
                                            </li>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                    <ul class="newFiles"></ul>
                </div>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->


</div><!-- /#page-wrapper -->

        
<script>

function checkValues() {
	var btitle = document.getElementById("btitle").value;
	var bcontent = document.getElementById("bcontent").value;
	var bwriter = document.getElementById("bwriter").value;
	
	//정규표현식: 빈칸만 입력된 패턴 금지
	var regExp = /^\s+$/;
	var result = regExp.test(btitle);
	
	if(!btitle || !bcontent || !bwriter || regExp.test(bcontent)){
		//alert("글제목, 글내용, 작성자를 모두 유효한 값으로 입력하세요.");
		return false;
	}
	return true;
}





var frmModify = $("#frmModify");

$('button').on("click", function() {
    var operation = $(this).data("oper");  // 각 버튼의 data-xxx 속성에 설정된 값을 저장

    if (!checkValues()) {
        alert("글제목, 글내용, 작성자를 모두 유효한 값으로 입력하세요.");
        return;
    }

    var attachFileInputHTML = "";

    // 기존 파일 처리
    $(".existingFiles li").each(function(i, objLi){
        var attachLi = $(objLi);

        attachFileInputHTML
        += "<input type='hidden' name='attachFileList[" + i + "].uuid' value='" + attachLi.data("uuid") + "'>"
        + "<input type='hidden' name='attachFileList[" + i + "].uploadPath' value='" + attachLi.data("uploadpath") + "'>"
        + "<input type='hidden' name='attachFileList[" + i + "].fileName' value='" + attachLi.data("filename") + "'>"
        + "<input type='hidden' name='attachFileList[" + i + "].fileType' value='" + attachLi.data("filetype") + "'>";
    });

    // 새 파일 처리
    $(".newFiles li").each(function(i, objLi){
        var attachLi = $(objLi);

        attachFileInputHTML
        += "<input type='hidden' name='attachFileList[" + ($(".existingFiles li").length + i) + "].uuid' value='" + attachLi.data("uuid") + "'>"
        + "<input type='hidden' name='attachFileList[" + ($(".existingFiles li").length + i) + "].uploadPath' value='" + attachLi.data("uploadpath") + "'>"
        + "<input type='hidden' name='attachFileList[" + ($(".existingFiles li").length + i) + "].fileName' value='" + attachLi.data("filename") + "'>"
        + "<input type='hidden' name='attachFileList[" + ($(".existingFiles li").length + i) + "].fileType' value='" + attachLi.data("filetype") + "'>";
    });

    if (attachFileInputHTML) {
        frmModify.append(attachFileInputHTML);
    }

    if(operation == "modify"){  // 게시물 수정 요청
        frmModify.attr("action", "${contextPath}/myboard/modify").attr("method","post");
    } else if(operation == "deleteFlag"){  // 게시물 삭제 요청
        frmModify.attr("action", "${contextPath}/myboard/deleteFlag").attr("method","post"); // 실제 삭제
    } else if(operation == "list"){  // 게시물 목록 화면 요청
        frmModify.attr("action","${contextPath}/myboard/list").attr("method","get");
        // 페이징 데이터의 input 요소 복사
        var pageNumInput = $("input[name='pageNum']").clone();
        var rowAmountInput = $("input[name='rowAmountPerPage']").clone();
        var scopeInput = $("#scope").clone(); 
        var keywordInput = $("#keyword").clone();

        frmModify.empty(); // form의 모든 자식 요소 삭제
        // 복사된 페이징 데이터가 저장된 input 요소를 다시 form에 추가
        frmModify.append(pageNumInput);
        frmModify.append(rowAmountInput);
        frmModify.append(scopeInput);
        frmModify.append(keywordInput);
    } else {
        alert("유효하지 않은 요청입니다");
        return;
    }

    frmModify.submit();
});

function checkValues() {
    var btitle = document.getElementById("btitle").value;
    var bcontent = document.getElementById("bcontent").value;
    var bwriter = document.getElementById("bwriter").value;

    var regExp = /^\s+$/;

    if(!btitle || !bcontent || !bwriter || regExp.test(bcontent)){
        return false;
    }
    return true;
}

var _csrfHeaderName = "${_csrf.headerName}"; 
var _csrfToken = "${_csrf.token}";

//파일 업로드 처리
$('.uploadDiv').on("change", "input[type='file']", function() {
    var fileInput = $(this);
    var uploadFiles = fileInput[0].files;

    var formData = new FormData();
    
    for (var i = 0; i < uploadFiles.length; i++) {
        if (!checkUploadFile(uploadFiles[i].name, uploadFiles[i].size)) {
            console.log("파일이름: " + uploadFiles[i].name);
            console.log("파일용량: " + uploadFiles[i].size);
            $('#fileInput').val("");
            return;
        }
        formData.append("uploadFiles", uploadFiles[i]);
    }

    $.ajax({
        type: "post", // POST 메서드 사용
        url: "${contextPath}/doFileUploadByAjax",
        data: formData,
        contentType: false, 
        processData: false, 
        dataType: "json",
        beforeSend: function (xhr) {
        xhr.setRequestHeader(_csrfHeaderName, _csrfToken);
        },
        success: function(uploadResult, status){
            console.log(uploadResult);
            $('#fileInput').val("");
            showUploadResult(uploadResult);
        }
    });
});


// 파일 삭제: 서버에 업로드된 파일이 삭제되고, 이를 화면에 반영해 주어야 함
$(".fileUploadResult ul").on("click","li span", function(){
    var fileLi = $(this).parent();
    var fileName = $(this).data("filename");
    var fileType = $(this).data("filetype");

    $.ajax({
        type: "delete",
        url: "${contextPath}/deleteFile",
        data: {fileName: fileName, fileType: fileType},
        dataType: "text",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(_csrfHeaderName, _csrfToken);
        },
        success: function(result){
            if(result == "DelSuccess") {
                alert("파일이 삭제되었습니다.");
                fileLi.remove();
            } else {
                if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까?")) {
                    fileLi.remove();
                    alert("파일이 삭제되었습니다.");
                }
            }
        }
    });
});

// 업로드 결과 표시 함수
function showUploadResult(uploadResult) {
    var htmlStr = "";
    var fileUploadResultUL = $(".newFiles"); 

    $(uploadResult).each(function(i, attachFile) {
        var fullFileName = encodeURI(attachFile.repoPath + "/" +
                                    attachFile.uploadPath + "/" +
                                    attachFile.uuid + "_" + attachFile.fileName);

        if (attachFile.fileType == "F") {
            htmlStr 
                += "<li data-uploadpath='" + attachFile.uploadPath + "'"
                + " data-uuid='" + attachFile.uuid + "'"
                + " data-filename='"+attachFile.fileName + "'"
                + " data-filetype='F'>" 
                + "<img src='${contextPath}/resources/icons/icon-attach.png' style='width:30px;'/>"
                + "&emsp;" + attachFile.fileName
                + "<span class='glyphicon glyphicon-remove-sign' data-filename='" + fullFileName + "'"
                + " data-filetype='F' style='color:red;'></span>"
                + "</li>";
        } else {    
            var thumbnail = encodeURI(attachFile.repoPath + "/" +
                                      attachFile.uploadPath + "/s_" +
                                      attachFile.uuid + "_" +
                                      attachFile.fileName);

            htmlStr 
                += "<li data-uploadpath='" + attachFile.uploadPath + "'"
                + " data-uuid='" + attachFile.uuid + "'"
                + " data-filename='"+attachFile.fileName + "'"
                + " data-filetype='I'>"
                + "<img src='${contextPath}/displayThumbnail?fileName=" + thumbnail + "'>"
                + "&emsp; " + attachFile.fileName
                + "<span class='glyphicon glyphicon-remove-sign ' data-filename='" + thumbnail + "'"
                + " data-filetype='I' style='color:red;'></span>"
                + "</li>";
        }
    });

    fileUploadResultUL.append(htmlStr);
}

// 파일 크기 및 확장자 제한 검사 함수
function checkUploadFile(fileName, fileSize){
    var allowedMaxSize = 1048576; 
    var regExpForbiddenFile = /(^\.[^.]+$|^[^.]+$|\.(exe|sh|c|dll|alz|zip|tar|7z)$)/i;

    if (fileSize > allowedMaxSize) {
        alert("업로드 파일의 크기는 1MB보다 작아야 합니다.");
        return false;
    }

    if (regExpForbiddenFile.test(fileName)) {
        alert("선택하신 파일은 업로드 할 수 없는 유형입니다.");
        return false;
    }

    return true;
}

	
</script>

<!-- [수정] 버튼을 누르면 수정된 내용이 컨트롤러로 보내지고 detail로 이동 -->
<!-- [삭제] 버튼을 누르면 해당 게시물의 bdelFlag값을 1로 변경하고 list로 이동 -->

<%@ include file="../include/footer.jsp" %>
