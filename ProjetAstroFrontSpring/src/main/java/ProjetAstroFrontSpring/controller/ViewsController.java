package ProjetAstroFrontSpring.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import astro.metier.Etoile;
import astro.metier.Planete;
import astro.metier.Simulation;
import astro.repositories.PositionsRepository;
import astro.repositories.SystemeInitRepository;
import astro.repositories.SystemeRepository;

@Controller
@RequestMapping("")
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

	@GetMapping("/views/connect")
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
	public String wait(Model model) {
		
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
	
	@PostMapping("/views/updatePlan")
	public String updatePlan(Planete p, @RequestParam int id_parent,Model model) {
		p.setParent(sysIRepo.findById(id_parent).get());
		sysIRepo.save(p);
		//model.addAttribute("systeminit", sysIRepo.findAll());
		return "redirect:Modification";
		//return "redirect:/produit
	}
	
	@PostMapping("/views/lancerSimu")
	public String updatePlan(@RequestParam int timestep, @RequestParam boolean calc,Model model) throws IOException {
		
		sim.lancerSimu(timestep, calc);
		//model.addAttribute("systeminit", sysIRepo.findAll());
		return "redirect:menu";
		//return "redirect:/produit
	}
}
