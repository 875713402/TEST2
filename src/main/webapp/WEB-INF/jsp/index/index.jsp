<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="static/script/jquery.min.js"></script>
<title>Hello World</title>
</head>
<body>
    ${command}
    <br/>
    <a href="https://www.baidu.com" target="_blank">跳转百度</a>
    <br/>
    <a href="qiniu" >打开七牛云上传页面</a>
    <br/>
    <a href="uploadS">上传a.txt</a>
    <br/>
    <form action="/20170619_01/upload" enctype="multipart/form-data" method="post">
    	<input type="file" name="file"/>
    	<input type="submit" value="上传"/>
    </form>
    
</body>
<script type="text/javascript">
</script>
</html>