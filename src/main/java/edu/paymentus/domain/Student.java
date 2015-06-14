package edu.paymentus.domain;

import java.util.List;


public class Student {
	
	public Student(){}
	
	public Student(String fn) {
		this.firstName = fn;
	}
	public Student(Long id, String fn, String ln, String email) {
		this.setId(id);
		this.setFirstName(fn);
		this.setLastName(ln);
		this.setEmail(email);
	}
	private long id;
	private String firstName = "";
	private String lastName = "";
	private String email = "";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Object [] getFields(){
		return new Object [] { this.getFirstName(), this.getLastName(), this.getEmail(),  this.getId()};
	}


	
	
}
