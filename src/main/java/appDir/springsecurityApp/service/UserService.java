package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;

public interface UserService {
    Person show(int id);

    void update(Person updatedPerson);

    void delete(int id);
    void register(Person person);
}
