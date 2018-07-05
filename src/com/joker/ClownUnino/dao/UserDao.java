package com.joker.ClownUnino.dao;

import java.util.List;

import com.joker.ClownUnino.entity.User;
import com.joker.ClownUnino.util.JDBCUtil;
public class UserDao {

	/**
	 * �����û����������ѯ�������û���Ϣ
	 * @param username
	 * @param password
	 * @return  null��ʾ�û����������
	 */
	public User queryUserbyUandP(String username,String password) {
		User user = (User)JDBCUtil.get(User.class, "select * from user where username=? and password=?", username,password);
		if(user!=null) {
			return user;
		}
		return null;	
	}
	
	/**
	 * �û�ע��
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean insertUser(String username,String password) {
		boolean flag = JDBCUtil.executeUpdate("insert into user(username,password) values(?,?)",username,password);
		return flag;
	}
	
	/**
	 * ��ѯ���û����Ƿ����
	 * @param username
	 * @return
	 */
	public User UserValue(String username) {
		User user = (User)JDBCUtil.get(User.class, "select * from user where username=?", username);
		return user;
		
	}
}
