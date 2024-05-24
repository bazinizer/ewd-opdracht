package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Wedstrijd;

public interface WedstrijdRepository extends CrudRepository<Wedstrijd, Long> {

	List<Wedstrijd> findAllByOrderByDatumTijdAsc();

	boolean existsByOlympicNumber1(int olympicNumber1);
	
	List<Wedstrijd> findBySportId(Long sportId);



}
