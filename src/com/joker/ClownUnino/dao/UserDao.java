package com.joker.ClownUnino.dao;

import java.util.List;

import com.joker.ClownUnino.entity.User;
import com.joker.ClownUnino.util.JDBCUtil;
public class UserDao {

	/**
	 * 根据用户名和密码查询并返回用户信息
	 * @param username
	 * @param password
	 * @return  null表示用户和密码错误
	 */
	public User queryUserbyUandP(String username,String password) {
		User user = (User)JDBCUtil.get(User.class, "select * from user where username=? and password=?", username,password);
		if(user!=null) {
			return user;
		}
		return null;	
	}
	
	/**
	 * 用户注册
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
	 * 查询该用户名是否存在
	 * @param username
	 * @return
	 */
	public User UserValue(String username) {
		User user = (User)JDBCUtil.get(User.class, "select * from user where username=?", username);
		return user;
		
	}
}
