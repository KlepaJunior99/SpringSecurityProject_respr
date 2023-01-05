package appDir.springsecurityApp.services;

import appDir.springsecurityApp.dao.PersonDAO;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.rep.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    private final PersonDAO personDAO;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository, PersonDAO personDAO) {
        this.peopleRepository = peopleRepository;
        this.personDAO = personDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return person.get();
    }
}
