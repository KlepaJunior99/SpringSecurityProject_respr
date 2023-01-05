package appDir.springsecurityApp.service;

import appDir.springsecurityApp.dao.PersonDAO;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {
    private final PersonDAO personDAO;
    @Autowired
    public UserServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Transactional()
    @Override
    public Person show(int id) {
        return personDAO.show(id);
    }

    @Override
    public void update(Person updatedPerson, Role updatedRole, int id) {
        personDAO.update(updatedPerson, updatedRole, id);
    }

    @Override
    public void delete(int id) {
        personDAO.delete(id);
    }
}
