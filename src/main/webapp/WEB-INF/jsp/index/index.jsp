<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="static/script/AjaxUpload.js"></script>
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
    <input id="upload" type="button" value="AjaxUpload上传"/>
</body>
<script type="text/javascript">
	new AjaxUpload('#upload', {  
        action: 'upload?',  
        data: {},  
        name: 'file',  
        onSubmit: function(file, ext) {  
            if (!(ext && /^(jpg|JPG|png|PNG|gif|GIF)$/.test(ext))) {  
                alert("您上传的图片格式不对，请重新选择！");  
                return false;  
            }  
        },  
        onComplete: function(file, response) {  
        }  
    });
</script>
</html>