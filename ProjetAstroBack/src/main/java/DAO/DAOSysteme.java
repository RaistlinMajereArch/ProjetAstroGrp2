package DAO;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import metier.CorpsCeleste;
import metier.Etoile;
import metier.Planete;
import metier.Satellite;


public class DAOSysteme implements IDAO<CorpsCeleste,Integer>{

	@Override
	public CorpsCeleste findById(Integer id) {
		CorpsCeleste c=null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);

			PreparedStatement ps = conn.prepareStatement("SELECT * from system where id=?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				if(rs.getString("type").equals("planete")) {
					c = new Planete(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getDouble("x"),rs.getDouble("y"),rs.getDouble("vx"),rs.getDouble("vy"),rs.getBoolean("etat"),rs.getString("nom"),rs.getInt("id_parent"));
				}
				else if(rs.getString("type").equals("etoile")) {
					c = new Etoile(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getBoolean("etat"),rs.getString("nom"));
					//int id, double masse, double diametre, boolean etat,
					//String nom
				}
				else if(rs.getString("type").equals("satellite")) {
					c = new Satellite(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getDouble("x"),rs.getDouble("y"),rs.getDouble("vx"),rs.getDouble("vy"),rs.getBoolean("etat"),rs.getString("nom"),rs.getInt("id_parent"));
				}
			}

			rs.close();
			ps.close();
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return c;
	}
	

	@Override
	public List<CorpsCeleste> findAll() {
		List<CorpsCeleste> corpsCelestes = new ArrayList<CorpsCeleste>();
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);

			PreparedStatement ps = conn.prepareStatement("SELECT * from system");

			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{
				if(rs.getString("type").equals("planete")) {
					Planete c = new Planete(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getDouble("x"),rs.getDouble("y"),rs.getDouble("vx"),rs.getDouble("vy"),rs.getBoolean("etat"),rs.getString("nom"),rs.getInt("id_parent"));
					corpsCelestes.add(c);
				}
				else if(rs.getString("type").equals("etoile")) {
					Etoile e = new Etoile(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getBoolean("etat"),rs.getString("nom"));
					corpsCelestes.add(e);
					//int id, double masse, double diametre, boolean etat,
					//String nom
				}
				else if(rs.getString("type").equals("satellite")) {
					Satellite s = new Satellite(rs.getInt("id"),rs.getDouble("masse"),rs.getDouble("diametre"),rs.getDouble("x"),rs.getDouble("y"),rs.getDouble("vx"),rs.getDouble("vy"),rs.getBoolean("etat"),rs.getString("nom"),rs.getInt("id_parent"));
					corpsCelestes.add(s);
				}
				//int id, double masse, double diametre, double x, double y, double vx, double vy, boolean etat,
				//String nom,int id_parent
			}

			rs.close();
			ps.close();
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return corpsCelestes;
	}
	


@Override
	public CorpsCeleste insert(CorpsCeleste c) {
	try 
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);
		
		PreparedStatement ps = conn.prepareStatement("INSERT into system (masse,diametre,id_parent,type,x,y,vx,vy,etat,nom) VALUES (?,?,?,?,?,?,?,?,?,?)");

		ps.setDouble(1, c.getMasse());
		ps.setDouble(2, c.getDiametre());
		ps.setInt(3, c.getIdParent());
		if (c instanceof Etoile){
			ps.setString(4, "Etoile");
		}
		else if (c instanceof Planete){
			ps.setString(4, "Planete");
		}
		else if (c instanceof Satellite){
			ps.setString(4, "Satellite");
		}
		ps.setDouble(5, c.getX());
		ps.setDouble(6, c.getY());
		ps.setDouble(7, c.getVx());
		ps.setDouble(8, c.getVy());
		ps.setBoolean(9, c.isEtat());
		ps.setString(10, c.getNom());

		ps.executeUpdate();
		ps.close();
		conn.close();
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}
	
	//Y ajouter l'id ?
	return c;
}



	@Override
	public CorpsCeleste update(CorpsCeleste c) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);

			PreparedStatement ps = conn.prepareStatement("UPDATE system set masse=?,diametre=?,id_parent=?,type=?,x=?,y=?,vx=?,vy=?,etat=?,nom=? where id=?");

			ps.setDouble(1, c.getMasse());
			ps.setDouble(2, c.getDiametre());
			ps.setInt(3, c.getIdParent());
			if (c instanceof Etoile){
				ps.setString(4, "Etoile");
			}
			else if (c instanceof Planete){
				ps.setString(4, "Planete");
			}
			else if (c instanceof Satellite){
				ps.setString(4, "Satellite");
			}
			ps.setDouble(5, c.getX());
			ps.setDouble(6, c.getY());
			ps.setDouble(7, c.getVx());
			ps.setDouble(8, c.getVy());
			ps.setBoolean(9, c.isEtat());
			ps.setString(10, c.getNom());
			ps.setInt(11, c.getId());
			ps.executeUpdate();

			ps.close();
			conn.close();
		}
		catch(Exception e) {e.printStackTrace();}
		return c;
	}

	@Override
	public void delete(Integer id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);

			PreparedStatement ps = conn.prepareStatement("DELETE from system where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();

			ps.close();
			conn.close();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	public void deleteAll() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBDD,loginBDD,passwordBDD);

			PreparedStatement ps = conn.prepareStatement("DELETE from system");
			ps.executeUpdate();
			ps = conn.prepareStatement("ALTER TABLE system AUTO_INCREMENT = 1");
			ps.executeUpdate();

			ps.close();
			conn.close();
		}
		catch(Exception e) {e.printStackTrace();}
	}

}
