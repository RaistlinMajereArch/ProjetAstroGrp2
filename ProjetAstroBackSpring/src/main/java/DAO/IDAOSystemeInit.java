package DAO;

import metier.CorpsCeleste;

public interface IDAOSystemeInit extends IDAO<CorpsCeleste,Integer> {
	
	public void deleteAll();
	
}
