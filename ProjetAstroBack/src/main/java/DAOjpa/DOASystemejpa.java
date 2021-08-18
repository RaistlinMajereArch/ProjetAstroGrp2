package DAOjpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import metier.CorpsCeleste;
import util.Context;

public class DOASystemejpa {
	public CorpsCeleste findById(Integer id) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		CorpsCeleste c = em.find(CorpsCeleste.class,id);
		em.close();
		return c;
	}

	
	public List<CorpsCeleste> findAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		List<CorpsCeleste> corpsCelestes = em.createQuery("from SystemeInit",CorpsCeleste.class).getResultList();
		em.close();
		return corpsCelestes;
	}

	
	public CorpsCeleste insert(CorpsCeleste c) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();

		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		em.close();
		return c;
	}

	
	public CorpsCeleste update(CorpsCeleste c) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		c=em.merge(c);
		em.getTransaction().commit();
		em.close();
		return c;
	}

	
	public void delete(Integer id) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		CorpsCeleste c = em.find(CorpsCeleste.class,id);
		em.getTransaction().begin();
		em.remove(c);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		Query q=em.createQuery("DELETE FROM systemeinit");
		em.getTransaction().begin();
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
}
