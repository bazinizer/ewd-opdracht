package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import domain.MyUser;

@Component
public interface MyUserRepository extends CrudRepository<MyUser, Long> {

	MyUser findByUsername(String name);

	@Query("SELECT u.id FROM MyUser u WHERE u.username = :username")
	Optional<Long> findUserIdByUsername(@Param("username") String username);
}
