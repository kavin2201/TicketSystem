package org.ticketsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ticketsystem.logger.CustomException;
import org.ticketsystem.model.Ticket;
import org.ticketsystem.model.TicketDto;
import org.ticketsystem.service.TicketSystemService;

/**
 * @author Kavin
 *
 */
@RestController
public class TicketSystemController {

	@Autowired
	TicketSystemService ticketSystemService;

	/**
	 * @param ticket
	 * @return
	 * @throws CustomException
	 */
	@PostMapping("/assignToAgent")
	public TicketDto assignToAgent(@RequestBody Ticket ticket) throws CustomException {
		return new TicketDto(ticketSystemService.updateTicket(ticket));
	}

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	@DeleteMapping("/delete/{id}")
	private Map<String, Object> deleteTicket(@PathVariable("id") Integer id) throws CustomException {
		return ticketSystemService.delete(id);
	}

	/**
	 * @param paramTicket
	 * @return
	 */
	@GetMapping("/tickets")
	private List<TicketDto> getAllTickets(@RequestBody Ticket paramTicket) {
		return ticketSystemService.getAllTickets(paramTicket);
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/ticket/{id}")
	private TicketDto getTicketDetails(@PathVariable("id") Integer id) {
		return new TicketDto(ticketSystemService.getTicketDetails(id));
	}

	/**
	 * @param id
	 * @param ticket
	 * @return
	 * @throws CustomException
	 */
	@PutMapping("/updateStatus/{id}")
	public TicketDto updateStatus(@PathVariable(value = "id") Integer id, @RequestBody Ticket ticket)
			throws CustomException {
		return new TicketDto(ticketSystemService.updateStatus(id, ticket));
	}

	/**
	 * @param ticket
	 * @return
	 */
	@PostMapping("/upsert")
	private TicketDto upsertTicket(@RequestBody Ticket ticket) {
		return new TicketDto(ticketSystemService.saveOrUpdate(ticket));
	}

}