package com.capp.command;
/**
 * Role of command: it takes data from Controller to View and vice versa
 * @author sanika
 *
 */


public class LoginCommand {
	
	private String loginName;
	private String password;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}


