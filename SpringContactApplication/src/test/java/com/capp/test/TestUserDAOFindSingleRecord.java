package com.capp.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.capp.config.SpringRootConfig;
import com.capp.dao.UserDAO;
import com.capp.domain.User;

public class TestUserDAOFindSingleRecord {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringRootConfig.class);
		UserDAO userDAO = ctx.getBean(UserDAO.class);
		User u = userDAO.findById(7);
		System.out.println(u.getAddress());
		System.out.println(u.getEmail());
		System.out.println(u.getLoginName());
		System.out.println(u.getName());
		System.out.println(u.getPhone());
		System.out.println(u.getUserId());
		System.out.println("Find by Id executed");

	}

}
