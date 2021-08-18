package DAOjpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import metier.CorpsCeleste;
import metier.Position;
import util.Context;

public class DAOPositionsjpa {
	
	public Position findById(Integer id) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		Position p = em.find(Position.class,id);
		em.close();
		return p;
	}

	
	public List<Position> findAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		List<Position> positions = em.createQuery("from Positions",Position.class).getResultList();
		em.close();
		return positions;
	}

	
	public Position insert(Position p) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();

		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.close();
		return p;
	}

	
	public Position update(Position p) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		p=em.merge(p);
		em.getTransaction().commit();
		em.close();
		return p;
	}

	
	public void delete(Integer id) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		Position p = em.find(Position.class,id);
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		Query q=em.createQuery("DELETE FROM positions");
		q.executeUpdate();
		em.close();
	}
}
