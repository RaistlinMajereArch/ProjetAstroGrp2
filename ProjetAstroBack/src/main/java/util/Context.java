package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.IDAOCompte;
import DAO.IDAOPositions;
import DAO.IDAOSysteme;
import DAO.IDAOSystemeInit;
import DAOjpa.DAOComptejpa;
import DAOjpa.DAOPositionsjpa;
import DAOjpa.DAOSystemeInitjpa;
import DAOjpa.DAOSystemejpa;
import metier.Compte;
import metier.Etoile;

public class Context {
	private Compte connected=null;
	private IDAOCompte daoC = new DAOComptejpa();
	private IDAOPositions daoP = new DAOPositionsjpa();
	private IDAOSysteme daoS = new DAOSystemejpa();
	private IDAOSystemeInit daoSI = new DAOSystemeInitjpa();
	private Etoile etoile;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet_astro");
	
	private static Context _instance=null;

	private Context() {}
	
	public static Context getInstance() 
	{
		if(_instance==null) {
			_instance=new Context();
		}
		return _instance;
	}
	
	public Compte getConnected() {
		return connected;
	}

	public void setConnected(Compte connected) {
		this.connected = connected;
	}

	public IDAOCompte getDaoC() {
		return daoC;
	}

	public void setDaoC(IDAOCompte daoC) {
		this.daoC = daoC;
	}

	public IDAOPositions getDaoP() {
		return daoP;
	}

	public void setDaoP(IDAOPositions daoP) {
		this.daoP = daoP;
	}

	public IDAOSysteme getDaoS() {
		return daoS;
	}

	public void setDaoS(IDAOSysteme daoS) {
		this.daoS = daoS;
	}

	public IDAOSystemeInit getDaoSI() {
		return daoSI;
	}

	public void setDaoSI(IDAOSystemeInit daoSI) {
		this.daoSI = daoSI;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	
	public Etoile getEtoile() {
		return etoile;
	}

	public void setEtoile(Etoile etoile) {
		this.etoile = etoile;
	}

	public void closeEmf() {
		emf.close();
	}
}
	