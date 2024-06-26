<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../include/header.jsp"%>



<style>
th {
	text-align: center;
}
</style>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">Board - Register</h3>
		</div>
		<!-- /.col-lg-12 -->

	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>게시물 등록</h4>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<form id="frmRegister" role="form" method="post"
						action="${contextPath}/myboard/register" name="frmBoard">
						<div class="form-group">
							<label>제목</label> <input class="form-control" id="btitle"
								name="btitle" placeholder="글제목을 입력하세요.">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" id="bcontent"
								name="bcontent" placeholder="글내용을 입력하세요."></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label> <input class="form-control" id="bwriter" name="bwriter"
    													value='<security:authentication property="principal.username"/>' readonly="readonly">

						</div>
						<button type="button" class="btn btn-primary" data-oper="register">등록</button>
						<button type="button" class="btn btn-warning" data-oper="list">취소</button>
						<%-- <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}"> --%>
						<security:csrfInput/>
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

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>파일첨부</h4>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<div class="form-group uploadDiv">
						<input type="file" class="btn btn-primary fileInput"
							id="fileInput" name="fileInput" multiple>
					</div>
					<div class="fileUploadResult">
						<ul>

						</ul>
					</div>


				</div><!-- /.panel-body -->

			</div><!-- /.panel -->

		</div><!-- /.col-lg-12 -->

	</div><!-- /.row -->

</div>
<!-- /#page-wrapper -->



<script>
	function checkValues() {
	    var btitle = document.getElementById("btitle").value;
	    var bcontent = document.getElementById("bcontent").value;
	    var bwriter = document.getElementById("bwriter").value;
	
	    var regExp = /^\s+$/;
	
	    if (!btitle || regExp.test(btitle) || !bcontent || regExp.test(bcontent) || !bwriter) {
	        alert("글제목, 글내용, 작성자를 모두 유효한 값으로 입력하세요.");
	        return false;
	    }
	    return true;
	}

	/* 	//등록버튼 클릭 처리 제이쿼리
	 $("button").on("click", function() {
	 location.href = "${contextPath}/myboard/list"	
	 }); */
	
	
	var frmRegister = $("#frmRegister");
	var fileUploadResultUL = $(".fileUploadResult ul") ;
	 
	//버튼 클릭 처리 제이쿼리
	$("button").on("click", function() {
	    var oper = $(this).data("oper");
	    if (oper == "list") {
	        frmRegister.empty();
	        fileUploadResultUL.empty();
	        location.href = "${contextPath}/myboard/list";
	    } else {
	        if (!checkValues()) {
	            alert("글제목, 글내용, 작성자를 모두 유효한 값으로 입력하세요.");
	            return;
	        }
	        
	        var attachFileInputHTML= "";
	
	        $("div.fileUploadResult ul li").each(function(i, objLi){
	            var attachLi = $(objLi);
	            attachFileInputHTML += "<input type='hidden' name='attachFileList[" + i + "].uuid' value='" + attachLi.data("uuid") + "'>"
	                + "<input type='hidden' name='attachFileList[" + i + "].uploadPath' value='" + attachLi.data("uploadpath") + "'>"
	                + "<input type='hidden' name='attachFileList[" + i + "].fileName' value='" + attachLi.data("filename") + "'>"
	                + "<input type='hidden' name='attachFileList[" + i + "].fileType' value='" + attachLi.data("filetype") + "'>";
	        });
	
	        if (attachFileInputHTML) {
	            frmRegister.append(attachFileInputHTML);
	        }
	
	        frmRegister.submit();
	    }
	});
	
	//업로드 결과 표시 함수
	function showUploadResult(uploadResult){
	    var htmlStr = "";
	    

	    if (uploadResult == null || uploadResult.length == 0) {
	        htmlStr = "<li>첨부파일이 없습니다.</li>";
	        fileUploadResultUL.html(htmlStr);
	        return;
	    }

	    var fullFileName = null;
	    
	    $(uploadResult).each(function(i, attachFile){
	    	
	    	var fullFileName = encodeURI(attachFile.repoPath + "/" +
					                attachFile.uploadPath + "/" +
					                attachFile.uuid + "_" + attachFile.fileName);
	    	
	    	
	        if (attachFile.fileType == "F") {
	            htmlStr 
	            	+="<li data-uploadpath='" + attachFile.uploadPath + "'"
	            	+" data-uuid='" + attachFile.uuid + "'"
	            	+" data-filename='"+attachFile.fileName + "'"
	            	+" data-filetype='F'>" 
	                + "    	<img src='${contextPath}/resources/icons/icon-attach.png' style='width:30px;'/>"
	                + "    	&emsp;" + attachFile.fileName
	                + "    <span class='glyphicon glyphicon-remove-sign' data-filename='" + fullFileName + "'"
	                + "          data-filetype='F' style='color:red;'></span>"

	                + "</li>"
	        } else {	
	            var thumbnail = encodeURI(attachFile.repoPath + "/" +
			                              attachFile.uploadPath + "/s_" +
			                              attachFile.uuid + "_" +
			                              attachFile.fileName);

	            htmlStr 
		            +="<li data-uploadpath='" + attachFile.uploadPath + "'"
	            	+" data-uuid='" + attachFile.uuid + "'"
	            	+" data-filename='"+attachFile.fileName + "'"
	            	+" data-filetype='I'>"
	                + "    	<img src='${contextPath}/displayThumbnail?fileName=" + thumbnail + "'>"
	                + "    	&emsp; " + attachFile.fileName
	                + "    <span class='glyphicon glyphicon-remove-sign ' data-filename='" + thumbnail + "'"
	                + "          data-filetype='I' style='color:red;'></span>"

	                + "</li>"
	        }
	    });
	    //fileUploadResultUL.html(htmlStr);
	    fileUploadResultUL.append(htmlStr);
	}
	
	//파일 크기 및 확장자 제한 검사 함수
	function checkUploadFile(fileName, fileSize){
	    var allowedMaxSize = 1048576; // 1MB
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

	
	var _csrfHeaderName = "${_csrf.headerName}"; 
	var _csrfToken = "${_csrf.token}";
	
	
	$('#fileInput').on("change", function(){
		
	    var fileInput = $(this);
	    
	    var uploadFiles = fileInput[0].files;
	    console.log(uploadFiles);

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
	        contentType: false, // contentType에 전송타입(즉, MIME 타입)을 지정하지 않음.
	        processData: false, // contentType에 설정된 형식으로 data를 변환처리가 수행되지 않음
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


	<%-- 파일 삭제: 서버에 업로드된 파일이 삭제되고, 이를 화면에 반영해 주어야 함 --%>
	$(".fileUploadResult ul").on("click","li span", function(){
	    var fileName = $(this).data("filename") ;
	    var fileType = $(this).data("filetype") ;
	    
	    var fileLi = $(this).parent() ;
	    //var fileLi = $(this).closest("li");

	    $.ajax({
	        type: "delete" ,
	        url: "${contextPath}/deleteFile" ,
	        data: {fileName: fileName, fileType: fileType} ,
	        
	        dataType: "text" ,
	        beforeSend: function (xhr) {
	        xhr.setRequestHeader(_csrfHeaderName, _csrfToken);
	        },
	        success: function(result){
	            if(result == "DelSuccess") {
	                alert("파일이 삭제되었습니다.") ;
	                fileLi.remove() ;

	            } else {
	                if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까 ?") ) {

	                    fileLi.remove() ; 
	                    alert("파일이 삭제되었습니다.") ;
	                 }
	            }
	        } //success-end,
	    });  //ajax-end
	});
	
	
	
</script>

<%@ include file="../include/footer.jsp"%>
