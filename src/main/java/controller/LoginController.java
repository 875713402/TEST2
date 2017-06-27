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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import entity.LoginForm;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

@Controller()
public class LoginController {
	private final String AK = "z2xHZEgi1GJ1lUyZv2pPOXEmWyy8M6_oOKUhG8z3";
	private final String SK = "k86grogaXK2Y0IE7D7lMsLBBEiivsbmVqeem5RPV";
	private final String BUCKET = "bilibili";
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
	
	/*@RequestMapping(value="upload", method = RequestMethod.POST)
	public ModelAndView upload(){
		ModelAndView mv = new ModelAndView("/result/result");
		String msg = new String();
		String upToken = Auth.create(AK, SK).uploadToken(BUCKET);
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		if(file.length()>1024*1024*200){
			msg = "fail:文件大小大于200M,请重新上传！";
		}else{
			try {
				Response responsed = uploadManager.put(file, file.getName(), upToken);
				//解析上传成功返回的结果
			    DefaultPutRet putRet = new Gson().fromJson(responsed.bodyString(), DefaultPutRet.class);
			    msg = "success:" + putRet.key;
			} catch (QiniuException e) {
				e.printStackTrace();
				msg = "fail:" + e;
			} 
		}
		mv.addObject("result", msg);
		return mv;
	}*/
}
