package SoprAjc.ProjetAstroSpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

import astro.repositories.SystemeInitRepository;
import astro.metier.CorpsCeleste;

@Controller
@RequestMapping("/systeme")
public class CorpsCelesteController {


	@Autowired
	private SystemeInitRepository siRepo;
	
	@GetMapping("")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("/system/Modification");
		modelAndView.addObject("systeme", siRepo.findAll());
		return modelAndView;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Integer id) {
		siRepo.deleteById(id);
		return new ModelAndView("redirect:/systeme");
	}

	@GetMapping("/add")
	public ModelAndView add() {
		return goEdit(new CorpsCeleste());
	}
		
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Integer id) {
		return goEdit(siRepo.findById(id).get());
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid @ModelAttribute CorpsCeleste cc, BindingResult br) {
		if (br.hasErrors()) {
			return goEdit(cc);
		}
		siRepo.save(cc);
		return new ModelAndView("redirect:/systeme");
	}
	
	private ModelAndView goEdit(CorpsCeleste cc) {
		ModelAndView modelAndView = new ModelAndView("systeme/edit");
		modelAndView.addObject("CorpsCeleste", cc);
		return modelAndView;
	}
}
