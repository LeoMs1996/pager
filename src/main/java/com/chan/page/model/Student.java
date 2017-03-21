package com.chan.page.model;

import java.io.Serializable;
import java.util.Map;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3897923233573876531L;

	private int id;		//学生id
	private String address;		//学生住址
	private String stuName;		//学生姓名
	private int age;		//学生年龄
	private int gender;		//学生性别

	public Student() {
		super();
	}

	public Student(int id, String address, String stuName, int age, int gender) {
		super();
		this.id = id;
		this.address = address;
		this.stuName = stuName;
		this.age = age;
		this.gender = gender;
	}
	
	public Student(Map<String, Object> map)
	{
		this.id = Integer.parseInt(String.valueOf(map.get("id")));
		this.stuName = (String)map.get("stu_name");
		this.age = Integer.parseInt(String.valueOf(map.get("age")));
		this.gender = Integer.parseInt(String.valueOf(map.get("gender")));
		this.address = (String)map.get("address");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
