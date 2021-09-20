package SoprAjc.ProjetAstroSpringBoot.restController;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.Etoile;
import SoprAjc.ProjetAstroSpringBoot.model.Planete;
import SoprAjc.ProjetAstroSpringBoot.model.Satellite;
import SoprAjc.ProjetAstroSpringBoot.model.Simulation;
import SoprAjc.ProjetAstroSpringBoot.repositories.PositionsRepository;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeInitRepository;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeRepository;

@RestController
@RequestMapping("/api/views")
@CrossOrigin(origins = "*")
public class ViewsRestController {
	
	@Autowired
	SystemeInitRepository sysIRepo;
	@Autowired
	SystemeRepository sysRepo;
	@Autowired
	PositionsRepository posRepo;
	@Autowired
	Simulation sim;
	
	@PostMapping("/createSystem")
	public void createSystem(Etoile e) {
		posRepo.deleteAll();
		sysRepo.deleteAll();
		
		sysIRepo.deleteAll();
		
		sysIRepo.resetId();
		
		sysIRepo.save(e);
	}
	
	@PostMapping("/addPlanet")
	public void addPlanete(Planete p) {
		p.setParent(sysIRepo.findById(1).get());
		sysIRepo.save(p);
	}
	@PostMapping("/addSat")
	public void addSatellite(Satellite s,@RequestParam int id_parent) {
		s.setParent(sysIRepo.findById(id_parent).get());
		sysIRepo.save(s);
	}
	
	@PostMapping("/wait")
	public String wait(Model model) {
		return "wait";
	}
	
	@PostMapping("/updateCorps")
	@Transactional
	public void updateCorps(CorpsCeleste s) {
		CorpsCeleste c = sysIRepo.getById(s.getId());
		c.setNomInit(s.getNomInit());
		c.setDiametreInit(s.getDiametreInit());
		c.setMasseInit(s.getMasseInit());
		c.setxInit(s.getxInit());
		c.setyInit(s.getyInit());
		c.setVxInit(s.getVxInit());
		c.setVyInit(s.getVyInit());
		sysIRepo.save(c);
	}
	
	@PostMapping("/updateEtoile")
	public void updateEtoile(CorpsCeleste s) {
		CorpsCeleste c = sysIRepo.getById(s.getId());
		c.setNomInit(s.getNomInit());
		c.setDiametreInit(s.getDiametreInit());
		c.setMasseInit(s.getMasseInit());
		sysIRepo.save(c);
	}
	
	@PostMapping("/deleteCorps")
	public void deleteCorps(@RequestParam int id) {
		if (id==1) {
			throw new RuntimeException("Vous ne pouvez pas supprimer une etoile");
		}
		sysIRepo.deleteById(id);
		}
	
	
	
	
	@PostMapping("/lancerSimu")
	public void lancerSimu(@RequestParam int timestep, @RequestParam boolean calc) throws IOException {
		System.out.println("entree dans simu");
		sim.lancerSimu(timestep, calc);
	}
}
