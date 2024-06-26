<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../include/header.jsp"%>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">Board-List</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 style="display: inline-block;">게시물 목록</h4>
					<button type="button" class="btn btn-info pull-right"
						id="toRegister">새글 등록</button>
				</div>
				<div class="panel-body">
					<form role="form" class="form-inline" id="frmSendValue"
						action="${contextPath}/myboard/list" method="get">
						<!-- 기간 선택 -->
						<div id="selectDate">
	                        <div class="form-group">
	                            <label for="startDate" class="sr-only">시작 날짜</label>
	                            <input type="date" class="form-control" id="startDate" name="startDate" placeholder="YYYY-MM-DD" value="${param.startDate}">
	                        </div>
	                        <span style="margin: 0 5px;">-</span>
	                        <div class="form-group">
	                            <label for="endDate" class="sr-only">종료 날짜</label>
	                            <input type="date" class="form-control" id="endDate" name="endDate" placeholder="YYYY-MM-DD" value="${param.endDate}">
	                        </div>
                        </div>
						<!-- 1. select: 표시 게시물 수 선택 -->
						<div class="form-group">
							<label class="sr-only">SelectRowAmountPerPage</label> 
							<select
								class="form-control" id="selecRowAmountPerPage"
								name="rowAmountPerPage">
								<option value="10"
									${pagingCreator.myBoardPaging.rowAmountPerPage eq '10' ? 'selected' : ''}>10개</option>
								<option value="20"
									${pagingCreator.myBoardPaging.rowAmountPerPage eq '20' ? 'selected' : ''}>20개</option>
								<option value="50"
									${pagingCreator.myBoardPaging.rowAmountPerPage eq '50' ? 'selected' : ''}>50개</option>
							</select>
						</div>
						<input type='hidden' name='pageNum' value='${pagingCreator.myBoardPaging.pageNum}'> 
						<input type='hidden' name='lastPageNum' value='${pagingCreator.lastPageNum}'>
						<!-- 2. select: 검색 범위 선택 -->
						<div class="form-group">
							<label class="sr-only">SelectScope</label> <select
								class="form-control" id="selectScope" name="scope">
								<option value=""
									${pagingCreator.myBoardPaging.scope == null ? 'selected' : ''}>검색범위</option>
								<option value="T"
									${pagingCreator.myBoardPaging.scope eq 'T' ? 'selected' : ''}>제목</option>
								<option value="C"
									${pagingCreator.myBoardPaging.scope eq 'C' ? 'selected' : ''}>내용</option>
								<option value="W"
									${pagingCreator.myBoardPaging.scope eq 'W' ? 'selected' : ''}>작성자</option>
								<option value="TC"
									${pagingCreator.myBoardPaging.scope eq 'TC' ? 'selected' : ''}>제목+내용</option>
							</select>
						</div>
						<!-- 3. input: 검색어 입력 -->
						<div class="input-group custom-search-form">
							<input type="text" class="form-control" id="inputKeyword"
								name="keyword" placeholder="검색어를 입력하세요..."
								value='${pagingCreator.myBoardPaging.keyword}'> <span
								class="input-group-btn">
								<button class="btn btn-warning" type="button" id="btnSearchGo">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
						<!-- 4. 검색어 초기화 버튼 -->
						<div class="input-group custom-search-form">
							<button type="button" class="btn btn-danger" id="btnReset">
								<i class="fa fa-refresh"></i>
							</button>
						</div>
					</form>

					<table class="table table-striped table-bordered table-hover"
						id="dataTables-example" style="width: 100%; text-align: center;">
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
							<c:forEach items="${pagingCreator.myBoardList}" var="board">
								<c:choose>
									<c:when test="${board.bdelFlag == 1}">
										<tr style="background-color: skyblue; font-style: italic;">
											<td><c:out value="${board.bno}" /></td>
											<td colspan="5"><em>작성자에 의해서 삭제된 게시글입니다.</em></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr class="moveDetail" data-bno='<c:out value="${board.bno}"/>' >
											<td><c:out value="${board.bno}" /></td>
											<td><c:out value="${board.btitle}" /></td>
											<td><c:out value="${board.bwriter}" /></td>
											<td><fmt:formatDate pattern="yyyy/MM/dd"
													value="${board.bregDate}" /></td>
											<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss"
													value="${board.bmodDate}" /></td>
											<td><c:out value="${board.bviewsCnt}" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
					<!-- Modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true"
						style="display: none;">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">×</button>
									<h4 class="modal-title" id="myModalLabel">처리 결과</h4>
								</div>
								<div class="modal-body" id="my-modal-body"></div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">닫기</button>
								</div>
							</div>
						</div>
					</div>

					<div class='pull-right'>
						<ul class="pagination pagination-sm">
							<%-- 페이징 버튼 클릭 시, jQuery로 페이지 번호를 전달하도록 a 태그에 전달된 pagingCreator 객체의 필드 지정  --%>
							<c:if test="${pagingCreator.prev}">
								<li class="paginate_button previous"><a href="1">&laquo;</a>
								<%-- 맨 앞으로 페이지로 이동 --%></li>
							</c:if>
							<c:if test="${pagingCreator.prev}">
								<li class="paginate_button previous"><a
									href="${pagingCreator.startPagingNum - 1}">Previous</a>
								<%-- 이전 페이징 그룹 끝 페이지로 이동 --%></li>
							</c:if>
							<%-- 페이징 그룹의 페이징 숫자(10개 표시) --%>
							<c:forEach var="pageNum" begin="${pagingCreator.startPagingNum}"
								end="${pagingCreator.endPagingNum}">
								<%-- 선택된 숫자의 경우, Bootstrap의 active 클래스 이름 추가  --%>
								<li
									class='paginate_button ${pagingCreator.myBoardPaging.pageNum == pageNum ? "active":"" }'>
									<a href="${pageNum}">${pageNum}</a>
								</li>
							</c:forEach>
							<c:if test="${pagingCreator.next}">
								<li class="paginate_button next"><a
									href="${pagingCreator.endPagingNum +1}">Next</a></li>
								<%-- 다음 페이징 그룹의 첫 페이지로 이동 --%>
							</c:if>
							<c:if test="${pagingCreator.next}">
								<li class="paginate_button next"><a
									href="${pagingCreator.lastPageNum}">&raquo;</a></li>
								<%-- 맨 마지막으로 페이지로 이동 --%>
							</c:if>
						</ul>
					</div>


					<%-- <div class="pull-right">
						<ul class="pagination pagination-sm">
							<c:if test="${pagingCreator.prev}">
								<li class="paginate_button previous"><a href="1">&laquo;</a></li>
							</c:if>
							<c:if test="${pagingCreator.prev}">
								<li class="paginate_button previous"><a
									href="${pagingCreator.startPagingNum - 1}">Previous</a></li>
							</c:if>
							<c:forEach var="pageNum" begin="${pagingCreator.startPagingNum}"
								end="${pagingCreator.endPagingNum}">
								<li
									class='paginate_button ${pagingCreator.myBoardPaging.pageNum == pageNum ? "active":"" }'>
									<a href="${pageNum}">${pageNum}</a>
								</li>
							</c:forEach>
							<c:if test="${pagingCreator.next}">
								<li class="paginate_button next"><a
									href="${pagingCreator.endPagingNum +1}">Next</a></li>
							</c:if>
							<c:if test="${pagingCreator.next}">
								<li class="paginate_button next"><a
									href="${pagingCreator.lastPageNum}">&raquo;</a></li>
							</c:if>
						</ul>
					</div> --%>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$("#toRegister").on("click", function() {
		location.href = "${contextPath}/myboard/register";
	});

/* 	$(".myTr").on("click", function() {
		frmSendValue.attr("action", "${contextPath}/myboard/detail");
		frmSendValue.attr("method", "get");
		frmSendValue.submit();
	}); */

	var result = '<c:out value="${result}"/>';

	function runModal(result) {
		var myMsg;
		if (!result) {
			return;
		} else if (result === "RemoveSuccess" || result === "DeleteSuccess") {
			myMsg = "게시물이 삭제되었습니다.";
		} else {
			myMsg = result + "번 게시글이 등록되었습니다.";
		}

		$("#my-modal-body").html(myMsg);
		$("#myModal").modal("show");
	}

	$(document).ready(function() {
		runModal(result);
	});

	var frmSendValue = $("#frmSendValue");

	$(".moveDetail").on(
			"click",
			function() {
				frmSendValue.append("<input type='hidden' name='bno' value='"
						+ $(this).data("bno") + "'/>");
				frmSendValue.attr("action", "${contextPath}/myboard/detail");
				frmSendValue.attr("method", "get");
				frmSendValue.submit();
				$("#bno").remove();
			});

	$(".paginate_button a").on("click", function(e) {
		e.preventDefault();
		frmSendValue.find("input[name='pageNum']").val($(this).attr("href"));
		frmSendValue.attr("action", "${contextPath}/myboard/list");
		frmSendValue.attr("method", "get");
		frmSendValue.submit();
	});
<%--표시행수 변경 이벤트 처리--%>
	$("#selecRowAmountPerPage").on("change", function() {
		/* 	    frmSendValue.find("input[name='pageNum']").val(1);
		 frmSendValue.attr("action", "${contextPath}/myboard/list");
		 frmSendValue.attr("method", "get"); */

		$("pageNum").val(1);
		frmSendValue.submit();
	});
	
	//날짜검색
	$("#selectDate").on("change", function() {

		$("pageNum").val(1);
		frmSendValue.submit();
	});

	//검색 이벤트 처리
	$("#btnSearchGo").on("click", function() {

		var scope = $("#selectScope").find("option:selected").val();
		var keyword = $("#inputKeyword").val();
		if (!scope || !keyword) {
			alert("검색범위를 선택하고 검색어를 입력하세요");
			return;
		}

		frmSendValue.find("input[name='pageNum']").val(1);
		frmSendValue.submit();

	});

	//초기화 이벤트 처리
	$("#btnReset").on("click", function() {
		$("#selectRowAmountPerPage").val(10);
		$("#selectScope").val("");
		$("#inputKeyword").val("");
		$("#pageNum").val(1);
		$("#startDate").val("");
		$("#endDate").val("");

		frmSendValue.submit();
	});
	
/* 	if(startDate > endDate) {
		alert("시작날짜가 끝날짜보다 작아야 합니다");
		$("#startDate").val("");
		$("#endDate").val("");
		return;
		
	} */

	$(document).ready(function() {
		runModal(result);
	});

/* 	$(document).ready(function() {
		checkModal(result);

	}); */
</script>

<%@ include file="../include/footer.jsp"%>
