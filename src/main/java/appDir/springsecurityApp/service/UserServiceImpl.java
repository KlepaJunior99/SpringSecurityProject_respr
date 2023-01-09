package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.rep.PeopleRepository;
import appDir.springsecurityApp.rep.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Person show(int id) {
        return peopleRepository.findById(id).get();
    }
    @Override
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByName("ROLE_USER").get());
        person.setRoles(roleSet);
        peopleRepository.save(person);
    }

    @Override
    public void update(Person updatedPerson) {
        peopleRepository.saveAndFlush(updatedPerson);
    }

    @Override
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
