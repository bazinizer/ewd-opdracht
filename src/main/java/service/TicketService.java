package service;

import java.util.List;

import domain.Ticket;

public interface TicketService {
    
    List<Ticket> findAll();
    boolean hasTicketsForSport(Long sportId, String username);
	List<Ticket> findTicketsByUser(String username);
	void saveAll(Iterable<Ticket> tickets);
	boolean hasTickets(String username);
	int countByUserId(Long userId);
	int countByUserUsernameAndWedstrijdId(String username, Long wedstrijdId);
    void save(Ticket ticket);
    int getTotalTicketsBoughtByUser(Long userId);
    int getTicketsBoughtForWedstrijdByUser(Long wedstrijdId, Long userId);
	String purchaseTickets(Long userId, Long wedstrijdId, int amount);
    public int getTotalTicketsBoughtForWedstrijdByUser(Long wedstrijdId, Long userId);


	
    
}
