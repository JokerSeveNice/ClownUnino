package com.joker.ClownUnino.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.ClownUnino.entity.User;
import com.joker.ClownUnino.service.UserService;

@WebServlet("/registerServlet")
public class RegisterController extends HttpServlet{

	private UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User usernames = userService.getUserValue(username);
		if(usernames!=null) {
			req.setAttribute("errors", "此用户已存在！");
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
		}else {
			boolean flag =  userService.addUserInfo(username, password);
			if(flag) {
				//req.getSession().setAttribute("user", userService.getUserValue(username));
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}else {
				req.setAttribute("error", "注册失败！");
			}
		}
	}
}
