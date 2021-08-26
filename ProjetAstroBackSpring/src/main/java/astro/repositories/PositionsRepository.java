package astro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import astro.metier.Position;



public interface PositionsRepository extends JpaRepository<Position, Integer> {
	
	List<Position> findByid_corpsCeleste(Integer id);
	
	List<Position> findByid_timeStep(Integer t);

}
