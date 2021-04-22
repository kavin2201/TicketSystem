package org.ticketsystem.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ticketsystem.service.TicketSystemService;

/**
 * @author Kavin
 *
 */
@Component
public class TicketSystemComponent {

    @Autowired
    private TicketSystemService ticketSystemService;

    /**
     * afterPropertiesSet method called after application has been initiated
     */
    @PostConstruct
    public void afterApplicationStarted() throws Exception {
    	ticketSystemService.batchStatusUpdate();
    }
}
