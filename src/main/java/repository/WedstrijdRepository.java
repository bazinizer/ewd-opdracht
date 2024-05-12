package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import domain.Wedstrijd;

public interface WedstrijdRepository extends CrudRepository<Wedstrijd, Long> {

	List<Wedstrijd> findAllByOrderByDatumTijdAsc();
}
