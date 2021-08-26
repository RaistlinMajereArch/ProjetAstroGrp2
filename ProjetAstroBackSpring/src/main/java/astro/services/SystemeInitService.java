package astro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import astro.metier.CorpsCeleste;
import astro.metier.Etoile;
import astro.metier.Planete;
import astro.repositories.SystemeInitRepository;

@Service
public class SystemeInitService {
	@Autowired
	private SystemeInitRepository sysInitRepo;
	
	public void delete(CorpsCeleste c) {
		if (c instanceof Etoile) {
			System.out.println("delete etoile");
			sysInitRepo.deleteAll();
		}
		else if (c instanceof Planete) {
			System.out.println("delete planete");
			List<CorpsCeleste> enfants=sysInitRepo.selectEnfants(c);
			for (CorpsCeleste corps :enfants) {
				System.out.println(corps);
				sysInitRepo.updateIdParent(corps.getId());
				Optional<CorpsCeleste> opt = sysInitRepo.findById(corps.getId());
				corps =  opt.get();
				System.out.println(corps);
				sysInitRepo.delete(corps);
			}
			sysInitRepo.updateIdParent(c.getId());
			sysInitRepo.delete(c);
		}
		else {
			System.out.println("delete satellite");
			sysInitRepo.updateIdParent(c.getId());
			sysInitRepo.delete(c);
		}
	}
	
}
