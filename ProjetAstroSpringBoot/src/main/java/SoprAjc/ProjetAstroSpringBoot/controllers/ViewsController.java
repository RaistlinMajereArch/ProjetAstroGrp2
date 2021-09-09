package SoprAjc.ProjetAstroSpringBoot.controllers;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import SoprAjc.ProjetAstroSpringBoot.model.CorpsCeleste;
import SoprAjc.ProjetAstroSpringBoot.model.Etoile;
import SoprAjc.ProjetAstroSpringBoot.model.Planete;
import SoprAjc.ProjetAstroSpringBoot.model.Satellite;
import SoprAjc.ProjetAstroSpringBoot.model.Simulation;
import SoprAjc.ProjetAstroSpringBoot.repositories.PositionsRepository;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeInitRepository;
import SoprAjc.ProjetAstroSpringBoot.repositories.SystemeRepository;

@Controller
public class ViewsController {
	
	@Autowired
	SystemeInitRepository sysIRepo;
	@Autowired
	SystemeRepository sysRepo;
	@Autowired
	PositionsRepository posRepo;
	@Autowired
	Simulation sim;
	
//	@GetMapping("/produit")
//	public String produit(@RequestParam(name = "id", required = true) int id, Model model) {
//		// id=id*2;
//		model.addAttribute("id", id);
//		return "produit";
//	}

	@GetMapping("/connect")
	public String connect(Model model) {
		return "views/connect";
		//return "redirect:/produit
	}
	
	@GetMapping("/views/menu")
	public String menu(Model model) {
		return "views/menu";
		//return "redirect:/produit
	}
	
	@GetMapping("/views/Modification")
	public String modification(Model model) {
		model.addAttribute("systeminit", sysIRepo.findAll());
		return "views/Modification";
		//return "redirect:/produit
	} 
	
	@GetMapping("/views/create")
	public String create(Model model) {
		return "views/create";
		//return "redirect:/produit
	}
	
	@GetMapping("/views/initialisation")
	public String initialisation(Model model) {
		
		return "views/initialisation";
		//return "redirect:/produit
	}
	
	@GetMapping("/views/result")
	public String result(Model model) {
		return "views/result";
		//return "redirect:/produit
	}
	
	@PostMapping("/views/createSystem")
	public String createSystem(Etoile e,Model model) {
		posRepo.deleteAll();
		sysRepo.deleteAll();
		
		sysIRepo.deleteAll();
		
		sysIRepo.resetId();
		
		sysIRepo.save(e);
		model.addAttribute("systeminit", sysIRepo.findAll());
		return "redirect:Modification";
		//return "redirect:/produit
	}
	
	@PostMapping("/views/addPlanet")
	public String addPlanete(Planete p,Model model) {
		p.setParent(sysIRepo.findById(1).get());
		sysIRepo.save(p);
		//model.addAttribute("systeminit", sysIRepo.findAll());
		return "redirect:Modification";
		//return "redirect:/produit
	}
	@PostMapping("/views/addSat")
	public String addSatellite(Satellite s,@RequestParam int id_parent, Model model) {
		s.setParent(sysIRepo.findById(id_parent).get());
		sysIRepo.save(s);
		//sysIRepo.save(s);
		//model.addAttribute("systeminit", sysIRepo.findAll());
		return "redirect:Modification";
		//return "redirect:/produit
	}
	
	@PostMapping("/views/wait")
	public String wait(Model model) {
		return "views/wait";
		//return "redirect:/produit
	}
	@PostMapping("/views/updateCorps")
	@Transactional
	public String updateCorps(CorpsCeleste s) {
		CorpsCeleste c = sysIRepo.getById(s.getId());
		c.setNomInit(s.getNomInit());
		c.setDiametreInit(s.getDiametreInit());
		c.setMasseInit(s.getMasseInit());
		c.setxInit(s.getxInit());
		c.setyInit(s.getyInit());
		c.setVxInit(s.getVxInit());
		c.setVyInit(s.getVyInit());
		sysIRepo.save(c);
		return "redirect:Modification";
	}
	
	@PostMapping("/views/updateEtoile")
	public String updateEtoile(CorpsCeleste s) {
		CorpsCeleste c = sysIRepo.getById(s.getId());
		c.setNomInit(s.getNomInit());
		c.setDiametreInit(s.getDiametreInit());
		c.setMasseInit(s.getMasseInit());
		sysIRepo.save(c);
		return "redirect:Modification";
	}
	
	@PostMapping("/views/deleteCorps")
	public String deleteCorps(@RequestParam int id) {
		if (id==1) {
			throw new RuntimeException("Vous ne pouvez pas supprimer une etoile");
		}
		sysIRepo.deleteById(id);
		return "redirect:Modification";
		}
	
	
	
	
	@PostMapping("/views/lancerSimu")
	public String lancerSimu(@RequestParam int timestep, @RequestParam boolean calc,Model model) throws IOException {
		sim.lancerSimu(timestep, calc);
		//model.addAttribute("systeminit", sysIRepo.findAll());
		return "views/result";
		//return "redirect:/produit
	}
}
