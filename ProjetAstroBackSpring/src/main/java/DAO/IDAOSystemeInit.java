package DAO;

import astro.metier.CorpsCeleste;

public interface IDAOSystemeInit extends IDAO<CorpsCeleste,Integer> {
	
	public void deleteAll();
	
}
