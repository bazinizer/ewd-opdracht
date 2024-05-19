package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.MyUser;
import domain.Ticket;
import domain.Wedstrijd;
import jakarta.transaction.Transactional;
import repository.MyUserRepository;
import repository.TicketRepository;
import repository.WedstrijdRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MyUserRepository myUserRepository;
    
    @Autowired
    private WedstrijdRepository wedstrijdRepository;


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
    public int countByUserId(Long userId) {
        return ticketRepository.countByUserId(userId);
    }
    @Override
    public int countByUserUsernameAndWedstrijdId(String username, Long wedstrijdId) {
        return ticketRepository.countByUserUsernameAndWedstrijdId(username, wedstrijdId);
    }


    @Override
    public boolean hasTicketsForSport(Long sportId, String username) {
        return !ticketRepository.findByUser_UsernameAndWedstrijd_Sport_Id(username, sportId).isEmpty();
    }
    @Override
    public int getTotalTicketsBoughtByUser(Long userId) {
        return ticketRepository.countByUserId(userId);
    }

    @Override
    public int getTicketsBoughtForWedstrijdByUser(Long wedstrijdId, Long userId) {
        return ticketRepository.countByWedstrijdIdAndUserId(wedstrijdId, userId);
    }
    @Override
    public String purchaseTickets(Long userId, Long wedstrijdId, int amount) {
//        String validationResult = ticketValidator.validateTicketPurchase(userId, wedstrijdId, amount);
//        if (validationResult != null) {
//            return validationResult;
//        }

        MyUser user = myUserRepository.findById(userId).orElse(null);
        Wedstrijd wedstrijd = wedstrijdRepository.findById(wedstrijdId).orElse(null);

        Ticket ticket = new Ticket(wedstrijd, user, amount, amount * wedstrijd.getPrijsPerTicket());
        ticketRepository.save(ticket);
        wedstrijd.setVrijePlaatsen(wedstrijd.getVrijePlaatsen() - amount);
        wedstrijdRepository.save(wedstrijd);

        return amount + " tickets successfully purchased!";
    }
}



