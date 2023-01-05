package appDir.springsecurityApp.services;

import appDir.springsecurityApp.dao.RoleDAO;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.rep.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;
    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleDAO.show(1));
        person.setRoles(roleSet);
        peopleRepository.save(person);
    }
}
