package DAOjpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DAO.IDAOSystemeInit;
import metier.CorpsCeleste;
import util.Context;

public class DAOSystemeInitjpa implements IDAOSystemeInit {
	
	public CorpsCeleste findById(Integer id) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		CorpsCeleste c = em.find(CorpsCeleste.class,id);
		em.close();
		return c;
	}

	
	public List<CorpsCeleste> findAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		List<CorpsCeleste> corpsCelestes = em.createNativeQuery("SELECT * FROM systeminit",CorpsCeleste.class).getResultList();
		em.close();
		return corpsCelestes;
	}

	public CorpsCeleste insert(CorpsCeleste c) {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();

		em.getTransaction().begin();
		c=em.merge(c);
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
		c=em.merge(c);
		em.remove(c);
		em.getTransaction().commit();
		em.close();
	}
	
	public void deleteAll() {
		EntityManager em = Context.getInstance().getEmf().createEntityManager();
		List<CorpsCeleste> cc = findAll();
		em.getTransaction().begin();
		for(CorpsCeleste c: cc) {
			c=em.merge(c);
			em.remove(c);
		}
		//Query q=em.createNativeQuery("DELETE FROM systeminit");
		//q.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	public List<CorpsCeleste> filter(String mot) {
		List<CorpsCeleste> corpsCeleste=new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);
 
			PreparedStatement ps = conn.prepareStatement("SELECT id from systeminit where nom like ? or id like ? or type like ?");
			ps.setString(1, "%"+mot+"%");
			ps.setString(2, "%"+mot+"%");
			ps.setString(3, "%"+mot+"%");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				corpsCeleste.add(findById(rs.getInt("id")));
			}
			
			conn.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return corpsCeleste;
	}
	
}
