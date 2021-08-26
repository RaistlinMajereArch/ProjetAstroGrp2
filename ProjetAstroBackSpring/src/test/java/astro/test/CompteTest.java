package astro.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import astro.metier.Compte;
import metier.Departement;
import metier.Region;

public class CompteTest {

	
	@Test
	@Rollback(true)
	@Transactional
	public void testInsert() {
		Compte compteAInserer=new Compte("Loire", "42", Region.Auvergne_Rhone_Alpes);
		depRepo.save(depAInserer);
		assertEquals(depAInserer, depRepo.findById(depAInserer.getId()).get());
	}

}
