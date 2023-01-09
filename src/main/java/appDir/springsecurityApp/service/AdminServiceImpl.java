package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Role;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.rep.PeopleRepository;
import appDir.springsecurityApp.rep.RoleRepository;
import appDir.springsecurityApp.services.AdminServiceCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
    private final PeopleRepository peopleRepository;
    private final AdminServiceCheck adminServiceCheck;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(PeopleRepository peopleRepository, AdminServiceCheck adminServiceCheck, PasswordEncoder passwordEncoder,
                            RoleRepository roleRepository) {
        this.peopleRepository = peopleRepository;
        this.adminServiceCheck = adminServiceCheck;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> index() {
        adminServiceCheck.doModeratorStuff();
        return peopleRepository.findAll();
    }

    @Transactional()
    @Override
    public Person show(int id) {
        adminServiceCheck.doAdminStuff();
        return peopleRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void save(Person person, Role role) {
        adminServiceCheck.doAdminStuff();
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Set<Role> roles = person.getRoles();
        roles.add(role);
        person.setRoles(roles);
        System.out.println(person.getRoles().toString());
        peopleRepository.saveAndFlush(person);
    }

    @Transactional
    @Override
    public void update(Person updatedPerson, Role role) {
        adminServiceCheck.doAdminStuff();
        if(!updatedPerson.getPassword().equals(peopleRepository.findById(updatedPerson.getId()).get().getPassword())) {
            updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        }
        Set<Role> roles = updatedPerson.getRoles();
        roles.add(role);
        updatedPerson.setRoles(roles);
        peopleRepository.saveAndFlush(updatedPerson);
    }

    @Transactional
    @Override
    public void delete(int id) {
        adminServiceCheck.doAdminStuff();
        peopleRepository.deleteById(id);

    }
}
