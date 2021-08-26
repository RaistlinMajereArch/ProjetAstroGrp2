package astro.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import astro.config.AppConfig;
import astro.metier.CorpsCeleste;
import astro.metier.Etoile;
import astro.metier.Planete;
import astro.metier.Satellite;
import astro.repositories.SystemeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SystemeTest {

	@Autowired
	private SystemeRepository sysRepo;
	
	@Test
	public void sysRepoExist() {
		assertNotNull(sysRepo);
	}

	@Test
	public void findById() {
		Optional<CorpsCeleste> opt=sysRepo.findById(1);
		CorpsCeleste c=opt.get();
		assertEquals(10,c.getG(),0);
		assertEquals(1000,c.getDiametre(),0);
		assertEquals(1,c.isEtat());
		assertEquals(1000,c.getMasse(),0);
		assertEquals("Soleil",c.getNom());
		assertEquals(0,c.getVx(),0);
		assertEquals(0,c.getVy(),0);
		assertEquals(0,c.getX(),0);
		assertEquals(0,c.getY(),0);
		assertEquals(1,c.getId(),0);
	}
	
	@Test
	public void findAll() {
		assertEquals(2,sysRepo.count(),0);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void insert() {
		CorpsCeleste e = new Etoile(1000,1000,"Soleil");
		CorpsCeleste p = new Planete(10, 10, 10, 10, 10, 10, "Terre", e);
		CorpsCeleste s = new Satellite(1, 1, 1, 1, 1, 1, "Lune", p);
		sysRepo.save(e);
		sysRepo.save(p);
		sysRepo.save(s);
		assertEquals(e,sysRepo.findById(e.getId()).get());
		assertEquals(p,sysRepo.findById(e.getId()).get());
		assertEquals(s,sysRepo.findById(e.getId()).get());
	}
}