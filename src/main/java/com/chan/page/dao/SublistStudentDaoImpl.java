package com.chan.page.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chan.page.constant.Constant;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;
import com.chan.page.util.JdbcUtil;


public class SublistStudentDaoImpl implements StudentDao {

	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum, int pageSize) {
		List<Student> allStudentList = getAllStudent(searchModel);
		
		Pager<Student> pager = new Pager<Student>(pageNum, pageSize, allStudentList);
		
		return pager;
	}
	
	private static List<Student> getAllStudent(Student searchModel)
	{
		List<Student> result = new ArrayList<Student>();
		List<Object> paramList = new ArrayList<Object>();
		
		String stuName = searchModel.getStuName();
		int gender = searchModel.getGender();
		
		StringBuilder sql = new StringBuilder("select * from t_student where 1=1");
		
		if(stuName != null && !stuName.equals(""))
		{
			sql.append(" and stu_name like ?");
			paramList.add("%" + stuName + "%");
		}
		if(gender == Constant.GENDER_FEMALE || gender == Constant.GENDER_MALE)
		{
			sql.append(" and gender = ?");
			paramList.add(gender);
		}
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection();
			List<Map<String, Object>> list= jdbcUtil.findResult(sql.toString(), paramList);
			if(list != null)
			{
				for(Map<String, Object> map: list)
				{
					Student student = new Student(map);
					result.add(student);
				}
			}
		} catch (SQLException e) {
			
			throw new RuntimeException("查询数据异常", e);
		}finally {
			if(jdbcUtil != null)
			{
				jdbcUtil.releaseConn();     //释放资源
			}
		}
		return result;
	}
}
