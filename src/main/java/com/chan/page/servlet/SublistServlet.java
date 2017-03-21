package com.chan.page.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chan.page.constant.Constant;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;
import com.chan.page.service.StudentService;
import com.chan.page.service.SublistStudentServiceImpl;
import com.chan.page.util.StringUtil;

public class SublistServlet extends HttpServlet {

	private static final long serialVersionUID = -7786800317670224170L;
	
	private StudentService studentService = new SublistStudentServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//接收request里的参数
		String stuName = request.getParameter("stuName");   //学生姓名
		
		//获取学生性别
		int gender = Constant.DEFAULT_GENDER;
		String genderStr = request.getParameter("gender");
		if(genderStr != null && !"".equals(genderStr))
		{
			gender = Integer.parseInt(genderStr);
		}
		
		//校验pageNum参数输入合法性
		String pageNumStr = request.getParameter("pageNum");
		if(pageNumStr != null && !StringUtil.isNum(pageNumStr))
		{
			request.setAttribute("errorMsg", "参数传输错误");
			request.getRequestDispatcher("sublistStudent.jsp").forward(request, response);
			return;
		}
		
		int pageNum = Constant.DEFAULT_PAGE_NUM;
		if(pageNumStr != null && !"".equals(pageNumStr))
		{
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = Constant.DEFAULT_PAGE_SIZE;
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr != null && "".equals(pageSizeStr.trim()))
		{
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		Student searchModel = new Student();
		searchModel.setStuName(stuName);
		searchModel.setGender(gender);
		
		Pager<Student> result = studentService.findStudent(searchModel, pageNum, pageSize);
		
		request.setAttribute("result", result);
		request.setAttribute("stuName", stuName);
		request.setAttribute("gender", gender);
		
		request.getRequestDispatcher("sublistStudent.jsp").forward(request, response);
	}
	
	
}
