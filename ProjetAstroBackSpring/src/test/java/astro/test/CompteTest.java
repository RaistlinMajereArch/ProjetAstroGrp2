package astro.test;

import static org.junit.Assert.*; 

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
	@Rollback(true)
	@Transactional
	public void testInsert() {
		Admin cptAInserer=new Admin(3,"admin2", "admin2");
		cptRepo.save(cptAInserer);
		assertEquals(cptAInserer, cptRepo.findById(cptAInserer.getId()).get());
	}

}
