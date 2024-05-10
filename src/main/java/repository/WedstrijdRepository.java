package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Wedstrijd;

public interface WedstrijdRepository extends CrudRepository<Wedstrijd, Long> {
    // You can define custom query methods if needed
}
