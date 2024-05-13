package service;

import java.util.List;
import java.util.Set;

import domain.Wedstrijd;

public interface WedstrijdService {
    void save(Wedstrijd wedstrijd);
    void saveAll(Iterable<Wedstrijd> wedstrijden);
    List<Wedstrijd> findAll();
    Wedstrijd findById(Long id);
	List<Wedstrijd> findAllSortedByDate();
	int countTicketsByUserIdAndWedstrijdId(String username, Long wedstrijdId);
	boolean existsByOlympicNumber1(int olympicNumber1);
	Set<String> findDisciplinesBySportId(Long sportId);
}
