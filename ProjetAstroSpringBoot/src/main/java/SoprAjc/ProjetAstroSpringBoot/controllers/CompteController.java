package SoprAjc.ProjetAstroSpringBoot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompteController {

	@GetMapping("/login")
	public String login() {
		return "connect";
	}
}
