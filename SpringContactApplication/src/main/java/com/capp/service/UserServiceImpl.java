package com.capp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.capp.dao.BaseDAO;
import com.capp.dao.UserDAO;
import com.capp.domain.User;
import com.capp.exception.UserBlockedException;
import com.capp.rm.UserRowMapper;

import com.capp.service.UserService;
 
@Service
public class UserServiceImpl extends BaseDAO implements UserService {

	@Autowired
	private UserDAO userDAO;  // dependency injection by using autowired annotation
	
	public void register(User u) {
		userDAO.save(u);
		
	}
	
	/**
	  * This method handles login operation (authentication) using given credentials
	  * When succesfully logged in , it return sUser object else null
	  * When user account is blocked, an exception will be thrown by this method.
	  */

	public User login(String loginName, String password) throws UserBlockedException {
		String sql = "SELECT userId, name, phone, email, address, role, loginStatus, loginName"
                + " FROM user WHERE loginName=:ln AND password=:pw";
		
		Map m = new HashMap();
		m.put("ln", loginName);
		m.put("pw", password);
		
		try{
			User u = getNamedParameterJdbcTemplate().queryForObject(sql, m, new UserRowMapper());
			if(u.getLoginStatus().equals(UserService.LOGIN_STATUS_BLOCKED)) {
				throw new UserBlockedException("Your accout has been blocked. Contact to admin.");
			}
			else {
				return u;
			}
		}catch(EmptyResultDataAccessException ex) {
			return null;	
		}
		
	}

	public List<User> getUserList() {
		return userDAO.findByProperty("role", UserService.ROLE_USER);
	}

	public void changeLoginStatus(Integer userId, Integer loginStatus) {
		// TODO Auto-generated method stub
		
	}

	
}
