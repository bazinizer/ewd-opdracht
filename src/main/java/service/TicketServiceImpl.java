package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Ticket;
import domain.Wedstrijd;
import jakarta.transaction.Transactional;
import repository.TicketRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }
    @Override
    public void saveAll(Iterable<Ticket> tickets) {
    	ticketRepository.saveAll(tickets);
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> result = new ArrayList<>();
        ticketRepository.findAll().forEach(result::add);
        return result;
    }
    @Override
    public boolean hasTickets(String username) {
        return !findTicketsByUser(username).isEmpty();
    }
    @Override
    public List<Ticket> findTicketsByUser(String username) {
        return ticketRepository.findByUser_Username(username);
    }

    @Override
    public boolean hasTicketsForSport(Long sportId, String username) {
        return !ticketRepository.findByUser_UsernameAndWedstrijd_Sport_Id(username, sportId).isEmpty();
    }
}
