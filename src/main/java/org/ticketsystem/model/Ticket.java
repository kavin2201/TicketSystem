package org.ticketsystem.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Kavin
 *
 */
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Integer id = 0;
	private String type;
	private String description;
	private String title;
	private String createdByUser;
	private String customer;
	private String assignedToUser;
	private String priority;
	private String status;
	private String customerMailId;
	@Basic
	private java.time.LocalDate localDate;
	@Basic
	private java.time.LocalDate resolvedDate;

	/**
	 * @return the resolvedDate
	 */
	public java.time.LocalDate getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate the resolvedDate to set
	 */
	public void setResolvedDate(java.time.LocalDate resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return the localDate
	 */
	public java.time.LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * @param localDate the localDate to set
	 */
	public void setLocalDate(java.time.LocalDate localDate) {
		this.localDate = localDate;
	}

	/**
	 * @return the customerMailId
	 */
	public String getCustomerMailId() {
		return customerMailId;
	}

	/**
	 * @param customerMailId the customerMailId to set
	 */
	public void setCustomerMailId(String customerMailId) {
		this.customerMailId = customerMailId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the createdByUser
	 */
	public String getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * @param createdByUser the createdByUser to set
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the assignerToUser
	 */
	public String getAssignedToUser() {
		return assignedToUser;
	}

	/**
	 * @param assignerToUser the assignerToUser to set
	 */
	public void setAssignedToUser(String assignerToUser) {
		this.assignedToUser = assignerToUser;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
