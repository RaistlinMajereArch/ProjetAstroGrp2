package astro.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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
import util.Context;

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
		assertEquals(6.6743E-20,c.getG(),0);
		assertEquals(1000,c.getDiametre(),0);
		assertEquals(true,c.isEtat());
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
		assertEquals(3,sysRepo.count(),0);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void insert() {
		Etoile e = new Etoile(1000,1000,"Soleil");
		e = sysRepo.save(e);
		assertEquals(e,sysRepo.findById(e.getId()).get());
		Planete p = new Planete(10, 10, 10, 10, 10, 10, "Terre", e);
		p= sysRepo.save(p);
		assertEquals(p,sysRepo.findById(p.getId()).get());
		Satellite s = new Satellite(1, 1, 1, 1, 1, 1, "Lune", p);
		s= sysRepo.save(s);
		assertEquals(s,sysRepo.findById(s.getId()).get());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void update() {
		Optional<CorpsCeleste> opt = sysRepo.findById(1);
		CorpsCeleste c = opt.get();
		c.setDiametre(1000.0);
		c = sysRepo.save(c);
		assertEquals(c,sysRepo.findById(c.getId()).get());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void delete() {
		Optional<CorpsCeleste> opt = sysRepo.findById(1);
		CorpsCeleste c = opt.get();
		sysRepo.delete(c);
		opt = sysRepo.findById(1);
		assertTrue(opt.isEmpty());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void deleteAll() {
		sysRepo.deleteAll();
		List<CorpsCeleste> opt= sysRepo.findAll();
		assertTrue(opt.isEmpty());
	}
	
}