package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import DAO.IDAOCompte;
import DAO.IDAOPositions;
import DAO.IDAOSysteme;
import DAO.IDAOSystemeInit;


import DAO.jpa.DAOCompteJPA;
import DAO.jpa.*;
import metier.Compte;

public class Context {
	private Compte connected=null;
	private IDAOCompte daoC = new DAOComptejpa();
	private IDAOPositions daoP = new DAOPositionsjpa();
	private IDAOSysteme daoS = new DAOSsystemejpa();
	private IDAOSystemeInit daoSI = new DAOSsystemeInitjpa();

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("astro-persistence");
	
	private static Context _instance=null;
	
	
	
}
