package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({@NamedQuery(name="find_all_clients", query="select c from Client c"), 
@NamedQuery(name="find_by_age", query="select c from Client c where c.age =:age"),
@NamedQuery(name="find_by_gender", query="select c from Client c where c.gender =:gender"),
@NamedQuery(name="find_by_tobacco", query="select c from Client c where c.tobacco =:tobacco"),
@NamedQuery(name="find_by_zip", query="select c from Client c where c.zip_code =:zip_code"),
@NamedQuery(name="find_by_age_gender", query="select c from Client c where c.age =:age and c.gender = :gender"),
@NamedQuery(name="find_by_age_tobacco", query="select c from Client c where c.age =:age and c.tobacco = :tobacco"),
@NamedQuery(name="find_by_age_zip", query="select c from Client c where c.age =:age and c.zip_code = :zip_code"),
@NamedQuery(name="find_by_zip_gender", query="select c from Client c where c.zip_code =:zip_code and c.gender = :gender"),
@NamedQuery(name="find_by_zip_tobacco", query="select c from Client c where c.zip_code =:zip_code and c.tobacco = :tobacco"),
@NamedQuery(name="find_by_gender_tobacco", query="select c from Client c where c.gender =:gender and c.tobacco = :tobacco"),
@NamedQuery(name="find_by_age_zip_gender", query="select c from Client c where c.age =:age and c.zip_code =:zip_code and c.gender = :gender"),
@NamedQuery(name="find_by_age_zip_tobacco", query="select c from Client c where c.age =:age and c.zip_code =:zip_code and c.tobacco = :tobacco"),
@NamedQuery(name="find_by_age_gender_tobacco", query = "select c from Client c where c.age=:age and c.gender=:gender and c.tobacco=:tobacco"),
@NamedQuery(name="find_by_zip_gender_tobacco", query = "select c from Client c where c.zip_code=:zip_code and c.gender=:gender and c.tobacco=:tobacco"),
@NamedQuery(name="find_by_age_zip_gender_tobacco", query="select c from Client c where c.age=:age and c.zip_code =:zip_code and c.gender=:gender and c.tobacco=:tobacco")})
public class Client {
	@Id
	@GeneratedValue
	private int id;
	private String zip_code;
	private String age;
	private String gender;
	private String tobacco;
	protected Client() {

	}
	public Client(int id, String zip_code, String age, String gender, String tobacco) {
		super();
		this.id = id;
		this.zip_code = zip_code;
		this.age = age;
		this.gender = gender;
		this.tobacco = tobacco;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTobacco() {
		return tobacco;
	}
	public void setTobacco(String tobacco) {
		this.tobacco = tobacco;
	}
	@Override
	public String toString() {
		return "Client [zip_code=" + zip_code + ", age=" + age + ", gender=" + gender + ", tobacco=" + tobacco + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
