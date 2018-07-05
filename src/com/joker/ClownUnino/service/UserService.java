package com.joker.ClownUnino.service;

import com.joker.ClownUnino.dao.UserDao;
import com.joker.ClownUnino.entity.User;
import com.joker.ClownUnino.util.BASE64;
import com.joker.ClownUnino.util.MD5;

public class UserService {

	private UserDao userDao = new UserDao();
	
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return true��¼�ɹ�   false��¼ʧ��
	 */
	public boolean login(String username,String password) {
		User user = userDao.queryUserbyUandP(username, password);
		if(user!=null) {
			return true;
		}
		return false;
	}
	
	/**
	 * ע���û�
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean addUserInfo(String username,String password) {
		//���������MD5����
		password = MD5.getResult(password);
		//BASE64 ����ת��
		try {
			password = BASE64.encryptBASE64(password.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag= userDao.insertUser(username, password);
		return flag;
		
	}
	
	/**
	 * �û����Ƿ��Ѵ���
	 * @param username
	 * @return
	 */
	public User getUserValue(String username) {
		
		User user = userDao.UserValue(username);
		return user;
		
	}
}
