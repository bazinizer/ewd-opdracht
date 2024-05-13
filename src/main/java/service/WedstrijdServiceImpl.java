package service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<String> findDisciplinesBySportId(Long sportId) {
        List<Wedstrijd> wedstrijden = wedstrijdRepository.findBySportId(sportId);
        return wedstrijden.stream()
                          .flatMap(w -> w.getDisciplines().stream())
                          .collect(Collectors.toSet());
    }
    


    @Override
    public List<Wedstrijd> findAll() {
        return (List<Wedstrijd>) wedstrijdRepository.findAll();
    }

    @Override
    public Wedstrijd findById(Long id) {
        return wedstrijdRepository.findById(id).orElse(null);
    }
    @Override
    public boolean existsByOlympicNumber1(int olympicNumber1) {
        return wedstrijdRepository.existsByOlympicNumber1(olympicNumber1);
    }
}
