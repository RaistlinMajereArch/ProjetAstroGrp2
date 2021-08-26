package astro.services;

import java.util.List;

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
	
	private void delete(CorpsCeleste c) {
		if (c instanceof Etoile) {
			sysInitRepo.deleteAll();
		}
		else if (c instanceof Planete) {
			List<CorpsCeleste> enfants=sysInitRepo.selectEnfants(c.getId());
			for (CorpsCeleste corps :enfants) {
				sysInitRepo.updateIdParent(corps.getId());
				sysInitRepo.delete(corps);
			}
			sysInitRepo.updateIdParent(c.getId());
			sysInitRepo.delete(c);
		}
		else {
			sysInitRepo.updateIdParent(c.getId());
			sysInitRepo.delete(c);
		}
	}
	
}
