package com.wgc.mfaces.model;

import java.util.Date;
/**
 * 班级表
 * @author Administrator
 *
 */

public class NewsInfo {
private int id;
private String newsTitle;
private String newsContent;
private Date newsTime;
private int newsLB;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNewsTitle() {
	return newsTitle;
}
public void setNewsTitle(String newsTitle) {
	this.newsTitle = newsTitle;
}
public String getNewsContent() {
	return newsContent;
}
public void setNewsContent(String newsContent) {
	this.newsContent = newsContent;
}
public Date getNewsTime() {
	return newsTime;
}
public void setNewsTime(Date newsTime) {
	this.newsTime = newsTime;
}
public int getNewsLB() {
	return newsLB;
}
public void setNewsLB(int newsLB) {
	this.newsLB = newsLB;
}



}
