package com.learn.issuetracker.model;

/*
 * Model class for storing Employee details. Complete the code as per the comments given below
*/
public class Employee {

	private int emplId;
	private String name;
	private String location;

	public Employee() {
		// Default Constructor
		this.emplId = 0;
		this.name = "";
		this.location = "";
	}

	/*
	 * Complete the parameterized Constructor
	 */
	public Employee(int emplId, String name, String location) {
		this.emplId = emplId;
		this.name = name;
		this.location = location;
	}

	/*
	 * Override toString() here . The toString() should return the employee details
	 * in the below format
	 * 
	 * Employee : {Employee Id : xxx; Name : xxxx; Location : xxxxx}
	 */

	@Override
	public String toString() {
		return "Employee : {Employee Id : " + this.emplId+ "; Name : " + this.name + "; Location : " + this.location +"}";
	}

	/*
	 * Complete the Getter and Setters
	 */
	public int getEmplId() {
		return this.emplId;
	}

	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}