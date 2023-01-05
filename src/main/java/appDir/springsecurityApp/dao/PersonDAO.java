package appDir.springsecurityApp.dao;


import appDir.springsecurityApp.model.Person;
import appDir.springsecurityApp.model.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Person> index() {
        return entityManager.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional()
    public Person show(int id) {
        return entityManager.find(Person.class,id);
    }

    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Transactional
    public void update(Person updatedPerson, Role updateRole, int id) {
        entityManager.merge(updatedPerson);
        entityManager.find(Person.class,id).getRoles().add(updateRole);
    }

    @Transactional
    public void delete(int id) {
        entityManager.remove(show(id));
    }
}