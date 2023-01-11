package appDir.springsecurityApp.rep;

import appDir.springsecurityApp.model.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<Person> findByUsername(String username);
}
