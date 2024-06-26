<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

<style>
	th {text-align: center; }
	/* 
	a {text-decoration: none; color: inherit} 
	a:link { text-decoration: none;color: inherit }
	a:visited { text-decoration: none; color: inherit}
	a:hover { text-decoration: none;color: inherit }
	a:active { text-decoration: none;color: inherit} */

</style>


        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">Board - List</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 style="display:inline-block;">게시물 목록</h4><%-- 
                            <button type="button" class="btn btn-info pull-right" onclick="self.location = '${contextPath }/myboard/register';">새글 등록</button> --%>
                            <button type="button" id="toRegister" class="btn btn-info pull-right" >새글 등록</button>
                        </div><%-- /.panel-heading --%>
                        <div class="panel-body">
                        
<form role="form" class="form-inline" id="frmSendValue" method="get" action="${contextPath }/myboard/list">

    <!-- 1. select: 표시 게시물 수 선택 -->
    <div class="form-group">
        <label class="sr-only">SelectRowAmountPerPage</label>
        <select class="form-control" id="selectRowAmountPerPage" name="rowAmountPerPage" >
            <option value="10" ${pagingCreator.myBoardPaging.rowAmountPerPage == 10 ? "selected" : "" }>10</option>
            <option value="20" ${pagingCreator.myBoardPaging.rowAmountPerPage == 20 ? "selected" : "" }>20</option>
            <option value="50" ${pagingCreator.myBoardPaging.rowAmountPerPage == 50 ? "selected" : "" }>50</option>
        </select>
    </div>

    <!-- 5. 기간검색 -->
	<div class="form-group ">
		<input class="form-control" id="inputBeginDate" name="beginDate" type="date"
			   value="${pagingCreator.myBoardPaging.beginDate}" 
			   />
		<input class="form-control" id="inputEndDate" name="endDate" type="date"
			   value="${pagingCreator.myBoardPaging.endDate}" 
			   />
	</div><!-- 
	<div class="input-group custom-search-form">
        <button class="btn btn-primary" type="button" id="btnPriodSearch">
            <span class="glyphicon glyphicon-search" ></span>
        </button>
    </div> -->
 
<div class="pull-right">
    <!-- 2. select: 검색 범위 선택 -->
    <div class="form-group">
        <label class="sr-only">selectScope</label>
        <select class="form-control" id="selectScope" name="scope" >
            <option value="" ${pagingCreator.myBoardPaging.scope == null ? "selected" : "" }>검색범위</option>
            <option value="T" ${pagingCreator.myBoardPaging.scope == "T" ? "selected" : "" }>제목</option>
            <option value="C" ${pagingCreator.myBoardPaging.scope == "C" ? "selected" : "" }>내용</option>
            <option value="W" ${pagingCreator.myBoardPaging.scope == "W" ? "selected" : "" }>작성자</option>
            <option value="TC" ${pagingCreator.myBoardPaging.scope == "TC" ? "selected" : "" }>제목+내용</option>
             
        </select>
    </div>
    <!-- 3. input: 검색어 입력 -->
    <div class="input-group custom-search-form">
        <input type="text" class="form-control" id="inputKeyword" name="keyword" 
               placeholder="검색어를 입력하세요..." value='<c:out value="${pagingCreator.myBoardPaging.keyword}" />' >
        <span class="input-group-btn">
            <button class="btn btn-warning" type="button" id="btnSearchGo">
                <i class="fa fa-search" ></i>
            </button>
        </span>
    </div>
    
    <!-- 4. 초기화 -->
    <div class="input-group custom-search-form">
        <button class="btn btn-danger" type="button" id="btnReset">
            <span class="glyphicon glyphicon-remove" ></span>
        </button>
    </div>

</div>
 
     
     
     
     
 
    <input type="hidden" id="pageNum" name="pageNum" value='<c:out value="${pagingCreator.myBoardPaging.pageNum }" />'>  
    
</form>                        
<br>
                        
<table  style="width:100%;text-align:center;" class="table table-striped table-bordered table-hover" id="dataTables-example">
<thead>
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>수정일</th>
        <th>조회수</th>
    </tr>
</thead>
<tbody>
<c:choose>
<c:when test="${not empty pagingCreator.myBoardList }">
    <c:forEach var="myboard" items="${pagingCreator.myBoardList }">
    <c:choose>
        <c:when test="${myboard.bdelFlag == 1 }">
        <tr style="background-color: Moccasin; text-align: center">
            <td><c:out value="${myboard.bno}"/></td>
			<td colspan="5"><em>작성자에 의해서 삭제된 게시글입니다.</em></td>

		</tr>
        </c:when>
        <c:otherwise><%-- 
		<tr class="myTr" data-bno='<c:out value="${myboard.bno}"/>' onclick="goToDetail(this);"> --%>
		<tr class="myTr" data-bno='<c:out value="${myboard.bno}"/>' >
			<td><c:out value="${myboard.bno}"/></td>
			
			<td>
			    <%-- <a href="${contextPath }/myboard/detail?bno=${myboard.bno}"> --%>
			        <c:out value="${myboard.btitle }"/>
			    <!-- </a> -->
			</td>
			<td><c:out value="${myboard.bwriter }"/></td>
			<td><fmt:formatDate value="${ myboard.bregDate }" pattern="yyyy/MM/dd"/></td>
			<td><fmt:formatDate value="${ myboard.bmodDate }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td><c:out value="${myboard.bviewsCnt }"/></td>
		</tr>
        </c:otherwise>
    </c:choose>
	</c:forEach>
</c:when>
<c:otherwise>
        <tr style="background-color: Moccasin; text-align: center">
            <td colspan="6"><strong>등록된 게시물이 없습니다</strong></td>
        </tr>

</c:otherwise>
</c:choose> 
</tbody>
</table><%-- /.table-responsive --%>
<div class="pull-right">
    <ul class="pagination">
    <%-- 맨 첫페이지로 이동 --%>
    <c:if test="${pagingCreator.prev }">
        <li class="paginate_button previous" tabindex="0" >
	        <a href="1">&laquo;</a>
	    </li>
	</c:if>
    <%-- 이전 페이징 번호 그룹(현재 11 ~ 20 > 1 ~ 10 이동 --%>
    <c:if test="${pagingCreator.prev }">
        <li class="paginate_button previous" tabindex="0" >
            <a href="${pagingCreator.startPagingNum - 1 }">이전</a>
        </li>
    </c:if>
    <%-- 페이징 그룹의 페이징 숫자(10개 표시) --%>
    <c:forEach var="pagingNum" begin="${pagingCreator.startPagingNum }" end="${pagingCreator.endPagingNum }">
        <li class='paginate_button ${pagingCreator.myBoardPaging.pageNum == pagingNum  ?  "active" : "" }' tabindex="0">
            <a href="${pagingNum}">${pagingNum}</a>
        </li><%-- 
        <c:if test="${pagingCreator.myBoardPaging.pageNum == pagingNum }">
           <li class='paginate_button active' tabindex="0">
               <a href="${pagingNum}">${pagingNum}</a>
           </li>
        </c:if>
        <c:if test="${pagingCreator.myBoardPaging.pageNum != pagingNum }">
            <li class='paginate_button' tabindex="0">
                <a href="${pagingNum}">${pagingNum}</a>
            </li>
        </c:if> --%>
        
    </c:forEach>
	    <%-- 다음 페이징 번호 그룹(현재 11 ~ 20 > 21 ~ 30 이동 --%>
	    <c:if test="${pagingCreator.next}">
        <li class="paginate_button next"  tabindex="0" >
            <a href="${pagingCreator.endPagingNum + 1 }">다음</a>
        </li>
	    </c:if>
	    <%-- 맨 마지막 페이지로 이동 --%>
	    <c:if test="${pagingCreator.next}">	    
	    <li class="paginate_button next"  tabindex="0" >
	    <a href="${pagingCreator.lastPageNum }">&raquo;</a>
	    </li>
	    </c:if>
	</ul>
</div>
                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
        </div><%-- /#page-wrapper --%>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">처리 결과</h4>
            </div>
            <div class="modal-body" id="my-model-body">

                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal --%>



<script>
<%-- 등록 페이지 이동 --%>
$("#toRegister").on("click", function(){
	location.href = "${contextPath}/myboard/register" ;
}) ;
/* 
function goToDetail(myElement) {
	//console.log(myElement) ;
	var bno = myElement.dataset.bno ;
	//console.log(bno) ;
	location.href = "${contextPath}/myboard/detail?bno=" + bno ;
	
} */

var frmSendValue = $("#frmSendValue") ;

<%-- 상세 페이지 이동 --%>
$(".myTr").on("click", function(){
//방법1
	//var bno = $(this).data("bno") ;
//	location.href = "${contextPath}/myboard/detail?bno="+ $(this).data("bno") ;

//방법2
	var bno = $(this).data("bno") ;
	
	frmSendValue.append('<input type="hidden" id="bno" name="bno" value="' + bno + '">') ;
	
	frmSendValue.attr("action", "${contextPath}/myboard/detail") ;
	frmSendValue.attr("method", "get");
	
	frmSendValue.submit() ;
	
	//frmSendValue.empty() ; //모든 자식 요소 삭제, 뒤로가기 했다가 다시 이동시 페이징 데이터 유지 않됨
	//$("#bno").remove() ;
	
	var bnoInput = $("#bno") ;  //권장
	bnoInput.remove() ;       
	

	
});

var result = '<c:out value="${result}" />' ;

console.log(result) ;
console.log(typeof result) ;


<%-- 게시물 등록-삭제 시 처리 결과 표시 모달 호출 함수 --%>
//function runModal(result) {
function runModal(result, deletedAttachFileCnt, deletedReplyCnt) {
	console.log("result: " + result);
	console.log("deletedAttachFileCnt: " +  deletedAttachFileCnt);
	console.log("deletedReplyCnt: " + deletedReplyCnt);

	var myMsg ;
	
	if(!result) {
		return ;
	} else if (result == "successRemove" || result == "successDelete"){<%--
		myMsg = "게시글이 삭제되었습니다." ;--%>
        myMsg = deletedAttachFileCnt + "개의 첨부파일과 \n" 
              + deletedReplyCnt + "개의 댓글이\n같이 삭제되었습니다." ;<%-- 수정 --%>
	} else  { //if (parseInt(result) > 0 ) {
		myMsg = result + "번 게시글이 등록되었습니다." ;
	}
	
	$("#my-model-body").html(myMsg) ;
	
	$("#myModal").modal("show") ;
	
	myMsg = "" ;
}
/* 
$("h4").on("click", function(){
	alert(result) ;
}); */


$(".paginate_button a").on("click", function(e) {
//방법1
	/* 	
	e.preventDefault() ;//클릭 시의  a 요소의 기본동작(이동) : e   > 막음
	
	var pagingNum = $(this).attr("href")  //this 는 a 요소
	
	//$(location).attr("href", "${contextPath}/myboard/list?pageNum=" + pagingNum) ;
	
	location.href="${contextPath}/myboard/list?pageNum=" + pagingNum ; */

//방법2
    e.preventDefault() ;
	
	//$("#pageNum").val($(this).attr("href")) ;
	frmSendValue.find("input[id='pageNum']").val($(this).attr("href")) ;
	//alert($("#pageNum").val()) ;
	
	frmSendValue.attr("action", "${contextPath}/myboard/list") ;
	frmSendValue.attr("method", "get");
	
	frmSendValue.submit() ;

}) ;

<%--표시 행수 변경 이벤트 처리 --%>
$("#selectRowAmountPerPage").on("change", function() {

    <%--frmSendValue.find("input[id='pageNum']").val(1) ;--%>
    $("#pageNum").val(1) ;
    frmSendValue.submit();

}); 


$("#inputEndDate").on("change", function(){
	var beginDate = $("#inputBeginDate").val();
	var endDate = $("#inputEndDate").val();
	
	if(!beginDate) {
		alert("시작날짜를 입력하십시오.") ;
		return ;
	}
	
	if(beginDate > endDate) {
		alert("시작날짜가 끝날짜보다 작아야 합니다.") ;
		$("#inputBeginDate").val("");
		$("#inputEndDate").val("");
		return ;
	}
	
<%--

		var _endDate = new Date(endDate) ;
		
		_endDate.setDate(_endDate.getDate() + 1) ; //하루 후의 날짜
		
		_endDate = _endDate.toISOString().slice(0, 10) ;

		$("#inputEndDate").val(_endDate);
		
		endDate = $("#inputEndDate").val() ;
		alert("변환후 endDate: " + endDate);

--%>	
    frmSendValue.find("input[name='pageNum']").val(1) ;
    frmSendValue.submit(); 
});


<%--검색 버튼 클릭 이벤트 처리 --%>
$("#btnSearchGo").on("click", function(){
	
	var scope = $("#selectScope").find("option:selected").val() ;
	var keyword = $("#inputKeyword").val() ;
	
	if(!scope || !keyword ) {
		alert("검색범위를 선택하고 검색어를 입력하세요") ;
		return ;
	}
	
	frmSendValue.find("input[name='pageNum']").val(1) ;
    frmSendValue.submit(); 

});




<%-- 검색 초기화 버튼 클릭 이벤트 처리 --%>

$("#btnReset").on("click", function(){
    $("#selectRowAmountPerPage").val(10) ;
    $("#selectScope").val("") ;
    $("#inputKeyword").val("") ;
    $("#pageNum").val(1) ;
    $("#inputBeginDate").val("");
    $("#inputEndDate").val("");
    frmSendValue.submit() ;
	

}) ;


/*
 <div class="input-group custom-search-form">
        <button class="btn btn-danger" type="button" id="btnReset">
            <span class="glyphicon glyphicon-remove" ></span>
        </button>
    </div> 
 
 */
	

$(document).ready(function(){
	runModal(result) ;
});



</script>

<%@ include file="../myinclude/myfooter.jsp" %>