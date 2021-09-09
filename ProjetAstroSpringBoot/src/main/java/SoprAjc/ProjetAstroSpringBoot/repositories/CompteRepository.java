package SoprAjc.ProjetAstroSpringBoot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SoprAjc.ProjetAstroSpringBoot.model.Compte;


public interface CompteRepository extends JpaRepository<Compte, Integer> {

	Optional<Compte> findByLogin(String login);
	
	public Optional<Compte> findByLoginAndPassword(String login, String password);
	
	@Query("from Compte c where c.login like :lib or c.password like :lib")
	public List<Compte> filterCompte(@Param("lib")String lib) ;
}
