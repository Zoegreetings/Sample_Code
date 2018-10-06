package edu.gatech.saad.p3.model;

public class LoginDetail {

	String userName;
	String password;
	String role;

	Boolean authenticated = true;

	public LoginDetail(String userName, String password, String role,
			Boolean authenticated) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.authenticated = authenticated;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "LoginDetail [userName=" + userName + ", password=" + password
				+ ", role=" + role + "]";
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

}
