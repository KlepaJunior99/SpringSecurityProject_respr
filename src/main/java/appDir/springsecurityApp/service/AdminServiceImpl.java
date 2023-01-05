package appDir.springsecurityApp.service;

import appDir.springsecurityApp.dao.PersonDAO;
import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.services.AdminServiceCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import appDir.springsecurityApp.model.Role;

import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {
    private final PersonDAO personDAO;
    private final AdminServiceCheck adminServiceCheck;

    @Autowired
    public AdminServiceImpl(PersonDAO personDAO, AdminServiceCheck adminServiceCheck) {
        this.personDAO = personDAO;
        this.adminServiceCheck = adminServiceCheck;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> index() {
        adminServiceCheck.doModeratorStuff();
        return personDAO.index();
    }

    @Transactional()
    @Override
    public Person show(int id) {
        adminServiceCheck.doAdminStuff();
        return personDAO.show(id);
    }

    @Transactional
    @Override
    public void save(Person person) {
        adminServiceCheck.doAdminStuff();
        personDAO.save(person);
    }

    @Transactional
    @Override
    public void update(Person updatedPerson, Role updatedRole, int id) {
        adminServiceCheck.doAdminStuff();
        personDAO.update(updatedPerson, updatedRole, id);
    }

    @Transactional
    @Override
    public void delete(int id) {
        adminServiceCheck.doAdminStuff();
        personDAO.delete(id);
    }
}
