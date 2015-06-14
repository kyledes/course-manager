package edu.paymentus.domain;

public class Course {
	
	private long id;
	private String courseName = "";
	private String location = "";
	private int credit;
	
	public Course(){}

	public Course (long id, String courseName, String location, int credit){
		this.id = id;
		this.courseName = courseName;
		this.location = location;
		this.credit = credit;
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Object[] getFields(){
		return new Object[] {this.getCourseName(), this.getLocation(), this.getCredit(), this.getId()};
	}
}
