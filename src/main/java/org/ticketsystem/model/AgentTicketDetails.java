/**
 * 
 */
package org.ticketsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Kavin
 *
 */
@Entity
public class AgentTicketDetails {

	@Id
	@Column(name = "ASSIGNED_TO_USER",unique=true,columnDefinition="VARCHAR(255)")
	private String assignedToUser;
	
	private Integer ticketCount;

	/**
	 * @return the assignedToUser
	 */
	public String getAssignedToUser() {
		return assignedToUser;
	}

	/**
	 * @param assignedToUser the assignedToUser to set
	 */
	public void setAssignedToUser(String assignedToUser) {
		this.assignedToUser = assignedToUser;
	}

	/**
	 * @return the ticketCount
	 */
	public Integer getTicketCount() {
		return ticketCount;
	}

	/**
	 * @param ticketCount the ticketCount to set
	 */
	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	

}
