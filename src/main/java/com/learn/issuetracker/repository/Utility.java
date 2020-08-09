package com.learn.issuetracker.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.learn.issuetracker.model.Employee;
import com.learn.issuetracker.model.Issue;

/*
 * This class has methods for parsing the String read from the files in to corresponding Model Objects
*/
public class Utility {
	
	private Utility() {
		//Private Constructor to prevent object creation
	}

	/*
	 * parseEmployee takes a string with employee details as input parameter and parses it in to an Employee Object 
	*/
	public static Employee parseEmployee(String employeeDetail) {
		String[] empData = employeeDetail.split(",");
		Employee E = new Employee(Integer.parseInt(empData[0]),empData[1],empData[2]);
		return E;
	}

	/*
	 * parseIssue takes a string with issue details and parses it in to an Issue Object. The employee id in the 
	 * Issue details is used to search for an an Employee, using EmployeeRepository class. If the employee is found
	 * then it is set in the Issue object. If Employee is not found, employee is set as null in Issue Object  
	*/

	public static Issue parseIssue(String issueDetail) {
		String[] issueData = issueDetail.split(",");
		if(issueData[0].startsWith("IS")) {
			DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate createdDate = LocalDate.parse(issueData[2],dtFormatter);
			LocalDate resolutionDate = LocalDate.parse(issueData[3],dtFormatter);
			Optional<Employee> E = EmployeeRepository.getEmployee(Integer.parseInt(issueData[6]));
			Issue I;

			if(E.isPresent()) {
				I = new Issue(issueData[0],issueData[1],createdDate,resolutionDate,issueData[4],issueData[5],E.get());
			} else {
				I = new Issue(issueData[0],issueData[1],createdDate,resolutionDate,issueData[4],issueData[5],null);
			}

			return I;
		}
		
		return null;
	}
}
