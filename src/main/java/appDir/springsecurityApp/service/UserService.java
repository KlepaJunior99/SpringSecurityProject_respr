package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;

public interface UserService {
    Person show(int id);

    void update(Person updatedPerson, Role updatedRole, int id);

    void delete(int id);
}
