package com.chan.page.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;







public class JdbcUtil {

		//表示定义数据库的用户名
		private static String USERNAME;
		
		//表示定义数据库的密码
		private static String PASSWORD;
		
		//表示定义数据库的驱动信息
		private static String DRIVER;
		
		//表示定义数据库的地址
		private static String URL;
		
		//表示定义数据库的链接
		private static Connection connection = null;
		
		//定义sql语句的执行对象
		private PreparedStatement  preparedStatement;
		
		//定义查询返回的结果集合
		private ResultSet resultSet;
		
		static
		{
			loadConfig();
		}
		
		
		public JdbcUtil() {
			
		}

		/**
		 *    加载配置数据库信息，并给相关属性赋值
		 */
		public static void loadConfig()
		{
			
			try {
				
				InputStream inputStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
				Properties properties = new Properties();
				properties.load(inputStream);
				USERNAME = properties.getProperty("jdbc.username");
				PASSWORD = properties.getProperty("jdbc.password");
				DRIVER = "com.mysql.jdbc.Driver";
				URL = properties.getProperty("jdbc.url");
				
			} catch (Exception e) {
				throw new RuntimeException("读取数据库配置文件异常",e);
			}	
		}
		
		/**
		 * @return 返回数据库连接，这样写的话每次应该先实例化该类，然后调用getConnection方法，才可以调用下面两个方法
		 */
		public Connection getConnection()
		{
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			} 
			catch (Exception e) {
				throw new RuntimeException("get connection error!", e);
			}
			return connection;
		}
		
		/**
		 * 执行更新操作
		 * @param sql	sql语句
		 * @param params	执行参数
		 * @return		执行结果
		 * @throws SQLException
		 */
		public boolean updateByPreparedStatement(String sql, List<?> params) throws SQLException
		{
			boolean flag = false;
			int result = -1; //表示当用户执行添加删除和修改的时候所影响数据库的行数
			preparedStatement = connection.prepareStatement(sql);
			int index = 1;
			//填充sql语句中的占位符
			if(params != null && !params.isEmpty())
			{
				for(int i = 0; i < params.size(); i++)
				{
					preparedStatement.setObject(index++, params.get(i));
				}
			}
			result = preparedStatement.executeUpdate();
			flag = result > 0 ? true : false;
			return flag;
		}
		
		/**
		 * 执行查询操作
		 * @param sql	sql语句
		 * @param params	执行参数
		 * @return		
		 * @throws SQLException 
		 */
		public List<Map<String, Object>> findResult(String sql, List<?> params) throws SQLException
		{
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			int index = 1;
			preparedStatement = connection.prepareStatement(sql);
			if(params != null && !params.isEmpty())
			{
				for(int i = 0; i < params.size(); i++)	
				{
					preparedStatement.setObject(index++, params.get(i));
				}
			}
			resultSet = preparedStatement.executeQuery();
			//可用于获取关于 ResultSet 对象中列的类型和属性信息的对象,
			//创建 ResultSetMetaData 对象 metadata，并使用 metadata 查找 rs 有多少列
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while(resultSet.next())
			{
				Map<String, Object> map = new HashMap<String, Object>();
				//循环操作，将每一个对象中所有列类型和对应属性信息写入map中，然后将map写入list中
				for(int i = 0; i < cols_len; i++)
				{
					String cols_name = metaData.getColumnName(i+1);
					Object cols_value = resultSet.getObject(cols_name);
					if(cols_value == null)
					{
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				list.add(map);
			}
			return list;
		}
		
		/**
		 * 释放资源
		 */
		public void releaseConn()
		{
			if(resultSet != null)
			{
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(preparedStatement != null)
			{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
}
