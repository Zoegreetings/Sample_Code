package edu.gatech.saad.p3.model;

public class User extends Identified {

	private String firstName;
	private String lastName;
	private String username;
	private Integer deptId;
	private Integer roleId;

	public User(Integer id) {
		super(id);
	}

	public Integer getUserId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [userId=" + id + ", firstName=" + firstName
				+ ", lastNname=" + lastName + ", username=" + username
				+ ", deptId=" + deptId + ", roleId=" + roleId + "]";
	}

}
