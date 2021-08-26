package astro.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import astro.config.AppConfig;
import astro.metier.Admin;
import astro.metier.Compte;
import astro.metier.Utilisateur;
import astro.repositories.CompteRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class CompteTest {

	@Autowired
	private CompteRepository cptRepo;
	
	@Test
	public void test() {
		assertNotNull(cptRepo);
	}
	
	@Test
	//@Rollback(true)
	//@Transactional
	public void testInsert() {
		Compte cptAInserer=new Admin(1,"admin", "admin");
		Compte c2=new Utilisateur(2,"util", "util");
		Compte c3=new Admin(3,"jabid", "jord");
		Compte c4=new Utilisateur(4,"Lebowski", "WhereIsMyCarpet");
		cptRepo.save(cptAInserer);
		cptRepo.save(c2);
		cptRepo.save(c3);
		cptRepo.save(c4);
		assertEquals(cptAInserer, cptRepo.findById(cptAInserer.getId()).get());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testFindByLoginAndPassword() {
		Optional<Compte> cptATrouver=Optional.of(new Admin(1,"admin", "admin"));
		Optional<Compte> c=cptRepo.findByLoginAndPassword("admin","admin");
		assertEquals(cptATrouver, c);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testFilterCompte() {
		List<Compte> cptATrouver=new ArrayList();
		
		List<Compte> c=cptRepo.filterCompte("y");
		assertEquals(cptATrouver, c);
	}
}
