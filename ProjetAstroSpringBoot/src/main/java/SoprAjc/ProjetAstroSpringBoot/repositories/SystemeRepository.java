package SoprAjc.ProjetAstroSpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;

public interface SystemeRepository extends JpaRepository<CorpsCeleste, Integer>{
	@Modifying
	@Query(value="DELETE FROM system", nativeQuery=true)
	public void deleteAll();
}
