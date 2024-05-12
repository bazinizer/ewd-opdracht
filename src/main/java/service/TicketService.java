package service;

import java.util.List;

import domain.Ticket;

public interface TicketService {
    void save(Ticket ticket);
    List<Ticket> findAll();
    boolean hasTicketsForSport(Long sportId, String username);
	List<Ticket> findTicketsByUser(String username);
	void saveAll(Iterable<Ticket> tickets);
	boolean hasTickets(String username);
	int countByUserId(Long userId);
	int countByUserUsernameAndWedstrijdId(String username, Long wedstrijdId);
    
}
