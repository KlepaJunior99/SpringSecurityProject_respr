package appDir.springsecurityApp.service;

import appDir.springsecurityApp.model.Person;

public interface UserService {
    Person show(long id);

    void update(Person updatedPerson);

    void delete(long id);
    void register(Person person);
}
