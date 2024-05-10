package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import domain.MyUser;

@Component
public interface MyUserRepository extends CrudRepository<MyUser, Long> {

	MyUser findByUsername(String name);
}
