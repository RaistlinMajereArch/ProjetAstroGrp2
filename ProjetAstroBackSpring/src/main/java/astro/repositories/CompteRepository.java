package astro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import astro.metier.Compte;

public interface CompteRepository extends JpaRepository<Compte, Integer> {

	public Optional<Compte> findByLoginAndPassword(String login, String password);
	
	@Query("from Compte c where c.login like :lib or c.password like :lib")
	public List<Compte> filterCompte(@Param("lib")String lib) ;
}
