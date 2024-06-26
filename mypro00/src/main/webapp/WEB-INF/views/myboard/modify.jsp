<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

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
<div class="panel-heading">
    <h4 style="display:inline-block;white-space: nowrap;" >No. <c:out value="${myBoard.bno}"/>-글 수정</h4>
</div><%-- /.panel-heading --%>
                        <div class="panel-body">

<form role="form" id="frmModify" method="post" >
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">글제목</label>
        <input class="form-control" id="btitle" name="btitle"  value='<c:out value="${myBoard.btitle }"/>'>
    </div>
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">글내용</label>
        <textarea class="form-control" id="bcontent" name="bcontent" rows="3" ><c:out value="${myBoard.bcontent }"/></textarea>
    </div>
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">작성자</label>
        <input class="form-control" id="bwriter" name="bwriter" value='<c:out value="${myBoard.bwriter }"/>' readonly="readonly">
    </div>
    <div class="form-group">
        <label class="control-label" style="white-space: nowrap;">최종수정일</label> [등록일시: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${myBoard.bregDate}"/>]
        <input class="form-control" name="bmoddate" 
               value='<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${myBoard.bmodDate}"/>'
               readonly="readonly" />
    </div>
        
    <button type="button" class="btn btn-primary" id="btnModify" data-oper="modify">수정</button>
    <button type="button" class="btn btn-danger" id="btnRemove" data-oper="remove">삭제</button>
    <button type="button" class="btn btn-warning" id="btnToList" data-oper="toList">목록</button>
    <input type="hidden" value="${myBoard.bno }" name="bno"/>
    <input type="hidden" id="pageNum" name="pageNum" value="${myBoardPaging.pageNum }">
    <input type="hidden" id="rowAmountPerPage" name="rowAmountPerPage" value="${myBoardPaging.rowAmountPerPage }">
    <input type="hidden" id="scope" name="scope" value="${myBoardPaging.scope }">
    <input type="hidden" id="keyword" name="keyword" value="${myBoardPaging.keyword }">
    <input type="hidden" id="beginDate" name="beginDate" value="${myBoardPaging.beginDate }">
    <input type="hidden" id="endDate" name="endDate" value="${myBoardPaging.endDate }">
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
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
                            <div class="form-group uploadDiv">
                                <input type="file" class="btn btn-primary fileInput" id="fileInput"  name="fileInput" multiple >
                            </div>
                            <div class="form-group fileUploadResult">
                                <ul>
<c:choose>
<c:when test="${empty myBoard.attachFileList }">
    <li style="font-size: 12pt;">첨부파일이 없습니다.</li>
</c:when>
<c:otherwise>
    <c:forEach var="attachFile" items="${myBoard.attachFileList }">
    <c:set var="fullFileName" value="${attachFile.repoPath}/${attachFile.uploadPath}/${attachFile.uuid}_${attachFile.fileName}"/>
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
				        <span class='glyphicon glyphicon-remove-sign' data-filename='${fullFileName}'
                              data-filetype='F' style='color:red;'></span>
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
				        <span class='glyphicon glyphicon-remove-sign' data-filename='${thumbnail}'
                              data-filetype='I' style='color:red;'></span>
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
        </div><%-- /#page-wrapper --%>

<script>
function checkValues(){
    var btitle = document.getElementById("btitle").value ;
    var bcontent = document.getElementById("bcontent").value ;
    var bwriter = document.getElementById("bwriter").value ;
	
    var regExp = /^\s+$/ ;
  
    if( !btitle || !bcontent || !bwriter || regExp.test(btitle) || regExp.test(bcontent) ){
    	//alert("글제목/글내용/작성자를 모두 유효한 값으로 입력해야 합니다.");
    	return false ;
    } 
    return true ;
}

$("button").on("click", function(){
	
	var operation = $(this).data("oper") ;
	
	var frmModify = $("#frmModify") ;
	
	if (operation == "toList") {
		
		var pageNumInput = $("input[id='pageNum']").clone() ;
		var rowAmountInput = $("#rowAmountPerPage").clone() ;
		var scopeInput = $("#scope").clone() ;
		var keywordInput = $("#keyword").clone() ;
		var beginDateInput = $("#beginDate").clone() ;
		var endDateInput = $("#endDate").clone() ;
		
		frmModify.empty() ;
		
		frmModify.append(pageNumInput);
		frmModify.append(rowAmountInput);
		frmModify.append(scopeInput) ;
		frmModify.append(keywordInput) ;
		frmModify.append(beginDateInput) ;
		frmModify.append(endDateInput) ;
		
		//frmModify.attr("action","${contextPath}/myboard/list" ) ;
		//frmModify.attr("method", "get") ;
		
		frmModify.attr("action","${contextPath}/myboard/list" ).attr("method", "get") ;
		
	} else if (operation == "modify") {
		
		if (!checkValues()){
			alert("글제목/글내용/작성자를 모두 유효한 값으로 입력해야 합니다.");
	    	return ;
		}
		
		var attachFileInputHTML = "" ;
		
		//li가 없으면
		//첨부파일 없음 Li가 하나 있음
        $(".fileUploadResult ul li").each(function(i, objLi) {
			
			var attachLi = $(objLi);
			
			if (attachLi.data("filename") == undefined) {
				attachLi.remove() ;
				return false ;
			}
			
			attachFileInputHTML
			+= "<input type='hidden' name='attachFileList[" + i + "].uuid' value='" + attachLi.data("uuid") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].uploadPath' value='" + attachLi.data("uploadpath") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].fileName' value='" + attachLi.data("filename") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].fileType' value='" + attachLi.data("filetype") + "'>" ;
			
		});
		
		//alert(attachFileInputHTML) ;
		
	
		if (attachFileInputHTML) {
			frmModify.append(attachFileInputHTML) ;	
		}
		
		
		frmModify.attr("action", "${contextPath}/myboard/modify");
		
	} else { //operation = remove
		
		//frmModify.attr("action", "${contextPath}/myboard/delete");
		frmModify.attr("action", "${contextPath}/myboard/remove");
	}
	
	frmModify.submit() ;
	
});

<%-- 첨부파일 처리 시작 --%>
<%-- 업로드 결과 표시 함수 --%>
var fileUploadResultUL = $(".fileUploadResult ul") ;

var myCsrfHeaderName = "${_csrf.headerName}" ;
var myCsrfToken = "${_csrf.token}" ;
$(document).ajaxSend(function(e, xhr){
	xhr.setRequestHeader(myCsrfHeaderName, myCsrfToken) ;
		
});

function showUploadResult(uploadResult){
	
	var fileLi = $(".fileUploadResult ul li") ;
	
	if (!fileLi.data("filename")) {
	//if (fileLi.data("filename") == undefined) {
	
		fileLi.remove() ;
	}

	
	var htmlStr = "";
	
//	if(uploadResult == null || uploadResult.length == 0) {
//	//if(!uploadResult) { //동작 않함
//		htmlStr = "<li>첨부파일이 없습니다.</li>" ;
//		fileUploadResultUL.html(htmlStr) ;
//		return ;
//	}
	
	var fullFileName = null ;
	
	<%-- 전달받은 파일들의 정보 각각에 대하여 --%>
	$(uploadResult).each(function(i, attachFile){
		
	    fullFileName = encodeURI(attachFile.repoPath + "/" +
                                 attachFile.uploadPath + "/" +
                                 attachFile.uuid + "_" + attachFile.fileName) ;	
	
		if(attachFile.fileType == "F") {
			htmlStr
			+="<li data-uploadpath='" + attachFile.uploadPath + "'" 
			+ "    data-uuid='" + attachFile.uuid + "'" 
			+ "    data-filename='" + attachFile.fileName + "'" 
			+ "    data-filetype='F'>"
			+ "        <img src='${contextPath}/resources/icons/icon-attach.png' style='width:50px;'/>"
			+ "        &emsp;" + attachFile.fileName
			+ "        <span class='glyphicon glyphicon-remove-sign' data-filename='" + fullFileName + "'"
            + "              data-filetype='F' style='color:red;'></span>"
			+ "</li>"
		} else {
			var thumbnail = encodeURI(attachFile.repoPath + "/" +
			                          attachFile.uploadPath + "/s_" +
			                          attachFile.uuid + "_" + attachFile.fileName) ;
			htmlStr
			+="<li data-uploadpath='" + attachFile.uploadPath + "'" 
			+ "    data-uuid='" + attachFile.uuid + "'" 
			+ "    data-filename='" + attachFile.fileName + "'" 
			+ "    data-filetype='I'>"
			+ "        <img src='${contextPath}/displayThumbnail?thumbnail=" + thumbnail + "' style='width:50px;'/>"
			+ "        &emsp;" + attachFile.fileName
            + "        <span class='glyphicon glyphicon-remove-sign ' data-filename='" + thumbnail + "'"
            + "          data-filetype='I' style='color:red;'></span>"
			+ "</li>"
		}
		
	});
	

	//fileUploadResultUL.html(htmlStr) ;
	fileUploadResultUL.append(htmlStr) ;

}



<%-- 파일 크기 및 확장자 제한 검사 함수 --%>
function checkUploadFile(fileName, fileSize){
	
	var allowedMaxSize = 1048576 ;
	var regExpForbiddenFile = /(^\.[^.]+$|^[^.]+$|\.(exe|sh|c|dll|alz|zip|tar|7z)$)/i;
	
	if (fileSize > allowedMaxSize) {
		alert("업로드 파일의 크기는 1MB보다 작아야 합니다.") ;
		return false;
	}
	
	if (regExpForbiddenFile.test(fileName)) {
		alert("선택하신 파일은 업로드 할 수 없는 유형입니다.") ;
		return false ;
	}
	
	return true ;
	
}

//input 초기화를 위해 div 요소의 비어있는 input 요소를 복사해서 저장함.
var cloneFileInput = $(".uploadDiv").clone();         
//clone(): 선택된 요소가 자식 요소를 가지고 있으면, 자식요소가 복사됨
//clone(): 선택된 요소가 자식 요소를 가지고 있지 않으면, 선택된 요소가 복사됨
//console.log("cloneFileInput: " + cloneFileInput.html()) ;
//<input type="file" class="fileInput" id="fileInput" name="fileInput"  multiple >
<%-- 업로드 처리, 파일 input 요소의 "내용이 바뀌면" 업로드가 수행되도록 수정 --%>
<%-- 업로드 후, 복사된 파일input으로 파일input을 초기화(교체) -> 이벤트 위임으로 요소를 선택해야 함 --%>
//
$(".uploadDiv").on("change", "input[type='file']", function(){
	
	var fileInput = $("input[name='fileInput']") ;<%--
    var fileInput = $(this) ; --%>  //잘 동작하지 않음

    var uploadFiles = fileInput[0].files ;//반드시 [0]을 붙여야 함
    console.log(uploadFiles) ;
    

    <%-- Ajax를 이용한 파일 업로드는, 웹 브라우저의 FormData() 내장 생성자를 이용하여 전송 --%>
    var formData = new FormData() ;
    
    for(var i = 0 ; i < uploadFiles.length ; i++) {
    	
    	if(!checkUploadFile(uploadFiles[i].name, uploadFiles[i].size)){
    		console.log("파일이름: " + uploadFiles[i].name) ;
    		console.log("파일크기: " + uploadFiles[i].size) ;
    		$("#fileInput").val("") ;
    		return ;	
    	}
    	formData.append("uploadFiles", uploadFiles[i]) ;
    	
    }
    
    $.ajax({
    	type: "post" ,
    	url : "${contextPath}/doFileUploadByAjax" ,
    	data: formData ,
    	contentType: false ,  //contentType에 전송타입(즉, MIME 타입)을 지정하지 않음.
    	processData: false ,  //contentType에 설정된 형식으로 data를 변환처리가 수행되지 않음
    	dataType: "json" ,
    	success: function(uploadResult, status){
    		console.log(uploadResult)  ;
    		$(".uploadDiv").html(cloneFileInput.html()) ;
    		//$("#fileInput").val("") ;
    		showUploadResult(uploadResult) ;
    	}
    });

}) ;


// div.form-group.fileUploadResult > ul > li:nth-child(2)
<%-- 파일 삭제: 화면에 표시되는 파일 정보만 삭제(사용자의 수정 취소 대비) --%>
<%-- 이벤트 위임(Event Delegation) 적용 div.fileUploadResult > ul > li:nth-child(1) > span --%>
$(".fileUploadResult ul").on("click", "li span" , function(){
    var fileName = $(this).data("filename") ;
    var fileType = $(this).data("filetype") ;
    
    
    //var fileLi = $(this).parent() ;
    var fileLi = $(this).closest("li") ;
    
    if(confirm("선택한 파일을 삭제하시겠습니까?")) {
		fileLi.remove() ;
		
	} else {
		if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까?")) {
			fileLi.remove() ;
		}
	}
    
//	var parentUL = $(".fileUploadResult ul") ;
//	console.log(parentUL.children("li").length);
//	alert(parentUL.children("li").length);
//	if (parentUL.children("li").length == 0){
//		parentUL.append("<li style='font-size: 12pt;'>첨부파일이 없습니다</li>") ;
//	}
    
}) ;


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