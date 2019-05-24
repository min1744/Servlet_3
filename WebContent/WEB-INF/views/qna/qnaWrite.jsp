<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">
	$(function() {
		var count=0;
		var d1=0;
		$("#add").click(function() {
			d1++;
			if(count<5){
				$("#addFile").append('<div id="'+d1+'"><input type="file" class="form-control" id="" name="f'+d1+'"><span title="'+d1+'" class="del">X</span></div>')
				count++;
				
			}else {
				alert('5개만 가능');
			}
		});
		
		///////////////////
		$("#addFile").on("click", ".del", function() {
			//$(this).prev().remove();
			//$(this).remove();
			var v = $(this).attr('title');
			$('#'+v).remove();
			count--;
			
		});
	});
</script>
<style type="text/css">
	.del {
		color: red;
		cursor: pointer;
	}
</style>
</head>
<body>

<c:import url="../temp/header.jsp" />
	<div class="container">
		<h1>Qna Write Page</h1>
		<form action="./qnaWrite" method="post" enctype="multipart/form-data">
		    <div class="form-group">
		      <label for="title">Title:</label>
		      <input type="text" class="form-control" id="title" name="title">
		    </div>
		    <div class="form-group">
		      <label for="writer">Writer:</label>
		      <input type="text" class="form-control" id="writer" name="writer">
		    </div>
		    <div class="form-group">
			  <label for="contents">Contents:</label>
			  <textarea class="form-control" rows="20" id="contents" name="contents"></textarea>
			</div>
			
			<div class="form-group" id="addFile">
		      <label for="file">File:</label>
		      
		    </div>
		    
		    <div class="form-group">
		    	<input type="button" value="Add" id="add" class="btn btn-primary">
		    </div>
		    
		    <button class="btn btn-danger">Write</button>
		  </form>
	</div>
</body>
</html>