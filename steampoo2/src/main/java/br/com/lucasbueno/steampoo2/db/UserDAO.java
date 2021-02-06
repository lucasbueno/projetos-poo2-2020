package br.com.lucasbueno.steampoo2.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.lucasbueno.steampoo2.entities.User;

public class UserDAO implements InterfaceDAO<User> {

	@Override
	public void persist(User t) {
		try {
			EntityManager em = UtilDB.getEntityManager();
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			EntityManager em = UtilDB.getEntityManager();
			em.getTransaction().rollback();
			User original = get(t.getUsername());
			em.getTransaction().begin();
			original.setPassword(t.getPassword());
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(User t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public User get(Object pk) {
		EntityManager em = UtilDB.getEntityManager();
		User t = em.find(User.class, pk);
		return t;
	}

	@Override
	public List<User> getAll() {
		EntityManager em = UtilDB.getEntityManager();
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		return users;
	}
}
