<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
</head>
<body>
	<form enctype="multipart/form-data"
		action="/system/exportCSV"
		method="POST">
		Export CSV: <input name="formFile" type="file" /><br /> <input
			type="submit" value="Upload File" />
	</form>
</body>
</html>