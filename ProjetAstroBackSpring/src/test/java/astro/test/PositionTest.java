package astro.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import astro.metier.Position;
import astro.repositories.PositionsRepository;
import astro.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class PositionTest {
	@Autowired
	private static AnnotationConfigApplicationContext ctx;
	@Autowired
	private PositionsRepository posRepo;

	@Test
	@Rollback(true)
	@Transactional
	public void testInsert() {
		Position posIns = new Position(0,1,0.0,0.0);
		posIns = posRepo.save(posIns);
		assertEquals(posIns,posRepo.findById(posIns.getId()).get());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDelete() {
		Position posIns = new Position(0,1,0.0,0.0);
		posIns = posRepo.save(posIns);
		posRepo.delete(posIns);
		assertFalse(posRepo.findById(posIns.getId()).isPresent());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testFindByIdCorps() {
		Position p = new Position(0,1,0.0,0.0);
		posRepo.save(p);
		assertNotNull(posRepo.findByIdCorpsCeleste(p.getIdCorpsCeleste()));
	}
	@Test
	@Rollback(true)
	@Transactional
	public void testFindByIdTimeStep() {
		Position p = new Position(0,1,0.0,0.0);
		posRepo.save(p);
		assertNotNull(posRepo.findByIdTimeStep(p.getIdTimeStep()));
	}
}

