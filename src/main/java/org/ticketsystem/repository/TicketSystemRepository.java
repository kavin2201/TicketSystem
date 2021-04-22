package org.ticketsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.ticketsystem.model.Ticket;

/**
 * @author Kavin
 *
 */
public interface TicketSystemRepository extends CrudRepository<Ticket, Integer> {
}