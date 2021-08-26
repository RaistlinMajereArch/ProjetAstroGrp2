package astro.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository {

	@Query("from Compte c where c.login = :login and c.password=:password")
	public void seConnecter(@Param("login")String login, @Param("password")String password) ;
	
	@Query("from Compte c where c.login like :lib or c.password like :lib")
	public void filterCompte(@Param("lib")String lib) ;
}
