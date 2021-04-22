package org.ticketsystem.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ticketsystem.enums.TicketSystemStatusEnum;
import org.ticketsystem.logger.CustomException;
import org.ticketsystem.model.Ticket;
import org.ticketsystem.model.TicketDto;
import org.ticketsystem.repository.TicketSystemRepository;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * @author Kavin
 *
 */
@Service
public class TicketSystemService {

	@Autowired
	TicketSystemRepository ticketSystemRepository;

	/**
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public Map<String, Object> delete(Integer id) throws CustomException {
		Map<String, Object> response = new HashMap<>();
		try {
			ticketSystemRepository.deleteById(id);
			response.put("status", true);
		} catch (Exception e) {
			response.put("errorMessage", "Ticket not found :- " + id);
			response.put("status", false);
		}
		return response;
	}

	/**
	 * getAllTickets method will return the all tickets with filtered conditions
	 * 
	 * @param paramTicket
	 * @return
	 */
	public List<TicketDto> getAllTickets(Ticket paramTicket) {
		List<TicketDto> ticketDto = new ArrayList<>();
		getTickets(paramTicket).stream().forEach(ticket -> ticketDto.add(new TicketDto(ticket)));
		return ticketDto;
	}

	/**
	 * getTickets method will return the all tickets with filtered conditions
	 * 
	 * @param paramTicket
	 * @return
	 */
	public List<Ticket> getTickets(Ticket paramTicket) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		String status = Objects.nonNull(paramTicket) ? paramTicket.getStatus() : null;
		String assignerUser = Objects.nonNull(paramTicket) ? paramTicket.getAssignedToUser() : null;
		String customer = Objects.nonNull(paramTicket) ? paramTicket.getCustomer() : null;
		ticketSystemRepository.findAll().forEach(movie -> tickets.add(movie));
		return tickets.stream().filter(predicate -> {
			boolean isMatched = true;
			boolean andCondition = false;
			if (Objects.nonNull(status)) {
				isMatched = status.equals(predicate.getStatus());
				andCondition = true;
			}
			if (Objects.nonNull(assignerUser)) {
				if (andCondition)
					isMatched = isMatched && assignerUser.equals(predicate.getAssignedToUser());
				else
					isMatched = assignerUser.equals(predicate.getAssignedToUser());
				andCondition = true;
			}
			if (Objects.nonNull(customer)) {
				if (andCondition)
					isMatched = isMatched && customer.equals(predicate.getCustomer());
				else
					isMatched = customer.equals(predicate.getCustomer());
			}
			return isMatched;
		}).collect(Collectors.toList());
	}

	/**
	 * @param resultTicket
	 * @return
	 */
	private Mail getMailContent(Ticket resultTicket) {
		Email from = new Email("yogesh@sinecycle.com");
		String subject = resultTicket.getType() + " - " + resultTicket.getId() + " - " + resultTicket.getTitle();
		Email to = new Email(resultTicket.getCustomerMailId());
		Content content = new Content("text/plain",
				"The Ticket has been created / modified with below details." + "\n" + "1. Status - "
						+ resultTicket.getStatus() + "\n" + "2. Assigned Agent - " + resultTicket.getAssignedToUser()
						+ "\n" + "3. Description - " + resultTicket.getDescription() + "\n" + "4. Priority - "
						+ resultTicket.getPriority());
		Mail mail = new Mail(from, subject, to, content);
		return mail;
	}

	/**
	 * @param id
	 * @return
	 */
	public Ticket getTicketDetails(Integer id) {
		return ticketSystemRepository.findById(id).get();
	}

	/**
	 * @param ticketId
	 * @return
	 */
	private boolean isExists(Integer ticketId) {
		return ticketSystemRepository.existsById(ticketId);
	}

	/**
	 * @param ticket
	 * @return
	 */
	public Ticket saveOrUpdate(Ticket ticket) {
		Integer ticketId = ticket.getId();
		if (!isExists(ticketId)) {
			int size = getAllTickets(null).size() + 1;
			ticket.setId(size);
			ticket.setLocalDate(LocalDate.now());
		}
		setResolvedDate(ticket);
		Ticket resultTicket = ticketSystemRepository.save(ticket);
		send(resultTicket);
		return resultTicket;
	}

	/**
	 * setResolvedDate method updates the resolved status date
	 * 
	 * @param ticket
	 */
	private void setResolvedDate(Ticket ticket) {
		if (Objects.nonNull(ticket) && StringUtils.equals(TicketSystemStatusEnum.resolved.name(), ticket.getStatus())) {
			ticket.setResolvedDate(LocalDate.now());
		}
	}

	/**
	 * @param resultTicket
	 * @return
	 */
	public Map<String, Object> send(Ticket resultTicket) {
		Map<String, Object> responceMap = new HashMap<>();
		try {
			final SendGrid sg = new SendGrid("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");
			final Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(getMailContent(resultTicket).build());

			final Response response = sg.api(request);
			responceMap.put("response", response.getStatusCode());
			responceMap.put("body", response.getBody());
			responceMap.put("headers", response.getHeaders());
			responceMap.put("status", true);
		} catch (IOException e) {
			responceMap.put("errorMessage", "Error occured during mail sent : - " + e.getMessage());
			responceMap.put("status", false);
		}
		return responceMap;
	}

	/**
	 * @param ticket
	 * @param exTicket
	 */
	private void setTicketDetails(Ticket ticket, Ticket exTicket) {
		if (ticket.getTitle() != null)
			exTicket.setTitle(ticket.getTitle());
		if (ticket.getStatus() != null)
			exTicket.setStatus(ticket.getStatus());
		if (ticket.getAssignedToUser() != null)
			exTicket.setAssignedToUser(ticket.getAssignedToUser());
		if (ticket.getDescription() != null)
			exTicket.setDescription(ticket.getDescription());
		if (ticket.getCustomer() != null)
			exTicket.setCustomer(ticket.getCustomer());
		if (ticket.getPriority() != null)
			exTicket.setPriority(ticket.getPriority());

		setResolvedDate(exTicket);
	}

	/**
	 * @param id
	 * @param ticket
	 * @return
	 * @throws CustomException
	 */
	public Ticket updateStatus(Integer id, Ticket ticket) throws CustomException {
		Ticket exTicket = ticketSystemRepository.findById(id).orElse(null);
		if (exTicket != null) {
			setTicketDetails(ticket, exTicket);
			Ticket resultTicket = ticketSystemRepository.save(exTicket);
			send(resultTicket);
			return resultTicket;
		}
		throw new CustomException("Ticket not found - " + id);
	}

	/**
	 * @param ticket
	 * @return
	 * @throws CustomException
	 */
	public Ticket updateTicket(Ticket ticket) throws CustomException {
		Ticket exTicket = ticketSystemRepository.findById(ticket.getId()).orElse(null);
		if (exTicket != null) {
			setTicketDetails(ticket, exTicket);
			Ticket resultTicket = ticketSystemRepository.save(exTicket);
			send(resultTicket);
			return resultTicket;
		}
		throw new CustomException("Ticket not found - " + ticket.getId());
	}

	/**
	 * batchStatusUpdate method to update the ticket status as closed which has resolved status more
	 * than 30 days
	 */
	public void batchStatusUpdate() {
		List<Ticket> filteredTickets = new ArrayList<>();
		Ticket condition = new Ticket();
		condition.setStatus(TicketSystemStatusEnum.resolved.name());
		List<Ticket> tickets = getTickets(condition);
		tickets.stream().forEach(ticket -> {
			LocalDate aDate = LocalDate.now();
			Period period = Period.between(aDate, ticket.getResolvedDate());
			int diff = Math.abs(period.getDays());
			if (diff >= 30) {
				ticket.setStatus(TicketSystemStatusEnum.closed.name());
				filteredTickets.add(ticket);
			}
		});
		if(!filteredTickets.isEmpty()) ticketSystemRepository.saveAll(filteredTickets);
	}

}