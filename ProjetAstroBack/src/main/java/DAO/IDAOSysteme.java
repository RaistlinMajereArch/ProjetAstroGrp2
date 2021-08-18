package DAO;

import metier.CorpsCeleste;

public interface IDAOSysteme extends IDAO<CorpsCeleste,Integer>{
	
	public void deleteAll(); 
}
