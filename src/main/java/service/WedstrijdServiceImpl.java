package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Wedstrijd;
import repository.TicketRepository;
import repository.WedstrijdRepository;

@Service
public class WedstrijdServiceImpl implements WedstrijdService {

    @Autowired
    private WedstrijdRepository wedstrijdRepository;
    @Autowired
    private TicketRepository ticketRepository;
    
    

    @Override
    public void save(Wedstrijd wedstrijd) {
        wedstrijdRepository.save(wedstrijd);
    }

    @Override
    public void saveAll(Iterable<Wedstrijd> wedstrijden) {
        wedstrijdRepository.saveAll(wedstrijden);
    }
    @Override
    public List<Wedstrijd> findAllSortedByDate() {
        return wedstrijdRepository.findAllByOrderByDatumTijdAsc();
    }

    @Override
    public int countTicketsByUserIdAndWedstrijdId(String username, Long wedstrijdId) {
        return ticketRepository.countByUserUsernameAndWedstrijdId(username, wedstrijdId);
    }


    @Override
    public List<Wedstrijd> findAll() {
        return (List<Wedstrijd>) wedstrijdRepository.findAll();
    }

    @Override
    public Wedstrijd findById(Long id) {
        return wedstrijdRepository.findById(id).orElse(null);
    }
}
