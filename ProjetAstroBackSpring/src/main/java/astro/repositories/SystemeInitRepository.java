package astro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import astro.metier.CorpsCeleste;

public interface SystemeInitRepository extends JpaRepository<CorpsCeleste, Integer>{


	@Query("select distinct c from CorpsCeleste c where c.nom like :lib or c.id like :lib or c.type like :lib")
	public List<CorpsCeleste> filterCorps(@Param("lib") String texte);
	
	@Query("update CorpsCeleste c set c.parent=NULL WHERE c.id =:id")
	public void updateIdParent(@Param("id") Integer id);
	
	@Query("select distinct c from CorpsCeleste c where c.idParent=:id")
	public List<CorpsCeleste> selectEnfants(@Param("id") Integer id);
}
