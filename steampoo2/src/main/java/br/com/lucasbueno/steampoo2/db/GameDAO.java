package br.com.lucasbueno.steampoo2.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import br.com.lucasbueno.steampoo2.entities.Game;

public class GameDAO implements InterfaceDAO<Game> {

	@Override
	public void persist(Game t) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Game original = get(t.getName());
			em.getTransaction().begin();
			original.setDescription(t.getDescription());
			original.setPrice(t.getPrice());
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Game t) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(t);
		em.getTransaction().commit();
	}

	@Override
	public Game get(Object pk) {
		return UtilDB.getEntityManager().find(Game.class, pk);
	}

	@Override
	public List<Game> getAll() {
		return UtilDB.getEntityManager().createQuery("SELECT g FROM Game g", Game.class).getResultList();
	}
}
