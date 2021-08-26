package DAO;

import java.util.List;

import astro.metier.Position;

public interface IDAOPositions extends IDAO<Position,Integer> {
	
	public List<Position> findByTimestep(Integer t);
			
	public List<Position> findByIdCorps(Integer id);	
				
	public void deleteAll();
}
		


