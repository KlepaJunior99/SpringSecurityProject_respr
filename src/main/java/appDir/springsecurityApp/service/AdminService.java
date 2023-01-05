package appDir.springsecurityApp.service;


import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;

import java.util.List;

public interface AdminService {

    List<Person> index();

    Person show(int id);

    void save(Person person);

    void update(Person updatedPerson, Role updatedRole, int id);

    void delete(int id);
}
