package com.joker.ClownUnino.util;
/**
 * JDBC�����࣬��װ�����ӺͶϿ��ȹ��õķ���
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
	
	//Ϊ��̬��Ա������ʼ��ֵ
	//��̬�飡����������
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
	 * �������ݿ⣬�������ݿ����Ӷ���
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
	 * �ر����ݿ����ӣ��ͷ���Դ
	 * @param con ���ݿ����Ӷ���
	 * @param st sql������
	 * @param rs ���������
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
	 * ͨ�õ���ɾ�ķ���
	 * @param sql sql���
	 * @param params �󶨱�����ֵ
	 */
	public static boolean executeUpdate(String sql,Object...params){
		boolean flag=false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = JDBCUtil.getConnection();
			//��ȡԤ����sql������
			ps = con.prepareStatement(sql);
			//��ֵ
			if(params!=null&&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1,params[i]);
				}
			}
			//����
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
	 * ��ѯ�����ص���ʵ��
	 * @param cls
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Object get(Class cls,String sql,Object... params){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//��ȡ����
		con = getConnection();
		//sql������
		try {
			ps = con.prepareStatement(sql);
			//Ϊ�󶨱���(?)��ֵ
			if(params!=null&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			//ִ�в�ѯ
			rs = ps.executeQuery();
			if(rs.next()){
				//��װ��ѯ����������
				//����(Class����)  ��������   Ϊ��Ա������ֵ
				//��������
				Object obj = cls.newInstance();
				//Ϊ��Ա������ֵ
				//��ȡ�����еĳ�Ա����
				Field[] fields = cls.getDeclaredFields();
				outer:for(Field field:fields){
					//���ĳ�Ա�����Ŀɼ���
					field.setAccessible(true);
					//��ȡ��Ա����������
					String fieldName = field.getName();
					
					//�ж� ���ֶ��Ƿ��ڲ�ѯ����д���
					ResultSetMetaData rsmd = rs.getMetaData();
					//��ȡ������
					int colunmCount = rsmd.getColumnCount();
					for(int i=1;i<=colunmCount;i++){
						String columnLabel = rsmd.getColumnLabel(i);
						if(fieldName.equals(columnLabel)){
							//��ֵ
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
	 * ��ȡ����
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
		//��ȡ����
		con = getConnection();
		//sql������
		try {
			ps = con.prepareStatement(sql);
			//Ϊ�󶨱���(?)��ֵ
			if(params!=null&params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			//ִ�в�ѯ
			rs = ps.executeQuery();
			while(rs.next()){
				//��װ��ѯ����������
				//����(Class����)  ��������   Ϊ��Ա������ֵ
				//��������
				Object obj = cls.newInstance();
				//Ϊ��Ա������ֵ
				//��ȡ�����еĳ�Ա����
				Field[] fields = cls.getDeclaredFields();
				outer:for(Field field:fields){
					//���ĳ�Ա�����Ŀɼ���
					field.setAccessible(true);
					//��ȡ��Ա����������
					String fieldName = field.getName();
					
					//�ж� ���ֶ��Ƿ��ڲ�ѯ����д���
					ResultSetMetaData rsmd = rs.getMetaData();
					//��ȡ������
					int colunmCount = rsmd.getColumnCount();
					for(int i=1;i<=colunmCount;i++){
						String columnLabel = rsmd.getColumnLabel(i);
						if(fieldName.equals(columnLabel)){
							//��ֵ
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