package SoprAjc.ProjetAstroSpringBoot.services;

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
