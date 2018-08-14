package com.taotao.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UploadResult implements Serializable{
	
	private String code;
	
	private String message;
	
	private String url;
	
	private static Map<String,Object> map=new HashMap<>();
	
	public UploadResult add(String key,Object value){
		this.getMap().put(key, value);
		
		return this;
	}

	public static UploadResult success(String url){
		UploadResult uploadResult=new UploadResult();
		uploadResult.setCode("0");
		uploadResult.setMessage("上传成功！");
		
		uploadResult.setUrl(url);
		
		return uploadResult;
	}

	public static UploadResult error(String msg){
		UploadResult uploadResult=new UploadResult();
		uploadResult.setCode("1");
		uploadResult.setMessage("上传失败！ "+msg);
		
		return uploadResult;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "UploadResult [code=" + code + ", message=" + message + ", url=" + url + ", map=" + map + "]";
	}

	public UploadResult(String code, String message, String url, Map<String, Object> map) {
		this.code = code;
		this.message = message;
		this.url = url;
		this.map = map;
	}

	public UploadResult() {
	}

}
