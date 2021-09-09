package SoprAjc.ProjetAstroSpringBoot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;


public interface SystemeInitRepository extends JpaRepository<CorpsCeleste, Integer>{


	@Query("select distinct c from CorpsCeleste c where c.nom like :lib or c.id like :lib or c.type like :lib")
	public List<CorpsCeleste> filterCorps(@Param("lib") String texte);
	
//	@Modifying
//	@Query("update CorpsCeleste c set c.parent=null WHERE c.parent =:corps")
//	public void updateIdParent(@Param("corps") CorpsCeleste c);
	
	@Modifying
	@Query("delete CorpsCeleste c WHERE c.parent =:corps")
	public void deleteEnfants(@Param("corps") CorpsCeleste c);
	
	@Query("select distinct c from CorpsCeleste c where c.parent=:parent")
	public List<CorpsCeleste> selectEnfants(@Param("parent") CorpsCeleste c);
	
	@Transactional
	@Modifying
	@Query(value="Alter Table systeminit auto_increment=1", nativeQuery=true)
	public void resetId();
	
}
