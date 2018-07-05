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
		//获取用户名、密码
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//MD5加密
		password = MD5.getResult(password);
		
		try {
			//BASE64 编码
			String newpassword = BASE64.encryptBASE64(password.getBytes());
			
			//登录处理
			boolean result = userService.login(username, newpassword);
			//判断，产生不同的响应
			if(result) {
				//登录成功
				req.getRequestDispatcher("/main.jsp").forward(req, resp);
			}else {
				//登录失败
				req.setAttribute("error", "用户名或者密码错误！");
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
