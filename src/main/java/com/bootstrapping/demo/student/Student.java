package com.bootstrapping.demo.student;

public class Student {

	private Integer Sid;
	private String Sname;
	public Integer getSid() {
		return Sid;
	}
	public void setSid(Integer sid) {
		Sid = sid;
	}
	public String getSname() {
		return Sname;
	}
	public void setSname(String sname) {
		Sname = sname;
	}
	public Student(int i, String sname) {
		Sid = i;
		Sname = sname;
	}
	
	
	
}
