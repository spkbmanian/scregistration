<html>
<head>
<style type="text/css">
.link {
	margin: 0;
	border: 0;
	background: none;
	overflow: visible;
	color: blue;
	cursor: pointer;
}
</style>
</head>
<body>
	<h2>Student Course Registration</h2>
	<form action="studentservice" method="get">
		<input type="hidden" id="limit" name="limit" value="10" /> <input
			type="hidden" id="pageNumber" name="pageNumber" value="1" /> <input
			type="submit" value="Students List" class="link" />
	</form>

	<form action="courseservice" method="get">
		<input type="hidden" id="limit" name="limit" value="10" /> <input
			type="hidden" id="pageNumber" name="pageNumber" value="1" /> <input
			type="submit" value="Courses List" class="link" />
	</form>

	<form action="studentCourseService" method="get">
		<input type="hidden" id="limit" name="limit" value="10" /> <input
			type="hidden" id="pageNumber" name="pageNumber" value="1" /> <input
			type="submit" value="Students Courses List" class="link" />
	</form>

</body>
</html>
