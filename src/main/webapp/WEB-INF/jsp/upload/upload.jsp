<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>qiniuyun</title>
<link rel="stylesheet" href="static/css/bootstrap.css">
<link rel="stylesheet" href="static/css/highlight.css">
<link rel="stylesheet" href="static/css/main.css">
</head>
<body>
	七牛云上传
	<br/>
	${AK }
	<br/>
	${SK }
	<br/>
	<input type="text" value="${token }"/>
	<br/>
	<input id="fileUrl" type="text"/>
	<br/>
	 <div class="col-md-12">
        <div id="container">
            <a class="btn btn-default btn-lg " id="pickfiles" href="#" >
                <i class="glyphicon glyphicon-plus"></i>
                <span>选择文件</span>
            </a>
        </div>
    </div>
    <div style="display:none" id="success" class="col-md-12">
        <div class="alert-success">
          	  队列全部文件处理完毕
        </div>
    </div>
    <div class="col-md-12 ">
        <table class="table table-striped table-hover text-left"   style="margin-top:40px;display:none">
            <thead>
              <tr>
                <th class="col-md-4">Filename</th>
                <th class="col-md-2">Size</th>
                <th class="col-md-6">Detail</th>
              </tr>
            </thead>
            <tbody id="fsUploadProgress">
            </tbody>
        </table>
    </div>
</body>
<script type="text/javascript" src="static/script/jquery.min.js"></script>
<script type="text/javascript" src="static/script/bootstrap.min.js"></script>
<script type="text/javascript" src="static/script/moxie.js"></script>
<script type="text/javascript" src="static/script/plupload.dev.js"></script>
<script type="text/javascript" src="static/script/ui.js"></script>
<script type="text/javascript" src="static/script/qiniu.js"></script>
<script type="text/javascript" src="static/script/highlight.js"></script>
<script type="text/javascript" src="static/script/zh_CN.js"></script>
<script type="text/javascript" src="static/script/main.js"></script>
<script type="text/javascript">hljs.initHighlightingOnLoad();</script>
<script type="text/javascript">
var uploader = Qiniu.uploader({
    runtimes: 'html5,flash,html4',    //上传模式,依次退化
    browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
    //uptoken_url: '/token',            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
    uptoken : '${token}', //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
    unique_names: false, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
    // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
    domain: 'http://oqwg77zw9.bkt.clouddn.com/',   //bucket 域名，下载资源时用到，**必需** 
    get_new_uptoken: false,  //设置上传文件的时候是否每次都重新获取新的token
    container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
    max_file_size: '100mb',           //最大文件体积限制
    flash_swf_url: 'js/plupload/Moxie.swf',  //引入flash,相对路径
    max_retries: 3,                   //上传失败最大重试次数
    dragdrop: true,                   //开启可拖曳上传
    drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
    chunk_size: '4mb',                //分块上传时，每片的体积
    auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
    init: {
        'FilesAdded': function(up, files) {
            plupload.each(files, function(file) {
                // 文件添加进队列后,处理相关的事情
            });
        },
        'BeforeUpload': function(up, file) {
               // 每个文件上传前,处理相关的事情
        },
        'UploadProgress': function(up, file) {
               // 每个文件上传时,处理相关的事情
        },
        'FileUploaded': function(up, file, info) {
               // 每个文件上传成功后,处理相关的事情
               // 其中 info 是文件上传成功后，服务端返回的json，形式如
               // {
               //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
               //    "key": "gogopher.jpg"
               //  }
               // 参考http://developer.qiniu.com/docs/v6/api/overview/up/response/simple-response.html

               var domain = up.getOption('domain');
               var res = JSON.parse(info);
               var sourceLink = domain + res.key; //获取上传成功后的文件的Url
               alert(sourceLink);
               $("#fileUrl").val(sourceLink);
        },
        'Error': function(up, err, errTip) {
               //上传出错时,处理相关的事情
               
        },
        'UploadComplete': function() {
               //队列文件处理完毕后,处理相关的事情
        },
        'Key': function(up, file) {
            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
            // 该配置必须要在 unique_names: false , save_key: false 时才生效

            var key = file.name;
            // do something with key here
            return key
        }
    }
});
</script>

</html>