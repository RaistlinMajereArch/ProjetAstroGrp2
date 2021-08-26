package astro.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import astro.metier.CorpsCeleste;
import astro.metier.Etoile;
import astro.metier.Planete;
import astro.metier.Satellite;
import astro.repositories.SystemeInitRepository;
import astro.services.SystemeInitService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SystemeInitTest {

	@Autowired
	private SystemeInitRepository sysInitRepo;
	
	@Autowired
	private SystemeInitService sysInitService;
	
	@Test
	public void sysInitRepoExist() {
		assertNotNull(sysInitRepo);
	}
	
	@Test
	public void findById() {
		Optional<CorpsCeleste> opt=sysInitRepo.findById(1);
		CorpsCeleste c=opt.get();
		Etoile e = new Etoile(1000,1000,"Soleil");
		e.setId(1);
		assertEquals(e, c);
	}
	
	@Test
	public void findAll() {
		assertEquals(3,sysInitRepo.count(),0);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void insert() {
		Etoile e = new Etoile(1000,1000,"Soleil");
		e = sysInitRepo.save(e);
		assertEquals(e,sysInitRepo.findById(e.getId()).get());
		Planete p = new Planete(10, 10, 10, 10, 10, 10, "Terre", e);
		p= sysInitRepo.save(p);
		assertEquals(p,sysInitRepo.findById(p.getId()).get());
		Satellite s = new Satellite(1, 1, 1, 1, 1, 1, "Lune", p);
		s= sysInitRepo.save(s);
		assertEquals(s,sysInitRepo.findById(s.getId()).get());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void update() {
		Optional<CorpsCeleste> opt = sysInitRepo.findById(1);
		CorpsCeleste c = opt.get();
		c.setDiametre(1000.0);
		c = sysInitRepo.save(c);
		assertEquals(c,sysInitRepo.findById(c.getId()).get());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void delete() {
		Optional<CorpsCeleste> opt = sysInitRepo.findById(2);
		Planete c = (Planete) opt.get();
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		System.out.println(c instanceof Planete);
		sysInitService.delete(c);
		opt = sysInitRepo.findById(2);
		assertTrue(opt.isEmpty());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void deleteAll() {
		sysInitRepo.deleteAll();
		List<CorpsCeleste> opt= sysInitRepo.findAll();
		assertTrue(opt.isEmpty());
	}
	

}
