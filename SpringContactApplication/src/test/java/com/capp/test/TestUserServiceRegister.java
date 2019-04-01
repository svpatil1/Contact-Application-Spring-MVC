package com.capp.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.capp.config.SpringRootConfig;
import com.capp.dao.UserDAO;
import com.capp.domain.User;
import com.capp.service.UserService;

public class TestUserServiceRegister {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringRootConfig.class);
		UserService userService = ctx.getBean(UserService.class);

		User u = new User();
		u.setName("Amit");
		u.setPhone("9890368940");
		u.setAddress("Pune");
		u.setPassword("s123");
		u.setRole(userService.ROLE_ADMIN);
		u.setLoginName("AmitPatil");
		u.setEmail("Amit45@gmail.com");
		u.setLoginStatus(userService.LOGIN_STATUS_ACTIVE);
		userService.register(u);
		System.out.println("Info registered in database");

	}

}
