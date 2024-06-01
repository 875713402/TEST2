package controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBConnector;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import entity.LoginForm;

@Controller()
public class LoginController {
	private final String AK = "z2xHZEgi1GJ1lUyZv2pPOXEmWyy8M6_oOKUhG8z3";
	private final String SK = "k86grogaXK2Y0IE7D7lMsLBBEiivsbmVqeem5RPV";
	private final String BUCKET = "bilibili";
	private final String DOMAIN = "oqwg77zw9.bkt.clouddn.com";
	@RequestMapping(value="login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginForm command ){
		String username = command.getUsername();
	    ModelAndView mv = new ModelAndView("/index/index","command","LOGIN SUCCESS, " + username);
	    return mv;
	}

	@RequestMapping(value="qiniu")
	public ModelAndView qiniu(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/upload/upload");
		String token = Auth.create(AK, SK).uploadToken(BUCKET);
		mv.addObject("AK", AK);
		mv.addObject("SK", SK);
		mv.addObject("token", token);
		return mv;
	}

	@RequestMapping(value="uploadS", method = RequestMethod.GET)
	public ModelAndView uploadS(HttpServletRequest request, HttpServletResponse response, String ak, String sk){
		String upToken = Auth.create(AK, SK).uploadToken(BUCKET);
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		ModelAndView mv = new ModelAndView("/result/result");
		File f = new File("E:\\a.txt");
		int i = new Random().nextInt();
		try {
			Response responsed = uploadManager.put(f, "ABC"+i, upToken);
			//解析上传成功返回的结果
		    DefaultPutRet putRet = new Gson().fromJson(responsed.bodyString(), DefaultPutRet.class);
		    System.out.println(putRet.key);
		    System.out.println(putRet.hash);
		    mv.addObject("result", "success:"+putRet.key);
		} catch (QiniuException e) {
			mv.addObject("result", "fail:"+e);
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value="upload", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("file") MultipartFile file){
		ModelAndView mv = new ModelAndView("/result/result");
		String msg = new String();
		String src = "";
		String upToken = Auth.create(AK, SK).uploadToken(BUCKET);
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		if(file.getSize()>1024*1024*200){
			msg = "fail:文件大小大于200M,请重新上传！";
		}else{
			try {
				Response responsed = uploadManager.put(file.getBytes(), file.getOriginalFilename(), upToken);
				//解析上传成功返回的结果
			    DefaultPutRet putRet = new Gson().fromJson(responsed.bodyString(), DefaultPutRet.class);
			    msg = "success:" + putRet.key;
			    src = "http://" + DOMAIN + "/" + putRet.key;
			} catch (QiniuException e) {
				e.printStackTrace();
				msg = "fail:" + e;
			} catch (IOException e) {
				msg = "fail:" + e;
				e.printStackTrace();
			}
		}
		mv.addObject("result", msg);
		mv.addObject("src", src);
		return mv;
	}

	@RequestMapping(value="uploadB", method = RequestMethod.POST)
	@ResponseBody
	public String uploadB(@RequestParam("ajaxFile") MultipartFile ajaxFile){
		JsonObject json = new JsonObject();
		String msg = "";
		String src = "";
		String upToken = Auth.create(AK, SK).uploadToken(BUCKET);
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		if(ajaxFile.getSize()>1024*1024*200){
			msg = "fail:文件大小大于200M,请重新上传！";
		}else{
			try {
				Response responsed = uploadManager.put(ajaxFile.getBytes(), ajaxFile.getOriginalFilename(), upToken);
				//解析上传成功返回的结果
			    DefaultPutRet putRet = new Gson().fromJson(responsed.bodyString(), DefaultPutRet.class);
			    msg = "success:" + putRet.key;
			    src = "http://" + DOMAIN + "/" + putRet.key;
			} catch (QiniuException e) {
				e.printStackTrace();
				msg = "fail:" + e;
			} catch (IOException e) {
				msg = "fail:" + e;
				e.printStackTrace();
			}
		}
		json.addProperty("src", src);
		json.addProperty("msg", msg);
		return json.toString();
	}

	@RequestMapping("MongoDB")
	public ModelAndView mongoDB(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/index/index");
		 try{
	       // 连接到 mongodb 服务
	       MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	       // 连接到数据库
	       DB db = mongoClient.getDB("first_mongodb");
	       // 链接集合（数据表）
	       DBCollection dbc = db.getCollection("col");
	       // 设置插入的文档对象
	       BasicDBObject bo = new BasicDBObject();
	       bo.put("ccc", "aaa");
	       bo.put("啊啊啊", 666);
	       // 插入对象
	       dbc.insert(bo);
	       // 查询对象
	       DBCursor rusult = dbc.find();
	       for (DBObject dbObject : rusult) {
	    	   System.out.println(dbObject);
	       }
	       // 关闭链接
	       mongoClient.close();
	       System.out.println("Connect to database successfully");

	      }catch(Exception e){
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	     }
		 mv.addObject("username", "aaa");
		 return mv;
	}

}
