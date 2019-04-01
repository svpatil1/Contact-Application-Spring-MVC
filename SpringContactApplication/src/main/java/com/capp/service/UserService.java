package com.capp.service;

import java.util.List;

import com.capp.domain.User;
import com.capp.exception.UserBlockedException;

// All user related business logic related to User will be provided here like user registration and login

public interface UserService {
	
	public static final Integer LOGIN_STATUS_ACTIVE = 1;
	public static final Integer LOGIN_STATUS_BLOCKED = 2;
	
	public static final Integer ROLE_ADMIN = 1;
	public static final Integer ROLE_USER = 2;
	
	/**
	 * The method handles the user registration task.
	 * @param u the new user detail as User object.
	 */
			
	public void register(User u);
	
	/**
	 * the method handles login operation(authentication) using given credentials.
	 * It returns User object when success and null when failed.
	 * When user is blocked, the exception will be thrown.
	 * @param loginName
	 * @param password
	 * @throws UserBlockedException when user account is blocked
	 * @return
	 */
	public User login(String loginName, String password) throws UserBlockedException;
	
	/**
	 * Call this method to get list of registered Users.
	 * @return
	 */
	public List<User> getUserList();
	
	/**
	 * This method change the user login status for detials of the user passed in argument
	 * @param userId
	 * @param loginStatus
	 */
	public void changeLoginStatus(Integer userId, Integer loginStatus);

}
