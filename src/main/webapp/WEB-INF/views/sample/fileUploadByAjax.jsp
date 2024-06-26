<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload By Ajax</title>
<link rel="shortcut icon" href="${contextPath}/resources/icons/favicon-floppy.png" type="image/png"/>

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

</head>
<body>

<div class="uploadDiv">
    <input type="file" class="fileInput" id="fileInput" name="uploadFiles" multiple="multiple">
</div>
<button type="button" id="btnFileUpload">파일전송</button>
<div class="fileUploadResult">
    <ul>
       
    </ul>
</div>
<div class='bigImageWrapper'><%-- 추가 --%>
    <div class='bigImage'>
        <%-- 이미지파일이 표시되는 DIV --%>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>

//업로드 결과 표시 함수
function showUploadResult(uploadResult){
    var htmlStr = "";
    var fileUploadResultUL = $(".fileUploadResult ul") ;

    if (!uploadResult || uploadResult.length == 0) {
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
            	+="<li>"
            	+ "    <a href='${contextPath}/doFileDownloadAjax?fileName=" + fullFileName +"'>"
                + "    		<img src='${contextPath}/resources/icons/icon-attach.png' style='width:30px;'/>"
                + "    		&emsp;" + attachFile.fileName
                + "	   </a>"
                +  "  <span data-filename='" + fullFileName + "' data-filetype='F'>[삭제]</span>"   <%-- 추가 --%>
        } else {	
            var thumbnail = encodeURI(attachFile.repoPath + "/" +
		                              attachFile.uploadPath + "/s_" +
		                              attachFile.uuid + "_" +
		                              attachFile.fileName);

            htmlStr 
            	+= "<li>"
           		//+ "    <a href='${contextPath}/doFileDownloadAjax?fileName=" + fullFileName +"'>"
           		+ "    <a href=\"javascript:showImage('" + fullFileName + "')\" > "
                + "    		<img src='${contextPath}/displayThumbnail?fileName=" + thumbnail + "'>"
                + "    		&emsp; " + attachFile.fileName
                + "	   </a>"
                + "  <span data-filename='" + thumbnail + "' data-filetype='I'>[삭제]</span>"    <%-- 추가 --%>
                + "</li>";
        }
    });
    fileUploadResultUL.html(htmlStr);
    
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

$('#btnFileUpload').on("click", function(){
    
    var fileInput = $('#fileInput');
    var uploadFiles = fileInput[0].files;
    console.log(uploadFiles);

    if (uploadFiles.length == 0) {
    	alert("파일을 먼저 선택하세요...");
    	return;
    }
    
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
        success: function(uploadResult, status){
            console.log(uploadResult);
            $('#fileInput').val("");
            showUploadResult(uploadResult);
        }
    });
});

function showImage(fullFileName) {
	
	$(".bigImageWrapper").css("display", "flex").show() ; <%-- display 속성을 flex 로 지정 > 이미지가 중앙에 표시됨.--%>
	$(".bigImage").html("<img src='${contextPath}/doFileDownloadByAjax?fileName=" + fullFileName + "'>");
          				 /*  .animate({width: '50%'}, 3000) */ 
}

/* $(".bigImageWrapper").on("click", function(){
	
    $(".bigImage").animate({width:'0%', height: '0%'}, 1000);
    //setTimeout(() => {$(this).hide();}, 1000);
    setTimeout(function () {
                   $(".bigImageWrapper").hide();
               }, 1000);
}); */
<%-- 이미지 제거: 원본 이미지 표시 DIV 클릭 이벤트처리: 클릭 시 1초 후에 이미지 사라짐.--%>
$(".bigImageWrapper").on("click", function(){
    $(".bigImage").animate({width: '0%'}, 3000) ;

    $(".bigImageWrapper").hide() ;

})

<%-- 파일 삭제: 서버에 업로드된 파일이 삭제되고, 이를 화면에 반영해 주어야 함 --%>
<%-- 이벤트 위임(Event Delegation) 적용 body > div.fileUploadResult > ul > li:nth-child(2) > span --%>
$(".fileUploadResult ul").on("click","li span", function(){
    var fileName = $(this).data("filename") ;
    var fileType = $(this).data("filetype") ;
    
    var fileLi = $(this).parent() ;
    //var fileLi = $(this).closest("li");

    $.ajax({
        type: "post" ,
        url: "${contextPath}/deleteFile" ,
        data: {fileName: fileName, fileType: fileType} ,
        
        dataType: "text" ,
        success: function(result){
            if(result == "DelSuccess") {
                alert("파일이 삭제되었습니다.") ;
                fileLi.remove() ;

            } else {
                if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까 ?") ) {
                    <%--이벤트 위임을 이용하여, 삭제된 파일의 항목을 DOM에서 삭제--%>
                    <%--[주의] $(this).parent().remove(); 와  $(this).closest("li").remove(); 실행문은 삭제않됨 --%>
                    fileLi.remove() ; <%-- fileLi.remove();--%>
                    alert("파일이 삭제되었습니다.") ;
                 }
            }
        } //success-end,
    });  //ajax-end
});










</script>
</body>
</html>
