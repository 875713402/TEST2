<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="static/script/jquery.min.js"></script>
<script type="text/javascript" src="static/script/ajaxfileupload.js"></script>
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
    	<input type="file" name="file" id="file"/>
    	<input type="submit" value="上传"/>
    </form>
    <input type="file" name="ajaxFile" id="ajaxFile" onchange="fun()"/>
    <br />
    <select id="selectA" onchange="selectA(this.value)">
    	<option>请选择</option>
    	<option value="aaa">吃饭</option>
    	<option value="bbb">睡觉</option>
    	<option value="ccc">打豆豆</option>
    </select>
    <br/>
    <a href="MongoDB">MongoDB</a>
</body>
<script type="text/javascript">
function fun(){
	$.ajaxFileUpload({
		type: "POST",  
	    url: "uploadB",  
	    secureuri : false,//是否启用安全提交，默认为false  
	    fileElementId:'ajaxFile',//文件选择框的id属性  
	    dataType: 'json',//服务器返回的格式  
	    async : false,  
	    success: function(data){  
	        alert("msg" + data.msg);
	        alert("src" + data.src);
	    },  
	    error: function (data, status, e){  
	    	alert("msg" + data.msg);
	    }
	});
}
function selectA(obj){
	console.log(obj);
}
</script>
</html>