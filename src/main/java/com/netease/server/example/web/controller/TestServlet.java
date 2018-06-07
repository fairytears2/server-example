package com.netease.server.example.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8700326875081023475L;

	@Override
	public void init() throws ServletException {
		System.out.println("init /hello");
	}

//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		System.out.println("service method");
//		super.service(req, resp);
//	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet method");
		PrintWriter pw = resp.getWriter();
		pw.print("/hello");
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		login(request, response);
		}

	public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String name = null;
        HttpSession session = request.getSession();
        String password = (String) session.getAttribute("userPassword"); 
        if(password!=null) {
        	System.out.println("密码为："+password);
//        	session.invalidate();
        }else {
        	session.setAttribute("userPassword", userPassword);; 
        } 
        userPassword = (String) session.getAttribute("userPassword");
        Cookie usernamecookie = new Cookie("userName", userName);
        
        usernamecookie.setMaxAge(30*60);       
        response.addCookie(usernamecookie);
        
        Cookie[] cookies = request.getCookies(); 
        if(cookies!=null) {
        	for (Cookie cookie:cookies) {
        		if(cookie.getName().equals("userName")) {
        			userName = cookie.getValue();
        		}
        		}
        	}
		if(userName!=name) {
			System.out.println("当前用户："+userName);
		}

	try {
        if (userName.equals("123") && userPassword.equals("123")) {
            PrintWriter writer = response.getWriter();
            writer.println("<html>");
            writer.println("<head><title>用户中心</title></head>");
            writer.println("<body>");
            writer.println("<p>用户名：" + userName + "</p>");
            writer.println("<p>用户说明：" + userPassword + "</p>");
            writer.println("</body>");
            writer.println("</html>");
            response.setContentType("text/html;charset=UTF-8");
            writer.close();
        }else {
            dispatcher = request.getRequestDispatcher("/error.html");
            dispatcher.forward(request, response);
        }
    } catch (Exception e) {
        e.printStackTrace();
        dispatcher = request.getRequestDispatcher("/error.html");
        dispatcher.forward(request, response);
    }
}
 
}
        
