<%@page import="com.iu.notice.NoticeDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
<c:import url="../temp/header.jsp"/>
	<div class="container">
		<h1>Notice List</h1>
		<table class="table table-hover">
			<tr>
				<td>NUM</td><td>TITLE</td><td>WRITER</td><td>DATE</td><td>HIT</td>
			</tr>
			<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.num}</td>
				<td><a href="./noticeSelect?num=${dto.num}">${dto.title}</a></td>
				<td>${dto.writer}</td>
				<td>${dto.reg_date}</td>
				<td>${dto.hit}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div class="container">
		<ul class="pager">
			<c:if test="${pager.curBlock gt 1}">
				<li class="previous"><a href="./noticeList?curPage=${pager.startNum-1}&kind=${pager.search.kind}&search=${pager.search.search}">Previous</a></li>
			</c:if>
			<li>
				<ul class="pagination">
					<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" step="1" var="i">
						<li><a href="./noticeList?curPage=${i}&kind=${pager.search.kind}&search=${pager.search.search}">${i}</a></li>
					</c:forEach>
				</ul>
			</li>
			<c:if test="${pager.curBlock lt pager.totalBlock}">
				<li class="next"><a href="./noticeList?curPage=${pager.lastNum+1}&kind=${pager.search.kind}&search=${pager.search.search}">Next</a></li>
			</c:if>
		</ul>
	</div>
	<a href="./noticeWrite">Go Write</a>
</body>
</html>