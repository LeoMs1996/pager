package com.chan.page.service;

import com.chan.page.dao.StudentDao;
import com.chan.page.dao.SublistStudentDaoImpl;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;

public class SublistStudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	
	
	public SublistStudentServiceImpl() {
		
		//创建service实例时，初始化DAO对象
		studentDao = new SublistStudentDaoImpl();
	}

	public StudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum, int pageSize) {
		
		Pager<Student> result = studentDao.findStudent(searchModel, pageNum, pageSize);
		return result;
	}

}
