package com.capp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.capp.command.LoginCommand;
import com.capp.command.UserCommand;
import com.capp.domain.User;
import com.capp.exception.UserBlockedException;
import com.capp.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;  // userService is injected in UserController
	
	@RequestMapping(value = {"/", "/index"})
	public String index(Model m) {
		m.addAttribute("command", new LoginCommand());
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String handleLogin(@ModelAttribute("command") LoginCommand cmd, Model m, HttpSession session) {
		
		/**
		 * https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation.
		 * handleLogin method is called by dispatcher servlet(front controller). This controller will receive the request form user,
		 * read the data from the request parameter(Login details). And then will bind loginName and password to LoginCommand object.
		 * Then LoginCommand object will be given to controller method.
		 * i.e front controller binds the data to LoginCommand object, which can be directly used by me.
		 */
		
		try {
			User loggedInUser = userService.login(cmd.getLoginName(), cmd.getPassword());
			
			if(loggedInUser == null) {
				// add error message and go back to login form
				m.addAttribute("err", "Login Failed! Enter valid credentials");
				return "index" ; 	
			}
			else {
				//Successful login
				// check the role and redirect to appropriate dashboard
				
				if(loggedInUser.getRole().equals(UserService.ROLE_ADMIN)){
					//assign session to logged in user
					addUserInSession(loggedInUser, session);
					return "redirect:admin/dashboard";
				}
				else if(loggedInUser.getRole().equals(UserService.ROLE_USER)){	
					//assign session to logged in user
					addUserInSession(loggedInUser, session);
					return "redirect:user/dashboard";
					}
				else {
					// add error message and go back to login form
					m.addAttribute("err", "Invalid User ROLE");  // as it is added to the model, err msg is accessible in JSP as well.
					// whatever is added in model, is accessible on JSP
					return "index" ;
						
					}
				}
		} catch (UserBlockedException e) {
			// add error message and go back to login form
			m.addAttribute("err", e.getMessage());  // as it is added to the model, err msg is accessible in JSP as well.
			// whatever is added in model, is accessible on JSP
			return "index" ; 
		}
		
	}
	
	@RequestMapping(value = "/user/dashboard")
	public String userDashboard() {	
		return "dashboard_user";
	}
	
	@RequestMapping(value = "/admin/dashboard")
	public String adminDashboard() {
		return "dashboard_admin";
	}
	
	@RequestMapping(value = "/admin/users")
	public String getUserList(Model m) {
		m.addAttribute("userList", userService.getUserList());
		return "users";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index?act=lo";
	}
	
	private void addUserInSession(User u, HttpSession session) {
		
		session.setAttribute("user", u);
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("role", u.getRole());

	}
	
	@RequestMapping(value = "/reg_form")
	public String registrationForm(Model m) {
		UserCommand cmd = new UserCommand();
		m.addAttribute("command", cmd);
		return "reg_form";
	}
	
	@RequestMapping(value = "/register")
	public String registerUser(@ModelAttribute("command") UserCommand cmd, Model m) {
		try {
		User user = cmd.getUser();
		user.setRole(userService.ROLE_USER);
		user.setLoginStatus(userService.LOGIN_STATUS_ACTIVE);
		userService.register(user);
		return "redirect:index?act=reg";
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			m.addAttribute("err", "Username is already registered. Please select another username.");
			return "reg_form";
		}
			
	}
	

}
