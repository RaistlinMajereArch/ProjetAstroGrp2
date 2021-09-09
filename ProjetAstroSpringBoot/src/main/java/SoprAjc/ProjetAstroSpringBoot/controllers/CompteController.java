package SoprAjc.ProjetAstroSpringBoot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import SoprAjc.ProjetAstroSpringBoot.model.Compte;
import SoprAjc.ProjetAstroSpringBoot.model.Role;
import SoprAjc.ProjetAstroSpringBoot.repositories.CompteRepository;

@Controller
public class CompteController {

	@Autowired
	private CompteRepository compteRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping({ "/inscription" })
	public ModelAndView add() {
		Compte c = new Compte();
		return goEdit(c);
	}
	
	private ModelAndView goEdit(Compte compte) {
		ModelAndView modelAndView = new ModelAndView("newCompte");
		modelAndView.addObject("compte", compte);
		return modelAndView;
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid @ModelAttribute Compte compte, BindingResult br) {
		System.out.println(compte.toString());
		if (br.hasErrors()) {
			return goEdit(compte);
		}
		compte.setRole(Role.ROLE_USER);
		compte.setPassword(passwordEncoder.encode(compte.getPassword()));
		compteRepo.save(compte);
		return new ModelAndView("redirect:/connect");
	}
}
