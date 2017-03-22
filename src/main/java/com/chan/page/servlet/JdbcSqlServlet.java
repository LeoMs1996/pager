package com.chan.page.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chan.page.constant.Constant;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;
import com.chan.page.service.JdbcSqlStudentServiceImpl;
import com.chan.page.service.StudentService;
import com.chan.page.util.StringUtil;

public class JdbcSqlServlet extends HttpServlet {

	/**
	 * 
	 */
	
	private StudentService studentService = new JdbcSqlStudentServiceImpl();
	private static final long serialVersionUID = -3272074367359557591L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stuName = request.getParameter("stuName");
		
		int gender = Constant.DEFAULT_GENDER;
		String genderStr = request.getParameter("gender");
		if(genderStr != null && !"".equals(genderStr.trim()))
		{
			gender = Integer.parseInt(genderStr);
		}
		
		String pageNumStr = request.getParameter("pageNum");
		if(pageNumStr != null && !StringUtil.isNum(pageNumStr))
		{
			request.setAttribute("errorMsg", "参数传输错误");
			request.getRequestDispatcher("jdbcSqlStudent.jsp").forward(request, response);
			return;
		}
		
		int pageNum = Constant.DEFAULT_PAGE_NUM;
		if(pageNumStr != null && !"".equals(pageNumStr))
		{
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = Constant.DEFAULT_PAGE_SIZE;
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr != null && !"".equals(pageSizeStr))	
		{
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		Student searchModel = new Student();
		searchModel.setGender(gender);
		searchModel.setStuName(stuName);
		
		Pager<Student> result = studentService.findStudent(searchModel, pageNum, pageSize);
		
		request.setAttribute("result", result);
		request.setAttribute("stuName", stuName);
		request.setAttribute("gender", gender);
		
		request.getRequestDispatcher("jdbcSqlStudent.jsp").forward(request, response);

	}
	
}
