package com.joker.ClownUnino.util;
/**
 * JDBC工具类，封装了连接和断开等公用的方法
 * @author Administrator
 *
 */

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;




public class JDBCUtil{
	
	
	private static String classname;
	private static String url;
	private static String username;
	private static String password;
	
	//为静态成员变量初始化值
	//静态块！！！！！！
	static{
		Properties p = new Properties();
		InputStream in = null;
		try {
			in = JDBCUtil.class.getResourceAsStream("/com/joker/ClownUnino/util/jdbc.properties");
			p.load(in);
		
			classname=p.getProperty("classname");
			url=p.getProperty("url");
			username=p.getProperty("username");
			password=p.getProperty("password");
			
			
//			driverClass = p.getProperty("classname");
//			url = p.getProperty("url");
//			user = p.getProperty("username");
//			password = p.getProperty("password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	/**
	 * 连接数据库，返回数据库连接对象
	 * @return
	 */
	public static Connection getConnection(){
		try {
			try {
				Class.forName(classname);
				Connection con=DriverManager.getConnection(url,username,password);
				return con;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭数据库连接，释放资源
	 * @param con 数据库连接对象
	 * @param st sql语句对象
	 * @param rs 结果集对象
	 */
	public static void shutdownConnection(Connection con,Statement st,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用的增删改方法
	 * @param sql sql语句
	 * @param params 绑定变量的值
	 */
	public static boolean executeUpdate(String sql,Object...params){
		boolean flag=false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = JDBCUtil.getConnection();
			//获取预编译sql语句对象
			ps = con.prepareStatement(sql);
			//赋值
			if(params!=null&&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1,params[i]);
				}
			}
			//发送
			ps.executeUpdate();
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.shutdownConnection(con, ps, null);
		}
		return flag;
	}
	
	/**
	 * 查询并返回单个实体
	 * @param cls
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Object get(Class cls,String sql,Object... params){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//获取连接
		con = getConnection();
		//sql语句对象
		try {
			ps = con.prepareStatement(sql);
			//为绑定变量(?)赋值
			if(params!=null&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			//执行查询
			rs = ps.executeQuery();
			if(rs.next()){
				//封装查询出来的数据
				//反射(Class对象)  创建对象   为成员变量赋值
				//创建对象
				Object obj = cls.newInstance();
				//为成员变量赋值
				//获取到所有的成员变量
				Field[] fields = cls.getDeclaredFields();
				outer:for(Field field:fields){
					//更改成员变量的可见性
					field.setAccessible(true);
					//获取成员变量的名称
					String fieldName = field.getName();
					
					//判断 该字段是否在查询结果中存在
					ResultSetMetaData rsmd = rs.getMetaData();
					//获取总列数
					int colunmCount = rsmd.getColumnCount();
					for(int i=1;i<=colunmCount;i++){
						String columnLabel = rsmd.getColumnLabel(i);
						if(fieldName.equals(columnLabel)){
							//赋值
							field.set(obj, rs.getObject(fieldName));
							continue outer;
						}
					}
					
				}
				return obj;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally{
			shutdownConnection(con, ps, rs);
		}
		return null;
	}
	
	
	/**
	 * 获取所有
	 * @param cls
	 * @param sql
	 * @param params
	 * @return
	 */
	
	public static List<Object> getAll(Class cls,String sql,Object... params){
		List<Object> list=new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//获取连接
		con = getConnection();
		//sql语句对象
		try {
			ps = con.prepareStatement(sql);
			//为绑定变量(?)赋值
			if(params!=null&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			//执行查询
			rs = ps.executeQuery();
			while(rs.next()){
				//封装查询出来的数据
				//反射(Class对象)  创建对象   为成员变量赋值
				//创建对象
				Object obj = cls.newInstance();
				//为成员变量赋值
				//获取到所有的成员变量
				Field[] fields = cls.getDeclaredFields();
				outer:for(Field field:fields){
					//更改成员变量的可见性
					field.setAccessible(true);
					//获取成员变量的名称
					String fieldName = field.getName();
					
					//判断 该字段是否在查询结果中存在
					ResultSetMetaData rsmd = rs.getMetaData();
					//获取总列数
					int colunmCount = rsmd.getColumnCount();
					for(int i=1;i<=colunmCount;i++){
						String columnLabel = rsmd.getColumnLabel(i);
						if(fieldName.equals(columnLabel)){
							//赋值
							field.set(obj, rs.getObject(fieldName));
							continue outer;
						}
					}
					
				}
				list.add(obj);
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally{
			shutdownConnection(con, ps, rs);
		}
		return null;
	}
	
	
	
}