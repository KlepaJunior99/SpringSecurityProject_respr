package appDir.springsecurityApp.service;


import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;

import java.util.HashSet;
import java.util.List;

public interface AdminService {

    List<Person> index();

    Person show(int id);

    void save(Person person, Role role);

    void update(Person updatedPerson, Role role);

    void delete(int id);
}
