package astro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import astro.metier.CorpsCeleste;

public interface SystemeInitRepository extends JpaRepository<CorpsCeleste, Integer>{


	@Query("select distinct c from CorpsCeleste c where c.nom like :lib or c.id like :lib or c.type like :lib")
	public List<CorpsCeleste> filterCorps(@Param("lib") String texte);
	
	@Transactional
	@Modifying
	@Query("update CorpsCeleste c set c.parent=null WHERE c.id =:id")
	public void updateIdParent(@Param("id") Integer id);
	
	@Query("select distinct c from CorpsCeleste c where c.parent=:parent")
	public List<CorpsCeleste> selectEnfants(@Param("parent") CorpsCeleste c);
}
