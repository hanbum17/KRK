<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

<style>
    .fileUploadResult ul li { list-style: none; }
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
                        <div class="panel-heading"><h4>게시물 등록</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">

<form role="form" id="frmRegister" method="post" action="${contextPath }/myboard/register">
    <div class="form-group">
        <label>글제목</label>
        <input class="form-control" id="btitle" name="btitle"   placeholder="글제목을 입력하세요...">
    </div>
    <div class="form-group">
        <label>글내용</label>
        <textarea class="form-control" id="bcontent" name="bcontent" rows="3" placeholder="글내용을 입력하세요..."
                 ></textarea>
    </div>
    <div class="form-group">
        <label>작성자</label>
        <input class="form-control" id="bwriter" name="bwriter" 
        	   value='<security:authentication property="principal.username"/>' readonly="readonly">
    </div><%-- 
    <button type="button" class="btn btn-primary" onclick="checkValue();">등록</button>
    <button type="button" class="btn btn-warning" 
            onclick="location.href='${contextPath}/myboard/list'">취소</button> --%>
    
    <button type="button" class="btn btn-primary" data-oper="register">등록</button>
    <button type="button" class="btn btn-warning" data-oper="list">취소</button> 
    <%-- <input type="hidden" name="${_csrf.parameterName }" Value="${_csrf.token }"> --%> 
    <security:csrfInput/>
</form>




                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
            
            
             <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4>파일 첨부</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">

<div class="form-group uploadDiv">
	<input type="file" class="btn btn-primary fileInput" id="fileInput"  name="fileInput" multiple >
</div>
<div class="form-group fileUploadResult">
    <ul>
       <%-- 업로드 후 처리결과가 표시될 영역 --%>
    </ul>
</div>


                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
            
        </div><%-- /#page-wrapper --%>

<script>
/*//let과 var 차이
if(true){
	var myName = "홍길동" ;
	let yourName = "슈퍼맨" ;
}

alert(myName) ;
//alert(yourName) ;
*/

function checkValues(){
    var btitle = document.getElementById("btitle").value ;
    var bcontent = document.getElementById("bcontent").value ;
    var bwriter = document.getElementById("bwriter").value ;

    var regExp = /^\s+$/ ;
    
    if( !btitle || !bcontent || !bwriter || regExp.test(btitle) || regExp.test(bcontent) ){
    	//alert("글제목/글내용/작성자를 모두 유효한 값으로 입력해야 합니다.");
    	return false ;
    } else {
    	//frmRegister.submit() ;
        return true ;
    }
}


//등록 버튼 클릭 처리 제이쿼리 
//$("button").on("click", function(){
//	location.href = "${contextPath}/myboard/list" ;	
//}) ; 

var frmRegister = $("#frmRegister") ;
var fileUploadResultUL = $(".fileUploadResult ul")
//버튼 클릭 처리 제이쿼리
$("button").on("click", function(){

	var oper= $(this).data("oper") ;
	console.log(oper) ;
	if(oper == "list") {
		
		frmRegister.empty() ;
		fileUploadResultUL.empty() ;
		location.href = "${contextPath}/myboard/list" ;
		
	} else {
		if(!checkValues()){
			alert("글제목/글내용/작성자를 모두 유효한 값으로 입력해야 합니다.");
			return ;
		}
		
		var attachFileInputHTML = "" ;

		//자바스크립트 객체를 이용해서는 jQuery 함수를 사용할 수 없습니다.
		//jQuery 객체를 이용해서는 자바스크립트 함수를 사용할 수 없습니다.
		
		//var frmRegister = document.getElementById("frmRegister") ;
		//console.log(frmRegister.getAttribute("method"));

		
		//console.log(frmRegister.attr("method"));
		//
		//첨부파일이 없으면, 실행되지 않음
		$(".fileUploadResult ul li").each(function(i, objLi) {
			
			var attachLi = $(objLi);
			
			attachFileInputHTML
			+= "<input type='hidden' name='attachFileList[" + i + "].uuid' value='" + attachLi.data("uuid") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].uploadPath' value='" + attachLi.data("uploadpath") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].fileName' value='" + attachLi.data("filename") + "'>"
			+  "<input type='hidden' name='attachFileList[" + i + "].fileType' value='" + attachLi.data("filetype") + "'>" ;
			
		});
		
		//alert(attachFileInputHTML) ;
		
		if (attachFileInputHTML) {
			frmRegister.append(attachFileInputHTML) ;	
		}
				
		frmRegister.submit() ;
	}
}) ;


<%-- 업로드 결과 표시 함수 --%>
function showUploadResult(uploadResult){
	
	var htmlStr = "";
	
	if(uploadResult == null || uploadResult.length == 0) {
	//if(!uploadResult) { //동작 않함
		htmlStr = "<li>첨부파일이 없습니다.</li>" ;
		fileUploadResultUL.html(htmlStr) ;
		return ;
	}
	
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
//var cloneFileInput = $(".uploadDiv").clone();         //clone(): 선택된 요소의 자식요소가 복사됨
//console.log("cloneFileInput: " + cloneFileInput.html()) ;
//<input type="file" class="fileInput" id="fileInput" name="fileInput"  multiple ><br>

var _csrfHeaderName = "${_csrf.headerName}" ;
var _csrfToken = "${_csrf.token}";
$(document).ajaxSend(function(e,xhr,options){
	xhr.setRequestHeader(_csrfHeaderName, _csrfToken);
});

<%-- 업로드 처리, 파일 input 요소의 "내용이 바뀌면" 업로드가 수행되도록 수정 --%>
$("#fileInput").on("change", function(){
	
	var fileInput = $("input[name='fileInput']") ;
    //var fileInputs = $(this) ;

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
    	beforeSend: function(xhr){
    		xhr.setRequestHeader(_csrfHeaderName , _csrfToken);
    	},
    	success: function(uploadResult, status){
    		console.log(uploadResult)  ;
    		//$(".uploadDiv").html(cloneFileInput.html()) ;
    		$("#fileInput").val("") ;
    		showUploadResult(uploadResult) ;
    	}
    });

}) ;


<%-- 파일 삭제: 서버에 업로드된 파일이 삭제되고, 이를 화면에 반영해 주어야 함 --%>
<%-- 이벤트 위임(Event Delegation) 적용 div.fileUploadResult > ul > li:nth-child(1) > span --%>
$(".fileUploadResult ul").on("click", "li span" , function(){
   
	var fileName = $(this).data("filename") ;
	var fileType = $(this).data("filetype") ;
	var fileLi = $(this).parent() ;
   
   //var fileLi = $(this).closest("li") ;
   
   //post 가 아닌 다른 type의 전송방식(예, delete)를 사용하려면
   //프로젝트의 web.xml 파일에 org.springframework.web.filter.FormContentFilter 필터를 설정해야 함
   $.ajax({
       type: "post" ,  
       url: "${contextPath}/deleteFile" ,
       data: {fileName: fileName, fileType: fileType} ,
      //data: JSON.stringify({fileName: fileName, fileType: fileType}) ,
      // contentType: "application/json; charset=utf-8" ,
       dataType: "text" ,
       beforeSend: function(xhr){
       	xhr.setHeaderRequest(_csrfHeaderName, _csrfToken);
       },
       success: function(result) {
       	if(result == "DelSuccess") {
       		alert("파일이 삭제되었습니다.") ;
       		fileLi.remove() ;
       	} else {
       		if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까?")) {
       			fileLi.remove() ;
       		}
       	}
       } ,
       error: function(xhr, status, e) {
       	
       		alert("서버 장애") ;
       		console.log(e) ;
      	
       		if(confirm("파일이 존재하지 않습니다. 해당 항목을 삭제하시겠습니까?")) {
       			fileLi.remove() ;
       		}
       	
       } 
       
   }); 
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