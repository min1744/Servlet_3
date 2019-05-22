<%@page import="com.iu.notice.NoticeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//NoticeDTO noticeDTO = (NoticeDTO)request.getAttribute("dto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp"/>
<style type="text/css">
	.center{
		text-align: center;
	}
</style>
</head>
<body>
<jsp:include page="../temp/header.jsp"/>
	<div class="container">
		<h1>Notice Select Page</h1>
		<table class="table table-hover">
			<thead>
				<tr>
					<th class="center" colspan="2">SUBJECT</th>
					<th class="center">NAME</th>
					<th class="center">DATE</th>
					<th class="center">HIT</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2">${dto.title}</td>
					<td class="center">${dto.writer}</td>
					<td class="center">${dto.reg_date}</td>
					<td class="center">${dto.hit}</td>
				</tr>
				<tr>
					<td colspan="5">${dto.contents}</td>
				</tr>
				<tr>
					<td class="center">NEXT</td>
					<td>${dto.title}</td>
					<td class="center">${dto.writer}</td>
					<td class="center">${dto.reg_date}</td>
					<td class="center">${dto.hit}</td>
				</tr>
				<tr>
					<td class="center">PREV</td>
					<td>${dto.title}</td>
					<td class="center">${dto.writer}</td>
					<td class="center">${dto.reg_date}</td>
					<td class="center">${dto.hit}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<a href="./noticeUpdate">Go Update</a>
</body>
</html>