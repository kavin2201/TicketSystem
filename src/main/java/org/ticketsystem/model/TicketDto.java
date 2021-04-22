/**
 * 
 */
package org.ticketsystem.model;

import lombok.Data;

/**
 * @author Kavin
 *
 */
@Data
public class TicketDto {

	private String type;
	private String description;
	private String title;
	private String createdByUser;
	private String customer;
	private String assignedToUser;
	private String priority;
	private String status;

	/**
	 * TicketDto constructor
	 */
	public TicketDto(Ticket ticekt) {
		this.type = ticekt.getType();
		this.description = ticekt.getDescription();
		this.title = ticekt.getTitle();
		this.createdByUser = ticekt.getCreatedByUser();
		this.customer = ticekt.getCustomer();
		this.assignedToUser = ticekt.getAssignedToUser();
		this.priority = ticekt.getPriority();
		this.status = ticekt.getStatus();
	}

}
