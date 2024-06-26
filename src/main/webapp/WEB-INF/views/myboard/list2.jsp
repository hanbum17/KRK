<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<%@ include file="../myinclude/myheader.jsp" %>

<style>
	th {text-align: center; }
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
                        <div class="panel-heading"><h4>게시물 목록</h4></div><%-- /.panel-heading --%>
                        <div class="panel-body">
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
<c:when test="${not empty boardList }">
	<c:forEach var="myboard" items="${boardList}">
		<c:choose>
			<c:when test="${myboard.bdelFlag == 1 }">
				<tr style="background-color: Moccasin; text-align: center">
				    <td>${myboard.bno }</td>
				    <td colspan="6"><em>작성자에 의해서 삭제된 게시글입니다.</em></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td><c:out value="${myboard.bno }"/></td>
					<td style="text-align: left">
						<c:out value="${myboard.btitle }"/>
    				</td>
					<td>${myboard.bwriter }</td>
					<td class="center"><c:out value="${myboard.bregDate }"/></td>
					<td class="center"><fmt:formatDate value="${myboard.bmodDate }" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td class="center"><c:out value="${myboard.bviewsCnt }"/></td>
				 </tr>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:when>
<c:otherwise>
		<tr class="odd gradeX">
			<td colspan="6">등록된 게시물이 없습니다.</td>
		 </tr>
</c:otherwise>
</c:choose> 
</tbody>
</table><%-- /.table-responsive --%>
                        </div><%-- /.panel-body --%>
                    </div><%-- /.panel --%>
                </div><%-- /.col-lg-12 --%>
            </div><%-- /.row --%>
        </div><%-- /#page-wrapper --%>

<%@ include file="../myinclude/myfooter.jsp" %>