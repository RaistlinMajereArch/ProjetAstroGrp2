package astro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import astro.metier.Position;



public interface PositionsRepository extends JpaRepository<Position, Integer> {
	
	List<Position> findByIdCorpsCeleste(Integer id);
	
	List<Position> findByIdTimeStep(Integer t);

}
