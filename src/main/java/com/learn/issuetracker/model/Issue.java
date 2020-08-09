package com.learn.issuetracker.model;

import java.time.LocalDate;

/*
 * Model class for storing Issue Object
*/

public class Issue {

	private String issueId;
	private String summary;
	private LocalDate createdOn;
	private LocalDate expectedResolutionOn;
	private String priority;
	private String status;
	private Employee assignedTo;

	public Issue() {
		//Default Constructor
		this.issueId = "";
		this.summary = "";
		this.createdOn = LocalDate.now();
		this.expectedResolutionOn = LocalDate.now();
	}

	/*
	 * Complete the parameterized Constructor
	 */
	public Issue(final String issueId, final String summary, final LocalDate createdOn, final LocalDate expectedResolutionOn, final String priority,
			final String status, final Employee assignedTo) {
			
			this.issueId = issueId;
			this.summary = summary;
			this.createdOn = createdOn;
			this.expectedResolutionOn = expectedResolutionOn;
			this.priority = priority;
			this.status = status;
			this.assignedTo = assignedTo;
	}

	public String getIssueId() {
		return this.issueId;
	}

	public void setIssueId(final String issueId) {
		this.issueId = issueId;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public LocalDate getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDate getExpectedResolutionOn() {
		return this.expectedResolutionOn;
	}

	public void setExpectedResolutionOn(final LocalDate expectedResolutionOn) {
		this.expectedResolutionOn = expectedResolutionOn;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Employee getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(final Employee assignedTo) {
		this.assignedTo = assignedTo;
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", summary=" + summary + ", createdOn=" + createdOn
				+ ", expectedResolutionOn=" + expectedResolutionOn + ", priority=" + priority + ", status=" + status
				+ ", assignedTo=" + assignedTo + "]";
	}
}