package com.chan.page.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chan.page.constant.Constant;
import com.chan.page.model.Pager;
import com.chan.page.model.Student;
import com.chan.page.util.JdbcUtil;

public class JdbcSqlStudentDaoImpl implements StudentDao {

	@Override
	public Pager<Student> findStudent(Student searchModel, int pageNum, int pageSize) {
		
		Pager<Student> result = null;
		List<Object> paramList = new ArrayList<Object>();
		
		
		String stuName = searchModel.getStuName();
		int gender = searchModel.getGender();
		
		StringBuilder sql = new StringBuilder("select * from t_student where 1=1");
		StringBuilder countSql = new StringBuilder("select count(id) as totalRecord from t_student where 1=1");
		
		//获得总记录条数totalRecord
		if(stuName != null && !"".equals(stuName))
		{
			sql.append(" and stu_name like ?");
			countSql.append(" and stu_name like ?");
			paramList.add("%" + stuName + "%");
		}
		
		if(gender == Constant.GENDER_FEMALE || gender == Constant.GENDER_MALE)
		{
			sql.append(" and gender = ?");
			countSql.append(" and gender = ?");
			paramList.add(gender);
		}
		
		int fromIndex = pageSize * (pageNum - 1);
		
		sql.append(" limit " + fromIndex + ", " + pageSize);
		
		List<Student> studentList = new ArrayList<Student>();
		JdbcUtil jdbcUtil = null;
		
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection();
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			List<Map<String, Object>> studentResult = jdbcUtil.findResult(sql.toString(), paramList);
			if(studentResult != null)
			{
				for(Map<String, Object> map : studentResult)
				{
					Student student = new Student(map);
					studentList.add(student);
				}
			}
			
			int totalPage = totalRecord / pageSize;
			if(totalRecord / pageSize != 0)
			{
				totalPage++;
			}
			
			result = new Pager<Student>(pageSize, pageNum, totalRecord, totalPage, studentList);
		} catch (SQLException e) {
				throw new RuntimeException("查询所有数据异常", e);
		} finally {
			if(jdbcUtil != null)
			{
				jdbcUtil.releaseConn();
			}
		}
		
		
		
		//获得所有符合查询条件的学生对象
		
		return result;
	}

}
