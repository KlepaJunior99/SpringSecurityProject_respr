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
public class RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Role> index() {
        return entityManager.createQuery("select p from Role p", Role.class).getResultList();
    }

    @Transactional()
    public Role show(int id) {
        return entityManager.find(Role.class,id);
    }

    @Transactional
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Transactional
    public void update(Role updatedRole) {
        entityManager.merge(updatedRole);
    }

    @Transactional
    public void delete(int id) {
        entityManager.remove(show(id));
    }
}
