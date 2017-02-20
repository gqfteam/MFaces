package com.wgc.mfaces.model;

import java.util.ArrayList;
import java.util.List;



public class ResultMessage<T>  {
	//数据返回结果 0成功  其他失败   
	private int type;    
	//返回结果
	private String result;
	//返回信息
	private String message;
	//返回数据
	private List<T> datas;
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
