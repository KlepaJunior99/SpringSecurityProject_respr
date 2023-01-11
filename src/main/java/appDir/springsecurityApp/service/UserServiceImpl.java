package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.rep.PeopleRepository;
import appDir.springsecurityApp.rep.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(PeopleRepository peopleRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional()
    @Override
    public Person show(long id) {
        return peopleRepository.findById(id).get();
    }
    @Override
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Set<Role> roleSet =person.getRoles();
        roleSet.add(roleRepository.findByName("ROLE_USER").get());
        person.setRoles(roleSet);
        peopleRepository.save(person);
    }
    @Transactional
    @Override
    public void update(Person updatedPerson) {
        if(peopleRepository.findByUsername(updatedPerson.getUsername()).isPresent() && !(peopleRepository.findByUsername(updatedPerson.getUsername()).get().getId()).equals(updatedPerson.getId())) {
            throw new InvalidParameterException("Cannot save user, such email already exists in the database: "
                    + updatedPerson.getUsername());
        }
        if (updatedPerson.getPassword().isEmpty()) {
            updatedPerson.setPassword(peopleRepository.findById(updatedPerson.getId()).get().getPassword());
        } else {
            updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        }
        Set<Role> roleSet = updatedPerson.getRoles();
        roleSet.add(roleRepository.findByName("ROLE_USER").get());
        updatedPerson.setRoles(roleSet);
        peopleRepository.saveAndFlush(updatedPerson);
    }

    @Override
    public void delete(long id) {
        peopleRepository.deleteById(id);
    }
}
