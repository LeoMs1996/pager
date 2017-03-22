package com.chan.page.service;


import com.chan.page.dao.JdbcSqlStudentDaoImpl;
import com.chan.page.dao.StudentDao;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;

public class JdbcSqlStudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	
	public JdbcSqlStudentServiceImpl() {
		studentDao = new JdbcSqlStudentDaoImpl();
	}
	
	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum, int pageSize) {
		Pager<Student> result = studentDao.findStudent(searchModel, 
														pageNum, pageSize);
		return result;
	}

}
