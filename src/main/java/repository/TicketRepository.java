package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByUser_UsernameAndWedstrijd_Sport_Id(String username, Long sportId);
    List<Ticket> findByUser_Username(String username);
	int countByUserUsernameAndWedstrijdId(String username, Long wedstrijdId);
	int countByUserId(Long userId);
	int countByWedstrijdIdAndUserId(Long wedstrijdId, Long userId);
	List<Ticket> findByWedstrijdIdAndUserId(Long wedstrijdId, Long userId);

}
