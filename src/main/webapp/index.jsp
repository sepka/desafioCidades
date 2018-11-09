<!DOCTYPE html>
<html>
<title>Upload File Demo</title>
<head>
<meta charset="UTF-8">
</head>
<body>
	 Upload File<br/><br/>
	<form action="http://localhost:8080/cidades/upload/" method="post" enctype="multipart/form-data">
 		 File : <input type="file" name="upload" size="50" />
	  <br/>
 	   <input type="submit" value="Upload" />
	</form>
 </body>
</html>