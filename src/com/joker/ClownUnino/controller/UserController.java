package com.joker.ClownUnino.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.ClownUnino.service.UserService;
import com.joker.ClownUnino.util.BASE64;
import com.joker.ClownUnino.util.MD5;

@WebServlet("/loginServlet")
public class UserController extends HttpServlet{
	
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��ȡ�û���������
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//MD5����
		password = MD5.getResult(password);
		
		try {
			//BASE64 ����
			String newpassword = BASE64.encryptBASE64(password.getBytes());
			
			//��¼����
			boolean result = userService.login(username, newpassword);
			//�жϣ�������ͬ����Ӧ
			if(result) {
				//��¼�ɹ�
				req.getRequestDispatcher("/main.jsp").forward(req, resp);
			}else {
				//��¼ʧ��
				req.setAttribute("error", "�û��������������");
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
