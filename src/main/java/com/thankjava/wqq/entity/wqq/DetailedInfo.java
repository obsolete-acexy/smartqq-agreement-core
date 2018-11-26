package com.thankjava.wqq.entity.wqq;

import com.alibaba.fastjson.annotation.JSONField;

public class DetailedInfo {

	
	// 职位
	private String occupation;

	// 学校
	private String college;
	
	// qq号码 (腾讯已经移除了QQ号码的返回)
	@JSONField(name = "account")
	private String qq;
	
	// 个性签名
	private String lnick;
	
	// 主页
	private String homepage;
	
	// 邮件地址
	private String email;
	
	// 地址
	private String province;
	
	// 生日
	private Birthday birthday;

	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLnick() {
		return lnick;
	}
	public void setLnick(String lnick) {
		this.lnick = lnick;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Birthday getBirthday() {
		return birthday;
	}
	public void setBirthday(Birthday birthday) {
		this.birthday = birthday;
	}

	public class Birthday{
		
		private Integer year;
		private Integer month;
		private Integer day;
		public Integer getYear() {
			return year;
		}
		public void setYear(Integer year) {
			this.year = year;
		}
		public Integer getMonth() {
			return month;
		}
		public void setMonth(Integer month) {
			this.month = month;
		}
		public Integer getDay() {
			return day;
		}
		public void setDay(Integer day) {
			this.day = day;
		}
	}
}
