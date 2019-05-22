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
		<h1>noticeWrite Page</h1>
		<form action="./noticeWrite" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">Title : </label>
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="writer">Writer : </label>
				<input type="text" class="form-control" id="writer" name="writer">
			</div>
			<div class="form-group">
				<label for="contents">Contents : </label>
				<textarea class="form-control" rows="20" id="contents" name="contents"></textarea>
			</div>
			<div class="form-group">
				<label for="file">File : </label>
				<input type="file" class="form-control" id="f1" name="f1">
			</div>
			<button class="btn btn-danger">Write</button>
		</form>
	</div>
</body>
</html>