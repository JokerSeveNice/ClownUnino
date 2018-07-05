package com.joker.ClownUnino.service;

import com.joker.ClownUnino.dao.UserDao;
import com.joker.ClownUnino.entity.User;
import com.joker.ClownUnino.util.BASE64;
import com.joker.ClownUnino.util.MD5;

public class UserService {

	private UserDao userDao = new UserDao();
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return true登录成功   false登录失败
	 */
	public boolean login(String username,String password) {
		User user = userDao.queryUserbyUandP(username, password);
		if(user!=null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 注册用户
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean addUserInfo(String username,String password) {
		//对密码进行MD5加密
		password = MD5.getResult(password);
		//BASE64 编码转换
		try {
			password = BASE64.encryptBASE64(password.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag= userDao.insertUser(username, password);
		return flag;
		
	}
	
	/**
	 * 用户名是否已存在
	 * @param username
	 * @return
	 */
	public User getUserValue(String username) {
		
		User user = userDao.UserValue(username);
		return user;
		
	}
}
