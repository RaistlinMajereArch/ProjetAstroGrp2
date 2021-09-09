package SoprAjc.ProjetAstroSpringBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.Etoile;
import SoprAjc.ProjetAstroSpringBoot.model.Planete;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeInitRepository;

@Service
public class SystemeInitService {
	@Autowired
	private SystemeInitRepository sysInitRepo;
	
	public void delete(CorpsCeleste c) {
		if (c instanceof Etoile) {
			sysInitRepo.deleteAll();
		}
		else if (c instanceof Planete) {
			sysInitRepo.deleteEnfants(c);
			sysInitRepo.delete(c);
		}
		else {
			sysInitRepo.delete(c);
		}
	}
	
}
